package com.softserve.sprint16.controller;

import com.softserve.sprint16.dto.UserDto;
import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.entity.User;
import com.softserve.sprint16.exception.CreateException;
import com.softserve.sprint16.exception.EntityNotFoundException;
import com.softserve.sprint16.mapper.UserDtoMapper;
import com.softserve.sprint16.service.MarathonService;
import com.softserve.sprint16.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Data
public class StudentController {

    private UserService userService;
    private MarathonService marathonService;
    private UserDtoMapper mapper;

    @Autowired
    public StudentController(UserService userService,
                             MarathonService marathonService,
                             UserDtoMapper mapper) {
        this.userService = userService;
        this.marathonService = marathonService;
        this.mapper = mapper;
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        List<User> students = userService.getAllByRole("TRAINEE");
        model.addAttribute("students", students);
        model.addAttribute("add", false);
        return "students";
    }

    @GetMapping("/students/{marathon_id}")
    public String studentsFromMarathon(@PathVariable("marathon_id") Long id,Model model) {
            List<User> students = userService.getAllByMarathon(id);
            if(students.size() != 0) {
                model.addAttribute("students", students);
                model.addAttribute("marathon_id", id);
                model.addAttribute("add", false);
                return "students";
            }
            throw new EntityNotFoundException();
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/register")
    public String registerStudentForm() {
        return "register";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @PostMapping("/students/register")
    public String registerStudent(@ModelAttribute("user") UserDto user) {
        try {
            mapper.toDto(userService.createOrUpdateUser(mapper.toEntity(user)));
        }catch (Exception e){
            String errorMessage = e.getMessage();
            throw new CreateException(errorMessage.equals(null) ? "Incorrectly set parameters!" : errorMessage);
        }
        return "redirect:/students";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String deleteStudentByIdFromMarathon(@PathVariable("marathon_id") Long marathon_id,
                                 @PathVariable("student_id") Long student_id) {
        userService.deleteUserByIdFromMarathon(student_id,marathon_id);
        return "redirect:/students";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/delete/{student_id}")
    public String deleteStudents(@PathVariable("student_id") Long student_id) {
        userService.deleteUserById(student_id);
        return "redirect:/students";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", userService.getUserById(id));
        return "editStudent";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @PostMapping("/students/edit")
    public String editStudent(@ModelAttribute("student") User user) {
        userService.createOrUpdateUser(user);
        return "redirect:/students";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/{marathon_id}/add/{student_id}")
    public String addStudentToMarathon(@PathVariable("marathon_id") Long marathon_id,
                               @PathVariable("student_id") Long student_id) {
        User student = userService.getUserById(student_id);
        Marathon marathon = marathonService.getMarathonById(marathon_id);
        userService.addUserToMarathon(student, marathon);
        return "redirect:/marathons";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/{marathon_id}/add")
    public String findStudentForAdd(@PathVariable("marathon_id") Long marathon_id, Model model) {
        Marathon marathon = marathonService.getMarathonById(marathon_id);
        List<User> students = userService.getAll().stream()
                .filter(o->!o.getMarathons()
                        .contains(marathon))
                .collect(Collectors.toList());
        model.addAttribute("students",students);
        model.addAttribute("marathon_id", marathon_id);
        model.addAttribute("add", true);
        return "students";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/{student_id}/addMarathon")
    public String findMarathonForAdd(@PathVariable("student_id") Long student_id, Model model) {
        User student = userService.getUserById(student_id);
        List<Marathon> marathons = marathonService.getAll().stream().filter(o->!o.getUsers().contains(student)).collect(Collectors.toList());
        model.addAttribute("marathons", marathons);
        model.addAttribute("add", true);
        return "marathons";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/students/{student_id}/addMarathon/{marathon_id}")
    public String addMarathon(@PathVariable("marathon_id") Long marathon_id,
                              @PathVariable("student_id") Long student_id, Model model) {
        User student = userService.getUserById(student_id);
        Marathon marathon = marathonService.getMarathonById(marathon_id);
        userService.addUserToMarathon(student, marathon);
        model.addAttribute("add", false);
        return "redirect:/students";
    }

}

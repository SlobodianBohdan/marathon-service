package com.softserve.sprint16.controller;


import com.softserve.sprint16.dto.MarathonDto;
import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.entity.User;
import com.softserve.sprint16.exception.CreateException;
import com.softserve.sprint16.mapper.MarathonDtoMapper;
import com.softserve.sprint16.service.MarathonService;
import com.softserve.sprint16.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MarathonController {

    private MarathonService marathonService;
    private UserService userService;
    private MarathonDtoMapper mapper;

    @Autowired
    public MarathonController(MarathonService marathonService,
                              UserService userService,
                              MarathonDtoMapper mapper) {
        this.marathonService = marathonService;
        this.userService = userService;
        this.mapper = mapper;
    }


    @GetMapping({"/", "/marathons"})
    public String getAllMarathons(Model model) {
        List<Marathon> marathons = marathonService.getAll();
        model.addAttribute("marathons", marathons);
        model.addAttribute("add", false);

        return "marathons";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/marathons/edit/{id}")
    public String editMarathonForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("marathon", marathonService.getMarathonById(id));
        return "editMarathon";
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/marathons/create")
    public String createMarathonForm(Model model) {
        return "create";
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/marathons/edit")
    public String editMarathon(@ModelAttribute("marathon") Marathon marathon) {
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }


    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/marathons/create")
    public String createMarathons(@ModelAttribute("marathon") MarathonDto marathon) {
        try {
            mapper.toDto(marathonService.createOrUpdate(mapper.toEntity(marathon)));
        }catch (Exception e){
            throw new CreateException("Incorrectly set parameters!");
        }
        return "redirect:/marathons";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/marathons/delete/{id}")
    public String deleteMarathons(@PathVariable("id") Long id) {
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }


    @GetMapping("/marathons/{marathon_id}/students")
    public String getListStudentsByMarathon(@PathVariable("marathon_id") Long marathon_id, Model model) {
        List<User> students = userService.getAllByMarathon(marathon_id);
        model.addAttribute("students", students);
        model.addAttribute("add", false);
        return "students";
    }
}

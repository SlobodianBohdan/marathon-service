package com.softserve.sprint16;

import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.entity.User;
import com.softserve.sprint16.exception.EntityNotFoundException;
import com.softserve.sprint16.repository.*;
import com.softserve.sprint16.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserServiceTest extends BaseDaoTest {

    MarathonService marathonService;
    ProgressService progressService;
    UserService userService;
    TaskService taskService;
    SprintService sprintService;
    MarathonRepository marathonRepository;
    ProgressRepository progressRepository;
    UserRepository userRepository;
    TaskRepository taskRepository;
    SprintRepository sprintRepository;


    private static User createdUser1;
    private static User createdUser2;
    private static User createdUser3;
    private static Marathon createdMarathon1;

    @Autowired
    public UserServiceTest(MarathonService marathonService,
                           ProgressService progressService,
                           UserService userService,
                           TaskService taskService,
                           SprintService sprintService,
                           MarathonRepository marathonRepository,
                           ProgressRepository progressRepository,
                           UserRepository userRepository,
                           TaskRepository taskRepository,
                           SprintRepository sprintRepository) {
        this.marathonService = marathonService;
        this.progressService = progressService;
        this.userService = userService;
        this.taskService = taskService;
        this.sprintService = sprintService;
        this.marathonRepository = marathonRepository;
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
    }


    @BeforeEach
    public void fillDataService() {

        //1. Create Marathon
        createdMarathon1 = Marathon.builder().title("Java Marathon_1").build();
        marathonRepository.save(createdMarathon1);


        //2. Create users
        createdUser1 = User.builder()
                .firstName("Lida")
                .lastName("Black")
                .email("black@gmail.com")
                .password("password1")
                .role(User.Role.TRAINEE)
                .build();
        createdUser2 = User.builder()
                .firstName("Nazar")
                .lastName("Red")
                .email("red@gmail.com")
                .password("password2")
                .role(User.Role.TRAINEE)
                .build();
        createdUser3 = User.builder()
                .firstName("Ivan")
                .lastName("Blue")
                .email("blue@gmail.com")
                .password("password3")
                .role(User.Role.MENTOR)
                .build();

        userRepository.save(createdUser1);
        userRepository.save(createdUser2);
        userRepository.save(createdUser3);

        //3 Add Users to Marathon
        userService.addUserToMarathon(createdUser1, createdMarathon1);
        userService.addUserToMarathon(createdUser2, createdMarathon1);
        userService.addUserToMarathon(createdUser3, createdMarathon1);


    }

    @AfterEach
    public void clearData() {
        clearTables("marathon", "users");
    }


    @Test
    public void checkAllUsers() {
        List<User> expected = Arrays.asList(createdUser1, createdUser2, createdUser3);
        List<User> actual = userService.getAll();

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkUserById() {
        List<User> expected = Arrays.asList(createdUser1);
        List<User> actual = Arrays.asList(userService.getUserById(createdUser1.getId()));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkDeleteUserByIdFromMarathon() {
        List<User> expected = Arrays.asList();
        userService.deleteUserByIdFromMarathon(createdUser1.getId(), createdMarathon1.getId());
        userService.deleteUserByIdFromMarathon(createdUser2.getId(), createdMarathon1.getId());
        userService.deleteUserByIdFromMarathon(createdUser3.getId(), createdMarathon1.getId());

        List<User> actual = userService.getAllByMarathon(createdMarathon1.getId());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkDeleteUserById() {
        userService.deleteUserById(createdUser1.getId());
        userService.deleteUserById(createdUser2.getId());
        userService.deleteUserById(createdUser3.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(createdUser1.getId()));
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(createdUser2.getId()));
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(createdUser3.getId()));
    }

    @Test
    public void checkAllByMarathon() {
        List<User> expected = Arrays.asList(createdUser1, createdUser2, createdUser3);
        List<User> actual = userService.getAllByMarathon(createdMarathon1.getId());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkCreateOrUpdateUser() {
        List<User> expected = Arrays.asList(createdUser1, createdUser2, createdUser3);
        List<User> actual = Arrays.asList(userService.createOrUpdateUser(createdUser1),
                userService.createOrUpdateUser(createdUser2),
                userService.createOrUpdateUser(createdUser3));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAllByRole() {
        List<User> expected = Arrays.asList(createdUser1, createdUser2);
        List<User> actual = userService.getAllByRole("TRAINEE");

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAddUserToMarathon() {
        List<User> expected = Arrays.asList(createdUser1, createdUser2, createdUser3);
        List<User> actual = userService.getAllByMarathon(createdMarathon1.getId());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }
}

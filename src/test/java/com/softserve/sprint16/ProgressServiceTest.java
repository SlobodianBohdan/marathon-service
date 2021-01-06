package com.softserve.sprint16;

import com.softserve.sprint16.entity.*;
import com.softserve.sprint16.repository.ProgressRepository;
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
public class ProgressServiceTest extends BaseDaoTest{

    private ProgressService progressService;
    private ProgressRepository progressRepository;
    private TaskService taskService;
    private UserService userService;
    private MarathonService marathonService;
    private SprintService sprintService;

    private static Progress createdProgress;
    private static Marathon createdMarathon;
    private static Sprint createdSprint;
    private static Task createdTask;
    private static User createdUser;

    @Autowired
    public ProgressServiceTest(ProgressService progressService,
                               ProgressRepository progressRepository,
                               TaskService taskService,
                               UserService userService,
                               MarathonService marathonService,
                               SprintService sprintService) {
        this.progressService = progressService;
        this.progressRepository = progressRepository;
        this.taskService = taskService;
        this.userService = userService;
        this.marathonService = marathonService;
        this.sprintService = sprintService;
    }

    @BeforeEach
    public void fillDataService() {

        //1. Create Marathon
        createdMarathon = Marathon.builder().title("Java Marathon_1").build();
        marathonService.createOrUpdate(createdMarathon);

        //2. Create Task
        createdTask = Task.builder().title("task1").build();
        taskService.create(createdTask);

        //3. Create User
        createdUser = User.builder()
                        .firstName("Lida")
                        .lastName("Black")
                        .email("black@gmail.com")
                        .password("password1")
                        .role(User.Role.TRAINEE)
                        .build();
        userService.createOrUpdateUser(createdUser);

        //4. Create Sprint
        createdSprint = Sprint.builder()
                .title("Sprint 1")
                .build();
        sprintService.create(createdSprint);

        //5. Create Progress
        createdProgress = progressService.addTaskForStudent(createdTask, createdUser);
        progressRepository.save(createdProgress);


        sprintService.addSprintToMarathon(createdSprint, createdMarathon);

        userService.addUserToMarathon(createdUser, createdMarathon);

        taskService.addTaskToSprint(createdTask, createdSprint);


    }

    @AfterEach
    public void clearData() {
        clearTables("progress", "task", "users", "marathon", "sprint");
    }


    @Test
    public void checkProgressById() {
        List<Progress> expected = Arrays.asList(createdProgress);
        List<Progress> actual = Arrays.asList(progressService.getProgressById(createdProgress.getId()));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAddTaskForStudent() {
        createdProgress.setId(1L);
        List<Progress> expected = Arrays.asList(createdProgress);
        Progress actualProgress = progressService.addTaskForStudent(createdTask, createdUser);
        actualProgress.setId(1L);
        List<Progress> actual = Arrays.asList(actualProgress);

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAddOrUpdateProgress() {
        List<Progress> expected = Arrays.asList(createdProgress);
        List<Progress> actual = Arrays.asList(progressService.addOrUpdateProgress(createdProgress));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkSetStatus() {
        Progress.TaskStatus expected = Progress.TaskStatus.PASS;
        progressService.setStatus(Progress.TaskStatus.PASS, createdProgress);
        Progress.TaskStatus actual = createdProgress.getStatus();

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAllProgressByUserIdAndMarathonId() {
        List<Progress> expected = Arrays.asList(createdProgress);
        List<Progress> actual = progressService.allProgressByUserIdAndMarathonId(createdUser.getId(), createdMarathon.getId());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAllProgressByUserIdAndSprintId() {
        List<Progress> expected = Arrays.asList(createdProgress);
        List<Progress> actual = progressService.allProgressByUserIdAndSprintId(createdUser.getId(), createdSprint.getId());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }
}

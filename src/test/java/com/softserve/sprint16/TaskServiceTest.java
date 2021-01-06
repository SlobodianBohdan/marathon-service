package com.softserve.sprint16;

import com.softserve.sprint16.entity.Sprint;
import com.softserve.sprint16.entity.Task;
import com.softserve.sprint16.repository.TaskRepository;
import com.softserve.sprint16.service.SprintService;
import com.softserve.sprint16.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TaskServiceTest extends BaseDaoTest{

    private TaskService taskService;
    private TaskRepository taskRepository;
    private SprintService sprintService;

    private static Task createdTask1;
    private static Task createdTask2;
    private static Sprint createdSprint;

    @Autowired
    public TaskServiceTest(TaskService taskService, TaskRepository taskRepository, SprintService sprintService) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.sprintService = sprintService;
    }

    @BeforeEach
    public void fillDataService() {

        createdTask1 = Task.builder().title("task1").build();

        taskRepository.save(createdTask1);

        createdSprint = Sprint.builder()
                .title("Sprint 1")
                .build();

        sprintService.create(createdSprint);

    }

    @AfterEach
    public void clearData() {
        clearTables("task");
    }


    @Test
    public void checkAddTaskToSprint() {
        List<Task> expected = Arrays.asList(createdTask1);
        List<Task> actual = Arrays.asList(taskService.addTaskToSprint(createdTask1, createdSprint));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkTaskById() {
        List<Task> expected = Arrays.asList(createdTask1);
        List<Task> actual = Arrays.asList(taskService.getTaskById(createdTask1.getId()));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkCreate() {
        List<Task> expected = Arrays.asList(createdTask1);
        List<Task> actual = Arrays.asList(taskService.create(createdTask1));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }
}

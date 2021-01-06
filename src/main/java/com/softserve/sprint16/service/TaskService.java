package com.softserve.sprint16.service;

import com.softserve.sprint16.entity.Sprint;
import com.softserve.sprint16.entity.Task;

public interface TaskService {

    Task addTaskToSprint(Task task, Sprint sprint);
    Task getTaskById(Long idTask);

    Task create(Task task);
}

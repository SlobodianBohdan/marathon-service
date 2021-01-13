package com.softserve.marathon.service;

import com.softserve.marathon.entity.Sprint;
import com.softserve.marathon.entity.Task;

public interface TaskService {

    Task addTaskToSprint(Task task, Sprint sprint);
    Task getTaskById(Long idTask);

    Task create(Task task);
}

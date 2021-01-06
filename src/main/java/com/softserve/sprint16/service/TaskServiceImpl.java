package com.softserve.sprint16.service;

import com.softserve.sprint16.entity.Sprint;
import com.softserve.sprint16.entity.Task;
import com.softserve.sprint16.exception.EntityNotFoundException;
import com.softserve.sprint16.repository.SprintRepository;
import com.softserve.sprint16.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private SprintRepository sprintRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, SprintRepository sprintRepository) {
        this.taskRepository = taskRepository;
        this.sprintRepository = sprintRepository;
    }

    @Override
    public Task addTaskToSprint(Task task, Sprint sprint) {
        findByIdOrThrowException(sprintRepository, sprint.getId());
        task.setSprint(sprint);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return findByIdOrThrowException(taskRepository, taskId);
    }

    private <T> T findByIdOrThrowException(JpaRepository<T, Long> repository, Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity is not found!"));
    }
    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }
}

package com.softserve.marathon.service;

import com.softserve.marathon.entity.Progress;
import com.softserve.marathon.entity.TaskStatus;
import com.softserve.marathon.entity.Task;
import com.softserve.marathon.entity.User;
import com.softserve.marathon.exception.EntityNotFoundException;
import com.softserve.marathon.repository.ProgressRepository;
import com.softserve.marathon.repository.TaskRepository;
import com.softserve.marathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService{

    private ProgressRepository progressRepository;

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository,
                               TaskRepository taskRepository,
                               UserRepository userRepository) {
        this.progressRepository = progressRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Progress getProgressById(Long idProgress) {
        return findByIdOrThrowException(progressRepository, idProgress);
//        return progressRepository.findById(id).orElseThrow(() ->
//        new EntityNotFoundException(("No Progress exist for given id")));
    }

    @Override
    public Progress addTaskForStudent(Task task, User user) {
        findByIdOrThrowException(userRepository, user.getId());
        Task createdTask = taskRepository.save(task);

        Progress progress = Progress.builder()
                .status(TaskStatus.PENDING)
                .task(createdTask)
                .trainee(user)
                .build();

        return progressRepository.save(progress);

    }

    @Override
    public Progress addOrUpdateProgress(Progress progress) {
        if (progress.getId() != null){
            findByIdOrThrowException(progressRepository, progress.getId());
            return progressRepository.save(progress);
        }
        return progressRepository.save(progress);
    }

    @Override
    public boolean setStatus(TaskStatus taskStatus, Progress progress) {
        try {
            findByIdOrThrowException(progressRepository, progress.getId());
            progress.setStatus(taskStatus);
            progressRepository.save(progress);
            return true;
        }catch (EntityNotFoundException e){
            return false;
        }
    }

    @Override
    public List<Progress> allProgressByUserIdAndMarathonId(Long idUser, Long idMarathon) {
        return progressRepository.findProgressByTraineeIdAndMarathonId(idUser, idMarathon);
    }

    @Override
    public List<Progress> allProgressByUserIdAndSprintId(Long idUser, Long idSprint) {
        return progressRepository.findProgressByUserIdAndSprintId(idUser, idSprint);
    }

    private <T>  T findByIdOrThrowException(JpaRepository<T, Long> repository, Long id){
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity is not found!"));
    }
}

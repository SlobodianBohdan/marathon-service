package com.softserve.sprint16.service;

import com.softserve.sprint16.entity.Progress;
import com.softserve.sprint16.entity.Task;
import com.softserve.sprint16.entity.Progress.TaskStatus;
import com.softserve.sprint16.entity.User;


import java.util.List;

public interface ProgressService {

    Progress getProgressById(Long idProgress);

    Progress addTaskForStudent(Task task, User user);

    Progress addOrUpdateProgress(Progress progress);

    boolean setStatus(TaskStatus taskStatus, Progress progress);

    List<Progress> allProgressByUserIdAndMarathonId(Long idUser, Long idMarathon);

    List<Progress> allProgressByUserIdAndSprintId(Long idUser, Long idSprint);

}

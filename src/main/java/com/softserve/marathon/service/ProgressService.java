package com.softserve.marathon.service;

import com.softserve.marathon.entity.Progress;
import com.softserve.marathon.entity.Task;
import com.softserve.marathon.entity.TaskStatus;
import com.softserve.marathon.entity.User;


import java.util.List;

public interface ProgressService {

    Progress getProgressById(Long idProgress);

    Progress addTaskForStudent(Task task, User user);

    Progress addOrUpdateProgress(Progress progress);

    boolean setStatus(TaskStatus taskStatus, Progress progress);

    List<Progress> allProgressByUserIdAndMarathonId(Long idUser, Long idMarathon);

    List<Progress> allProgressByUserIdAndSprintId(Long idUser, Long idSprint);

}

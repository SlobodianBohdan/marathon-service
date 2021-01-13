package com.softserve.marathon.dto;

import com.softserve.marathon.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Data;

import com.softserve.marathon.entity.Task;
import com.softserve.marathon.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProgressDto {

    private Long id;

    @NotNull(message = "Started type should be specified")
    private LocalDate started;

    @NotNull(message = "Status type should be specified")
    private TaskStatus status;

    @NotNull(message = "Updated type should be specified")
    private LocalDate updated;

    private Task task;
    private User trainee;

}

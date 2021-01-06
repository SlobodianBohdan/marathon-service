package com.softserve.sprint16.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TaskDto {

    private Long id;

    @NotBlank(message = "Created cannot be empty")
    private LocalDate created;

    @NotBlank(message = "Title task cannot be empty")
    private String title;

    @NotBlank(message = "Updated cannot be empty")
    private LocalDate updated;
}

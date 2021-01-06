package com.softserve.sprint16.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SprintDto {

    private Long id;

    @NotNull(message = "Finish type should be specified")
    private LocalDate finish;

    @NotNull(message = "Start date type should be specified")
    private LocalDate startDate;

    @NotBlank(message = "Title sprint cannot be empty")
    private String title;

}

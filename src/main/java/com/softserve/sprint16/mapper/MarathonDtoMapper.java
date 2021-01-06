package com.softserve.sprint16.mapper;

import com.softserve.sprint16.dto.MarathonDto;
import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.exception.CreateException;
import org.springframework.stereotype.Component;

@Component
public class MarathonDtoMapper extends MarathonDto{

    public MarathonDto toDto(Marathon marathon) {
        return MarathonDto.builder()
                .id(marathon.getId())
                .title(marathon.getTitle())
                .build();
    }

    public Marathon toEntity(MarathonDto marathon) {
        return Marathon.builder()
                .id(marathon.getId())
                .title(validateTitle(marathon.getTitle()))
                .build();
    }

    private String validateTitle(String title){
        if (title.isEmpty()){
            throw new CreateException("Wrong role!");
        }
        return title;
    }
}

package com.softserve.marathon.mapper;

import com.softserve.marathon.dto.MarathonDto;
import com.softserve.marathon.entity.Marathon;
import com.softserve.marathon.exception.CreateException;
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

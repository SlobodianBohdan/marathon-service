package com.softserve.sprint16.mapper;

import com.softserve.sprint16.dto.UserDto;
import com.softserve.sprint16.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper extends UserDto {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .password(user.getPassword())
                .build();
    }

    public User toEntity(UserDto user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(User.Role.valueOf(user.getRole()))
                .password(user.getPassword())
                .build();
    }

//    private User.Role validateRole(String role){
//        try{
//            return User.Role.valueOf(role);
//        }catch (IllegalArgumentException e){
//            throw new CreateException("Wrong role!");
//        }
//    }

}

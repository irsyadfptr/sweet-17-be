package com.sweet17.qrgenerator;


import com.sweet17.qrgenerator.dto.UserDto;
import com.sweet17.qrgenerator.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);

    @Mapping(target = "point", constant = "0")
    User toUserEntity(UserDto userDto);
}

package com.sweet17.qrgenerator.service;


import com.sweet17.qrgenerator.ResourceNotFoundException;
import com.sweet17.qrgenerator.UserMapper;
import com.sweet17.qrgenerator.UserRepository;
import com.sweet17.qrgenerator.dto.ResponseDto;
import com.sweet17.qrgenerator.dto.UserDto;
import com.sweet17.qrgenerator.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userMapper.toUserEntity(userDto);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDto(users);
    }

//    public Page<StudentDto> findStudentsWithPagination(Pageable pageable){
//        Page<StudentDto> students = studentRepository.findAll(pageable).map(studentMapper::toStudentDto);
//        return students;
//    }

    public UserDto get(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + userId + " not found."));
        return userMapper.toUserDto(user);
    }

    public ResponseDto update(Long userId, Integer point) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + userId + " not found."));
        ResponseDto responseDto = new ResponseDto();
        String message = "request success. but, nothing changed";
        if (point != null) {
            user.setPoint(point + user.getPoint());
            userRepository.save(user);
            message = "Selamat anda mendapatkan " + point + "total point anda sekarang " + user.getPoint();
        }
        responseDto.setStatus(HttpStatus.ACCEPTED.value());
        responseDto.setMessage(message);
        return responseDto;
    }

}
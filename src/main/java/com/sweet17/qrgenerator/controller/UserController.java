package com.sweet17.qrgenerator.controller;


import com.sweet17.qrgenerator.dto.ResponseDto;
import com.sweet17.qrgenerator.service.UserService;
import com.sweet17.qrgenerator.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.getAll();
        return ResponseEntity.created(URI.create("/")).body(users);
    }


//    @GetMapping("/paging")
//    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
//    private ResponseEntity<Page<StudentDto>> studentPagination(Pageable pageable) {
//        Page<StudentDto> students = studentService.findStudentsWithPagination(pageable);
//        return ResponseEntity.created(URI.create("/")).body(students);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable(value = "id") Long id) {
        UserDto userDto = userService.get(id);
        return ResponseEntity.created(URI.create("/" + id)).body(userDto);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto responseData = userService.create(userDto);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updatePoint(@PathVariable(value = "id") Long id,
                                              @RequestParam (required = false) Integer point){
        ResponseDto responseDto = userService.update(id, point);
        return ResponseEntity.created(URI.create("/edit/")).body(responseDto);
    }
}
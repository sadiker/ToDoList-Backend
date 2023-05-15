package com.sadiker.mobisem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sadiker.mobisem.model.request.CreateToDoRequest;
import com.sadiker.mobisem.model.request.UserRequest;
import com.sadiker.mobisem.model.response.IResponse;
import com.sadiker.mobisem.model.response.MyResponse;
import com.sadiker.mobisem.service.ToDoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "TODO API Dokümantasyonu")
public class ToDoController {

    @Autowired
    ToDoService todoService;

    @PostMapping("/createToDo")
    @ApiOperation(value = "Kullanıcıya yeni TODO oluşturur.")
    public MyResponse createToDo(@Validated @RequestBody CreateToDoRequest createToDoRequest) {
        return todoService.createToDo(createToDoRequest);

    }

    @DeleteMapping("/deleteToDoByName/{name}")
    @ApiOperation(value = "Kullanıcının TODO'sunu siler(O addaki tüm TODO'ları)")
    public IResponse deleteToDoName(@PathVariable String name, @RequestBody UserRequest userRequest) {
        return todoService.deleteToDoByName(name, userRequest);
    }

    @PostMapping("/todos")
    @ApiOperation(value = "Kullanıcının TODO'larını getirir.")
    public IResponse todos(@RequestBody UserRequest userRequest) {
        return todoService.todos(userRequest);
    }

    @DeleteMapping("/deleteToDoByLine/{line}")
    @ApiOperation(value = "Kullanıcının TODO'sunu siler(TODO'nun sırasına göre)")
    public IResponse deleteToDoLine(@PathVariable int line, @RequestBody UserRequest userRequest) {
        return todoService.deleteToDoByLine(line, userRequest);
    }

}

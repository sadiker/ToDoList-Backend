package com.sadiker.mobisem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sadiker.mobisem.model.request.UserRequest;
import com.sadiker.mobisem.model.response.IResponse;
import com.sadiker.mobisem.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@RestController
@Api(value = "User API Dokümantasyonu")
public class UserController {

  @Autowired
  UserService userService;

  @ApiOperation("'Merhaba Dünya' yazar.Deneme amaçlıdır.")
  @GetMapping("/helloworld")
  public String helloworld() {
    return "Merhaba Dünya";
  }

  @PostMapping("/register")
  @ApiOperation("Yeni kullanıcı kaydı yapar.")
  public IResponse register(@Valid @RequestBody UserRequest userdto) {
    return userService.register(userdto);
  }

}

package com.sadiker.mobisem.model.request;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateToDoRequest {

    @Valid
    private ToDoRequest toDoRequest;
    @Valid
    private UserRequest userRequest;

}

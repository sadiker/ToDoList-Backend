package com.sadiker.mobisem.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDo {

    private String name;
    private String description;
    private LocalDateTime localDateTime;
    private int line;
}

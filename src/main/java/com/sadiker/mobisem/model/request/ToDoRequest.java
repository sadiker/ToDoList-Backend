package com.sadiker.mobisem.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoRequest {

    @NotEmpty(message = "TODO ismi boş olamaz")
    private String name;

    @NotEmpty(message = "TODO açıklaması boş olamaz")
    private String description;

    @NotNull(message = "Yıl boş olamaz")
    @Min(value = 2023, message = "Yıl 2023-2024 içinde olmalıdır.")
    @Max(value = 2024, message = "Yıl 2023-2024 içinde olmalıdır.")
    private Integer year;

    @NotNull(message = " Ay boş olamaz")
    @Min(value = 1, message = "Ay 1-12 arasında olmalıdır.")
    @Max(value = 12, message = "Ay 1-12 arasında olmalıdır.")
    private Integer month;

    @NotNull(message = "Gün boş olamaz")
    @Min(value = 1, message = "Gün 1-31 arasında olmalıdır.")
    @Max(value = 31, message = "Gün 1-31 arasında olmalıdır.")
    private Integer day;

    @NotNull(message = "Saat boş olamaz")
    @Min(value = 0, message = "Saatler 00-23 arasındadır.")
    @Max(value = 23, message = "Saatler 00-23 arasındadır.")
    private Integer hour;

    @NotNull(message = "Dakika boş olamaz")
    @Min(value = 0, message = "Dakika 00-59 arasındadır.")
    @Max(value = 59, message = "Dakika 00-59 arasındadır.")
    private Integer minute;

}

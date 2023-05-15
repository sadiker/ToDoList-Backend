package com.sadiker.mobisem.model.request;

// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "UserRequest", description = "(role,todo,id burada girilmez.)")
public class UserRequest {

    @ApiModelProperty(value = "Her kullanıcı için uniqe isim alanı")
    @NotEmpty(message = "İsim alanı boş olmamalıdır...")
    // @NotNull(message = "İsim alanı NULL olmamalıdır...")
    public String username;

    @ApiModelProperty(value = "Her kullanıcı için şifre alanı")
    @NotEmpty(message = "Şifre alanı boş olmamalıdır...")
    // @NotNull(message = "Şifre alanı NULL olmamalıdır...")
    public String password;
}

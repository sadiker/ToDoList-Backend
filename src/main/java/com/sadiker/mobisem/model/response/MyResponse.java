package com.sadiker.mobisem.model.response;

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
@ApiModel(value = "MyResponse", description = "Responseları gönderir.")

public class MyResponse implements IResponse {
    @ApiModelProperty(value = "Gerekli mesajlar döndürülür.")
    String message;
    @ApiModelProperty(value = "Gerekli hallerde döndürülecek datayı içerir.")

    Object data;
}

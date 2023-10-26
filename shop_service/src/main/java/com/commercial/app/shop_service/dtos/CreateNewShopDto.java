package com.commercial.app.shop_service.dtos;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NotNull
public class CreateNewShopDto {
    @NotBlank
    private String name;
}

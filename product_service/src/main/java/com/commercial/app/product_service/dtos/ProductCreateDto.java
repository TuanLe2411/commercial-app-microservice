package com.commercial.app.product_service.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private String price;

    @Min(1)
    @Digits(integer = 10, fraction = 0)
    @NotNull
    private int amount;
}

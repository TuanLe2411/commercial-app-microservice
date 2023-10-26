package com.commercial.app.product_service.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductUpdateDto {
    private String category;
    private String name;
    private String price;

    @Min(1)
    @Digits(integer = 10, fraction = 0)
    private int amount;
}

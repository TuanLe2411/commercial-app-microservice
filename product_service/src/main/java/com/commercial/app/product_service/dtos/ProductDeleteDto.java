package com.commercial.app.product_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDeleteDto {
    @NotBlank
    private String productId;
}

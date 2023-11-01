package com.commercial.app.product_service.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDeleteDto {
    @NotBlank
    private String productId;
}

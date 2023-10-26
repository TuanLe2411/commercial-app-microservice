package com.commercial.app.product_service.es.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductCommand {
    @JsonIgnore
    private String productId;
    private String name;
    private String category;
    private String price;
    private Integer amount;
}

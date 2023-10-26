package com.commercial.app.product_service.es.commands;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateNewProductCommand {
    private String name;
    private String category;
    private String price;
    private Integer amount;
    private String shopId;
}

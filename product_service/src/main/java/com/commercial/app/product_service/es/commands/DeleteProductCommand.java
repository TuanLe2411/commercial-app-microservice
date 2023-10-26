package com.commercial.app.product_service.es.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteProductCommand {
    private String productId;
}

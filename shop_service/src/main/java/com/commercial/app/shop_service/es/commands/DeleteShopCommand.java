package com.commercial.app.shop_service.es.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteShopCommand {
    private String shopId;
    private String ownerId;
}

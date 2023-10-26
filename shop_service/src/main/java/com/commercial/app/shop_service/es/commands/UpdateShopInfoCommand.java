package com.commercial.app.shop_service.es.commands;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateShopInfoCommand {
    private String name;
    private String shopId;
    private String newOwnerId;
    private String ownerId;
}

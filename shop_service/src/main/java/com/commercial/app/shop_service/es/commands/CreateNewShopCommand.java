package com.commercial.app.shop_service.es.commands;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateNewShopCommand {
    private String name;
    private String ownerId;
}

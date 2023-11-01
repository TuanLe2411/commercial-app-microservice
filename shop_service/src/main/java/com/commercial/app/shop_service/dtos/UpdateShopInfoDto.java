package com.commercial.app.shop_service.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
@NotNull
public class UpdateShopInfoDto {
    private String name;
    private String ownerId;
}

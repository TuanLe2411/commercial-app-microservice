package com.commercial.app.shop_service.controllers;

import com.commercial.app.shop_service.annonations.ValidAlphameric;
import com.commercial.app.shop_service.dtos.CreateNewShopDto;
import com.commercial.app.shop_service.dtos.UpdateShopInfoDto;
import com.commercial.app.shop_service.es.commands.CreateNewShopCommand;
import com.commercial.app.shop_service.es.commands.DeleteShopCommand;
import com.commercial.app.shop_service.es.commands.UpdateShopInfoCommand;
import com.commercial.app.shop_service.services.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/shops")
@Validated
public class ShopController {
    final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/create-one")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewShop(
        @Valid @RequestBody CreateNewShopDto createNewShopDto,
        @ValidAlphameric @RequestHeader(value = "user-id") String userId
    ) throws JsonProcessingException {
        this.shopService.execute(CreateNewShopCommand.builder()
            .name(createNewShopDto.getName())
            .ownerId(userId)
            .build());
    }

    @PostMapping("/update-one/{shop-id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateShop(
        @Valid @RequestBody UpdateShopInfoDto updateShopInfoDto,
        @ValidAlphameric @RequestHeader(value = "user-id") String userId,
        @PathVariable("shop-id") @NotBlank String shopId
    ) throws JsonProcessingException {
        this.shopService.execute(UpdateShopInfoCommand.builder()
            .name(updateShopInfoDto.getName())
            .shopId(shopId)
            .ownerId(userId)
            .newOwnerId(updateShopInfoDto.getOwnerId())
            .build());
    }

    @DeleteMapping("/delete-one/{shop-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShop(
        @ValidAlphameric @RequestHeader(value = "user-id") String userId,
        @PathVariable("shop-id") @NotBlank String shopId) {
        this.shopService.execute(DeleteShopCommand.builder()
            .ownerId(userId)
            .shopId(shopId)
            .build());
    }
}

package com.commercial.app.shop_service.controllers;

import com.commercial.app.shop_service.annonations.ValidAlphameric;
import com.commercial.app.shop_service.dtos.CreateNewShopDto;
import com.commercial.app.shop_service.dtos.UpdateShopInfoDto;
import com.commercial.app.shop_service.exceptions.ShopUnavailableToChangeException;
import com.commercial.app.shop_service.exceptions.ShopUnavailableToCreateException;
import com.commercial.app.shop_service.models.Shop;
import com.commercial.app.shop_service.services.ShopService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Shop createNewShop(
        @Valid @RequestBody CreateNewShopDto createNewShopDto,
        @ValidAlphameric @RequestHeader(value = "user-id") String userId
    ) {
        if(!this.shopService.isCanCreateNewShop(userId, createNewShopDto)) {
            throw new ShopUnavailableToCreateException("Existed shop with name or ownerId");
        }
        return this.shopService.createNewShop(
            Shop.builder()
                .ownerId(userId)
                .name(createNewShopDto.getName())
                .build()
        );
    }

    @PostMapping("/update-one/{shop-id}")
    @ResponseStatus(HttpStatus.OK)
    public Shop updateShop(
        @Valid @RequestBody UpdateShopInfoDto updateShopInfoDto,
        @ValidAlphameric @RequestHeader(value = "user-id") String userId,
        @PathVariable("shop-id") @NotBlank String shopId
    ) {
        Shop shop = this.shopService.getShopInfo(shopId);
        this.checkUserIdCanModifyShop(userId, shop);
        return this.shopService.updateShop(shop, updateShopInfoDto);
    }


    @DeleteMapping("/delete-one/{shop-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShop(
        @ValidAlphameric @RequestHeader(value = "user-id") String userId,
        @PathVariable("shop-id") @NotBlank String shopId) {
        Shop shop = this.shopService.getShopInfo(shopId);
        this.checkUserIdCanModifyShop(userId, shop);
        this.shopService.deleteShop(shop);
    }

    private void checkUserIdCanModifyShop(String userId, Shop shop) {
        if(!this.isShopExist(shop)) {
            throw new ShopUnavailableToChangeException("Shop is not existed");
        }
        if(!shop.isOwner(userId)) {
            throw new ShopUnavailableToChangeException("Must be shop owner");
        }
    }
    private boolean isShopExist(Shop shop) {
        return shop != null;
    }
}

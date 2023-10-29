package com.commercial.app.shop_service.services;

import com.commercial.app.shop_service.dtos.CreateNewShopDto;
import com.commercial.app.shop_service.dtos.UpdateShopInfoDto;
import com.commercial.app.shop_service.models.Shop;
import com.commercial.app.shop_service.repositories.ShopRepository;
import com.commercial.app.shop_service.utils.ValueRandomGenerator;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public boolean isCanCreateNewShop(String userId, CreateNewShopDto createNewShopDto) {
        return this.shopRepository.countAllByOwnerIdAndName(userId, createNewShopDto.getName()) == 0;
    }

    public Shop createNewShop(Shop shop) {
        String shopId = ValueRandomGenerator.getRandomShopId();
        shop.setId(shopId);
        return this.shopRepository.save(shop);
    }

    @Nullable
    public Shop getShopInfo(String shopId) {
        return this.shopRepository.findTopById(shopId);
    }

    public Shop updateShop(Shop shop, UpdateShopInfoDto updateShopInfoDto) {
        shop.updateFromDto(updateShopInfoDto);
        return this.shopRepository.save(shop);
    }

    public void deleteShop(Shop shop) {
        this.shopRepository.delete(shop);
    }
}

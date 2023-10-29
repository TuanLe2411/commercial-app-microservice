package com.commercial.app.shop_service.repositories;

import com.commercial.app.shop_service.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
    Shop findTopById(String id);

    int countAllByOwnerIdAndName(String ownerId, String name);
}

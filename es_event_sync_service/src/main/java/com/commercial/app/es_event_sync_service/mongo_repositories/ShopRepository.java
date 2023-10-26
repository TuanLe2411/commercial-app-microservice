package com.commercial.app.es_event_sync_service.mongo_repositories;

import com.commercial.app.es_event_sync_service.mongo_models.Product;
import com.commercial.app.es_event_sync_service.mongo_models.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {
    Shop findOneById(String id);
}

package com.commercial.app.es_event_sync_service.mongo_repositories;

import com.commercial.app.es_event_sync_service.mongo_models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    void deleteProductById(String id);

    Product findOneById(String id);
}

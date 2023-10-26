package com.commercial.app.es_event_sync_service.mongo_repositories;

import com.commercial.app.es_event_sync_service.mongo_models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'_id' : ?0}")
    @Update("{'$set': ?1}")
    void updateProductById(String id, Product updatedProduct);
}

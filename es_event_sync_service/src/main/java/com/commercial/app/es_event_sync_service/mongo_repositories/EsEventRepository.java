package com.commercial.app.es_event_sync_service.mongo_repositories;

import com.commercial.app.es_event_sync_service.mongo_models.EsEventHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsEventRepository extends MongoRepository<EsEventHistory, Long> {
    EsEventHistory findOneById(Long id);
}

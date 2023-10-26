package com.commercial.app.shop_service.es.repository;

import com.commercial.app.shop_service.es.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event save(Event event);
    Event findTopByStreamIdOrderByVersionDesc(String streamId);

    Event findTopByOwnerIdAndStreamOrderByCreatedAtDesc(String ownerId, String stream);
}

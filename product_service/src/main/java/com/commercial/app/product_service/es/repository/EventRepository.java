package com.commercial.app.product_service.es.repository;

import com.commercial.app.product_service.es.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event save(Event event);
    Event findTopByStreamIdOrderByVersionDesc(String streamId);
}

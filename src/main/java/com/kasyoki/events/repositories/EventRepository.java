package com.kasyoki.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kasyoki.events.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}

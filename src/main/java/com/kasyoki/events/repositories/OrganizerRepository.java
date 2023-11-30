package com.kasyoki.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kasyoki.events.models.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

}

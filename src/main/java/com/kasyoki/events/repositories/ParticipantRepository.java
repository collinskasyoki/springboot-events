package com.kasyoki.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kasyoki.events.models.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}

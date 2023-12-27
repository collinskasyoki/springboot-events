package com.kasyoki.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kasyoki.events.models.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	@Query(value = "SELECT p FROM Participant p WHERE p.email=:email")
	public Participant findByEmail(@Param("email") String email);
}

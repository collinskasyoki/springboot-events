package com.kasyoki.events.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasyoki.events.dtos.ParticipantDTO;
import com.kasyoki.events.models.Participant;
import com.kasyoki.events.repositories.ParticipantRepository;

@Service
public class ParticipantService {
	@Autowired
	ParticipantRepository participantRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public ParticipantDTO getParticipantByEmail(String email) {
		Participant participant = participantRepository.findByEmail(email);
		
		return participant == null ? null : modelMapper.map(participant, ParticipantDTO.class);
	}
}

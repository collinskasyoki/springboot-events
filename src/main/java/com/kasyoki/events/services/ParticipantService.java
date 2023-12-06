package com.kasyoki.events.services;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasyoki.events.dtos.EventsParticipantsDTO;
import com.kasyoki.events.dtos.ParticipantDTO;
import com.kasyoki.events.models.Event;
import com.kasyoki.events.repositories.EventRepository;
import com.kasyoki.events.repositories.ParticipantRepository;

@Service
public class ParticipantService {
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public EventsParticipantsDTO getEventParticipants (Long id) {
		Event event = eventRepository.findById(id).orElse(null);
		
		if (event == null) {
			return null;
		}
		
		return modelMapper.map(event, EventsParticipantsDTO.class);
	}
}

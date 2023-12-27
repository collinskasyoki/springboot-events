package com.kasyoki.events.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasyoki.events.dtos.BaseEventDTO;
import com.kasyoki.events.dtos.EventsParticipantsDTO;
import com.kasyoki.events.dtos.OrganizerDTO;
import com.kasyoki.events.dtos.ParticipantDTO;
import com.kasyoki.events.dtos.RoleDTO;
import com.kasyoki.events.models.Event;
import com.kasyoki.events.models.Organizer;
import com.kasyoki.events.models.Participant;
import com.kasyoki.events.models.Role;
import com.kasyoki.events.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public Set<BaseEventDTO> getAllEvents () {
		return eventRepository.findAll().stream()
				.map(event -> modelMapper.map(event, BaseEventDTO.class))
				.collect(Collectors.toSet());
	}
	
	public Optional<BaseEventDTO> getOneEvent(Long id) {
		Optional<Event> event = eventRepository.findById(id);
		return event.map(e -> modelMapper.map(e, BaseEventDTO.class));
	}
	
	public BaseEventDTO addEvent(BaseEventDTO baseEventDTO) {
	    Event event = modelMapper.map(baseEventDTO, Event.class);
	    
	    Set<OrganizerDTO> organizers = baseEventDTO.getOrganizers();
	    Set<Organizer> organizersSet = new HashSet<>();
	    if(organizers != null) {
		    for (OrganizerDTO organizerDTO : organizers) {
		    	Organizer tmpOrganizer = new Organizer();
		    	tmpOrganizer.setName(organizerDTO.getName());
		    	tmpOrganizer.setTitle(organizerDTO.getTitle());
		    	
		    	Set<RoleDTO> roleDtos = organizerDTO.getRoles();
		    	if (roleDtos != null) {
		    		Set<Role> roles = new HashSet<>();
		    		for (RoleDTO roleDTO: roleDtos) {
		    			Role tmpRole = new Role();
		    			tmpRole.setName(roleDTO.getName());
		    			tmpRole.setDescription(roleDTO.getDescription());
		    			tmpRole.setOrganizer(tmpOrganizer);
		    			roles.add(tmpRole);
			    	}
		    		tmpOrganizer.setRoles(roles);
		    	}
				organizersSet.add(tmpOrganizer);
			}
	    	event.setOrganizers(organizersSet);
	    }
	    Event newEvent = eventRepository.save(event);
	    
	    return modelMapper.map(newEvent, BaseEventDTO.class);
	}
	
	public BaseEventDTO addEventOrganizers(Long eventId, Set<OrganizerDTO> organizerDTOs) {
		Event theEvent = eventRepository.findById(eventId).orElse(null);
		
		if (theEvent == null) {
			return null;
		}
		
		Set<Organizer> organizers = new HashSet<>();
		for (OrganizerDTO organizerDTO: organizerDTOs) {
			Organizer tmpOrganizer = new Organizer();
			tmpOrganizer.setName(organizerDTO.getName());
			tmpOrganizer.setTitle(organizerDTO.getTitle());
			
			Set<Role> roles = new HashSet<>();
			for (RoleDTO roleDto: organizerDTO.getRoles()) {
				Role tmpRole = new Role();
				tmpRole.setName(roleDto.getName());
				tmpRole.setDescription(roleDto.getDescription());
				tmpRole.setOrganizer(tmpOrganizer);
				
				roles.add(tmpRole);
			}
			tmpOrganizer.setRoles(roles);
			
			organizers.add(tmpOrganizer);
		}
		
		theEvent.setOrganizers(organizers);
		Event returnEvent = eventRepository.save(theEvent);
		
		return modelMapper.map(returnEvent, BaseEventDTO.class);
	}
	
	public EventsParticipantsDTO getEventParticipants (Long id) {
		Event event = eventRepository.findById(id).orElse(null);
		
		if (event == null) {
			return null;
		}
		
		return modelMapper.map(event, EventsParticipantsDTO.class);
	}
	
	public Object getEventParticipantsCount(Long id) {
		Event event = eventRepository.findById(id).orElse(null);
		
		if (event == null) {
			return null;
		}
		
		return event.getParticipants().size();
	}
	
	public EventsParticipantsDTO addEventParticipant(Long id, ParticipantDTO participant) {
		Event event = eventRepository.findById(id).orElse(null);
		
		if(event == null) {
			return null;
		}
		
		Participant theParticipant =  new Participant();
		theParticipant.setEmail(participant.getEmail());
		theParticipant.setName(participant.getName());
				
		Set<Participant> existingParticipants = event.getParticipants();
		existingParticipants.add(theParticipant);
		
		event.setParticipants(existingParticipants);
		
		eventRepository.save(event);
		
		return modelMapper.map(event, EventsParticipantsDTO.class);
	}
}

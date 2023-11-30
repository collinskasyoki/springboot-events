package com.kasyoki.events.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kasyoki.events.dtos.BaseEventDTO;
import com.kasyoki.events.dtos.OrganizerDTO;
import com.kasyoki.events.dtos.OrganizerEventsDTO;
import com.kasyoki.events.models.Organizer;
import com.kasyoki.events.repositories.OrganizerRepository;

@Service
public class OrganizerService {
	@Autowired
	private OrganizerRepository organizerRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public Set<OrganizerDTO> getAll() {
		List<Organizer> organizers = organizerRepository.findAll();
		Set<OrganizerDTO> organizersSet = organizers.stream()
				.map(organizer -> modelMapper.map(organizer, OrganizerDTO.class))
				.collect(Collectors.toSet());
		return organizersSet;
	}
	
	public Optional<OrganizerDTO> getOneOrganizer(Long id) {
		Optional<Organizer> organizer = organizerRepository.findById(id);
		return organizer.map(e -> modelMapper.map(e, OrganizerDTO.class));
	}
	
	public OrganizerDTO addOrganizer (OrganizerDTO organizerDTO) {
		Organizer organizer = modelMapper.map(organizerDTO, Organizer.class);
		organizerRepository.save(organizer);
		
		return modelMapper.map(organizer, OrganizerDTO.class);
	}
	
	public OrganizerEventsDTO getOrganizerEvents(Long id) {
		Organizer organizer = organizerRepository.findById(id).orElse(null);
		
		if (organizer == null) {
			return null;
		}
		OrganizerEventsDTO organizerDTO = modelMapper.map(organizer, OrganizerEventsDTO.class);
		Set<BaseEventDTO> organizerEventsDTOs = organizer.getEvents().stream()
														.map(event -> modelMapper.map(event, BaseEventDTO.class))
														.collect(Collectors.toSet());
		organizerDTO.setEvents(organizerEventsDTOs);
		return organizerDTO;
	}
}

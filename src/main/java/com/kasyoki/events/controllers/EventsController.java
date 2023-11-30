package com.kasyoki.events.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kasyoki.events.dtos.BaseEventDTO;
import com.kasyoki.events.dtos.OrganizerDTO;
import com.kasyoki.events.services.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventsController {
	
	@Autowired
	private EventService eventService;

	@GetMapping
	public ResponseEntity<Set<BaseEventDTO>> getAllEvents() {
		return new ResponseEntity<Set<BaseEventDTO>>(eventService.getAllEvents(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<BaseEventDTO> addEvent(@RequestBody @Validated @Valid BaseEventDTO baseEventDTO) {
		BaseEventDTO event = eventService.addEvent(baseEventDTO);
		return new ResponseEntity<>(event, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BaseEventDTO> getOneEvent(@PathVariable Long id) {
		BaseEventDTO event = eventService.getOneEvent(id).orElse(null);
		if(event != null)
			return new ResponseEntity<BaseEventDTO>(event, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping("/{eventId}/organizers")
	public ResponseEntity<BaseEventDTO> addEventOrganizers(@PathVariable Long eventId, @RequestBody @Validated @Valid Set<OrganizerDTO> organizersDTO) {
		BaseEventDTO newEvent = eventService.addEventOrganizers(eventId, organizersDTO);
		if (newEvent != null) {
			return new ResponseEntity<>(newEvent, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

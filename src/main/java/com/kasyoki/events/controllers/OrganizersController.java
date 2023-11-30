package com.kasyoki.events.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kasyoki.events.dtos.OrganizerDTO;
import com.kasyoki.events.dtos.OrganizerEventsDTO;
import com.kasyoki.events.services.OrganizerService;



@RestController
@RequestMapping("/organizers")
public class OrganizersController {

	@Autowired
	private OrganizerService organizerService;
	
	@GetMapping
	public ResponseEntity<Set<OrganizerDTO>> getOrganizers() {
		return new ResponseEntity<Set<OrganizerDTO>>(organizerService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrganizerDTO> getOneOrganizer(@PathVariable Long id) {
		OrganizerDTO organizer = organizerService.getOneOrganizer(id).orElse(null);
		if (organizer != null) {
			return new ResponseEntity<OrganizerDTO>(organizer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/events")
	public ResponseEntity<OrganizerEventsDTO> getOrganizerEvents(@PathVariable Long id) {
		OrganizerEventsDTO organizerEvents = organizerService.getOrganizerEvents(id);
		if (organizerEvents != null) {
			return new ResponseEntity<OrganizerEventsDTO>(organizerEvents, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
//	@PostMapping
//	public ResponseEntity<OrganizerDTO> addOrganizer(@RequestBody @Validated @Valid OrganizerDTO organizerDTO) {
//		return new ResponseEntity<OrganizerDTO>(organizerService.addOrganizer(organizerDTO), HttpStatus.OK);
//	}
}

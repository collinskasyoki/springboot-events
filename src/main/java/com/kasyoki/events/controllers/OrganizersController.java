package com.kasyoki.events.controllers;

import java.util.HashMap;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("/organizers")
public class OrganizersController {

	@Autowired
	private OrganizerService organizerService;
	
	@Operation(summary = "Get a set of all organizers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully fetched organizers",
					content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrganizerDTO.class))))
	})
	@GetMapping
	public ResponseEntity<Set<OrganizerDTO>> getOrganizers() {
		return new ResponseEntity<Set<OrganizerDTO>>(organizerService.getAll(), HttpStatus.OK);
	}
	
	@Operation(summary = "Get a specific organizer by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully fetched the organizer",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizerDTO.class))),
			@ApiResponse(responseCode = "404", description = "Organizer not found",
					content = @Content(mediaType = "application/json", 
									   examples = @ExampleObject(value = "{'message' : 'The organizer with this id not found'}")))
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getOneOrganizer(@Parameter(description = "The id of the organizer") @PathVariable Long id) {
		OrganizerDTO organizer = organizerService.getOneOrganizer(id).orElse(null);
		if (organizer != null) {
			return new ResponseEntity<OrganizerDTO>(organizer, HttpStatus.OK);
		} else {
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "The organizer with this id not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Get a list of events for a particular organizer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully fetched the events of the organizer",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizerEventsDTO.class))),
			@ApiResponse(responseCode = "404", description = "The organizer with this id not found",
						content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'message' : 'The organizer with this id not found'}")))
	})
	@GetMapping("/{id}/events")
	public ResponseEntity<?> getOrganizerEvents(@Parameter(description = "The id of the organizer") @PathVariable Long id) {
		OrganizerEventsDTO organizerEvents = organizerService.getOrganizerEvents(id);
		if (organizerEvents != null) {
			return new ResponseEntity<OrganizerEventsDTO>(organizerEvents, HttpStatus.OK);
		}
		
		HashMap<String, String> response = new HashMap<>();
		response.put("message", "The organizer with this id not found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
//	@PostMapping
//	public ResponseEntity<OrganizerDTO> addOrganizer(@RequestBody @Validated @Valid OrganizerDTO organizerDTO) {
//		return new ResponseEntity<OrganizerDTO>(organizerService.addOrganizer(organizerDTO), HttpStatus.OK);
//	}
}

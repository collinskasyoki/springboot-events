package com.kasyoki.events.controllers;

import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kasyoki.events.dtos.BaseEventDTO;
import com.kasyoki.events.dtos.EventsParticipantsDTO;
import com.kasyoki.events.dtos.OrganizerDTO;
import com.kasyoki.events.dtos.ParticipantDTO;
import com.kasyoki.events.services.EventService;
import com.kasyoki.events.services.ParticipantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventsController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ParticipantService participantService;

	@Operation(summary = "Get a set all events")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Events successfully fetched",
	        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BaseEventDTO.class)))
	    )
	})
	@GetMapping
	public ResponseEntity<Set<BaseEventDTO>> getAllEvents() {
		return new ResponseEntity<Set<BaseEventDTO>>(eventService.getAllEvents(), HttpStatus.OK);
	}
	
	@Operation(summary = "Add a new event")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Event successfully added.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseEventDTO.class)))
	})
	@PostMapping
	public ResponseEntity<BaseEventDTO> addEvent(@RequestBody @Validated @Valid BaseEventDTO baseEventDTO) {
		BaseEventDTO event = eventService.addEvent(baseEventDTO);
		return new ResponseEntity<>(event, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Edit an event")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event successfully updated.",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseEventDTO.class))),
			@ApiResponse(responseCode = "401", description = "Event not found",
						content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'message': 'The event with this id cannot be found'}")))
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateOneEvent(@Parameter(description = "Integer id of the target event") @PathVariable Long id, @RequestBody @Validated @Valid BaseEventDTO eventDTO) {
		BaseEventDTO event = eventService.updateEvent(id, eventDTO);
		
		if (event == null) {
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "The event with this id cannot be found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<BaseEventDTO>(event, HttpStatus.OK);
	}
	
	@Operation(summary = "Get a specific event by id")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200", description = "Event fetched successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseEventDTO.class))
					),
			@ApiResponse(
					responseCode = "404", description = "Event not found.",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'message': 'The event with this id cannot be found'}"))
					)
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getOneEvent(@Parameter(description = "Integer id of the target event") @PathVariable Long id) {
		BaseEventDTO event = eventService.getOneEvent(id).orElse(null);
		if(event != null)
			return new ResponseEntity<BaseEventDTO>(event, HttpStatus.OK);
		else {
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "The event with this id cannot be found");
			
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@Operation(summary = "Add organizers to existing events. [Replaces Existing Organizers]")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Organizers added successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseEventDTO.class)))
	})
	@PostMapping("/{id}/organizers")
	public ResponseEntity<BaseEventDTO> addEventOrganizers(@Parameter(description = "The id of the event") @PathVariable Long id, @RequestBody @Validated @Valid Set<OrganizerDTO> organizersDTO) {
		BaseEventDTO newEvent = eventService.addEventOrganizers(id, organizersDTO);
		if (newEvent != null) {
			return new ResponseEntity<>(newEvent, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Get a set of participants for a particular event.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event participants fetched successfully.",
					content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParticipantDTO.class)))),
			@ApiResponse(responseCode = "404", description = "Event not found",
			content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'message': 'The event with this id cannot be found'}")))
	})
	@GetMapping("/{id}/participants")
	public ResponseEntity<?> getEventParticipants(@Parameter(description = "The id of the event") @PathVariable Long id) {
		EventsParticipantsDTO eventParticipants = eventService.getEventParticipants(id);
		
		if (eventParticipants == null) {
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "The event with this id cannot be found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<EventsParticipantsDTO>(eventParticipants, HttpStatus.OK);
	}
	
	@Operation(summary = "Get the count of participants for a particular event.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event participant count fetched successfully.",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'id': '1', 'participantCount': '10'}"))),
			@ApiResponse(responseCode = "404", description = "Event not found",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'message': 'The event with this id cannot be found'}")))
	})
	@GetMapping("/{id}/participants/count")
	public ResponseEntity<?> getEventParticipantsCount(@Parameter(description = "The id of the event") @PathVariable Long id) {
		Object participants = eventService.getEventParticipantsCount(id);
		
		HashMap<String, String> response = new HashMap<>();
		if (participants == null) {
			response.put("message", "The event with this id cannot be found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("id", id.toString());
		response.put("participantCount", participants.toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Operation(summary = "Add a new event participant")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Participant added successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventsParticipantsDTO.class))),
			@ApiResponse(responseCode = "400", description = "Participant already exists",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{'message': 'Participant with this email already exists'}")))
	})
	@PostMapping("/{id}/participants")
	public ResponseEntity<?> addEventParticipant(@Parameter(description = "The id of the event") @PathVariable Long id, @RequestBody @Validated @Valid ParticipantDTO participant) {
		if (participantService.getParticipantByEmail(participant.getEmail()) != null) {
			HashMap<String, String> response = new HashMap<>();
			response.put("message", "Participant with this email already exists");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		EventsParticipantsDTO event = eventService.addEventParticipant(id, participant);
		return new ResponseEntity<EventsParticipantsDTO>(event, HttpStatus.CREATED);
	}
}

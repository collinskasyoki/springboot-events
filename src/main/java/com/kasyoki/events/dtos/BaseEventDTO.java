package com.kasyoki.events.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kasyoki.events.models.Category;
import com.kasyoki.events.models.Status;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEventDTO {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	
	@NotEmpty (message = "Event name cannot be empty.")
	private String name;
	
	@NotEmpty(message = "Description cannot be empty.")
	private String description;
	
	@NotEmpty(message = "Date cannot be empty.")
	private String date;
	
	private String imageUrl;
	
	@NotEmpty(message = "Start time cannot be empty.")
	private String startTime;
	
	@NotEmpty(message = "End time cannot be empty.")
	private String endTime;
	
	private Set<OrganizerDTO> organizers;
	
	@NotEmpty(message = "Location cannot be empty.")
	private String location;
	
	private Category category;
	
	private Status status;
}

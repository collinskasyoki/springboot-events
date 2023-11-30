package com.kasyoki.events.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizerEventsDTO extends OrganizerDTO {
	private Set<BaseEventDTO> events;
}

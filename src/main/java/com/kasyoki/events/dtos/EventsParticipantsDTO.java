package com.kasyoki.events.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventsParticipantsDTO extends BaseEventDTO {
	private Set<ParticipantDTO> participants;
}

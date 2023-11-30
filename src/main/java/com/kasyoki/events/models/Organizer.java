package com.kasyoki.events.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organizer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String title;
	
	@ManyToMany(mappedBy = "organizers")
	private Set<Event> events = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organizer", fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>(); 
}

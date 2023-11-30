package com.kasyoki.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kasyoki.events.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}

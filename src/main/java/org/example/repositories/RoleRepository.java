package org.example.repositories;

import org.example.models.entities.Dwh;
import org.example.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository   extends JpaRepository<Role, String> {
    Optional<Dwh> findByName(String name);
}

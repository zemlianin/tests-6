package org.example.repositories;

import org.example.models.entities.AtlasType;
import org.example.models.entities.Dwh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AtlasTypeRepository  extends JpaRepository<AtlasType, String> {
    Optional<AtlasType> findByName(String name);
}

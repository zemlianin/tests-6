package org.example.repositories;

import org.example.models.entities.Dwh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DwhRepository  extends JpaRepository<Dwh, String> {
    Optional<Dwh> findByName(String name);

    @Query("SELECT d FROM Dwh d WHERE d.isUsed = false")
    List<Dwh> findAllByIsUsedFalse();

    @Modifying
    @Query("UPDATE Dwh d SET d.isUsed = true WHERE d.name = :name AND d.isUsed = false")
    Integer softUsedByName(String name);
}

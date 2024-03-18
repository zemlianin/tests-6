package org.example.repositories;

import org.example.models.entities.Dwh;
import org.example.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);

    @Modifying
    @Query("UPDATE User u SET u.dwh = :dwh WHERE u.id = :id")
    Integer softDwhByUserId(UUID id, Dwh dwh);
}

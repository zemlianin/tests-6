package org.example.repository;

import org.example.models.entities.Dwh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DwhRepository  extends JpaRepository<Dwh, String> {
}

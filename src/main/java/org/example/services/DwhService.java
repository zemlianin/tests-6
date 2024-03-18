package org.example.services;

import org.example.models.entities.Dwh;
import org.example.models.entities.User;
import org.example.repository.DwhRepository;
import org.springframework.stereotype.Service;

@Service
public class DwhService {
    private DwhRepository dwhRepository;

    public DwhService(DwhRepository dwhRepository) {
        this.dwhRepository = dwhRepository;
    }

    public Dwh LinkDwh(User user){
        return new Dwh();
    }
}

package org.example.services;

import com.mysql.cj.exceptions.WrongArgumentException;
import org.example.clients.AtlasAgentClient;
import org.example.models.entities.Dwh;
import org.example.models.entities.User;
import org.example.repository.DwhRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DwhService {
    private final DwhRepository dwhRepository;
    private final UserRepository userRepository;
    private final AtlasAgentClient atlasAgentClient;
    private final AtlasRoleFactory atlasRoleFactory;

    public DwhService(DwhRepository dwhRepository,
                      UserRepository userRepository,
                      AtlasAgentClient atlasAgentClient,
                      AtlasRoleFactory atlasRoleFactory) {
        this.userRepository = userRepository;
        this.dwhRepository = dwhRepository;
        this.atlasAgentClient = atlasAgentClient;
        this.atlasRoleFactory = atlasRoleFactory;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Dwh linkDwh(User user){
        if(user.getDwh() != null){
            throw new WrongArgumentException("User already has DWH");
        }

        var dwhList = dwhRepository.findFirstByIsUsedFalse();

        if(dwhList.isEmpty()){
            throw new NoSuchElementException("All dwh were used");
        }
        var dwh = dwhList.stream().findFirst().get();
        dwhRepository.softUsedByName(dwh.getName());
        userRepository.softDwhByUserId(user.getId(), dwh);
        return dwh;
    }

    public List<Dwh> getAll(){
        return dwhRepository.findAll();
    }

    public Dwh generateDwh(){
       /* var allPermissionRegex =
        var role = atlasRoleFactory.createDefaultDwhRole()*/
        return new Dwh();
    }
}

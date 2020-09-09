package br.com.eventosdahora.api.service;

import br.com.eventosdahora.api.entity.Profile;
import br.com.eventosdahora.api.exception.RegisterNotFoundException;
import br.com.eventosdahora.api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> findAll(){
        return profileRepository.findAll();
    }

    public Profile save(Profile profile){
        return profileRepository.save(profile);
    }
    
    public Profile findOne(Integer idProfile){
        Optional<Profile> optional = profileRepository.findById(idProfile);

        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new RegisterNotFoundException(Profile.class, "id", idProfile.toString());
        }
    }
    
    public Profile findOneByNmProfile(String nmProfile){
        Optional<Profile> optional = profileRepository.findOneByNmProfile(nmProfile);

        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new RegisterNotFoundException(Profile.class, "nmProfile", nmProfile);
        }
    }
}

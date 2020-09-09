package br.com.eventosdahora.api.service.validation;

import br.com.eventosdahora.api.entity.User;
import br.com.eventosdahora.api.entity.UserAdmin;
import br.com.eventosdahora.api.exception.handler.ValidationFieldException;
import br.com.eventosdahora.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validationUniqueKeyUser(User user){
        if(userRepository.findFirstByDsEmailIgnoreCase(user.getDsEmail()).isPresent()){
            throw new ValidationFieldException("dsEmail", "The email '" + user.getDsEmail() + "' is already in use.", user);

        }
    }
    
    public void validationUniqueKeyUserAdmin(UserAdmin userAdmin){
        if(userRepository.findFirstByDsEmailIgnoreCase(userAdmin.getDsEmail()).isPresent()){
            throw new ValidationFieldException("dsEmail", "The email '" + userAdmin.getDsEmail() + "' is already in use.", userAdmin);
            
        }
    }
}

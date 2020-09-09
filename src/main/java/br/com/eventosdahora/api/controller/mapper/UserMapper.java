package br.com.eventosdahora.api.controller.mapper;

import br.com.eventosdahora.api.controller.request.UserRequest;
import br.com.eventosdahora.api.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
	
	UserRequest toRequest(User entity);
	User toEntity(UserRequest request);
	
}

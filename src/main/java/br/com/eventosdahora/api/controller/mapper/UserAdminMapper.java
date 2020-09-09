package br.com.eventosdahora.api.controller.mapper;

import br.com.eventosdahora.api.controller.request.UserAdminRequest;
import br.com.eventosdahora.api.entity.UserAdmin;
import org.mapstruct.Mapper;

@Mapper
public interface UserAdminMapper {
	
	UserAdminRequest toRequest(UserAdmin entity);
	UserAdmin toEntity(UserAdminRequest request);
	
}

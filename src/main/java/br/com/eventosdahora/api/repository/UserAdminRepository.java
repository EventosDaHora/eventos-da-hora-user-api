package br.com.eventosdahora.api.repository;

import br.com.eventosdahora.api.entity.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Integer> {
	
	Optional<UserAdmin> findFirstByDsEmailIgnoreCase(String email);
}

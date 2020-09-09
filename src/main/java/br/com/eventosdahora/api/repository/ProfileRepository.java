package br.com.eventosdahora.api.repository;

import br.com.eventosdahora.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
	
	Optional<Profile> findOneByNmProfile(String nmProfile);
	
}

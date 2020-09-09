package br.com.eventosdahora.api.service;

import br.com.eventosdahora.api.controller.request.AuthenticationRequest;
import br.com.eventosdahora.api.controller.response.AuthenticationResponse;
import br.com.eventosdahora.api.exception.UnauthorizedException;
import br.com.eventosdahora.api.repository.UserAdminRepository;
import br.com.eventosdahora.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAdminRepository userAdminRepository;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthenticationResponse generateJwtTokenUser(AuthenticationRequest authentication) {
		
		Logger.getAnonymousLogger().info("--- User '" + authentication.getUsername() + "' has made a login attempt...");
		
		return userRepository.findFirstByDsEmailIgnoreCase(authentication.getUsername())
		                     .filter(user -> passwordEncoder.matches(authentication.getPassword(),
		                                                             user.getDsPassword()))
		                     .map(user -> new AuthenticationResponse(jwtTokenService.generateTokenUser(user)))
		                     .orElseThrow(UnauthorizedException::new);
		
	}
	
	public AuthenticationResponse generateJwtTokenUserAdmin(AuthenticationRequest authentication) {
		
		Logger.getAnonymousLogger().info("--- User Admin '" + authentication.getUsername() + "' has made a login attempt...");
		
		return userAdminRepository.findFirstByDsEmailIgnoreCase(authentication.getUsername())
		                     .filter(user -> passwordEncoder.matches(authentication.getPassword(),
		                                                             user.getDsPassword()))
		                     .map(user -> new AuthenticationResponse(jwtTokenService.generateTokenUserAdmin(user)))
		                     .orElseThrow(UnauthorizedException::new);
		
	}
	
}

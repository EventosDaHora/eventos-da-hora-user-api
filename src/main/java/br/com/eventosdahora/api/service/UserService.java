package br.com.eventosdahora.api.service;

import br.com.eventosdahora.api.controller.mapper.UserMapper;
import br.com.eventosdahora.api.controller.request.ChangePasswordRequest;
import br.com.eventosdahora.api.controller.request.CreatePasswordRequest;
import br.com.eventosdahora.api.controller.request.UserRequest;
import br.com.eventosdahora.api.entity.User;
import br.com.eventosdahora.api.enums.ProfileEnum;
import br.com.eventosdahora.api.exception.*;
import br.com.eventosdahora.api.exception.handler.ValidationFieldException;
import br.com.eventosdahora.api.repository.UserRepository;
import br.com.eventosdahora.api.service.validation.UserValidationService;
import br.com.eventosdahora.api.util.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final ProfileService profileService;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final UserValidationService userValidationService;
	private final UserMapper userMapper;
	
	private final ProfileEnum profileType;
	
	public UserService(UserRepository userRepository, ProfileService profileService,
	                   PasswordEncoder passwordEncoder, EmailService emailService,
	                   UserValidationService userValidationService,
	                   final UserMapper userMapper) {
		this.userRepository = userRepository;
		this.profileService = profileService;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.userValidationService = userValidationService;
		this.userMapper = userMapper;
		this.profileType = ProfileEnum.USER;
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findOne(Integer idUser) {
		Optional<User> optional = userRepository.findById(idUser);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new RegisterNotFoundException(User.class, "idUser", idUser.toString());
		}
	}
	
	public User save(UserRequest userRequest) {
		
		User user = userMapper.toEntity(userRequest);
		
		Optional<User> optionalUser = userRepository.findFirstByDsEmailIgnoreCase(user.getDsEmail());
		if (optionalUser.isPresent()) {
			throw new ValidationFieldException("dsEmail", "Email já está sendo utilizado", user);
		}
		
		user.setProfile(profileService.findOneByNmProfile(this.profileType.name()));
		user.setDsToken(PasswordGenerator.getRandomToken());
		user.setDtExpiry(new Timestamp(PasswordGenerator.calculateExpiryDate().getTime()));
		user.setDtCreate(new Timestamp(new Date().getTime()));
		user.setIsEmailVerified(false);
		
		User userResponse = userRepository.save(user);
		
		emailService.sendEmailUser(userResponse, "Bem vindo ao Eventos da Hora", "email-registro");
		
		return userResponse;
	}
	
	public User updateUser(UserRequest userRequest, Integer idUser) {
		
		User user = userMapper.toEntity(userRequest);
		
		Optional<User> optional = userRepository.findById(idUser);
		if (!optional.isPresent()) {
			throw new RegisterNotFoundException(User.class, "idUser", idUser.toString());
		}
		
		if (!optional.get().getDsEmail().equalsIgnoreCase(user.getDsEmail())) {
			userValidationService.validationUniqueKeyUser(user);
		}
		
		optional.get().setDsEmail(user.getDsEmail());
		optional.get().setNmUser(user.getNmUser());
		optional.get().setNuDdd(user.getNuDdd());
		optional.get().setNuCel(user.getNuCel());
		return userRepository.save(optional.get());
	}
	
	public User changePassword(Integer idUser, ChangePasswordRequest changePasswordRequest) {
		
		Optional<User> optional = userRepository.findById(idUser);
		if (!optional.isPresent()) {
			throw new RegisterNotFoundException(User.class, "idUser", idUser.toString());
		}
		
		if (!optional.get().getIsEmailVerified()) {
			throw new EmailDontVerifiedException("Email não verificado");
		}
		
		if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), optional.get().getDsPassword())) {
			optional.get().setDsPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
			optional.get().setDsToken(null);
			optional.get().setDtExpiry(null);
			return userRepository.save(optional.get());
		} else {
			throw new ValidationFieldException("oldPassword", "Senhas não conferem", changePasswordRequest);
		}
		
	}
	
	public User createPassword(Integer idUser, String dsToken, CreatePasswordRequest createPasswordRequest)
			throws IOException {
		Optional<User> optional = userRepository.findById(idUser);
		
		if (optional.isPresent()) {
			if (optional.get().getDsToken() != null && optional.get().getDtExpiry() != null) {
				if (optional.get().getDsToken().equals(dsToken)) {
					Calendar cal = Calendar.getInstance();
					if ((optional.get().getDtExpiry().getTime() - cal.getTime().getTime()) > 0) {
						optional.get().setDsPassword(passwordEncoder.encode(createPasswordRequest.getNewPassword()));
						optional.get().setDsToken(null);
						optional.get().setDtExpiry(null);
						optional.get().setIsEmailVerified(true);
						return userRepository.save(optional.get());
					} else {
						throw new ExpiredTokenException("Token expirado");
					}
				} else {
					throw new TokenDontMatchException("Token não corresponde");
				}
			} else {
				throw new PasswordResetNotRequestException("Redefinição de senha não solicitada");
			}
			
		} else {
			throw new RegisterNotFoundException(User.class, "idUser", idUser.toString());
		}
	}
	
	public User resetPassword(String dsEmail) throws IOException {
		Optional<User> optional = userRepository
				                          .findFirstByDsEmailIgnoreCase(dsEmail);
		
		if (optional.isPresent()) {
			optional.get().setDsToken(PasswordGenerator.getRandomToken());
			optional.get().setDtExpiry(new Timestamp(PasswordGenerator.calculateExpiryDate().getTime()));
			User userResponse = userRepository.save(optional.get());
			
			emailService.sendEmailUser(userResponse, "Complete a solicitação de redefinição de senha",
			                           "email-recupera-senha");
			
			return userResponse;
			
		} else {
			throw new RegisterNotFoundException(User.class, "dsEmail", dsEmail);
		}
	}
	
	public void deleteOne(final Integer idUser) {
		
		Optional<User> optional = userRepository.findById(idUser);
		if (!optional.isPresent()) {
			throw new RegisterNotFoundException(User.class, "idUser", idUser.toString());
		}
		
		userRepository.deleteById(optional.get().getIdUser());
		
	}
}

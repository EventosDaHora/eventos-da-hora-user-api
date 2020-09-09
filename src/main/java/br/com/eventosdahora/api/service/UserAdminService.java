package br.com.eventosdahora.api.service;

import br.com.eventosdahora.api.controller.mapper.UserAdminMapper;
import br.com.eventosdahora.api.controller.request.ChangePasswordRequest;
import br.com.eventosdahora.api.controller.request.CreatePasswordRequest;
import br.com.eventosdahora.api.controller.request.UserAdminRequest;
import br.com.eventosdahora.api.entity.UserAdmin;
import br.com.eventosdahora.api.enums.ProfileEnum;
import br.com.eventosdahora.api.exception.*;
import br.com.eventosdahora.api.exception.handler.ValidationFieldException;
import br.com.eventosdahora.api.repository.UserAdminRepository;
import br.com.eventosdahora.api.service.validation.UserValidationService;
import br.com.eventosdahora.api.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserAdminService {
	
	private final UserAdminRepository userAdminRepository;
	private final ProfileService profileService;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final UserValidationService userValidationService;
	private final UserAdminMapper userAdminMapper;
	
	@Value("${email.user.admin}")
	private String emailAdmin;
	
	private final ProfileEnum profileType;
	
	public UserAdminService(UserAdminRepository userAdminRepository, ProfileService profileService,
	                        PasswordEncoder passwordEncoder, EmailService emailService,
	                        UserValidationService userValidationService,
	                        final UserAdminMapper userAdminMapper) {
		this.userAdminRepository = userAdminRepository;
		this.profileService = profileService;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.userValidationService = userValidationService;
		this.userAdminMapper = userAdminMapper;
		this.profileType = ProfileEnum.ADMIN;
	}
	
	public List<UserAdmin> findAll() {
		return userAdminRepository.findAll();
	}
	
	public UserAdmin findOne(Integer idUser) {
		Optional<UserAdmin> optional = userAdminRepository.findById(idUser);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new RegisterNotFoundException(UserAdmin.class, "idUser", idUser.toString());
		}
	}
	
	public UserAdmin save(UserAdminRequest userAdminRequest) {
		
		UserAdmin user = userAdminMapper.toEntity(userAdminRequest);
		
		Optional<UserAdmin> optionalUser = userAdminRepository.findFirstByDsEmailIgnoreCase(user.getDsEmail());
		if (optionalUser.isPresent()) {
			throw new ValidationFieldException("dsEmail", "Email já está sendo utilizado", user);
		}
		
		user.setProfile(profileService.findOneByNmProfile(this.profileType.name()));
		user.setDsToken(PasswordGenerator.getRandomToken());
		user.setDtExpiry(new Timestamp(PasswordGenerator.calculateExpiryDate().getTime()));
		user.setDtCreate(new Timestamp(new Date().getTime()));
		user.setIsEmailVerified(false);
		
		UserAdmin userResponse = userAdminRepository.save(user);
		
		emailService.sendEmailUserAdmin(userResponse, "Bem vindo ao Eventos da Hora", "email-registro");
		
		return userResponse;
	}
	
	public UserAdmin updateUser(UserAdminRequest userAdminRequest, Integer idUser) {
		
		UserAdmin user = userAdminMapper.toEntity(userAdminRequest);
		
		Optional<UserAdmin> optional = userAdminRepository.findById(idUser);
		if (!optional.isPresent()) {
			throw new RegisterNotFoundException(UserAdmin.class, "idUser", idUser.toString());
		}
		
		if (!optional.get().getDsEmail().equalsIgnoreCase(user.getDsEmail())) {
			userValidationService.validationUniqueKeyUserAdmin(user);
		}
		
		optional.get().setDsEmail(user.getDsEmail());
		optional.get().setNmUser(user.getNmUser());
		optional.get().setNuDdd(user.getNuDdd());
		optional.get().setNuCel(user.getNuCel());
		return userAdminRepository.save(optional.get());
	}
	
	public UserAdmin changePassword(Integer idUser, ChangePasswordRequest changePasswordRequest) {
		
		Optional<UserAdmin> optional = userAdminRepository.findById(idUser);
		if (!optional.isPresent()) {
			throw new RegisterNotFoundException(UserAdmin.class, "idUser", idUser.toString());
		}
		
		if (!optional.get().getIsEmailVerified()) {
			throw new EmailDontVerifiedException("Email não verificado");
		}
		
		if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), optional.get().getDsPassword())) {
			optional.get().setDsPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
			optional.get().setDsToken(null);
			optional.get().setDtExpiry(null);
			return userAdminRepository.save(optional.get());
		} else {
			throw new ValidationFieldException("oldPassword", "Senhas não conferem", changePasswordRequest);
		}
	}
	
	public UserAdmin createPassword(Integer idUser, String dsToken, CreatePasswordRequest createPasswordRequest)
			throws IOException {
		Optional<UserAdmin> optional = userAdminRepository.findById(idUser);
		
		if (optional.isPresent()) {
			if (optional.get().getDsToken() != null && optional.get().getDtExpiry() != null) {
				if (optional.get().getDsToken().equals(dsToken)) {
					Calendar cal = Calendar.getInstance();
					if ((optional.get().getDtExpiry().getTime() - cal.getTime().getTime()) > 0) {
						optional.get().setDsPassword(passwordEncoder.encode(createPasswordRequest.getNewPassword()));
						optional.get().setDsToken(null);
						optional.get().setDtExpiry(null);
						optional.get().setIsEmailVerified(true);
						return userAdminRepository.save(optional.get());
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
			throw new RegisterNotFoundException(UserAdmin.class, "idUser", idUser.toString());
		}
	}
	
	public UserAdmin resetPassword(String dsEmail) throws IOException {
		Optional<UserAdmin> optional = userAdminRepository
				                               .findFirstByDsEmailIgnoreCase(dsEmail);
		
		if (optional.isPresent()) {
			optional.get().setDsToken(PasswordGenerator.getRandomToken());
			optional.get().setDtExpiry(new Timestamp(PasswordGenerator.calculateExpiryDate().getTime()));
			UserAdmin userResponse = userAdminRepository.save(optional.get());
			
			emailService.sendEmailUserAdmin(userResponse, "Complete a solicitação de redefinição de senha",
			                                "email-recupera-senha");
			
			return userResponse;
			
		} else {
			throw new RegisterNotFoundException(UserAdmin.class, "dsEmail", dsEmail);
		}
	}
	
	public void deleteOne(final Integer idUser) {
		
		Optional<UserAdmin> optional = userAdminRepository.findById(idUser);
		if (!optional.isPresent()) {
			throw new RegisterNotFoundException(UserAdmin.class, "idUser", idUser.toString());
		}
		
		//para que não seja possível remover o usuário padrão
		if (!optional.get().getDsEmail().equalsIgnoreCase(emailAdmin)) {
			userAdminRepository.delete(optional.get());
		}
		
	}
}

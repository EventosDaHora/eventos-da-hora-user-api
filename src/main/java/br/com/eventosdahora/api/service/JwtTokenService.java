package br.com.eventosdahora.api.service;

import br.com.eventosdahora.api.entity.User;
import br.com.eventosdahora.api.entity.UserAdmin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class JwtTokenService {
	
	private String secret;
	private Long expiration;
	
	public JwtTokenService(@Value("${jwt.expiration}") Long expiration, @Value("${jwt.secret}") String secret) {
		this.secret = secret;
		this.expiration = expiration;
	}
	
	public String generateTokenUser(User user) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);
		
		List<String> userRoles = new ArrayList<>();
		HashMap<String, Object> claims = new HashMap<>();
		
		user.getProfile().getRoles().forEach(role -> userRoles.add(role.getNmRole()));
		
		claims.put("rol", userRoles);
		claims.put("idUser", user.getIdUser());
		claims.put("nmUser", user.getNmUser());
		claims.put("dsCellphone", user.getNuCel());
		
		return Jwts.builder()
		           .setClaims(claims)
		           .setSubject(user.getDsEmail())
		           .setIssuedAt(createdDate)
		           .setExpiration(expirationDate)
		           .signWith(SignatureAlgorithm.HS512, secret)
		           .compact();
	}
	
	public String generateTokenUserAdmin(UserAdmin userAdmin) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);
		
		List<String> userRoles = new ArrayList<>();
		HashMap<String, Object> claims = new HashMap<>();
		
		userAdmin.getProfile().getRoles().forEach(role -> userRoles.add(role.getNmRole()));
		
		claims.put("rol", userRoles);
		claims.put("idUser", userAdmin.getIdUser());
		claims.put("nmUser", userAdmin.getNmUser());
		
		return Jwts.builder()
		           .setClaims(claims)
		           .setSubject(userAdmin.getDsEmail())
		           .setIssuedAt(createdDate)
		           .setExpiration(expirationDate)
		           .signWith(SignatureAlgorithm.HS512, secret)
		           .compact();
	}
	
	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + expiration);
	}
}

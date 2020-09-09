package br.com.eventosdahora.api.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthenticationService {
	
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static Authentication getAuthentication(HttpServletRequest request, String secret) {
		String token = request.getHeader(HEADER_STRING);


		if (token != null) {

			// faz parse do token
			String user = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();

			List<String> roles = (List<String>) Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.get("rol");

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
			}
		}
		return null;
	}
	
}

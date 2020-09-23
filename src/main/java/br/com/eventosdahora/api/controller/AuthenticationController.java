package br.com.eventosdahora.api.controller;

import br.com.eventosdahora.api.controller.request.AuthenticationRequest;
import br.com.eventosdahora.api.controller.response.AuthenticationResponse;
import br.com.eventosdahora.api.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Api(value="authenticator", description="Authenticator API")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "autenticação para usuário", response = AuthenticationResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest request) {
		return authenticationService.generateJwtTokenUser(request);
	}
	
	@PostMapping("/admin")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "autenticação para usuário administrador", response = AuthenticationResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public AuthenticationResponse loginUserAdmin(@RequestBody AuthenticationRequest request) {
		return authenticationService.generateJwtTokenUserAdmin(request);
	}
}

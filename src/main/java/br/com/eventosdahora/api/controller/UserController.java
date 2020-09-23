package br.com.eventosdahora.api.controller;

import br.com.eventosdahora.api.controller.request.ChangePasswordRequest;
import br.com.eventosdahora.api.controller.request.CreatePasswordRequest;
import br.com.eventosdahora.api.controller.request.UserRequest;
import br.com.eventosdahora.api.entity.User;
import br.com.eventosdahora.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "user", description = "User API")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Busca todos os usuários", response = User[].class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public List<User> findAll(@RequestHeader(value = "Authorization") String authorization) {
		return userService.findAll();
	}
	
	@DeleteMapping("/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Deleta um usuário pelo id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Deleção realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public ResponseEntity<Object> deleteOne(@RequestHeader(value = "Authorization") String authorization,
	                                        @PathVariable("idUser") Integer idUser) {
		userService.deleteOne(idUser);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Busca um usuario pelo id", response = User[].class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public User findOne(@RequestHeader(value = "Authorization") String authorization,
	                    @PathVariable("idUser") Integer idUser) {
		return userService.findOne(idUser);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Cria um novo usuário", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public User save(@RequestBody @Validated UserRequest userRequest) {
		return userService.save(userRequest);
	}
	
	@PutMapping("/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza dados de um usuário", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public User updateUser(@RequestHeader(value = "Authorization") String authorization,
	                       @RequestBody @Validated UserRequest userRequest,
	                       @PathVariable("idUser") Integer idUser) {
		return userService.updateUser(userRequest, idUser);
	}
	
	@PutMapping("/password/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza a senha de um usuário", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public User editPasswordUser(@RequestHeader(value = "Authorization") String authorization,
	                             @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
	                             @PathVariable("idUser") Integer idUser) throws IOException {
		return userService.changePassword(idUser, changePasswordRequest);
	}
	
	@PutMapping("/password/create/{idUser}/{dsToken}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Cria a senha de um usuario", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public User createPassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest,
	                           @PathVariable("idUser") Integer idUser,
	                           @PathVariable("dsToken") String dsToken) throws IOException {
		return userService.createPassword(idUser, dsToken, createPasswordRequest);
	}
	
	@PostMapping("/password/reset/{dsEmail}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Reseta a senha de um usuario e envia a nova senha por email", response = User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public User resetPassword(@PathVariable("dsEmail") String dsEmail) throws IOException {
		return userService.resetPassword(dsEmail);
	}
	
}

package br.com.eventosdahora.api.controller;

import br.com.eventosdahora.api.controller.request.ChangePasswordRequest;
import br.com.eventosdahora.api.controller.request.CreatePasswordRequest;
import br.com.eventosdahora.api.controller.request.UserAdminRequest;
import br.com.eventosdahora.api.entity.UserAdmin;
import br.com.eventosdahora.api.service.UserAdminService;
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
@RequestMapping("/users-admin")
@Api(value = "users-admin", description = "Users Admin API")
public class UserAdminController {
	
	private final UserAdminService userAdminService;
	
	public UserAdminController(UserAdminService userAdminService) {
		this.userAdminService = userAdminService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Busca todos os usuários", response = UserAdmin[].class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public List<UserAdmin> findAll(@RequestHeader(value = "Authorization") String authorization) {
		return userAdminService.findAll();
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
		userAdminService.deleteOne(idUser);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Busca um usuario pelo id", response = UserAdmin[].class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public UserAdmin findOne(@RequestHeader(value = "Authorization") String authorization,
	                    @PathVariable("idUser") Integer idUser) {
		return userAdminService.findOne(idUser);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Cria um novo usuário", response = UserAdmin.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public UserAdmin save(@RequestHeader(value = "Authorization") String authorization,
	                 @RequestBody @Validated UserAdminRequest userAdminRequest) {
		return userAdminService.save(userAdminRequest);
	}
	
	@PutMapping("/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza dados de um usuário", response = UserAdmin.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public UserAdmin updateUser(@RequestHeader(value = "Authorization") String authorization,
	                       @RequestBody @Validated UserAdminRequest userAdminRequest,
	                       @PathVariable("idUser") Integer idUser) {
		return userAdminService.updateUser(userAdminRequest, idUser);
	}
	
	@PutMapping("/password/{idUser}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Atualiza a senha de um usuário", response = UserAdmin.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public UserAdmin editPasswordUser(@RequestHeader(value = "Authorization") String authorization,
	                             @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
	                             @PathVariable("idUser") Integer idUser) throws IOException {
		return userAdminService.changePassword(idUser, changePasswordRequest);
	}
	
	@PutMapping("/password/create/{idUser}/{dsToken}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Cria a senha de um usuario", response = UserAdmin.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public UserAdmin createPassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest,
	                           @PathVariable("idUser") Integer idUser,
	                           @PathVariable("dsToken") String dsToken) throws IOException {
		return userAdminService.createPassword(idUser, dsToken, createPasswordRequest);
	}
	
	@PostMapping("/password/reset/{dsEmail}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Reseta a senha de um usuario e envia a nova senha por email", response = UserAdmin.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Persistencia do objeto realizada com sucesso"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	public UserAdmin resetPassword(@PathVariable("dsEmail") String dsEmail) throws IOException {
		return userAdminService.resetPassword(dsEmail);
	}
	
}

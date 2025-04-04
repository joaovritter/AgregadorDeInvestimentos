package com.joaozao.AgregadorInvestimentos.controller;

import com.joaozao.AgregadorInvestimentos.dto.AccountResponseDto;
import com.joaozao.AgregadorInvestimentos.dto.CreateAccountDto;
import com.joaozao.AgregadorInvestimentos.dto.CreateUserDto;
import com.joaozao.AgregadorInvestimentos.dto.UpdateUserDto;
import com.joaozao.AgregadorInvestimentos.model.User;
import com.joaozao.AgregadorInvestimentos.repository.UserRepository;
import com.joaozao.AgregadorInvestimentos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }


    @GetMapping ("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable ("userId") String userId) {
        var user = userService.getUserById(userId); //pega o id do usuario la na service
        if(user.isPresent()) { //se tiver presente:
            return ResponseEntity.ok(user.get()); //retorna um ok e o usuario inteiro
        } else{
            return ResponseEntity.notFound().build(); //se nao achar retorna um 404
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping ("/{userId}")
    public ResponseEntity<Void> updateUserbyId(@PathVariable ("userId") String userId, @RequestBody UpdateUserDto updateUserDto ) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build(); //retorna nada e só builda o usuário
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable ("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build(); //retorna nada e só builda o usuário
    }


    @PostMapping ("/accounts/{userId}")
    public ResponseEntity<Void> createAccount(@PathVariable ("userId") String userId, @RequestBody CreateAccountDto createAccountDto) {
        userService.createAccount(userId,createAccountDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para listar contas de um usuário
     * Retorna um List<AccountResponseDto> para evitar expor diretamente a entidade Account
     * O DTO permite controlar exatamente quais informações são expostas
     * AccountResponseDto serve para enviar dados formatados corretamente na resposta
     */

    @GetMapping ("/accounts/{userId}")
    public ResponseEntity<List<AccountResponseDto>> listAccounts (@PathVariable ("userId") String userId) {
       var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

}

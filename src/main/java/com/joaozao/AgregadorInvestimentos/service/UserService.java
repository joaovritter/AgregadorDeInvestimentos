package com.joaozao.AgregadorInvestimentos.service;

import com.joaozao.AgregadorInvestimentos.dto.AccountResponseDto;
import com.joaozao.AgregadorInvestimentos.dto.CreateAccountDto;
import com.joaozao.AgregadorInvestimentos.dto.CreateUserDto;
import com.joaozao.AgregadorInvestimentos.dto.UpdateUserDto;
import com.joaozao.AgregadorInvestimentos.model.Account;
import com.joaozao.AgregadorInvestimentos.model.BillingAddress;
import com.joaozao.AgregadorInvestimentos.model.User;
import com.joaozao.AgregadorInvestimentos.repository.AccountRepository;
import com.joaozao.AgregadorInvestimentos.repository.BillingAddressRepository;
import com.joaozao.AgregadorInvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){
       //DTO -> ENTITY
        var entity = new User(
                null, //o banco gera o UUID automaticamente
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null,
                null
        );
        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){ //optional pois o usuario pode ou nao existir. Verificacao feita na controller
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();
            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username()); //atualizando username
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password()); //atualizando password
            }
        userRepository.save(user);
        }
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);
        var userExist = userRepository.existsById(id);
        if(userExist) {
            userRepository.deleteById(id);
        }
    }


    public void createAccount(String userId, CreateAccountDto createAccountDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        // DTO -> ENTITY
        var account = new Account(
               null,
                user,
                null,
                createAccountDto.description(),
               new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                null,
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    /**
     * Retorna a lista de contas de um usuário, convertendo para DTO
     * Isso impede a serialização infinita entre Account e User, dependência cíclica
     * Aqui, o List<Account> é convertido em List<AccountResponseDto>, evitando expor o modelo Account.
     * user.getAccounts() é a lista de contas do usuario
     * stream().map(...).toList() para converter a lista para AccountResponseDto
     * .stream(): Transforma a List<Account> em um fluxo de dados (Stream<Account>, permitindo manipular os elementos da lista de forma funcional e eficiente.
     */
    public List<AccountResponseDto> listAccounts(String userId) {
        //busca o usuário
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não existe"));

        return  user.getAccounts()// Retorna a lista de contas do usuário (List<Account>)
                .stream() /// Converte a lista em um fluxo de dados (Stream<Account>)
                .map(ac -> new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription())) // Transforma Account em DTO, extraindo somente os dados necessarios (accountId e description)
                .toList(); // Converte o Stream<AccountResponseDto> de volta para List<AccountResponseDto>

    }
}

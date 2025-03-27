package com.joaozao.AgregadorInvestimentos.service;

import com.joaozao.AgregadorInvestimentos.dto.CreateAccountDto;
import com.joaozao.AgregadorInvestimentos.dto.CreateUserDto;
import com.joaozao.AgregadorInvestimentos.dto.UpdateUserDto;
import com.joaozao.AgregadorInvestimentos.model.Account;
import com.joaozao.AgregadorInvestimentos.model.BillingAddress;
import com.joaozao.AgregadorInvestimentos.model.User;
import com.joaozao.AgregadorInvestimentos.repository.AccountRepository;
import com.joaozao.AgregadorInvestimentos.repository.BillingAddressRepository;
import com.joaozao.AgregadorInvestimentos.repository.UserRepository;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        var entity = new User(
                null, //o banco gera o UUID automaticamente
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);
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

    public void deleteById(@PathVariable("userId") String userId){
        var id = UUID.fromString(userId);
        var userExist = userRepository.existsById(id);
        if(userExist) {
            userRepository.deleteById(id);
        }
    }


    public void createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId))
                   .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //DTO -> Entity
        var account = new Account(
                null,
                user,
                createAccountDto.description(),
                null,
                new ArrayList<>()
        );
        var accountSaved = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountSaved.getAccountId(),
                createAccountDto.street(),
                createAccountDto.number(),
                account
        );
        billingAddressRepository.save(billingAddress);
    }
}

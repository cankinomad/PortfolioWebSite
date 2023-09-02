package org.berka.service;

import org.berka.dto.register.LoginRequestDto;
import org.berka.dto.register.UpdateRequestDto;
import org.berka.dto.register.UserRegisterDto;
import org.berka.exception.ErrorType;
import org.berka.exception.UserManagerException;
import org.berka.mapper.IUserMapper;
import org.berka.repository.IUserRepository;
import org.berka.repository.entity.User;
import org.berka.utility.JwtTokenManager;
import org.berka.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, Long> {

    private final IUserRepository repository;

    private final JwtTokenManager jwtTokenManager;

    public UserService(IUserRepository repository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public String register(UserRegisterDto dto) {
        User user = IUserMapper.INSTANCE.toUser(dto);

        Boolean emailExists = repository.existsByEmail(user.getEmail());
        if (emailExists) {
            throw new UserManagerException(ErrorType.EMAIL_TAKEN);
        }

        try {
            save(user);


            return user.getFirstName()+" kisisi basariyla kaydedildi";
        }catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public String login(LoginRequestDto dto) {
        Optional<User> optionalUser = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if(optionalUser.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        Optional<String> token = jwtTokenManager.createToken(optionalUser.get().getId());
        if(token.isEmpty()){
            throw  new UserManagerException(ErrorType.TOKEN_NOT_CREATED);
        }

        return "Hosgeldin "+optionalUser.get().getFirstName()+"\nIslem yapmak icin gerekli tokenin : "+ token.get();
    }

    public String updateUser(UpdateRequestDto dto) {
        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(dto.getToken().trim());
        if (optionalId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }

        Optional<User> optionalUser = findById(optionalId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        Boolean existsedByEmail = repository.existsByEmail(dto.getEmail());

        if (existsedByEmail && !optionalUser.get().getEmail().equals(dto.getEmail())) {
            throw new UserManagerException(ErrorType.EMAIL_TAKEN);
        }

        optionalUser.get().setEmail(dto.getEmail());
        optionalUser.get().setPassword(dto.getPassword());
        optionalUser.get().setFirstName(dto.getFirstName());
        optionalUser.get().setLastName(dto.getLastName());

        update(optionalUser.get());
        return "Bilgileriniz guncellenmistir";
    }


    public Boolean deleteUser(String token) {

        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(token);

        if (optionalId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }

        Optional<User> optionalUser = findById(optionalId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        try {
            deleteById(optionalUser.get().getId());
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.USER_DELETE_FAILED);
        }

        return true;

    }

    public User findUserById(Long id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        return optionalUser.get();
    }
}

package com.api.evaluacion.service;

import com.api.evaluacion.model.User;
import com.api.evaluacion.rest.request.CreateRequest;
import com.api.evaluacion.rest.request.UpdateRequest;
import com.api.evaluacion.rest.response.CreateResponse;
import com.api.evaluacion.rest.response.ErrorException;
import com.api.evaluacion.rest.response.LoginResponse;
import com.api.evaluacion.repository.PhoneRepository;
import com.api.evaluacion.repository.UserRepository;
import com.api.evaluacion.rest.response.UserResponse;
import com.api.evaluacion.utils.TokenGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;


@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final TokenGenerator tokenGenerator = TokenGenerator.getInstance();


    public CreateResponse create(CreateRequest request) throws ErrorException {
        CreateResponse response = new CreateResponse();
        try {
            Optional<User> first = userRepository.findByEmail(request.getEmail()).stream().findFirst();
            if (first.isPresent())
                throw new ErrorException(100, "Usuario ya existe");

            User user = User.builder()
                    .active(true)
                    .created(new Date())
                    .token(tokenGenerator.generateToken(request.getEmail(), request.getPassword()))
                    .lastLogin(new Date()).build();

            modelMapper.map(request, user);
            user.addPhones();

            user = userRepository.save(user);
            modelMapper.map(user, response);
            response.setId(user.getId());

        } catch (ErrorException errorException) {
            throw errorException;
        } catch (Exception ex) {
            throw new ErrorException(ex.hashCode(), ex.getMessage());
        }
        return response;
    }

    @Override
    public void delete(String email) throws ErrorException {
        Optional<User> first = userRepository.findByEmail(email).stream().findFirst();
        if (!first.isPresent())
            throw new ErrorException(100, "Usuario no existe");
        userRepository.delete(first.get());
    }

    @Override
    public void update(UpdateRequest request) throws ErrorException {

        User userIn= new User();

        Optional<User> first = userRepository.findByEmail(request.getEmail()).stream().findFirst();
        if (!first.isPresent())
            throw new ErrorException(100, "Usuario no existe");

        modelMapper.map(request, userIn);

        User user = first.get();
        user.setActive(userIn.isActive());
        user.setPhones(userIn.getPhones());
        user.addPhones();
        user.setName(userIn.getName());
        user.setPassword(user.getPassword());
        user.setCreated(userIn.getCreated());
        user.setLastLogin(userIn.getLastLogin());
        userRepository.save(user);



    }

    @Override
    public UserResponse read(String email) throws ErrorException {
        Optional<User> first = userRepository.findByEmail(email).stream().findFirst();
        UserResponse userResponse= new UserResponse();
        if (!first.isPresent())
            throw new ErrorException(100, "Usuario no existe");
        modelMapper.map(first.get(),userResponse);
        return userResponse;

    }

}

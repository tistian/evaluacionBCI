package com.api.evaluacion.rest;

import com.api.evaluacion.rest.request.CreateRequest;
import com.api.evaluacion.rest.request.UpdateRequest;
import com.api.evaluacion.rest.response.CreateResponse;
import com.api.evaluacion.rest.response.ErrorException;
import com.api.evaluacion.rest.response.LoginResponse;
import com.api.evaluacion.rest.response.UserResponse;
import com.api.evaluacion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/evaluacion")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> create(@RequestBody @Valid CreateRequest request) {
        CreateResponse createResponse;
        createResponse = userService.create(request);
        return new ResponseEntity<CreateResponse>(createResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateRequest request) {
        CreateResponse createResponse = new CreateResponse();
        userService.update(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestHeader(value = "email", required = false) String email) {

        LoginResponse loginResponse = new LoginResponse();
        if (email == null || email.isEmpty()) throw new ErrorException(150, "Email no puede ser nulo ni vacio");
        userService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<UserResponse> read(@RequestHeader(value = "email", required = true) String email) {

        if (email == null || email.isEmpty()) throw new ErrorException(150, "Email no puede ser nulo ni vacio");
        UserResponse user = userService.read(email);
        return new ResponseEntity<UserResponse>(user, HttpStatus.OK);
    }

}

package com.api.evaluacion.service;

import com.api.evaluacion.model.User;
import com.api.evaluacion.rest.request.CreateRequest;
import com.api.evaluacion.rest.request.UpdateRequest;
import com.api.evaluacion.rest.response.CreateResponse;
import com.api.evaluacion.rest.response.ErrorException;
import com.api.evaluacion.rest.response.UserResponse;

public interface UserService {

    public CreateResponse create(CreateRequest request) throws ErrorException;
    public void delete(String email) throws ErrorException;
    public void update(UpdateRequest request) throws ErrorException;
    public UserResponse read(String email) throws ErrorException;


}

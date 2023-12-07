package com.api.evaluacion.rest.response;

import com.api.evaluacion.rest.request.PhoneRequest;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private Date created;
    private Date lastLogin;

    private String token;
    private String name;
    private String email;
    private String password;

    private List<PhoneRequest> phones;

}

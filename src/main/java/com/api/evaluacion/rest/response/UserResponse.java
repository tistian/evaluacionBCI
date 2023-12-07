package com.api.evaluacion.rest.response;

import com.api.evaluacion.model.Phone;
import com.api.evaluacion.rest.request.PhoneRequest;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

        private Long id;
        private String name;
        private String email;
        private String password;

        List<PhoneRequest> phones;
        private Date created;
        private Date lastLogin;
        private String token;
        private boolean active;
}

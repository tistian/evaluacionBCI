package com.api.evaluacion.rest.response;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateResponse {
    private Long id;

    private Date created;
    private Date lastLogin;


    private String token;

    private boolean isActive;
    @Override
    public String toString() {
        return "CreateResponse{" +
                "id=" + id +
                ", created=" + created +
                ", lastLogin=" + lastLogin +
                ", token='" + token + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

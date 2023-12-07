package com.api.evaluacion.rest.request;

import com.api.evaluacion.model.User;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRequest {
    private Long number;
    private int citycode;
    private String contrycode;

}

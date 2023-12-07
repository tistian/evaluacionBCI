package com.api.evaluacion.rest.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInErrorResponse {

    private List<ErrorException> error;

}

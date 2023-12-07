package com.api.evaluacion.rest.request;



import com.api.evaluacion.utils.Constants;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequest {


  private String name;
    @NotNull(message = "email no puede ser Nulo")
    @Pattern(regexp = Constants.REGEXP_EMAIL,message = "Formato de correo incorrecto")
    private String email;
    @NotNull(message = "password no puede ser Nulo")
    @Pattern(regexp = Constants.REGEXP_PASSWORD,message = "Formato de password incorrecto")
    private String password;

    List<PhoneRequest> phones;

}

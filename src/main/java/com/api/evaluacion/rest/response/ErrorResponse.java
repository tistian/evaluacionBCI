package com.api.evaluacion.rest.response;

import lombok.*;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Timestamp timestamp;
    private int codigo;
    private String detail;
    private List<CampoError> fieldErrors;

    public ErrorResponse(int codigo, String detail, Timestamp timestamp) {
        this.codigo = codigo;
        this.detail = detail;
        this.timestamp = timestamp;
        this.fieldErrors= new ArrayList<>();
    }

    public void addFieldError(String fielName, String message) {
        CampoError error = new CampoError(fielName, message);
        fieldErrors.add(error);
    }


}

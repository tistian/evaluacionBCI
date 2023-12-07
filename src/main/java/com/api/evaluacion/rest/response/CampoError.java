package com.api.evaluacion.rest.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CampoError {
    private String fieldName;
    private String errorMessage;

    public CampoError(String fieldName,String errorMessage)
    {
        this.fieldName=fieldName;
        this.errorMessage= errorMessage;
    }
}

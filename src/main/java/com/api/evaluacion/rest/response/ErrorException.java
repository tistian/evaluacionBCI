package com.api.evaluacion.rest.response;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorException extends RuntimeException {

    private Timestamp timestamp;
    private int codigo;
    private String detail;

    public ErrorException(int codigo, String detail) {
        this.codigo = codigo;
        this.detail = detail;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.codigo, this.detail, this.timestamp);
    }
}

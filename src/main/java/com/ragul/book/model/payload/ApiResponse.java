package com.ragul.book.model.payload;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private T payload;
    private int status;
    private String message;
    private String error;


    public ApiResponse(T payload) {
        this.payload = payload;
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
    }

    public ApiResponse(T payload, HttpStatus success) {
        this.payload = payload;
        this.status = success.value();
        this.message = success.name();
    }

    public ApiResponse(T payload, HttpStatus status, String message) {
        this.payload = payload;
        this.status = status.value();
        this.error = status.name();
        this.message = message;
    }

    public ApiResponse(HttpStatus status, String message) {
        this.message = message;
        if (status.isError()) {
            this.error = status.name();
        }
        this.status = status.value();
    }

}

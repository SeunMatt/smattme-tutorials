package com.smattme.testcontainers.manualconfig.responses;

import reactor.core.publisher.Mono;

public class GenericResponse <T> {

    private boolean status;
    private String message;
    private T data;

    public GenericResponse() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public static <T> Mono<GenericResponse<T>> success(T data) {
        GenericResponse<T> response = new GenericResponse<>();
        response.setData(data);
        response.setMessage("Operation Successful");
        response.setStatus(true);
        return Mono.just(response);
    }


    public static <T> Mono<GenericResponse<T>> error(String message) {
        GenericResponse<T> response = new GenericResponse<>();
        response.setMessage(message);
        response.setStatus(false);
        return Mono.just(response);
    }




}

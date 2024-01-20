
package com.smattme.apikey.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@ToString
public class Response<T> {

    private int code;
    private String message;
    boolean success;
    private T data;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response() {
        this.code = HttpStatus.OK.value();
        this.message = "Operation Successful";
    }

    public static <T> Response<T> failedResponse(String message) {
        return failedResponse(HttpStatus.BAD_REQUEST, message, null);
    }

    public static <T> Response<T> failedResponse(HttpStatus httpStatus, String message) {
        return failedResponse(httpStatus, message, null);
    }

    public static <T> Response<T> failedResponse(HttpStatus httpStatus, String message, T data) {
        Response<T> response = new Response<>(httpStatus.value(), message);
        response.setSuccess(false);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> successfulResponse(String message, T data) {
        return successfulResponse(HttpStatus.OK, message, data);
    }

    public static <T> Response<T> successfulResponse(HttpStatus httpStatus, String message, T data) {
        Response<T> response = new Response<>(httpStatus.value(), message);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}

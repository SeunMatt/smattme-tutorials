package com.smattme.springbootrequestvalidator.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {

    private boolean status;
    private String message;
    private List<String> errors;
    private int code;
    private Object data;

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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static GenericResponse genericValidationErrorsObj(List<String> errors) {
        return genericErrorResponseObj(HttpStatus.BAD_REQUEST, "Missing required parameter(s)", errors);
    }

    public static GenericResponse genericErrorResponseObj(HttpStatus status, String message) {
        return genericErrorResponseObj(status, message, Collections.singletonList(message));
    }

    public static GenericResponse genericErrorResponseObj(HttpStatus status, String message, List<String> errors) {
        return genericErrorResponseObj(status, message, errors, null);
    }

    public static GenericResponse genericErrorResponseObj(HttpStatus status, String message, List<String> errors, Object data) {
        GenericResponse response = new GenericResponse();
        response.setStatus(false);
        response.setCode(status.value());
        response.setMessage(message);
        response.setErrors(errors);
        if (!Objects.isNull(data)) response.setData(data);
        return response;
    }

    public static GenericResponse generic200ResponseObj(String message) {
        return generic200ResponseObj(message, null);
    }

    public static GenericResponse generic200ResponseObj(Object data) {
        return generic200ResponseObj("Operation successful", data);
    }

    public static GenericResponse generic200ResponseObj(String message, Object data) {
        GenericResponse response = new GenericResponse();
        response.setStatus(true);
        response.setCode(HttpStatus.OK.value());
        response.setMessage(((message != null && !message.isEmpty()) ? message : "Operation Successful"));
        if(data != null) response.setData(data);
        return response;
    }

}

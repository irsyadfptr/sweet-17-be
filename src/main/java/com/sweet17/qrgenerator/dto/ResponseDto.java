package com.sweet17.qrgenerator.dto;



public class ResponseDto {
    // Attributes
    private String status = "200";
    private String message;

    // Empty Constructor
    public ResponseDto() {
    }

    // Constructor with parameters
    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto(String message, Integer status) {
        this.status = status.toString();
        this.message = message;
    }

    // Getter and Setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status.toString();
    }

    // toString
    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
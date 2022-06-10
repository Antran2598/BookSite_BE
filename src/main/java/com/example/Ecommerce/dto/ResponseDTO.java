package com.example.Ecommerce.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResponseDTO {

    private String errorCode;

    private Object data;

    private String successCode;

}

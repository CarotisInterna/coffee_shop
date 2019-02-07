package ru.popova.practice.shop.dto;

import java.util.ArrayList;
import java.util.List;

public class ListErrorDto {

    private List<ErrorDto> errorDtos = new ArrayList<>();

    public void addErrorDto(String field, String message) {
        ErrorDto errorDto = new ErrorDto(field, message);
        errorDtos.add(errorDto);
    }
}

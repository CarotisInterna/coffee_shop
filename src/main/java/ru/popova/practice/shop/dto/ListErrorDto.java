package ru.popova.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListErrorDto {

    private List<ErrorDto> errorDtos = new ArrayList<>();

    public void addErrorDto(String field, String message) {
        ErrorDto errorDto = new ErrorDto(field, message);
        errorDtos.add(errorDto);
    }
}

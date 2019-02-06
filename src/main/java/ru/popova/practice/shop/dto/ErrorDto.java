package ru.popova.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    List<String> message = new ArrayList<>();

    public ErrorDto(String ... errors)  {
        message.addAll(Arrays.asList(errors));
    }
}

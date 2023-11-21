package com.pifrans.exampleproject.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonErroDto {
    private String message;
    private String path;
}

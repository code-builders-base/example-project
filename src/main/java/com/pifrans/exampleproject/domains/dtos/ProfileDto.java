package com.pifrans.exampleproject.domains.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileDto {


    @Getter
    @Setter
    public static class Full {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    public static class Save {
        private String name;
    }
}

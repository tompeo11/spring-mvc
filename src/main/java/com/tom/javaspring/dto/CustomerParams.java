package com.tom.javaspring.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CustomerParams extends PaginationParams{
    private String search;
    private String sort;
}

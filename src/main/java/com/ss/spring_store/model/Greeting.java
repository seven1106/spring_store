package com.ss.spring_store.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Greeting {
    private long id;
    private String content;
}

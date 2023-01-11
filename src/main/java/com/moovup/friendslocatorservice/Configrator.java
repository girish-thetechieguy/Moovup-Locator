package com.moovup.friendslocatorservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("com.moovup")
@Getter
@Setter
public class Configrator {
    private String endpoint;

    private String token;

}

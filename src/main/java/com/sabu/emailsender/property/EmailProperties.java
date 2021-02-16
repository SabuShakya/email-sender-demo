package com.sabu.emailsender.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class EmailProperties {
    private String host;

    private String username;

    private String password;

    private int port;

    private String protocol;
}

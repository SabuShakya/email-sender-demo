package com.sabu.emailsender.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Mail {

    private String from;
    private String to;
    private String subject;
    private String content;
}

package org.example.app;

import org.example.infrastructure.annotation.Log;

public interface EmailSender {

    void send(String to, String subject, String body);
}

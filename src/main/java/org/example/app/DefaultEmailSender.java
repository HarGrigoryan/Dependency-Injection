package org.example.app;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Log;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;

@Log
@Scope(scopeType = ScopeType.PROTOTYPE)
@Component
public class DefaultEmailSender implements EmailSender {

    @Override
    public void send(String to, String subject, String body) {
        System.out.printf(
                "\u001B[34mSending email to %s. Subject: %s. Body: %s\u001B[0m\n",
                to,
                subject,
                body
        );
    }
}

package org.example.app;

import org.example.infrastructure.annotation.Log;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;

@Log
@Scope(scopeType = ScopeType.PROTOTYPE)
public class DefaultEmailSender implements EmailSender {

    @Override
    public void send(String to, String subject, String body) {
        System.out.printf(
                "Sending email to %s. Subject: %s. Body: %s\n",
                to,
                subject,
                body
        );
    }
}

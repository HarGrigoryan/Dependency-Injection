package org.example.app;

import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;

@Scope(scopeType = ScopeType.PROTOTYPE)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

package org.example.app;


import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;

@Scope(scopeType = ScopeType.PROTOTYPE)
@Component
public class UserService {

    @Inject
    private UserRepository userRepository;
}

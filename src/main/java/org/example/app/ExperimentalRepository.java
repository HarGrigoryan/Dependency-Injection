package org.example.app;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;

import java.util.List;

@Scope(scopeType = ScopeType.PROTOTYPE)
@Component
public class ExperimentalRepository implements UserRepository{

    @Override
    public void save(User user) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}

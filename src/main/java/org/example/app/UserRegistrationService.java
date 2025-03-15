package org.example.app;

import org.example.infrastructure.annotation.CacheKey;
import org.example.infrastructure.annotation.Cacheable;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.Log;


public class UserRegistrationService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private EmailSender emailSender;

    @Log
    public void register( User user) {
        User existingUser = userRepository.getUser(user.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExistsException(
                    "User is already registered. Username: " + user.getUsername()
            );
        }

        userRepository.save(user);
        //System.out.println("Second: " + userRepository.getUser(user.getUsername()));//Experiment
        emailSender.send(
                user.getEmail(),
                "Account confirmation",
                "Please confirm your newly created account"
        );
    }
}

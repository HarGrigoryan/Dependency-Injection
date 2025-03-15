package org.example.app;

import org.example.infrastructure.Application;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Property;

public class Main {
    @Property()
    private String dataSourcePassword;//experiment
    public static void main(String[] args) {

        ApplicationContext context = Application.run("org.example");
        //experiment for @Property
        Main m = context.getObject(Main.class);
        m.experiment();

        UserRegistrationService registrationService = context.getObject(UserRegistrationService.class);

        registrationService.register(
                new User(
                        "Gurgen",
                        "gurgen@inconceptlabsc.com",
                        "password123"
                )
        );


        registrationService.register(
                new User(
                        "Harutyun",
                        "harutyun@inconceptlabsc.com",
                        "password124"
                )
        );
    }

    public void experiment()
    {
        System.out.println("Value from properties is: " + dataSourcePassword);
    }
}
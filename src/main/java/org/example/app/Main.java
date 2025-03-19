package org.example.app;

import org.example.infrastructure.Application;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.*;

@Scope(scopeType = ScopeType.PROTOTYPE)
@Component
public class Main {
    @Property()
    private String dataSourcePassword;//experiment

    @Env()
    private String DriverData; //experiment

    @Inject
    @Qualifier(implementationClassType = ExperimentalRepository.class)
    private UserRepository uR;

    public static void main(String[] args) {

        ApplicationContext context = Application.run("org.example");
        //experiments
        Main m = context.getObject(Main.class);
        m.propertyExperiment();
        m.envExperiment();
        m.qualifierCheck();
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
        //Experiment for Cacheable (classes)
        System.out.println(m.getMessage("Test"));
        System.out.println(m.getMessage("Test"));
    }

    public void propertyExperiment()
    {
        System.out.println("@Property test: Value from properties is: " + dataSourcePassword);
    }

    public void envExperiment()
    {
        System.out.println("@Env test: Value from environment variables with the given name is: " + DriverData);
    }

    @Cacheable
    public String getMessage(@CacheKey String message)
    {
        return message;
    }

    public void qualifierCheck()
    {
        System.out.println("@Qualifier test: " + uR.getClass());
    }
}
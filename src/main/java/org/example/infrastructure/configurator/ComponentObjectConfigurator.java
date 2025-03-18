package org.example.infrastructure.configurator;

import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.exception.NotAComponentException;

public class ComponentObjectConfigurator implements ObjectConfigurator{

    @Override
    public void configure(Object obj, ApplicationContext context) {
        if(!context.getObjectConfigReader().isComponent(obj.getClass()))
            throw new NotAComponentException(obj.getClass() + " is not a component of the DI framework!");
        System.out.println("@Component test: " + obj.getClass() + " is a component.");
    }
}

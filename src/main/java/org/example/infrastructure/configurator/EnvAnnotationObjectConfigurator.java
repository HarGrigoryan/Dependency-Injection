package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Env;
import org.example.infrastructure.annotation.Property;

import java.lang.reflect.Field;

public class EnvAnnotationObjectConfigurator implements ObjectConfigurator{
    @SneakyThrows
    @Override
    public void configure(Object obj, ApplicationContext context) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            Env annotation = field.getAnnotation(Env.class);
            if(annotation != null) {
                String envName = annotation.variableName();
                if(envName.isEmpty())
                    envName = field.getName();
                String envValue = System.getenv(envName);
                field.setAccessible(true);
                field.set(obj, envValue);
            }
        }
    }
}

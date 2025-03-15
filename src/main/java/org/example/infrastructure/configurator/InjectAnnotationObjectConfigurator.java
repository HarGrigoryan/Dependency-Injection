package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.PostConstruct;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                field.set(obj, context.getObject(field.getType()));
            }
        }
        //@PostConstruct implementation
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class))
            {
                method.setAccessible(true);
                method.invoke(obj, (Object[]) null);
            }
        }
    }
}

package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.PostConstruct;
import org.example.infrastructure.annotation.Qualifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> cls = field.getType();
                Qualifier q = field.getAnnotation(Qualifier.class);
                if(q != null){
                    cls = q.implementationClassType();
                    System.out.println("@Qualifier test: Using the specified implementation: " + cls);
                }
                field.setAccessible(true);
                field.set(obj, context.getObject(cls));
            }
        }
    }
}

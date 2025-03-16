package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.annotation.PostConstruct;

import java.lang.reflect.Method;

public class PostConstructAnnotationObjectConfigurator{

    @SneakyThrows
    public void configure(Object obj) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class))
            {
                method.setAccessible(true);
                method.invoke(obj, (Object[]) null);
            }
        }
    }
}

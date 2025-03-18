package org.example.infrastructure;

import lombok.SneakyThrows;
import org.example.infrastructure.annotation.PostConstruct;
import org.example.infrastructure.exception.PostConstructorHasArgumentsException;

import java.lang.reflect.Method;

public class PostConstructAnnotationHandler {

    @SneakyThrows
    public void configure(Object obj) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class))
            {
                if(method.getParameterCount() == 0) {
                    method.setAccessible(true);
                    method.invoke(obj, (Object[]) null);
                }
                else
                    throw new PostConstructorHasArgumentsException(method.getName() + " should not have any arguments.");
            }
        }
    }
}

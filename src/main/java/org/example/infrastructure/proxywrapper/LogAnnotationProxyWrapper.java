package org.example.infrastructure.proxywrapper;

import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LogAnnotationProxyWrapper implements ProxyWrapper {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T wrap(T obj, Class<T> cls) {
        if (!cls.isAnnotationPresent(Log.class)) {
            if (cls.getInterfaces().length != 0) {
                return (T) Proxy.newProxyInstance(
                        cls.getClassLoader(),
                        cls.getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                Method originalMethod = cls.getMethod(method.getName(), method.getParameterTypes());
                                return log(method, args, originalMethod, obj, cls);
                            }
                        }
                );
            }
            return (T) Enhancer.create(
                    cls,
                    new net.sf.cglib.proxy.InvocationHandler() {
                        @Override
                        public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                            return log(method, args, method, obj, cls);
                        }

                    }
            );

        }

        if (cls.getInterfaces().length != 0) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            logToConsole(method, args, cls);
                            return method.invoke(obj, args);
                        }
                    }
            );
        }

        return (T) Enhancer.create(
                cls,
                new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                        logToConsole(method, args, cls);
                        return method.invoke(obj, args);
                    }

                }
        );
    }

    private static <T> Object log(Method method, Object[] args, Method originalMethod, T obj, Class<T> cls) throws IllegalAccessException, InvocationTargetException {
        if (!originalMethod.isAnnotationPresent(Log.class))
            return method.invoke(obj, args);
        logToConsole(method, args, cls);
        return method.invoke(obj, args);
    }

    private static void logToConsole(Method method, Object[] args, Class<?> cls) {
        System.out.printf(
                "@Logging: Type: %s Calling method: %s. Args: %s\n", cls.getName(), method.getName(), Arrays.toString(args));
    }
}

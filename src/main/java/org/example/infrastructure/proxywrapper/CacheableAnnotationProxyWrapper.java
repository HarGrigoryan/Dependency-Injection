package org.example.infrastructure.proxywrapper;

import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.annotation.CacheKey;
import org.example.infrastructure.annotation.Cacheable;
import org.example.infrastructure.exception.CacheKeyNotSpecifiedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;

public class CacheableAnnotationProxyWrapper implements ProxyWrapper{

    private final HashMap<String, Object> cache = new HashMap<>();
    @Override
    @SuppressWarnings("unchecked")
    public <T> T wrap(T obj, Class<T> cls) {

        if (cls.getInterfaces().length != 0) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Method originalMethod = cls.getMethod(method.getName(), method.getParameterTypes());
                            return cache(originalMethod, args, obj);
                        }
                    }
            );
        }
        else
        {
            return (T) Enhancer.create(
                    cls,
                    new net.sf.cglib.proxy.InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Method originalMethod = cls.getMethod(method.getName(), method.getParameterTypes());
                            return cache(method, args, obj);
                            /*if(!originalMethod.isAnnotationPresent(Cacheable.class))
                                return method.invoke(obj, args);
                            String key = buildKey(method.getParameterAnnotations(), method, args);

                            if (key.length() == 0)
                                throw new CacheKeyNotSpecifiedException("For @Cacheable to work one of the parameters must be marked with @CacheKey");
                            if (cache.containsKey(key)) {
                                System.out.println("@Cacheable test: Working with classes: Method name: " + method.getName() + ". Parameters: " + Arrays.toString(args) + ". Using the cached information.");
                                return method.getReturnType().cast(cache.get(key));
                            } else {
                                Object o = method.invoke(obj, args);
                                cache.put(key, o);
                                System.out.println("@Cacheable test: Working with classes: Method name: " + method.getName() + ". Parameters: " + Arrays.toString(args) + ". Saved in cache.");
                                return method.getReturnType().cast(o);
                            }*/
                        }
                    }
            );
        }
    }

    private <T> Object cache(Method originalMethod, Object[] args, T obj) throws IllegalAccessException, InvocationTargetException {
        if(!originalMethod.isAnnotationPresent(Cacheable.class))
            return originalMethod.invoke(obj, args);
        String key = buildKey(originalMethod.getParameterAnnotations(), originalMethod, args);
        if(key.length() == 0)
            throw new CacheKeyNotSpecifiedException("For @Cacheable to work one of the parameters must be marked with @CacheKey");
        if (cache.containsKey(key)) {
            System.out.println("@Cacheable test: Working with interfaces: Method name: " + originalMethod.getName() + ". Parameters: " + Arrays.toString(args) + ". Using the cached information.");
            return originalMethod.getReturnType().cast(cache.get(key));
        } else {
            Object o = originalMethod.invoke(obj, args);
            cache.put(key, o);
            System.out.println("@Cacheable test: Working with interfaces: Method name: " + originalMethod.getName() + ". Parameters: " + Arrays.toString(args) + ". Saved in cache.");
            return originalMethod.getReturnType().cast(o);
        }
    }

    // Helper method to check for @CacheKey annotation
    private boolean hasCacheKeyAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof CacheKey) {
                return true;
            }
        }
        return false;
    }

    //Helper method to build the key
    private String buildKey(Annotation[][] parameterAnnotations, Method method, Object[] args)
    {
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            if (hasCacheKeyAnnotation(parameterAnnotations[i])) {
                keyBuilder.append(method.getName());
                keyBuilder.append('-');
                keyBuilder.append(args[i]);
                break;
            }
        }
        return keyBuilder.toString();
    }
}

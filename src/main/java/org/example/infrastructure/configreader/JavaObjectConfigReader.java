package org.example.infrastructure.configreader;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.annotation.ScopeType;
import org.example.infrastructure.exception.InterfaceHasMultipleImplementationsException;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.Set;

public class JavaObjectConfigReader implements ObjectConfigReader {

    private Reflections reflections;

    public JavaObjectConfigReader(String packageToScan) {
        this.reflections = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> cls) {
        if (!cls.isInterface()) {
            return cls;
        }

        Set<Class<? extends T>> subTypesOf =
                reflections.getSubTypesOf(cls);

        if (subTypesOf.size() != 1) {
            throw new InterfaceHasMultipleImplementationsException("Interface has multiple implementations.");
        }

        return subTypesOf.iterator().next();
    }

    @Override
    public <T> Collection<Class<? extends T>> getImplClasses(Class<T> cls) {
        return reflections.getSubTypesOf(cls);
    }

    @Override
    public <T> boolean isComponent(Class<T> cls) {
        return cls.isAnnotationPresent(Component.class);
    }

    @Override
    public <T> boolean isSingleton(Class<T> cls) {
        Scope annotation = cls.getAnnotation(Scope.class);
        boolean exp = annotation==null || annotation.scopeType() == ScopeType.SINGLETON;
        if(!exp)
            System.out.println("@Scope testing: " + cls + " is not a Singleton.");
        return exp;
    }

    @Override
    public <T> boolean isSubtype(Class<T> superType, Class<?> subType) {
        return superType.isAssignableFrom(subType);
    }
}

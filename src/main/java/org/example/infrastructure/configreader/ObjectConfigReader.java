package org.example.infrastructure.configreader;

import java.util.Collection;
import java.util.List;

public interface ObjectConfigReader {

    <T> Class<? extends T> getImplClass(Class<T> cls);

    <T> Collection<Class<? extends T>> getImplClasses(Class<T> cls);

    <T> boolean isComponent(Class<T> cls);

    <T> boolean isSingleton(Class<T> cls);

    <T> boolean isSubtype(Class<T> superType, Class<?> subType);
}

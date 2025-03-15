package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Property;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PropertyAnnotationObjectConfigurator implements ObjectConfigurator{
    @SneakyThrows
    @Override
    public void configure(Object obj, ApplicationContext context) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            Property annotation = field.getAnnotation(Property.class);
            if(annotation != null)
            {
                Map<String, String> properties = readProperties("C:\\Users\\Harutyun\\Desktop\\JobHunting\\InConcept Labs\\Internship\\Homework\\Lesson1\\Project\\java-dependency-injection\\resources\\application.properties");
                String propertyName = annotation.propertyName();
                if(propertyName.isEmpty())
                    propertyName = field.getName();
                String propertyValue = properties.get(propertyName);
                field.setAccessible(true);
                field.set(obj, propertyValue);
            }
        }
    }

    private Map<String, String> readProperties(String fileName)
    {
        Path path = Paths.get(fileName);
        Map<String, String> properties=null;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            properties = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("=");
                properties.put(strings[0], strings[1]);
            }
            return properties;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }
}

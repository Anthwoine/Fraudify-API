package fr.antoine.fraudify.utils;

import java.lang.reflect.Field;


public class Patcher {
    public static void patchObject(Object source, Object patchSource) throws NoSuchFieldException {
        Class<?> internClass = source.getClass();
        Field[] fields = internClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(patchSource, value);
                }
            } catch (IllegalAccessException e) {
                throw new NoSuchFieldException("Field not found");
            } finally {
                field.setAccessible(false);
            }
        }
    }
}

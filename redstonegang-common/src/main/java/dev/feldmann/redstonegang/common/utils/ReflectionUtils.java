package dev.feldmann.redstonegang.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static List<Field> getFieldsWithAnnotation(Class classe, Class<? extends Annotation> an) {
        List<Field> list = new ArrayList<Field>();

        for (Field f : classe.getDeclaredFields()) {
            Annotation annotation = f.getAnnotation(an);
            if (annotation != null) {
                f.setAccessible(true);
                list.add(f);
            }
        }
        return list;
    }
}


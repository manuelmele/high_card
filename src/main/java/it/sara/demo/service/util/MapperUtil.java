package it.sara.demo.service.util;
import it.sara.demo.exception.GenericException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import static java.util.Optional.ofNullable;

public class MapperUtil {

    /**
     * Maps an object of any class to a new instance of targetClass.
     *
     * @param targetClass The class to map to
     * @param source      The source object
     * @param <T>         Type of the target class
     * @return A new instance of targetClass with copied properties
     */
    @SneakyThrows
    public static <T> T map(Class<T> targetClass, Object source) {
        if (ofNullable(source).isEmpty()) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new GenericException(500, "Failed to map " + source.getClass() + " to " + targetClass);
        }
    }
}

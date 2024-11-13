package br.com.actionlabs.carboncalc.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> clazz, String id) {
        super(String.format("Resource of type %s with id %s not found", clazz.getSimpleName(), id));
    }
}

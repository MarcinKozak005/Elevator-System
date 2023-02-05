package org.example.utils;

import java.util.function.Predicate;

public class IntValidator {
    private final Predicate<Integer> predicate;
    private final String errorMessage;

    public IntValidator(Predicate<Integer> predicate, String errorMessage){
        this.predicate = predicate;
        this.errorMessage = errorMessage;
    }

    public void validate(Integer value) throws ValidationException {
        if (this.predicate.test(value)){
            throw new ValidationException(errorMessage);
        }
    }

    public static IntValidator emptyValidator(){
        return new IntValidator(value -> false, "");
    }
}

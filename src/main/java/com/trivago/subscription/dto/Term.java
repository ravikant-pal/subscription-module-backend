package com.trivago.subscription.dto;

import java.util.stream.Stream;

public enum Term implements IdBasedEnum {
    MONTHLY(1),
    YEARLY(2);

    private final int id;

    Term(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public static Term instanceOf(String name) {
        if (name != null) {
            for (Term s : values()) {
                if (s.name().equals(name))
                    return s;
            }
        }
        return null;
    }

    public static Term of(int id) {
        return Stream.of(values())
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

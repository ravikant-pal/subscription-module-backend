package com.trivago.subscription.dto;

import java.util.stream.Stream;

public enum Status implements IdBasedEnum {
    ACTIVE(1),
    EXPIRED(-1),
    CANCELED(0);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public static Status instanceOf(String name) {
        if (name != null) {
            for (Status s : values()) {
                if (s.name().equals(name))
                    return s;
            }
        }
        return null;
    }

    public static Status of(int id) {
        return Stream.of(values())
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

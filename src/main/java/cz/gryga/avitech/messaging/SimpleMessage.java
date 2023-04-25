package cz.gryga.avitech.messaging;

import java.util.Objects;

public record SimpleMessage(String message) implements Message {

    @Override
    public String getMessage() {
        return message();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMessage that = (SimpleMessage) o;
        return Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }
}

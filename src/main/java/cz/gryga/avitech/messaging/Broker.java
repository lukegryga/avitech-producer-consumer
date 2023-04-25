package cz.gryga.avitech.messaging;

/**
 * Generic single producer single consumer broker.
 */
public interface Broker {

    /**
     * Push message to the broker.
     *
     * @param message message to push
     */
    void push(Message message);

    /**
     * Poll message from the broker. Each message is poll mostly once.
     *
     * @return message or null if no message is available
     */
    Message poll();
}

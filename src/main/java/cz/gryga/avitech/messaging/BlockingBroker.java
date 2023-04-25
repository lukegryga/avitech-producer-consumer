package cz.gryga.avitech.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * FIFO concurrent blocking broker.
 */
public class BlockingBroker implements Broker {

    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    /**
     * Push message to the broker at the end of the queue.
     *
     * @param message message to push
     */
    @Override
    public void push(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted while waiting for message to become available.", e);
        }
    }

    /**
     * Poll message from the head of the queue. Each message is poll mostly once.
     * This method blocks until message is available.
     * @return message
     */
    @Override
    public Message poll() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

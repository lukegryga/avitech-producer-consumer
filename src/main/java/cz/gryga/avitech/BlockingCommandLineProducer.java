package cz.gryga.avitech;

import cz.gryga.avitech.messaging.Producer;
import cz.gryga.avitech.messaging.SimpleMessage;
import cz.gryga.avitech.messaging.Broker;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Read lines from standard input and push them to the broker.
 */
public class BlockingCommandLineProducer implements Producer {

    private final Scanner scanner;

    private final Broker broker;

    /**
     * @param broker broker to push messages to
     * @param inputStream input stream to read from
     */
    public BlockingCommandLineProducer(Broker broker, InputStream inputStream) {
        this.broker = broker;
        scanner = new Scanner(inputStream);
        scanner.useDelimiter("\n");
    }

    /**
     * Read next line from the input stream and push it to the broker.
     * This method blocks until line is available or finish if no more elements will be available.
     */
    @Override
    public void produce() {
        String line;
        try {
            line = scanner.next();
        } catch (NoSuchElementException e) {
            return;
        }


        broker.push(new SimpleMessage(line));
    }
}

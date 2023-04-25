package cz.gryga.avitech;

import cz.gryga.avitech.command.CommandExecutor;
import cz.gryga.avitech.messaging.Broker;
import cz.gryga.avitech.messaging.Consumer;
import cz.gryga.avitech.messaging.Message;

/**
 * Blocking broker consumer.
 */
public class BlockingCommandConsumer implements Consumer {

    private final Broker broker;
    private final CommandExecutor commandExecutor;

    /**
     * @param broker broker to consume from
     * @param commandExecutor command executor
     */
    public BlockingCommandConsumer(Broker broker, CommandExecutor commandExecutor) {
        this.broker = broker;
        this.commandExecutor = commandExecutor;
    }

    /**
     * Consume message from the broker and invoke CommandExecutor. This method blocks until message is available.
     */
    @Override
    public void consume() {
        Message message;
        do {
            message = broker.poll();
        } while (message == null);

        commandExecutor.execute(message.getMessage());
    }
}

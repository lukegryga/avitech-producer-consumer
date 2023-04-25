package cz.gryga.avitech;

import cz.gryga.avitech.command.CommandExecutor;
import cz.gryga.avitech.db.HibernateUtils;
import cz.gryga.avitech.db.repository.DefaultUserRepository;
import cz.gryga.avitech.db.repository.UserRepository;
import cz.gryga.avitech.messaging.BlockingBroker;
import cz.gryga.avitech.messaging.Broker;
import cz.gryga.avitech.messaging.Consumer;
import cz.gryga.avitech.messaging.Producer;
import org.hibernate.Session;

import java.io.ByteArrayInputStream;
import java.util.logging.Level;

/**
 * Hello world!
 */
public class App {

    public static final String[] demoCommands = {
            "Add (1, \"a1\", \"Robert\")",
            "Add (2, \"a2\", \"Martin\")",
            "PrintAll",
            "DeleteAll",
            "PrintAll",
    };

    public static void main(String[] args) throws InterruptedException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        ByteArrayInputStream demoInputStream = new ByteArrayInputStream(
                String.join("\n", demoCommands).getBytes());

        Session session = HibernateUtils.getSessionFactory().openSession();
        UserRepository userRepository = new DefaultUserRepository(session);

        Broker broker = new BlockingBroker();
        Producer producer = new BlockingCommandLineProducer(broker, demoInputStream);

        CommandExecutor commandExecutor = new DbCommandExecutor(userRepository);
        Consumer consumer = new BlockingCommandConsumer(broker, commandExecutor);

        LoopRunner loopRunner = new LoopRunner();
        loopRunner.loopTask(producer::produce);
        loopRunner.loopTask(consumer::consume);

        Thread.sleep(5000);
        loopRunner.stop();
    }
}

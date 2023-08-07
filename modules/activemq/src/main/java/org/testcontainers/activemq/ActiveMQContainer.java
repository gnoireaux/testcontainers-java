package org.testcontainers.activemq;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

/**
 * Testcontainers implementation for Apache ActiveMQ.
 */
public class ActiveMQContainer extends GenericContainer<ActiveMQContainer> {

    private static final DockerImageName DEFAULT_IMAGE = DockerImageName.parse("apache/activemq-classic");

    private static final int WEB_CONSOLE_PORT = 8161;

    private static final int TCP_PORT = 61616;

    private static final int AMQP_PORT = 5672;

    private static final int STOMP_PORT = 61613;

    private static final int MQTT_PORT = 1883;

    private static final int WS_PORT = 61614;

    public ActiveMQContainer(String image) {
        this(DockerImageName.parse(image));
    }

    public ActiveMQContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE);

        withExposedPorts(WEB_CONSOLE_PORT, TCP_PORT, AMQP_PORT, STOMP_PORT, MQTT_PORT, WS_PORT);
        waitingFor(Wait.forLogMessage(".*Apache ActiveMQ.*started.*", 1).withStartupTimeout(Duration.ofMinutes(1)));
    }

    public String getBrokerUrl() {
        return String.format("tcp://%s:%s", getHost(), getMappedPort(TCP_PORT));
    }
}

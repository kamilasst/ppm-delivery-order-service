package com.ppm.delivery.order.consumer.config;

import com.ppm.delivery.order.consumer.properties.MessageProperties;
import com.ppm.delivery.order.consumer.properties.SupportedCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitConfigTest {

    @Mock
    private SupportedCountries supportedCountries;

    @Mock
    private MessageProperties messageProperties;

    private RabbitConfig rabbitConfig;

    @BeforeEach
    void setUp() {
        rabbitConfig = new RabbitConfig(supportedCountries, messageProperties);
    }

    @Test
    void commandExchange_shouldUseExchangeFromProperties() {
        //Arrange
        when(messageProperties.getExchange()).thenReturn("my.exchange");

        //Act
        TopicExchange exchange = rabbitConfig.commandExchange();

        //Assert
        assertNotNull(exchange);
        assertEquals("my.exchange", exchange.getName());

        verify(messageProperties).getExchange();
        verifyNoMoreInteractions(messageProperties, supportedCountries);
    }

    @Test
    void countryQueues_shouldCreateQueuesForEachCountryAndAction_andPopulateNamesCorrectly() {
        //Arrange
        when(messageProperties.getDomain()).thenReturn("order");
        when(messageProperties.getActions()).thenReturn(List.of("create", "update"));
        when(supportedCountries.getSupportedCountries()).thenReturn(List.of("BR", "US"));

        //Act
        List<Queue> queues = rabbitConfig.countryQueues();

        //Assert
        Set<String> expectedNames = Set.of(
                "br.order.create",
                "br.order.update",
                "us.order.create",
                "us.order.update"
        );

        Set<String> actualNames = queues.stream()
                .map(Queue::getName)
                .collect(Collectors.toSet());

        assertEquals(expectedNames, actualNames);
        assertTrue(queues.stream().allMatch(Queue::isDurable));

        verify(supportedCountries).getSupportedCountries();
        verify(messageProperties).getDomain();
        verify(messageProperties, atLeastOnce()).getActions();
        verifyNoMoreInteractions(messageProperties, supportedCountries);
    }

    @Test
    void queueNames_shouldReturnNamesDerivedFromCountryQueues() {
        //Arrange
        when(messageProperties.getDomain()).thenReturn("order");
        when(messageProperties.getActions()).thenReturn(List.of("create"));
        when(supportedCountries.getSupportedCountries()).thenReturn(List.of("BR"));

        //Act
        List<Queue> queues = rabbitConfig.countryQueues();
        List<String> queueNames = rabbitConfig.queueNames(queues);

        //Assert
        assertEquals(1, queueNames.size());
        assertEquals("br.order.create", queueNames.get(0));

        verify(supportedCountries).getSupportedCountries();
        verify(messageProperties).getDomain();
        verify(messageProperties).getActions();
        verifyNoMoreInteractions(messageProperties, supportedCountries);
    }

    @Test
    void createBindings_shouldBindEachQueueWithCorrectRoutingKey() {
        //Arrange
        when(messageProperties.getDomain()).thenReturn("order");
        when(messageProperties.getActions()).thenReturn(List.of("create", "delete"));
        when(supportedCountries.getSupportedCountries()).thenReturn(List.of("BR", "MX"));
        when(messageProperties.getExchange()).thenReturn("commands");

        //Act
        TopicExchange exchange = rabbitConfig.commandExchange();
        List<Queue> countryQueues = rabbitConfig.countryQueues();
        List<Binding> bindings = rabbitConfig.createBindings(exchange, countryQueues);

        //Assert
        Set<String> expectedRoutingKeys = Set.of(
                "br.create",
                "br.delete",
                "mx.create",
                "mx.delete"
        );

        Set<String> actualRoutingKeys = bindings.stream()
                .map(Binding::getRoutingKey)
                .collect(Collectors.toSet());

        assertEquals(expectedRoutingKeys, actualRoutingKeys);

        assertTrue(bindings.stream().allMatch(b -> b.getExchange().equals(exchange.getName())));

        Set<String> bindingDestinations = bindings.stream()
                .map(Binding::getDestination)
                .collect(Collectors.toSet());
        Set<String> queueNames = countryQueues.stream().map(Queue::getName).collect(Collectors.toSet());
        assertEquals(queueNames, bindingDestinations);

        verify(supportedCountries).getSupportedCountries();
        verify(messageProperties).getDomain();
        verify(messageProperties, atLeastOnce()).getActions();
        verify(messageProperties).getExchange();
        verifyNoMoreInteractions(messageProperties, supportedCountries);

    }

    @Test
    void whenNoActions_butHasSupportedCountries_thenNoQueuesOrBindingsCreated() {
        //Arrange
        when(messageProperties.getDomain()).thenReturn("order");
        when(messageProperties.getActions()).thenReturn(List.of());
        when(supportedCountries.getSupportedCountries()).thenReturn(List.of("BR"));
        when(messageProperties.getExchange()).thenReturn("cmd");

        //Act
        List<Queue> queues = rabbitConfig.countryQueues();

        //Assert
        assertTrue(queues.isEmpty());

        List<String> names = rabbitConfig.queueNames(queues);
        assertTrue(names.isEmpty());

        TopicExchange exchange = rabbitConfig.commandExchange();
        List<Binding> bindings = rabbitConfig.createBindings(exchange, queues);
        assertTrue(bindings.isEmpty());

        verify(supportedCountries).getSupportedCountries();
        verify(messageProperties).getDomain();
        verify(messageProperties).getActions();
        verify(messageProperties).getExchange();
        verifyNoMoreInteractions(supportedCountries, messageProperties);
    }

    @Test
    void whenNoSupportedCountries_butHasActions_thenNoQueuesOrBindingsCreated() {
        //Arrange
        when(messageProperties.getDomain()).thenReturn("order");
        when(supportedCountries.getSupportedCountries()).thenReturn(List.of());
        when(messageProperties.getExchange()).thenReturn("cmd");

        //Act
        List<Queue> queues = rabbitConfig.countryQueues();

        //Assert
        assertTrue(queues.isEmpty());

        List<String> names = rabbitConfig.queueNames(queues);
        assertTrue(names.isEmpty());

        TopicExchange exchange = rabbitConfig.commandExchange();
        List<Binding> bindings = rabbitConfig.createBindings(exchange, queues);
        assertTrue(bindings.isEmpty());

        verify(supportedCountries).getSupportedCountries();
        verify(messageProperties).getDomain();
        verify(messageProperties).getExchange();
        verifyNoMoreInteractions(messageProperties, supportedCountries);
    }

}

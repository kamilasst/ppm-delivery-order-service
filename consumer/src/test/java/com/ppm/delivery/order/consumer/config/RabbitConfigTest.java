package com.ppm.delivery.order.consumer.config;

import com.ppm.delivery.order.consumer.message.config.QueueInfo;
import com.ppm.delivery.order.consumer.message.config.RabbitConfig;
import com.ppm.delivery.order.consumer.properties.MessageApplicationProperties;
import com.ppm.delivery.order.consumer.properties.ApplicationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.TopicExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitConfigTest {

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private MessageApplicationProperties messageApplicationProperties;

    private RabbitConfig rabbitConfig;

    @BeforeEach
    void setUp() {
        rabbitConfig = new RabbitConfig(applicationProperties, messageApplicationProperties);
    }

    @Test
    void commandExchange_shouldUseExchangeFromProperties() {
        //Arrange
        when(messageApplicationProperties.getExchange()).thenReturn("my.exchange");

        //Act
        TopicExchange exchange = rabbitConfig.commandExchange();

        //Assert
        assertNotNull(exchange);
        assertEquals("my.exchange", exchange.getName());

        verify(messageApplicationProperties).getExchange();
        verifyNoMoreInteractions(messageApplicationProperties, applicationProperties);
    }

    @Test
    void countryQueues_shouldCreateQueuesForEachCountryAndAction_andPopulateNamesCorrectly() {
        //Arrange
        when(messageApplicationProperties.getDomain()).thenReturn("order");
        when(messageApplicationProperties.getActions()).thenReturn(List.of("create", "update"));
        when(applicationProperties.getSupportedCountries()).thenReturn(List.of("BR", "US"));

        //Act
        Map<String, QueueInfo> queuesInfo = rabbitConfig.queueMapInfo();

        //Assert
        Set<String> expectedNames = Set.of(
                "br.order.create",
                "br.order.update",
                "us.order.create",
                "us.order.update"
        );

        Set<String> actualNames = queuesInfo.values().stream()
                .map(queueInfo1 -> queueInfo1.getQueue().getName())
                .collect(Collectors.toSet());

        assertEquals(expectedNames, actualNames);
        assertTrue(queuesInfo.values().stream().allMatch(queueInfo1 -> queueInfo1.getQueue().isDurable()));

        verify(applicationProperties).getSupportedCountries();
        verify(messageApplicationProperties).getDomain();
        verify(messageApplicationProperties, atLeastOnce()).getActions();
        verifyNoMoreInteractions(messageApplicationProperties, applicationProperties);
    }

    @Test
    void queueNames_shouldReturnNamesDerivedFromQueueMapInfo() {
        //Arrange
        when(messageApplicationProperties.getDomain()).thenReturn("order");
        when(messageApplicationProperties.getActions()).thenReturn(List.of("create"));
        when(applicationProperties.getSupportedCountries()).thenReturn(List.of("BR"));

        //Act
        List<String> queueNames = rabbitConfig.queueNames();

        //Assert
        assertEquals(1, queueNames.size());
        assertEquals("br.order.create", queueNames.get(0));

        verify(applicationProperties, times(1)).getSupportedCountries();
        verify(messageApplicationProperties, times(1)).getDomain();
        verify(messageApplicationProperties, times(1)).getActions();
        verifyNoMoreInteractions(messageApplicationProperties, applicationProperties);
    }

    @Test
    void createBindings_shouldBindEachQueueWithCorrectRoutingKey() {
        //Arrange
        when(messageApplicationProperties.getDomain()).thenReturn("order");
        when(messageApplicationProperties.getActions()).thenReturn(List.of("create", "delete"));
        when(applicationProperties.getSupportedCountries()).thenReturn(List.of("BR", "MX"));
        when(messageApplicationProperties.getExchange()).thenReturn("commands");

        //Act
        TopicExchange exchange = rabbitConfig.commandExchange();
        Map<String,QueueInfo> queuesInfo = rabbitConfig.queueMapInfo();
        List<Binding> bindings = rabbitConfig.createBindings(exchange, queuesInfo);

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
        Set<String> queueNames = queuesInfo.values().stream()
                .map(queueInfo -> queueInfo.getQueue().getName())
                .collect(Collectors.toSet());
        assertEquals(queueNames, bindingDestinations);

        verify(applicationProperties).getSupportedCountries();
        verify(messageApplicationProperties).getDomain();
        verify(messageApplicationProperties, atLeastOnce()).getActions();
        verify(messageApplicationProperties).getExchange();
        verifyNoMoreInteractions(messageApplicationProperties, applicationProperties);

    }

    @Test
    void whenNoActions_butHasSupportedCountries_thenNoQueuesOrBindingsCreated() {
        //Arrange
        when(messageApplicationProperties.getDomain()).thenReturn("order");
        when(messageApplicationProperties.getActions()).thenReturn(List.of());
        when(applicationProperties.getSupportedCountries()).thenReturn(List.of("BR"));
        when(messageApplicationProperties.getExchange()).thenReturn("cmd");

        //Act
        Map<String,QueueInfo> queueInfo = rabbitConfig.queueMapInfo();

        //Assert
        assertTrue(queueInfo.isEmpty());

        List<String> names = rabbitConfig.queueNames();
        assertTrue(names.isEmpty());

        TopicExchange exchange = rabbitConfig.commandExchange();
        List<Binding> bindings = rabbitConfig.createBindings(exchange, queueInfo);
        assertTrue(bindings.isEmpty());

        verify(applicationProperties, times(2)).getSupportedCountries();
        verify(messageApplicationProperties, times(2)).getDomain();
        verify(messageApplicationProperties, times(2)).getActions();
        verify(messageApplicationProperties).getExchange();
        verifyNoMoreInteractions(applicationProperties, messageApplicationProperties);
    }

    @Test
    void whenNoSupportedCountries_butHasActions_thenNoQueuesOrBindingsCreated() {
        //Arrange
        when(messageApplicationProperties.getDomain()).thenReturn("order");
        when(applicationProperties.getSupportedCountries()).thenReturn(List.of());
        when(messageApplicationProperties.getExchange()).thenReturn("cmd");

        //Act
        Map<String,QueueInfo> queuesInfo = rabbitConfig.queueMapInfo();

        //Assert
        assertTrue(queuesInfo.isEmpty());

        List<String> names = rabbitConfig.queueNames();
        assertTrue(names.isEmpty());

        TopicExchange exchange = rabbitConfig.commandExchange();
        List<Binding> bindings = rabbitConfig.createBindings(exchange, queuesInfo);
        assertTrue(bindings.isEmpty());

        verify(applicationProperties, times(2)).getSupportedCountries();
        verify(messageApplicationProperties, times(2)).getDomain();
        verify(messageApplicationProperties).getExchange();
        verifyNoMoreInteractions(messageApplicationProperties, applicationProperties);
    }

}

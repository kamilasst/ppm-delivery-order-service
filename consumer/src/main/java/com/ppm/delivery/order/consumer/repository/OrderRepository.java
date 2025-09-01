package com.ppm.delivery.order.consumer.repository;

import com.ppm.delivery.order.consumer.domain.model.Order;
import com.ppm.delivery.order.consumer.properties.DatabaseProperties;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository implements IOrderRepository {

    private final DatabaseProperties databaseProperties;
    private final MongoOperations mongoOperations;

    public OrderRepository(DatabaseProperties databaseProperties, MongoOperations mongoOperations){
        this.databaseProperties = databaseProperties;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void save(final String country, Order order){

        mongoOperations.save(order, getCollectionName(country));
    }

    private String getCollectionName(final String country){
        return country.toUpperCase() + "-" + databaseProperties.getCollectionName();
    }

}
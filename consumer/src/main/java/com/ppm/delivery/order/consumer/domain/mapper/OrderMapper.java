package com.ppm.delivery.order.consumer.domain.mapper;

import com.ppm.delivery.order.consumer.domain.model.Order;
import com.ppm.delivery.order.consumer.message.domain.OrderMessage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order toEntity(OrderMessage message);

}
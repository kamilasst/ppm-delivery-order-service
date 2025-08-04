package com.ppm.delivery.order.producer.api.domain.request.header;

public record Metadata(
        String country,
        String platform,
        String profile) {
}
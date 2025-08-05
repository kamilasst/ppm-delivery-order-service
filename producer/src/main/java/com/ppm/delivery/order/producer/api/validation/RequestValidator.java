package com.ppm.delivery.order.producer.api.validation;

import com.ppm.delivery.order.producer.api.config.OrderProducerConfig;
import com.ppm.delivery.order.producer.api.domain.profile.Profile;
import com.ppm.delivery.order.producer.api.domain.request.header.Header;
import com.ppm.delivery.order.producer.api.exception.MessageErrorConstants;
import com.ppm.delivery.order.producer.exception.CorrelationIdNotSupportedException;
import com.ppm.delivery.order.producer.exception.CountryNotSupportedException;
import com.ppm.delivery.order.producer.exception.ProfileNotSupportedException;
import com.ppm.delivery.order.producer.exception.TimeStampNotSupportedException;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class RequestValidator {

    private final OrderProducerConfig orderProducerConfig;

    public RequestValidator(final OrderProducerConfig orderProducerConfig) {
        this.orderProducerConfig = Objects.requireNonNull(orderProducerConfig);
    }

    public void validateHeader(final Header header){
        validateCountry(header.metadata().country());
        validateCorrelationId(header.correlationId());
        validateProfile(header.metadata().profile());
        validateTimeStamp(header.timestamp());
    }

    private void validateCountry(final String country){

        if (StringUtils.isBlank(country)) {
            throw new CountryNotSupportedException(MessageErrorConstants.ERROR_COUNTRY_REQUIRED_HEADER);
        }

        List<String> supportedCountries = orderProducerConfig.getSupportedCountries();
        if (supportedCountries == null || !supportedCountries.contains(country)) {
            throw new CountryNotSupportedException(String.format(MessageErrorConstants.ERROR_COUNTRY_NOT_SUPPORTED, country));
        }
    }

    private void validateCorrelationId(final String correlationId){

        if (StringUtils.isBlank(correlationId)) {
            throw new CorrelationIdNotSupportedException(MessageErrorConstants.ERROR_CORRELATION_ID_REQUIRED_HEADER);
        }
    }

    private void validateProfile(final String profile){

        if (StringUtils.isBlank(profile)) {
            throw new ProfileNotSupportedException(MessageErrorConstants.ERROR_PROFILE_REQUIRED_HEADER);
        }

        try {
            Profile.valueOf(profile.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new ProfileNotSupportedException(MessageErrorConstants.ERROR_PROFILE_IS_INVALID);
        }
    }

    private void validateTimeStamp(final String timeStamp){

        if (StringUtils.isBlank(timeStamp)) {
            throw new TimeStampNotSupportedException(MessageErrorConstants.ERROR_TIME_STAMP_REQUIRED_HEADER);
        }
    }
}
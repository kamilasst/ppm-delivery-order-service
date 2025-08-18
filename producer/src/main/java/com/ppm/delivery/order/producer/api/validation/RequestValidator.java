package com.ppm.delivery.order.producer.api.validation;

import com.ppm.delivery.order.producer.api.exception.HeaderValidationException;
import com.ppm.delivery.order.producer.properties.ApplicationProperties;
import com.ppm.delivery.order.producer.api.domain.profile.Profile;
import com.ppm.delivery.order.producer.api.domain.request.header.Header;
import com.ppm.delivery.order.producer.exception.MessageErrorConstants;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class RequestValidator {

    private final ApplicationProperties applicationProperties;

    public RequestValidator(final ApplicationProperties applicationProperties) {
        this.applicationProperties = Objects.requireNonNull(applicationProperties);
    }

    public void validateHeader(final Header header){
        validateCountry(header.metadata().country());
        validateCorrelationId(header.correlationId());
        validateProfile(header.metadata().profile());
        validateTimestamp(header.timestamp());
    }

    private void validateCountry(final String country){

        if (StringUtils.isBlank(country)) {
            throw new HeaderValidationException(MessageErrorConstants.ERROR_COUNTRY_REQUIRED_HEADER);
        }

        List<String> supportedCountries = applicationProperties.getSupportedCountries();
        if (supportedCountries == null || !supportedCountries.contains(country)) {
            throw new HeaderValidationException(String.format(MessageErrorConstants.ERROR_COUNTRY_NOT_SUPPORTED, country));
        }
    }

    private void validateCorrelationId(final String correlationId){

        if (StringUtils.isBlank(correlationId)) {
            throw new HeaderValidationException(MessageErrorConstants.ERROR_CORRELATION_ID_REQUIRED_HEADER);
        }
    }

    private void validateProfile(final String profile){

        if (StringUtils.isBlank(profile)) {
            throw new HeaderValidationException(MessageErrorConstants.ERROR_PROFILE_REQUIRED_HEADER);
        }

        try {
            Profile.valueOf(profile.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new HeaderValidationException(MessageErrorConstants.ERROR_PROFILE_IS_INVALID);
        }
    }

    private void validateTimestamp(final String timestamp){

        if (StringUtils.isBlank(timestamp)) {
            throw new HeaderValidationException(MessageErrorConstants.ERROR_TIMESTAMP_REQUIRED_HEADER);
        }
    }
}
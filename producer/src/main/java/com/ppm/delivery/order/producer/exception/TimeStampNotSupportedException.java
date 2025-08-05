package com.ppm.delivery.order.producer.exception;

import com.ppm.delivery.order.producer.api.exception.HeaderValidationException;

// TODO atg - Review producer - Ponto mais arquitetura: Percebi que foi criado uma exception para cada header
//  que precisa ser validado. Porém, todas tem o mesmo comportamento ou seja não tem comportamento "diferente" ou especifico dos filhos.
//  Não seria melhor, seria melhor criar apenas uma única exception ? Exemplo: HeaderNotSupportedException
public class TimeStampNotSupportedException extends HeaderValidationException {
    public TimeStampNotSupportedException(String message) {
        super(message);
    }
}
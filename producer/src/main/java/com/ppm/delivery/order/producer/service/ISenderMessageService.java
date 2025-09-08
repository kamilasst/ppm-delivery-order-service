package com.ppm.delivery.order.producer.service;

import com.ppm.delivery.order.producer.api.domain.request.OrderRequest;

// TODO atg - Review code sprint 15 - Avalie renomear para ISenderOrderMessageService pois ele será responsável
//  exclusivamente por mensagens de order
public interface ISenderMessageService {

    // TODO atg - Review code sprint 15 - Avalie renomear para sendCreate pois como vc já está no service responsável por enviar mensagens de order,
    //  o sendCreateOrderMessage fica redundante
    void sendCreateOrderMessage(OrderRequest request);
}

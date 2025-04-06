package com.api.producer_service.service;

import org.springframework.kafka.core.KafkaTemplate;

import com.api.producer_service.model.Sale;

public class KafkaProducerSaleService {
    private final KafkaTemplate<String, Sale> kafkaTemplate;
    private final String topic = "sales-topic";

    public KafkaProducerSaleService(KafkaTemplate<String, Sale> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSale(Sale sale) {
        kafkaTemplate.send(topic, sale);
        System.out.println("✅ Venda enviada para o Kafka: " + sale.getProductName());
    }

}

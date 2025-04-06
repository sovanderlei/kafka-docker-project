package com.api.producer_service.controller;

import com.api.producer_service.model.Sale;
import com.api.producer_service.service.KafkaProducerSaleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    private final KafkaProducerSaleService kafkaProducerSaleService;

    public SalesController(KafkaProducerSaleService kafkaProducerSaleService) {
        this.kafkaProducerSaleService = kafkaProducerSaleService;
    }

    @PostMapping("/send")
    public String sendSales(@RequestBody List<Sale> sales) {
        for (Sale sale : sales) {
            kafkaProducerSaleService.sendSale(sale);
        }
        return "Sales sent: " + sales.size();
    }
}

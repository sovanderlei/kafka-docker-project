package com.api.producer_service.service;

import com.api.producer_service.model.Sale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

@Service
public class FileWatcherService {

    private static final String FOLDER_RECB = "FOLDERRECB";
    private static final String FOLDER_ENVIADO = "FOLDERRECB/enviado";
    private final KafkaProducerSaleService kafkaProducerSaleService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileWatcherService(KafkaProducerSaleService kafkaProducerSaleService) {
        this.kafkaProducerSaleService = kafkaProducerSaleService;
        new File(FOLDER_ENVIADO).mkdirs(); // Cria a pasta "enviado" se não existir
        watchFolder();
    }

    private void watchFolder() {
        new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Paths.get(FOLDER_RECB).register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        File file = new File(FOLDER_RECB + "/" + event.context().toString());
                        if (file.getName().endsWith(".json")) {
                            processFile(file);
                        }
                    }
                    key.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void processFile(File file) {
        try {
            // Lê o JSON e converte para lista de vendas
            List<Sale> sales = Arrays.asList(objectMapper.readValue(file, Sale[].class));

            // Envia cada venda para o Kafka
            for (Sale sale : sales) {
                kafkaProducerSaleService.sendSale(sale);
            }

            // Move o arquivo para a pasta "enviado"
            FileUtils.moveFileToDirectory(file, new File(FOLDER_ENVIADO), false);
            System.out.println("📂 Arquivo processado e movido para 'enviado': " + file.getName());

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar arquivo: " + file.getName());
            e.printStackTrace();
        }
    }
}

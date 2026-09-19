package com.nman.apiagent.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaHealthController {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @GetMapping("/health")
    public Map<String, Object> checkKafkaHealth() {

        Map<String, Object> response = new HashMap<>();

        Properties properties = new Properties();
        properties.put(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        try (AdminClient adminClient = AdminClient.create(properties)) {

            // Actually communicate with Kafka
            adminClient.listTopics()
                    .names()
                    .get(5, TimeUnit.SECONDS);

            response.put("status", "UP");
            response.put("kafka", "CONNECTED");
            response.put("bootstrapServer", bootstrapServers);
            response.put("message", "Successfully connected to Kafka");
            response.put("topic", adminClient.listTopics()
                    .names().toString());

        } catch (Exception e) {

            response.put("status", "DOWN");
            response.put("kafka", "NOT_CONNECTED");
            response.put("bootstrapServer", bootstrapServers);
            response.put("message", e.getMessage());
        }

        return response;
    }
    
    @GetMapping("/topics")
    public Set<String> getTopics() throws Exception {

        Properties properties = new Properties();

        properties.put(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        try (AdminClient adminClient = AdminClient.create(properties)) {

            return adminClient
                    .listTopics()
                    .names()
                    .get(5, TimeUnit.SECONDS);
        }
    }
}
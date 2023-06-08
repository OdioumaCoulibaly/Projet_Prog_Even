package org.example;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class Producteur {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String C_TO_F_TOPIC = "Temperature-Celsius";

    // Méthode pour démarrer les producteurs de température Celsius
    public static void startProcteurs() {
        // Producteur 1
        Thread producer1Thread = new Thread(() -> {
            // Configuration des propriétés du producteur
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            // Création d'un producteur Kafka avec les propriétés configurées
            KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

            Random random = new Random();
            while (true) {
                // Génération d'une température aléatoire en Celsius
                double temperatureCelsius = random.nextDouble() * 100;
                // Création d'un enregistrement de producteur avec la température en Celsius comme clé et valeur
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                // Envoi de l'enregistrement au topic approprié
                producer.send(record);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Producteur 2 (similaire au producteur 1)
        Thread producer2Thread = new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

            Random random = new Random();
            while (true) {
                double temperatureCelsius = random.nextDouble() * 100;
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                producer.send(record);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Démarrage des threads des producteurs
        producer1Thread.start();
        producer2Thread.start();
    }
}
package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;


public class Consommateur {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String C_TO_F_TOPIC = "Temperature-Celsius";
    private static final String F_TO_K_TOPIC = "Temperature-Fahrenheit";

    // Méthode pour démarrer les consommateurs de température Fahrenheit
    public static void startConsumers() {
        // Consommateur 1
        Thread consumer1Thread = new Thread(() -> {
            // Configuration des propriétés du consommateur
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("group.id", "temperature-consumers");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.DoubleDeserializer");

            // Création d'un consommateur Kafka avec les propriétés configurées
            KafkaConsumer<String, Double> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(java.util.Collections.singletonList(C_TO_F_TOPIC));

            // Configuration des propriétés du producteur pour les températures Fahrenheit
            Properties producerProps = new Properties();
            producerProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producerProps.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            // Création d'un producteur Kafka pour les températures Fahrenheit
            KafkaProducer<String, Double> producer = new KafkaProducer<>(producerProps);

            while (true) {
                // Récupération des enregistrements consommés
                ConsumerRecords<String, Double> records = consumer.poll(java.time.Duration.ofMillis(100));

                // Traitement de chaque enregistrement
                for (ConsumerRecord<String, Double> record : records) {
                    // Récupération de la température en Celsius à partir de l'enregistrement
                    double temperatureCelsius = record.value();
                    // Conversion de la température en Celsius en Fahrenheit
                    double temperatureFahrenheit = Convertisseur.celsiusToFahrenheit(temperatureCelsius);

                    // Création d'un enregistrement de producteur avec la température en Fahrenheit comme clé et valeur
                    ProducerRecord<String, Double> resultRecord = new ProducerRecord<>(F_TO_K_TOPIC, Double.toString(temperatureFahrenheit), temperatureFahrenheit);
                    // Envoi de l'enregistrement au topic approprié
                    producer.send(resultRecord);

                    // Affichage de la température en Celsius et en Fahrenheit
                    System.out.println("Temperature en Celsius: " + temperatureCelsius + " | Temperature en Fahrenheit: " + temperatureFahrenheit);
                }
            }
        });

        // Consommateur 2
        Thread consumer2Thread = new Thread(() -> {
            // Configuration des propriétés pour les flux Kafka
            Properties props = new Properties();
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "temperature-converter");
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

            // Création d'un générateur de flux Kafka
            StreamsBuilder builder = new StreamsBuilder();
            // Récupération du flux de températures Fahrenheit à partir du topic
            KStream<String, Double> stream = builder.stream(F_TO_K_TOPIC);
            // Affichage de chaque température Fahrenheit consommée
            stream.foreach((key, value) -> System.out.println("Temperature en Fahrenheit: " + value));

            // Création et démarrage des flux Kafka
            KafkaStreams streams = new KafkaStreams(builder.build(), props);
            streams.start();
        });

        // Démarrage des threads des consommateurs
        consumer1Thread.start();
        consumer2Thread.start();
    }
}
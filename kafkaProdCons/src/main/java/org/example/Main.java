package org.example;

import io.prometheus.client.Gauge;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        // Point d'entrée principal du programme

        Producteur producteur = new Producteur();
        producteur.startProcteurs();

        Consommateur consommateur = new Consommateur();
        consommateur.startConsumers();

        Prometheus.metrics();
    }
}

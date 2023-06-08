package org.example;

public class Convertisseur {
    public static void main(String[] args) {
        // Point d'entrée principal du programme
        Producteur.startProcteurs();
        Consommateur.startConsumers();
    }

    // Méthode pour convertir une température Celsius en Fahrenheit
    public static double celsiusToFahrenheit(double temperatureCelsius) {
        return (temperatureCelsius * 9 / 5) + 32;
    }
}
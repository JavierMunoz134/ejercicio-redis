package org.example;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class UrlShortenerClient {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. shorten URL");
            System.out.println("2. exit");
            System.out.println("3. url SHORTEDURL");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de leer el entero

            switch (choice) {
                case 1:
                    shortenUrlCommand(jedis, scanner);
                    break;
                case 2:
                    exitCommand();
                    break;
                case 3:
                    getOriginalUrl(jedis, scanner);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void shortenUrlCommand(Jedis jedis, Scanner scanner) {
        System.out.print("Introduce la URL a acortar: ");
        String urlToShorten = scanner.nextLine();
        jedis.rpush("DAVID:URLS_TO_SHORT", urlToShorten);
        System.out.println("URL '" + urlToShorten + "' ha sido agregada para acortar.");
    }

    private static void exitCommand() {
        System.out.println("Saliendo del programa.");
        System.exit(0);
    }

    private static void getOriginalUrl(Jedis jedis, Scanner scanner) {
        System.out.print("Introduce la URL acortada: ");
        String shortedUrl = scanner.nextLine();
        String originalUrl = jedis.hget("DAVID:SHORTED_URLS", shortedUrl);
        if (originalUrl != null) {
            System.out.println("La URL original es: " + originalUrl);
        } else {
            System.out.println("URL acortada no encontrada.");
        }
    }
}

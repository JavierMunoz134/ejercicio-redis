package org.example;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class UrlShortenerService {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        while (true) {
            processUrlsToShorten(jedis);
        }
    }

    private static void processUrlsToShorten(Jedis jedis) {
        while (jedis.llen("DAVID:URLS_TO_SHORT") > 0) {
            String urlToShorten = jedis.lpop("DAVID:URLS_TO_SHORT");
            if (urlToShorten != null) {
                String shortedUrl = shortenUrl(urlToShorten);
                jedis.hset("DAVID:SHORTED_URLS", shortedUrl, urlToShorten);
                System.out.println("URL acortada: " + shortedUrl);
            }
        }
    }

    private static String shortenUrl(String originalUrl) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }
}

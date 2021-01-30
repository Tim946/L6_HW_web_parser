package ua.mainacademy.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class DocumentExtractorService {

    public static Document getDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection
                    .headers(
                            Map.of(
                                    "accept","text/html, */*; q=0.01",
                                    "accept-encoding","gzip, deflate, br",
                                    "accept-language","ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,uk;q=0.6,de;q=0.5",
                                    "user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36"
                            )
                    );
            Document document= connection.get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Document was not downloaded");
    }
}

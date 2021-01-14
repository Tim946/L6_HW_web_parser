package ua.mainacademy.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DocumentExtractorService {
    public static Document getDocument(String url){
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Document was not downloaded");
    }
}

package org.example;

import java.util.List;
import java.util.Map;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PdfCreator pdfCreator = new PdfCreator();
        DocExtractor docExtractor = new DocExtractor();
        System.out.println(System.getProperty("user.dir"));
        List<Map<String, String>> flashCardsList = docExtractor.extractLines("src/main/resources/exampleText.txt");
        pdfCreator.pdfCreator(flashCardsList);
    }
}
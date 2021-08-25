package com.placeholder.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfBoxDemo {
    public static void main(String[] args) throws IOException {
        try (PDDocument doc = PDDocument.load(new File("E:\\book\\Manning.Netty.in.Action.2015.12.pdf"))) {
            int numberOfPages = doc.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            stripper.setStartPage(73);
            stripper.setEndPage(73);
            String content = stripper.getText(doc);

            Pattern p = Pattern.compile("[a-zA-Z-]+");

            List<String> words = new ArrayList<>();
            Matcher matcher = p.matcher(content);
            while (matcher.find()) {
                String group = matcher.group();
                words.add(group);
                System.out.println(group);
            }
            System.out.println(words.size());
        }
    }
}

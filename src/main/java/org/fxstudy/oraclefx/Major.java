package org.fxstudy.oraclefx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Major {
    private List<String> listOfCards;
    private List<String> listOfMajorPrediction;
    private List<String> listOfAscii;
    private List<String> asciiArts;
    private final int randomCard;

    Path cards = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\Cards");
    Path MajorPrediction = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\MajorPrediction");
    Path ascii = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AsciiCards");


    public Major() {
        this.randomCard = (int) (Math.random() * 22);
        try {
            this.listOfCards = Files.readAllLines(cards);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с картами");
            return;
        }
        try {
            this.listOfMajorPrediction = Files.readAllLines(MajorPrediction);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с предсказаниями");
            return;
        }
        try {
            this.listOfAscii = Files.readAllLines(ascii);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с артами");
            return;
        }
    }

    private void loadAsciiArts() {
        if (asciiArts != null) return;
        asciiArts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (String line : listOfAscii) {
            if (line.isEmpty()) {
                if (!current.isEmpty()) {
                    asciiArts.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                if (!current.isEmpty()) current.append("\n");
                current.append(line);
            }
        }
        if (!current.isEmpty()) asciiArts.add(current.toString());
    }

    public String getMajorCard() {
        return listOfCards.stream()
                .skip(randomCard - 1)
                .findFirst()
                .orElse("Карта не найдена");

    }

    public String getMajorPrediction() {
        return listOfMajorPrediction.stream()
                .skip((randomCard - 1) * 2L)
                .limit(2)
                .collect(Collectors.joining("\n"));
    }

    public String getAsciiArt() {
        loadAsciiArts();
        return asciiArts.stream()
                .skip(randomCard - 1)
                .findFirst()
                .orElse("Арт не найден");
    }
}

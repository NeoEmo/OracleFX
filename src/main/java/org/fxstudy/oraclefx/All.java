package org.fxstudy.oraclefx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class All {
    private List<String> listOfCards;
    private List<String> listOfAllPrediction;
    private List<String> listOfAscii;
    private List<String> asciiArts;
    private List<String> listOfAllUserPrediction;
    private final int randomCard;

    Path cards = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\Cards");
    Path allPrediction = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AllPrediction");
    Path ascii = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AsciiCards");
    Path allUserPrediction = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AllUserPrediction");

    public All(){
        this.randomCard = (int) (Math.random() * 78);
        try {
            this.listOfCards = Files.readAllLines(cards);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с картами");
            return;
        }
        try {
            this.listOfAllPrediction = Files.readAllLines(allPrediction);
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
        try {
            this.listOfAllUserPrediction = Files.readAllLines(allUserPrediction);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с пользовательскими предсказаниями");
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

    public String getAllCard() {
        return listOfCards.stream()
                .skip(randomCard - 1)
                .findFirst()
                .orElse("Карта не найдена");
    }

    public String getAllPrediction(){
        return listOfAllPrediction.stream()
                .skip(randomCard - 1)
                .findFirst()
                .orElse("Не найдено предсказание");
    }

    public String getAllUserPrediction() {
        return listOfAllUserPrediction.stream()
                .skip(randomCard - 1)
                .findFirst()
                .orElse("Не найдено пользовательское предсказание");
    }

    public String getAsciiArt() {
        loadAsciiArts();
        return asciiArts.stream()
                .skip(randomCard - 1)
                .findFirst()
                .orElse("Арт не найден");
    }
}

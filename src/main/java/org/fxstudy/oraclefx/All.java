package org.fxstudy.oraclefx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class All {
    private List<String> listOfCards;
    private List<String> listOfAllPrediction;
    private List<String> listOfAscii;
    private List<String> asciiArts;
    private List<String> listOfAllUserPrediction;
    private List<String> listOfAllPredictionX3;
    private int firstCard;
    private int secondCard;
    private int thirdCard;

    Path cards = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\Cards");
    Path allPrediction = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AllPrediction");
    Path ascii = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AsciiCards");
    Path allUserPrediction = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AllUserPrediction");
    Path allPredictionX3 = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\AllPredictionX3");

    public All(){
        this.firstCard = (int) (Math.random() * 78);
        this.secondCard = (int) (Math.random() * 78);
        this.thirdCard = (int) (Math.random() * 78);

        if(firstCard == secondCard) {
            int index = (int) (Math.random() * 10);
            secondCard = secondCard + index;
        } else if (secondCard == thirdCard) {
            int index = (int) (Math.random() * 10);
            thirdCard = thirdCard + index;
        }

        if (firstCard > 78) {
            firstCard = firstCard - 78;
        } else if (secondCard > 78) {
            secondCard = secondCard - 78;
        } else if (thirdCard > 78) {
            thirdCard = thirdCard - 78;
        }

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
        try {
            this.listOfAllPredictionX3 = Files.readAllLines(allPredictionX3);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с тремя предсказаниями");
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
                .skip(firstCard - 1)
                .findFirst()
                .orElse("Карта не найдена");
    }

    public String getAllPrediction(){
        return listOfAllPrediction.stream()
                .skip(firstCard - 1)
                .findFirst()
                .orElse("Не найдено предсказание");
    }

    public String getAllUserPrediction() {
        return listOfAllUserPrediction.stream()
                .skip(firstCard - 1)
                .findFirst()
                .orElse("Не найдено пользовательское предсказание");
    }

    public String getAsciiArt() {
        loadAsciiArts();
        return asciiArts.stream()
                .skip(firstCard - 1)
                .findFirst()
                .orElse("Арт не найден");
    }

    public String getAllCardsX3() {
        List<Integer> indexes = List.of(firstCard - 1, secondCard - 1, thirdCard - 1);
        return indexes.stream()
                .map(listOfCards::get)
                .collect(Collectors.joining("\n\n"));
    }

    public String getAllPredictionsX3() {
        List<Integer> indexes = List.of(firstCard*3-3, secondCard*3-2, thirdCard*3-1);
        return indexes.stream()
                .map(listOfAllPredictionX3::get)
                .collect(Collectors.joining("\n\n"));
    }

    public String getAllAsciiX3(int i) {
        loadAsciiArts();
        switch (i) {
            case 1 -> {
                return asciiArts.stream()
                        .skip(firstCard - 1)
                        .findFirst()
                        .orElse("Арт" + (firstCard - 1) + "не найден");
            }
            case 2 -> {
                return asciiArts.stream()
                        .skip(secondCard - 1)
                        .findFirst()
                        .orElse("Арт" + (secondCard - 1) + " не найден");
            }
            case 3 -> {
                return asciiArts.stream()
                        .skip(thirdCard - 1)
                        .findFirst()
                        .orElse("Арт" + (thirdCard - 1) + "не найден");
            }
        }
        return null;
    }
}
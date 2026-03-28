package org.fxstudy.oraclefx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Major {
    private final List<String> listOfCards;
    private final List<String> listOfMajorPrediction;
    private final List<String> listOfAscii;
    private List<String> asciiArts;
    private final List<String> listOfMajorPredictionX3;
    private int firstCard;
    private int secondCard;
    private int thirdCard;

    InputStream cards = getClass().getResourceAsStream("/org/fxstudy/oraclefx/Cards");
    InputStream majorPrediction = getClass().getResourceAsStream("/org/fxstudy/oraclefx/MajorPrediction");
    InputStream ascii = getClass().getResourceAsStream("/org/fxstudy/oraclefx/AsciiCards");
    InputStream majorPredictionX3 = getClass().getResourceAsStream("/org/fxstudy/oraclefx/AllPredictionX3");


    public Major() {
        this.firstCard = (int) (Math.random() * 22);
        this.secondCard = (int) (Math.random() * 22);
        this.thirdCard = (int) (Math.random() * 22);

        if(firstCard == secondCard) {
            int index = (int) (Math.random() * 10);
            secondCard = secondCard + index;
        } else if (secondCard == thirdCard) {
            int index = (int) (Math.random() * 10);
            thirdCard = thirdCard + index;
        }

        if (firstCard > 22) {
            firstCard = firstCard - 22;
        } else if (secondCard > 22) {
            secondCard = secondCard - 22;
        } else if (thirdCard > 22) {
            thirdCard = thirdCard - 22;
        }

        listOfCards = new BufferedReader(new InputStreamReader(Objects.requireNonNull(cards)))
                .lines()
                .collect(Collectors.toList());

        listOfMajorPrediction = new BufferedReader(new InputStreamReader(Objects.requireNonNull(majorPrediction)))
                .lines()
                .collect(Collectors.toList());

        listOfAscii = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ascii)))
                .lines()
                .collect(Collectors.toList());

        listOfMajorPredictionX3 = new BufferedReader(new InputStreamReader(Objects.requireNonNull(majorPredictionX3)))
                .lines()
                .collect(Collectors.toList());
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
                .skip(firstCard - 1)
                .findFirst()
                .orElse("Карта не найдена");

    }

    public String getMajorPrediction() {
        return listOfMajorPrediction.stream()
                .skip((firstCard - 1) * 2L)
                .limit(2)
                .collect(Collectors.joining("\n"));
    }

    public String getAsciiArt() {
        loadAsciiArts();
        return asciiArts.stream()
                .skip(firstCard - 1)
                .findFirst()
                .orElse("Арт не найден");
    }

    public String getMajorCardsX3() {
        List<Integer> indexes = List.of(firstCard - 1, secondCard - 1, thirdCard - 1);
        return indexes.stream()
                .map(listOfCards::get)
                .collect(Collectors.joining("\n\n"));
    }

    public String getMajorPredictionsX3() {
        List<Integer> indexes = List.of(firstCard*3-3, secondCard*3-2, thirdCard*3-1);
        return indexes.stream()
                .map(listOfMajorPredictionX3::get)
                .collect(Collectors.joining("\n\n"));
    }

    public String getMajorAsciiX3(int i) {
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
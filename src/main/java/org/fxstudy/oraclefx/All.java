package org.fxstudy.oraclefx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class All {
    private final List<String> listOfCards;
    private final List<String> listOfAllPrediction;
    private final List<String> listOfAscii;
    private List<String> asciiArts;
    private final List<String> listOfAllUserPrediction;
    private final List<String> listOfAllPredictionX3;
    private int firstCard;
    private int secondCard;
    private int thirdCard;

    InputStream cards = getClass().getResourceAsStream("/org/fxstudy/oraclefx/Cards");
    InputStream allPrediction = getClass().getResourceAsStream("/org/fxstudy/oraclefx/AllPrediction");
    InputStream ascii = getClass().getResourceAsStream("/org/fxstudy/oraclefx/AsciiCards");
    InputStream allUserPrediction = getClass().getResourceAsStream("/org/fxstudy/oraclefx/AllUserPrediction");
    InputStream allPredictionX3 = getClass().getResourceAsStream("/org/fxstudy/oraclefx/AllPredictionX3");

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

        listOfCards = new BufferedReader(new InputStreamReader(Objects.requireNonNull(cards)))
                .lines()
                .collect(Collectors.toList());

        listOfAllPrediction = new BufferedReader(new InputStreamReader(Objects.requireNonNull(allPrediction)))
                .lines()
                .collect(Collectors.toList());

        listOfAscii = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ascii)))
                .lines()
                .collect(Collectors.toList());

        listOfAllUserPrediction = new BufferedReader(new InputStreamReader(Objects.requireNonNull(allUserPrediction)))
                .lines()
                .collect(Collectors.toList());

        listOfAllPredictionX3 = new BufferedReader(new InputStreamReader(Objects.requireNonNull(allPredictionX3)))
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
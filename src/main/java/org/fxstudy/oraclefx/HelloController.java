package org.fxstudy.oraclefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelloController {
    public TextArea chat;
    public TextArea prediction;
    public TextArea card;
    public TextArea ascii1;
    public TextField questionField;
    public TextArea ascii2;
    public TextArea ascii3;
    private String userQuestion;

    Path welcomeText = Paths.get("src\\main\\resources\\org\\fxstudy\\oraclefx\\TextOracle");
    Timeline currentTimeLine;

    public void initialize() {
        welcome();
    }

    private void animateText(TextArea target, String text, Runnable onFinished) {
        if (currentTimeLine != null) {
            currentTimeLine.stop();
        }
        target.clear();
        StringBuilder current = new StringBuilder();
        int[] index = {0};

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event ->{
            if (index[0] < text.length()) {
                current.append(text.charAt(index[0]++));
                target.setText(current.toString());
                target.setScrollTop(Double.MAX_VALUE);
            } else {
                timeline.stop();
                if(onFinished != null) {
                    onFinished.run();
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        currentTimeLine = timeline;
        timeline.play();
    }

    private void animateIntro(Runnable onFinished) {
        List<String> text;
        try {
            text = Files.readAllLines(welcomeText);
        } catch (IOException e) {
            chat.setText("Не удалось загрузить текст предсказания.");
            return;
        }
        String introText = text.get(1);
        animateText(chat, introText, onFinished);
    }

    @FXML
    protected void welcome() {
        List<String> text;
        try {
            text = Files.readAllLines(welcomeText);
        } catch (IOException e) {
            chat.setText("Не удалось загрузить текст предсказания.");
            return;
        }
        String welcomeTxt = text.getFirst();

        chat.setEditable(false);
        animateText(chat,welcomeTxt, null);
    }

    @FXML
    protected void pressAll() {
        animateIntro(() -> {
            All all = new All();
            String cardText = all.getAllCard();
            String predictionText = all.getAllPrediction();
            ascii1.setText(all.getAsciiArt());
            animateText(card, cardText, () -> {
                animateText(prediction, predictionText, null);
            });
        });
    }

    @FXML
    protected void pressMajor() {
        animateIntro(() -> {
            Major major = new Major();
            String cardText = major.getMajorCard();
            String predictionText = major.getMajorPrediction();
            ascii1.setText(major.getAsciiArt());
            animateText(card, cardText, () -> {
                animateText(prediction, predictionText, null);
            });
        });
    }

    public void handleQuestion(ActionEvent event) {
        userQuestion = questionField.getText();
        questionField.clear();
        questionField.setPromptText("Вопрос принят");
        animateIntro(() -> {
            All all = new All();
            String cardText = all.getAllCard();
            String userPredictionText = "На вопрос \"" + userQuestion + "\" карты отвечают: " + all.getAllUserPrediction();

            ascii1.setText(all.getAsciiArt());
            animateText(card, cardText, () -> {
                animateText(prediction, userPredictionText, null);
            });
        });
    }

    @FXML
    protected void pressAllX3() {
        animateIntro(() -> {
            All all = new All();
            String cardsText = all.getAllCardsX3();
            String predictionText = all.getAllPredictionsX3();
            ascii1.setText(all.getAllAsciiX3(1));
            ascii2.setText(all.getAllAsciiX3(2));
            ascii3.setText(all.getAllAsciiX3(3));
            animateText(card, cardsText, () -> {
                animateText(prediction, predictionText, null);
            });
        });
    }

    @FXML
    protected void pressMajorX3() {
        animateIntro(() -> {
            Major major = new Major();
            String cardsText = major.getMajorCardsX3();
            String predictionsText = major.getMajorPredictionsX3();
            ascii1.setText(major.getMajorAsciiX3(1));
            ascii2.setText(major.getMajorAsciiX3(2));
            ascii3.setText(major.getMajorAsciiX3(3));
            animateText(card, cardsText, () -> {
                animateText(prediction, predictionsText, null);
            });
        });
    }
}
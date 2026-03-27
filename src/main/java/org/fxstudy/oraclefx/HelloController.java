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
    public TextArea ascii;
    public TextField questionField;
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

        chat.setStyle("-fx-padding: 0; -fx-background-insets: 0; -fx-border-width: 0;");
        chat.setEditable(false);
        animateText(chat,welcomeTxt, null);
    }

    @FXML
    protected void pressAll() {
        List<String> text;
        try {
            text = Files.readAllLines(welcomeText);
        } catch (IOException e) {
            chat.setText("Не удалось загрузить текст предсказания.");
            return;
        }
        String introText = text.get(1);
        animateText(chat, introText, () -> {
            All all = new All();
            String cardText = all.getAllCard();
            String predictionText = all.getAllPrediction();
            ascii.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
            ascii.setText(all.getAsciiArt());
            animateText(card, cardText, () -> {
                animateText(prediction, predictionText, null);
            });
        });
    }

    @FXML
    protected void pressMajor() {
        List<String> text;
        try {
            text = Files.readAllLines(welcomeText);
        } catch (IOException e) {
            chat.setText("Не удалось загрузить текст предсказания.");
            return;
        }
        String introText = text.get(1);
        animateText(chat, introText, () -> {
            Major major = new Major();
            String cardText = major.getMajorCard();
            String predictionText = major.getMajorPrediction();
            ascii.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
            ascii.setText(major.getAsciiArt());
            animateText(card, cardText, () -> {
                animateText(prediction, predictionText, null);
            });
        });
    }

    public void handleQuestion(ActionEvent event) {
        userQuestion = questionField.getText();
        questionField.clear();
        questionField.setPromptText("Вопрос принят");
        List<String> text;
        try {
            text = Files.readAllLines(welcomeText);
        } catch (IOException e) {
            chat.setText("Не удалось загрузить текст предсказания.");
            return;
        }
        String introText = text.get(1);
        animateText(chat, introText, () -> {
            All all = new All();
            String cardText = all.getAllCard();
            String userPredictionText = "На вопрос \"" + userQuestion + "\" карты отвечают: " + all.getAllUserPrediction();
            ascii.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px;");
            ascii.setText(all.getAsciiArt());
            animateText(card, cardText, () -> {
                animateText(prediction, userPredictionText, null);
            });
        });
    }
}

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;



public class Controller {

    public static int totalRecognized = 0;
    public static int incorrectlyRecognized = 0;
    public static int correctlyRecognized = 0;
    public static float precision = 0f;

    /**
     * Список для хранения изображений
     */
    public static ArrayList<WritableImage> screenShots = new ArrayList<WritableImage>();

    /**
     * Список для хранения имен изображений
     */
    public static ArrayList<String> screenshotsNames = new ArrayList<>();

    MediaPlayer mp = null;
    FileChooserHelper fileChooserHelper = new FileChooserHelper("Select Video", "user.dir");

    @FXML
    MediaView mediaView = new MediaView();

    /**
     * Label для вывода статистики
     */
    @FXML
    Label statisticLabel = new Label();

    /**
     * ImageView на первой вкладке
     */
    @FXML
    ImageView imageViewTop = new ImageView();
    @FXML
    ImageView imageViewCenter = new ImageView();
    @FXML
    ImageView imageViewBottom = new ImageView();

    /**
     * ImageView на второй вкладке
     */
    @FXML
    ImageView imageView_0 = new ImageView();



    /**
     *  Инициализация окна
     */
    @FXML
    private void initialize() {
        statisticLabel.setText(correctlyRecognized + " / " + totalRecognized);

        screenShots.add(null);
        screenShots.add(null);
        screenShots.add(null);

        screenshotsNames.add(null);
        screenshotsNames.add(null);
        screenshotsNames.add(null);
    }

    /**
     * Событие для открытия видео
     *
     * @param event
     */
    @FXML
    private void open(ActionEvent event) {

        try {
            File videoFile = fileChooserHelper.getFileChooser().showOpenDialog(new Stage());
            mp = new MediaPlayer(new Media(videoFile.toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            /**
             * Реализовать
             */
            e.printStackTrace();
        }

        mediaView.setMediaPlayer(mp);
    }

    /**
     * Событие для запуска видео
     *
     * @param event
     */
    @FXML
    private void start(ActionEvent event) {
        mp.play();
        mp.setMute(true);
    }

    /**
     * Событие для паузы видео
     *
     * @param event
     */
    @FXML
    private void pause(ActionEvent event) {
        mp.pause();
    }

    /**
     * Событие для остановки видео
     *
     * @param event
     */
    @FXML
    private void stop(ActionEvent event) {
        mp.stop();
    }

    /**
     * Событие для создания скриншота
     * @param event
     */
    @FXML
    private void snapshot(ActionEvent event) {

        WritableImage image = mediaView.snapshot(null, null);
        double time = mp.getCurrentTime().toSeconds();

        screenShots.add(image);
        screenshotsNames.add(String.valueOf(time));

        imageViewTop.setImage(screenShots.get(screenShots.size() - 1));
        imageViewCenter.setImage(screenShots.get(screenShots.size() - 2));
        imageViewBottom.setImage(screenShots.get(screenShots.size() - 3));

        /**
         * Добовляю первое изображение из массива изображений
         * на ImageView второй вкладки
         */
        imageView_0.setImage(screenShots.get(screenShots.size() - 3));

        totalRecognized++;
        correctlyRecognized = totalRecognized - incorrectlyRecognized;
        precision = ((float) correctlyRecognized / (float) totalRecognized) * 100;

        statisticLabel.setText(correctlyRecognized + " / " + totalRecognized + "   Точность: " + precision + "%");
    }

    /**
     * Событие для открытия окна для просмотра скриншота
     * @param event
     */
    @FXML
    private void openChoiceStage(ActionEvent event) {
        Stage choiceStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ScreenShootScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        choiceStage.setScene(new Scene(root, 1615, 830));
        choiceStage.setMaxWidth(1615);
        choiceStage.setMaxHeight(830);
        choiceStage.setMinWidth(1615);
        choiceStage.setMinHeight(830);
        choiceStage.show();

        mp.pause();
    }
}


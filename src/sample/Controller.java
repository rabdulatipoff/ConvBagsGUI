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
    public static int correctlyRecognized = 0;

    private int startingIndex = 0;
    private boolean isEnd = false;

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
    @FXML
    ImageView imageView_1 = new ImageView();
    @FXML
    ImageView imageView_2 = new ImageView();
    @FXML
    ImageView imageView_3 = new ImageView();
    @FXML
    ImageView imageView_4 = new ImageView();
    @FXML
    ImageView imageView_5 = new ImageView();
    @FXML
    ImageView imageView_6 = new ImageView();
    @FXML
    ImageView imageView_7 = new ImageView();
    @FXML
    ImageView imageView_8 = new ImageView();



    /**
     *  Инициализация окна
     */
    @FXML
    private void initialize() {
        statisticLabel.setText("0");

//        screenShots.add(null);
//        screenShots.add(null);
//        screenShots.add(null);
//
//        screenshotsNames.add(null);
//        screenshotsNames.add(null);
//        screenshotsNames.add(null);
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

        if (screenShots.size() > 0) {
            imageViewTop.setImage(screenShots.get(screenShots.size() - 1));
        }
        if (screenShots.size() > 1) {
            imageViewCenter.setImage(screenShots.get(screenShots.size() - 2));
        }
        if (screenShots.size() > 2) {
            imageViewBottom.setImage(screenShots.get(screenShots.size() - 3));
        }

        /**
         * Добовляю изображения из массива изображений
         * на ImageView второй вкладки
         */
        if (screenShots.size() > 0) {
            imageView_0.setImage(screenShots.get(startingIndex + 0));
        }
        if (screenShots.size() > 1) {
            imageView_1.setImage(screenShots.get(startingIndex + 1));
        }
        if (screenShots.size() > 2) {
            imageView_2.setImage(screenShots.get(startingIndex + 2));
        }
        if (screenShots.size() > 3) {
            imageView_3.setImage(screenShots.get(startingIndex + 3));
        }
        if (screenShots.size() > 4) {
            imageView_4.setImage(screenShots.get(startingIndex + 4));
        }
        if (screenShots.size() > 5) {
            imageView_5.setImage(screenShots.get(startingIndex + 5));
        }
        if (screenShots.size() > 6) {
            imageView_6.setImage(screenShots.get(startingIndex + 6));
        }
        if (screenShots.size() > 7) {
            imageView_7.setImage(screenShots.get(startingIndex + 7));
        }
        if (screenShots.size() > 8) {
            imageView_8.setImage(screenShots.get(startingIndex + 8));
        }

        statisticLabel.setText(String.valueOf(screenShots.size()));

        totalRecognized++;
        correctlyRecognized = totalRecognized;
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
        choiceStage.setMaxWidth(1920);
        choiceStage.setMaxHeight(986);
        choiceStage.setMinWidth(808);
        choiceStage.setMinHeight(415);
        choiceStage.show();

        mp.pause();
    }

    /**
     * Метод для перехода на страницу вправо
     * @param event
     */
    @FXML
    private void goRight(ActionEvent event) {

        if (!isEnd) {
            startingIndex += 9;
        }

        /**
         * Очищаю все ImageView при переходе на другую страницу
         */

        imageView_0.setImage(null);
        imageView_1.setImage(null);
        imageView_2.setImage(null);
        imageView_3.setImage(null);
        imageView_4.setImage(null);
        imageView_5.setImage(null);
        imageView_6.setImage(null);
        imageView_7.setImage(null);
        imageView_8.setImage(null);

        /**
         * Добовляю изображения из массива изображений
         * на ImageView второй вкладки
         */
        try {

            if (screenShots.size() > (startingIndex + 0)) {
                imageView_0.setImage(screenShots.get(startingIndex + 0));
            }
            if (screenShots.size() > (startingIndex + 1)) {
                imageView_1.setImage(screenShots.get(startingIndex + 1));
            }
            if (screenShots.size() > (startingIndex + 2)) {
                imageView_2.setImage(screenShots.get(startingIndex + 2));
            }
            if (screenShots.size() > (startingIndex + 3)) {
                imageView_3.setImage(screenShots.get(startingIndex + 3));
            }
            if (screenShots.size() > (startingIndex + 4)) {
                imageView_4.setImage(screenShots.get(startingIndex + 4));
            }
            if (screenShots.size() > (startingIndex + 5)) {
                imageView_5.setImage(screenShots.get(startingIndex + 5));
            }
            if (screenShots.size() > (startingIndex + 6)) {
                imageView_6.setImage(screenShots.get(startingIndex + 6));
            }
            if (screenShots.size() > (startingIndex + 7)) {
                imageView_7.setImage(screenShots.get(startingIndex + 7));
            }
            if (screenShots.size() > (startingIndex + 8)) {
                imageView_8.setImage(screenShots.get(startingIndex + 8));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

            isEnd = true;
            System.out.println("Скриншоты кончились!");
        }
    }

    /**
     * Метод для перехода на страницу влево
     * @param event
     */
    @FXML
    private void goLeft(ActionEvent event) {

        if (!isEnd) {
            startingIndex -= 9;
        }

        /**
         * Очищаю все ImageView при переходе на другую страницу
         */

        imageView_0.setImage(null);
        imageView_1.setImage(null);
        imageView_2.setImage(null);
        imageView_3.setImage(null);
        imageView_4.setImage(null);
        imageView_5.setImage(null);
        imageView_6.setImage(null);
        imageView_7.setImage(null);
        imageView_8.setImage(null);

        /**
         * Добовляю изображения из массива изображений
         * на ImageView второй вкладки
         */
        try {

            if (screenShots.size() > (startingIndex + 0)) {
                imageView_0.setImage(screenShots.get(startingIndex + 0));
            }
            if (screenShots.size() > (startingIndex + 1)) {
                imageView_1.setImage(screenShots.get(startingIndex + 1));
            }
            if (screenShots.size() > (startingIndex + 2)) {
                imageView_2.setImage(screenShots.get(startingIndex + 2));
            }
            if (screenShots.size() > (startingIndex + 3)) {
                imageView_3.setImage(screenShots.get(startingIndex + 3));
            }
            if (screenShots.size() > (startingIndex + 4)) {
                imageView_4.setImage(screenShots.get(startingIndex + 4));
            }
            if (screenShots.size() > (startingIndex + 5)) {
                imageView_5.setImage(screenShots.get(startingIndex + 5));
            }
            if (screenShots.size() > (startingIndex + 6)) {
                imageView_6.setImage(screenShots.get(startingIndex + 6));
            }
            if (screenShots.size() > (startingIndex + 7)) {
                imageView_7.setImage(screenShots.get(startingIndex + 7));
            }
            if (screenShots.size() > (startingIndex + 8)) {
                imageView_8.setImage(screenShots.get(startingIndex + 8));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

            isEnd = true;
            System.out.println("Скриншоты кончились!");
        }
    }
}


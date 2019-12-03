package sample;

import javafx.embed.swing.SwingFXUtils;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Controller {

    @FXML
    MediaView mediaView = new MediaView();
    MediaPlayer mp = null;
    FileChooserHelper fileChooserHelper = new FileChooserHelper("Select Video", "user.dir");
    ArrayList<WritableImage> screenShoots = new ArrayList<WritableImage>();

    @FXML
    ImageView imageViewTop = new ImageView();
    @FXML
    ImageView imageViewCenter = new ImageView();
    @FXML
    ImageView imageViewBottom = new ImageView();

    @FXML
    ImageView statView = new ImageView();

    @FXML
    Label statisticLabel = new Label();


    /**
     * Тестовый ImageView на второй вкладке
     */
    @FXML
    ImageView imageView_0 = new ImageView();

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

        screenShoots.add(null);
        screenShoots.add(null);
        screenShoots.add(null);
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

    @FXML
    private void snapshot(ActionEvent event) {
        WritableImage image = mediaView.snapshot(null, null);
        screenShoots.add(image);

        imageViewTop.setImage(screenShoots.get(screenShoots.size() - 3));
        imageViewCenter.setImage(screenShoots.get(screenShoots.size() - 2));
        imageViewBottom.setImage(screenShoots.get(screenShoots.size() - 1));

        statView.setImage(screenShoots.get(0));

        double time = mp.getCurrentTime().toSeconds();
        File file = new File(".\\Screenshots\\" + time + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }

        statisticLabel.setText("87 / 100");


        /**
         * Добовляю первое изображение из массива изображений на ImageView
         */
        imageView_0.setImage(screenShoots.get(0));



//        Stage choiceStage = new Stage();
//        Parent root = null;
//        try {
//            root = FXMLLoader.load(getClass().getResource("ScreenShootScene.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        choiceStage.setScene(new Scene(root, 1615, 830));
//        choiceStage.setMaxWidth(1615);
//        choiceStage.setMaxHeight(830);
//        choiceStage.setMinWidth(1615);
//        choiceStage.setMinHeight(830);
//        choiceStage.show();
    }
}


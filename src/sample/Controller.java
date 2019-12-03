package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
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

        double time = mp.getCurrentTime().toSeconds();
        File file = new File(".\\Screenshoots\\" + time + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }
}


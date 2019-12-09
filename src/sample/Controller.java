package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Controller {

    public static int totalRecognized = 0;
    public static int correctlyRecognized = 0;

    private int startingIndex = 0;
    private boolean isEnd = false;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    private VideoCapture capture = new VideoCapture();
    // a flag to change the button behavior
    private boolean cameraActive = false;
    // the id of the camera to be used
    private static int cameraId = 0;

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

    @FXML
    Button captureButton;

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

    @FXML
    private ImageView currentFrame;


    /**
     * Инициализация окна
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
     *
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
     *
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
     *
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
     *
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

    @FXML
    private void capture(ActionEvent event) {
        if (!this.cameraActive)
        {
            // start the video capture
            this.capture.open(cameraId);

            // is the video stream available?
            if (this.capture.isOpened())
            {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run()
                    {
                        // effectively grab and process a single frame
                        Mat frame = grabFrame();
                        // convert and show the frame
                        Image imageToShow = Utils.mat2Image(frame);
                        updateImageView(currentFrame, imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
                this.captureButton.setText("Stop Camera");
            }
            else
            {
                // log the error
                System.err.println("Impossible to open the camera connection...");
            }
        }
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the button content
            this.captureButton.setText("Start Camera");

            // stop the timer
            this.stopAcquisition();
        }
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Mat} to show
     */
    private Mat grabFrame()
    {
        // init everything
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened())
        {
            try
            {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty())
                {
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                }

            }
            catch (Exception e)
            {
                // log the error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed()
    {
        this.stopAcquisition();
    }
}


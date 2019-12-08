package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class ScreenShootController {

    int screenshotIndex;
    public static int totalRecognizedSSC;
    public static int incorrectlyRecognizedSSC;
    public static int correctlyRecognizedSSC;
    public static float precisionSSC;

    @FXML
    ImageView imageView = new ImageView();

    @FXML
    Label totalLabel = new Label();

    @FXML
    Label currentLabel = new Label();

    @FXML
    Label precisionLabel = new Label();

    @FXML
    public void initialize() {

        totalRecognizedSSC = Controller.totalRecognized;
        incorrectlyRecognizedSSC = 0;
        correctlyRecognizedSSC = Controller.correctlyRecognized;
        precisionSSC = ((float) correctlyRecognizedSSC / (float) totalRecognizedSSC) * 100f;

        totalLabel.setText(String.valueOf(totalRecognizedSSC));
        currentLabel.setText(String.valueOf(correctlyRecognizedSSC));
        precisionLabel.setText(String.valueOf(precisionSSC) + "%");

        screenshotIndex = Controller.screenShots.size() - (Controller.screenShots.size() - 3);
        imageView.setImage(Controller.screenShots.get(screenshotIndex));
    }

    /**
     * Нажатие кнопки "Верно"
     * @param event
     */
    @FXML
    private void trueChoice(ActionEvent event) {

        screenshotIndex++;
        try {
            imageView.setImage(Controller.screenShots.get(screenshotIndex));
        } catch (IndexOutOfBoundsException e) {

            // Переделать
            System.out.println("Скриншоты закончились!");
        }

    }

    /**
     * Нажатие кнопки "Неверно"
     * @param event
     */
    @FXML
    private void falseChoice(ActionEvent event) {

        /**
         * Сохронение изображения
         */
        File file = new File(".\\Screenshots\\" + Controller.screenshotsNames.get(screenshotIndex) + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(Controller.screenShots.get(screenshotIndex), null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }

        correctlyRecognizedSSC--;

        /**
         * НЕ РАБОТАЕТ! precision = 0.0
         */
        precisionSSC = ((float) correctlyRecognizedSSC / (float) totalRecognizedSSC) * 100f;

        currentLabel.setText(String.valueOf(correctlyRecognizedSSC));
        precisionLabel.setText(String.valueOf(precisionSSC) + "%");

        screenshotIndex++;
        try {
            imageView.setImage(Controller.screenShots.get(screenshotIndex));
        } catch (IndexOutOfBoundsException e) {

            // Переделать
            System.out.println("Скриншоты закончились!");
        }
    }
}

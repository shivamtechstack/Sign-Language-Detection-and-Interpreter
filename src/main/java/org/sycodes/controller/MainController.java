package org.sycodes.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.awt.*;

public class MainController {

    @FXML
    private ImageView cameraView;

//    @FXML
//    private Label detectedSignLabel;

    private VideoCapture capture;
    private boolean cameraActive = false;

    @FXML
    public void initialize(){
        startCamera();
    }

    private void startCamera(){
        capture = new VideoCapture(0);
        if (!capture.isOpened()){
            System.err.println("Cannot open camera");
            return;
        }

        cameraActive = true;

        Thread frameGrabber = new Thread(()-> {
            Mat frame = new Mat();
            while (cameraActive){
                if(capture.read(frame)){
                    Imgproc.cvtColor(frame,frame, Imgproc.COLOR_BGR2RGB);

                    Image imageToShow = mat2Image(frame);
                    Platform.runLater(() -> {
                        System.out.println("Setting image...");
                        cameraView.setImage(imageToShow);
                    });
                }
            }
        });
        frameGrabber.setDaemon(true);
        frameGrabber.start();
    }

    private Image mat2Image(Mat frame) {
        if (frame.empty()) {
            System.err.println("Empty frame!");
            return null;
        }

        int width = frame.width();
        int height = frame.height();
        int channels = frame.channels();

        byte[] sourcePixels = new byte[width * height * channels];
        frame.get(0, 0, sourcePixels);

        WritableImage image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();

        int r, g, b;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = (y * width + x) * channels;
                r = sourcePixels[index] & 0xFF;
                g = sourcePixels[index + 1] & 0xFF;
                b = sourcePixels[index + 2] & 0xFF;
                pw.setColor(x, y, Color.rgb(r, g, b));
            }
        }

        return image;
    }

    @FXML
    public void onSpeakClick(){
        System.out.println("Speaking : ");
    }
}

package applications;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 * Created by gokhangunay on 30/09/15.
 */
public class MediaBar extends HBox{


    Slider sliderTime = new Slider();
    Slider sliderVolume = new Slider();

    Button playButton = new Button("||");

    Label volume = new Label("Ses: ");

    MediaPlayer mediaPlayer;

    public MediaBar(final MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5,10,5,10));

        sliderVolume.setPrefWidth(70);
        sliderVolume.setMinWidth(30);
        sliderVolume.setValue(100);

        HBox.setHgrow(sliderTime, Priority.ALWAYS);

        playButton.setPrefWidth(30);
        getChildren().add(playButton);
        getChildren().add(sliderTime);
        getChildren().add(volume);
        getChildren().add(sliderVolume);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                Status status = mediaPlayer.getStatus();
                if(status == Status.PLAYING){
                    if(mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())){
                        mediaPlayer.seek(mediaPlayer.getStartTime());
                        mediaPlayer.play();
                    }
                    else{
                        mediaPlayer.pause();
                        playButton.setText(">");
                    }

                }

                if(status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED){
                    mediaPlayer.play();
                    playButton.setText("||");
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov){
                updateValue();
            }
        });

        sliderTime.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if(sliderTime.isPressed()){
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(sliderTime.getValue()/100));
                }
            }
        });

        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if(sliderVolume.isPressed()){
                    mediaPlayer.setVolume(sliderVolume.getValue()/100);
                }
            }
        });

    }

    protected void updateValue(){
        Platform.runLater(new Runnable() {
            public void run(){
                sliderTime.setValue(mediaPlayer.getCurrentTime().toMillis()/mediaPlayer.getTotalDuration().toMillis()*100);
            }
        });
    }
}

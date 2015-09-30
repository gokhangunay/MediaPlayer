package applications;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Created by gokhangunay on 29/09/15.
 */
public class Player extends BorderPane{

    Media media;
    MediaView mediaView;
    MediaPlayer mediaPlayer;
    Pane mediaPane;
    MediaBar mediaBar;

    public Player(String file){
        media = new Media(file);
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        mediaPane = new Pane();
        mediaPane.getChildren().add(mediaView);

        setCenter(mediaPane);

        mediaBar = new MediaBar(mediaPlayer);

        setBottom(mediaBar);

        setStyle("-fx-background-color: chartreuse");

        mediaPlayer.play();
    }

}

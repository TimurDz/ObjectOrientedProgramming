package PacMan.View;

import PacMan.Model.Direction;
import PacMan.View.Pane.Menu;
import PacMan.View.Pane.Plateau;
import PacMan.Model.Jeu;
import PacMan.View.Pane.EndScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class ApplicationPacMan extends Application {
    private Stage stage;
    private AudioClip acBeginning;
    private AudioClip acChomp;
    private AudioClip acDeath;
    private AudioClip acWin;

    private static int UPDATE = 0; // Use to play the death only one

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Importing the font that will be used in the game
        Font.loadFont(System.class.getResource("/fonts/alterebro-pixel-font.ttf").toExternalForm(), 20);

        stage = primaryStage;

        acBeginning = new AudioClip(System.class.getResource("/sound/pacman_beginning.wav").toExternalForm());
        acBeginning.setCycleCount(AudioClip.INDEFINITE);
        acBeginning.setVolume(0.50);
        acBeginning.play();

        acChomp = new AudioClip(System.class.getResource("/sound/pacman_chomp.wav").toExternalForm());
        acChomp.setCycleCount(AudioClip.INDEFINITE);
        acChomp.setVolume(0.25);

        acDeath = new AudioClip(System.class.getResource("/sound/pacman_death.wav").toExternalForm());
        acDeath.setCycleCount(1);
        acDeath.setVolume(0.5);
        
        acWin = new AudioClip(System.class.getResource("/sound/streetfighter_youwin.mp3").toExternalForm());
        acWin.setCycleCount(1);
        acWin.setVolume(0.5);

        setMenuOnStage();

        primaryStage.setTitle("PacMan FX");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);
        primaryStage.getIcons().add(new Image(System.class.getResourceAsStream("/icons/logo.png")));
        primaryStage.show();
    }

    private void setMenuOnStage() {
        Menu menu = new Menu();

        menu.playButton.setOnMouseClicked((click) -> {
            acBeginning.stop();
            setPlateauOnStage();

        });

        menu.exitButton.setOnMouseClicked((click) -> {
            Platform.exit();
        });

        Scene scene = new Scene(menu);

        stage.setScene(scene);
    }
    
    private void setEndScreenOnStage(boolean victory, int scorePacman) {
        EndScreen endScreen = new EndScreen(victory, scorePacman);

        endScreen.replayButton.setOnMouseClicked((click) -> {
            setPlateauOnStage();

        });

        endScreen.exitButton.setOnMouseClicked((click) -> {
            Platform.exit();
        });

        Scene scene = new Scene(endScreen);

        stage.setScene(scene);
    }

    private void setPlateauOnStage() {
        this.UPDATE = 0;
        acChomp.play();

        Plateau plateau = new Plateau();
        Scene scene = new Scene(plateau);
        Jeu jeu = plateau.getJeu();

        stage.setScene(scene);

        scene.setOnKeyPressed(key -> {
            Direction direction;
            switch(key.getCode()) {
                case UP:
                case Z:
                    direction = Direction.UP;
                    break;

                case DOWN:
                case S:
                    direction = Direction.DOWN;
                    break;

                case LEFT:
                case Q:
                    direction = Direction.LEFT;
                    break;

                case RIGHT:
                case D:
                    direction = Direction.RIGHT;
                    break;

                default:
                    direction = Direction.NOT_A_DIRECTION;
                    break;
            }

            jeu.deplacer(direction);

        });

        jeu.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                plateau.draw();

                if(ApplicationPacMan.UPDATE == 0) {
                    ApplicationPacMan.UPDATE = 1;

                    if (!plateau.getJeu().getPacman().isAlive() && !acDeath.isPlaying()) {
                        acChomp.stop();
                        acDeath.play();

                        while (acDeath.isPlaying()) {

                            //System.out.println("Waiting");
                        }
                        jeu.killThreads();
                        setEndScreenOnStage(false, plateau.getJeu().score);
                        return;

                    }

                    if (plateau.getJeu().finPartie()) {
                        acChomp.stop();
                        acWin.play();

                        jeu.killThreads();
                        setEndScreenOnStage(true, plateau.getJeu().score);
                    }

                    ApplicationPacMan.UPDATE = 0;
                }
            }
        });

        /**
         * Stop all thread to properly close the game
         */
        stage.setOnCloseRequest((e) -> {
            jeu.killThreads();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
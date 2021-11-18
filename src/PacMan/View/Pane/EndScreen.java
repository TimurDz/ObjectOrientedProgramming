/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PacMan.View.Pane;

import PacMan.View.Components.MenuButton;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Paul
 */
public class EndScreen extends StackPane{
    
    public MenuButton replayButton;
    public MenuButton exitButton;
    private ImageView banniere;
    
    public EndScreen(boolean victory, int scorePacman) {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setMinWidth(700);
        this.setMinHeight(700);
        
        if (victory) {
            banniere = new ImageView(new Image(System.class.getResourceAsStream("/icons/youwin.png")));
        } else {
            banniere = new ImageView(new Image(System.class.getResourceAsStream("/icons/gameover.png")));
        }

        StackPane.setAlignment(banniere, Pos.TOP_CENTER);

        this.getChildren().add(banniere);
        
        StackPane sPane = new StackPane();
        
        Text score = new Text("Score : " + scorePacman);
        Font customFont = new Font("Alterebro Pixel Font", 20);
        score.setFont(customFont);
        score.setX(50);
        score.setFill(Color.WHITE);
        
        StackPane.setAlignment(score, Pos.TOP_CENTER);
        sPane.getChildren().add(score);
        
        replayButton = new MenuButton("Replay");
        replayButton.setTextAlignment(TextAlignment.CENTER);

        StackPane.setAlignment(replayButton, Pos.CENTER);
        sPane.getChildren().add(replayButton);
        
        sPane.setMaxHeight(200);
        sPane.setMaxWidth(200);
            
        StackPane.setAlignment(sPane, Pos.CENTER);
        this.getChildren().add(sPane);

        exitButton = new MenuButton("Exit");
        exitButton.setTextAlignment(TextAlignment.CENTER);

        StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);

        this.getChildren().add(exitButton);
    }
}

package PacMan.View.Pane;

import PacMan.View.Components.MenuButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Menu extends StackPane {

    public MenuButton playButton;
    public MenuButton exitButton;
    private ImageView banniere;

    public Menu() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setMinWidth(700);
        this.setMinHeight(700);

        banniere = new ImageView(new Image(System.class.getResourceAsStream("/icons/banniere.png")));

        StackPane.setAlignment(banniere, Pos.TOP_CENTER);

        this.getChildren().add(banniere);

        playButton = new MenuButton("Play");
        playButton.setTextAlignment(TextAlignment.CENTER);

        StackPane.setAlignment(playButton, Pos.CENTER);

        this.getChildren().add(playButton);

        exitButton = new MenuButton("Exit");
        exitButton.setTextAlignment(TextAlignment.CENTER);

        StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);

        this.getChildren().add(exitButton);
    }
}

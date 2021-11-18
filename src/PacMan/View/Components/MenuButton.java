package PacMan.View.Components;

import javafx.scene.control.Button;

public class MenuButton extends Button {
    public MenuButton() {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add("rich-blue");

        this.getStylesheets().add(System.class.getResource("/css/button.css").toExternalForm());

        this.setMinHeight(40);
        this.setMinWidth(100);
    }

    public MenuButton(String text){
        super(text);
        this.getStyleClass().clear();
        this.getStyleClass().add("rich-blue");

        this.getStylesheets().add(System.class.getResource("/css/button.css").toExternalForm());

        this.setMinHeight(40);
        this.setMinWidth(100);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Bot;
import blokus.model.Model;
import blokus.model.Player;
import blokus.model.Point;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class firstScene extends GridPane {

    TextField play1 = new TextField();
    TextField play2 = new TextField();
    TextField play3 = new TextField();
    TextField play4 = new TextField();
    Button newGame;
    Stage window = new Stage();

    public firstScene(Model g, Stage primaryStage, Button bu) {
        super();
        window = primaryStage;
        VBox vbox = new VBox();
        this.newGame = new Button();
        this.setPadding(new Insets(20));
        this.setHgap(5);
        this.setVgap(5);
        Label player1 = new Label("Player 1 :");
        Label player2 = new Label("Player 2 :");
        Label player3 = new Label("Player 3 :");
        Label player4 = new Label("Player 4 :");
        Label topLabel = new Label("Bienvenue dans blokus veuillez rentrez vos joueurs");
        Label botLabel = new Label("Le premier joueur est obligatoire , si vous laisser un player vide il sera considéré comme bot");
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        topLabel.setMinHeight(50);
        this.add(topLabel, 1, 0);
        this.add(play1, 0, 2);
        this.add(player1, 0, 1);
        this.add(player2, 0, 3);
        this.add(play2, 0, 4);
        this.add(player3, 0, 5);
        this.add(play3, 0, 6);
        this.add(player4, 0, 7);
        this.add(play4, 0, 8);
        this.add(botLabel, 1, 10);
        Scene scene = new Scene(vbox, 1000, 750);
        newGame = new Button("Commencer partie");

        this.add(newGame, 0, 10);

        newGame.setOnAction(e -> {
           
            
            aze(play1,play2, play3, play4, g);
            WindowGame win = new WindowGame(g, bu);
            vbox.getChildren().add(win);
            window.setScene(scene);
        });

    }

    /**
     * can add Player in game
     *
     * @param play the case who the player can put his name
     * @param g the current game.
     */
    public void addPlayer(TextField play, Model g) {
        switch (g.getPlayersInGame().size()) {
            case 0:
                Player play1 = new Player(play.getText(), Color.RED, 1, new Point(19, 0));
                g.addPlayer(play1);
                break;
            case 1:
                Player play2 = new Player(play.getText(), Color.BLUE, 2, new Point(0, 19));
                g.addPlayer(play2);
                break;
            case 2:
                Player play3 = new Player(play.getText(), Color.YELLOW, 3, new Point(19, 19));
                g.addPlayer(play3);
                break;
            case 3:

                Player p4 = new Player(play.getText(), Color.GREEN, 4, new Point(0, 0));
                g.addPlayer(p4);
                break;
        }
    }

    /**
     * can add Player in game or bot.
     *
     * @param play the case who the player can put his name
     * @param playe2 the case who the player can put his name
     * @param playe3 the case who the player can put his name
     * @param playe4 the case who the player can put his name
     * @param g the current game
     */
    public void aze( TextField playe1 ,TextField playe2, TextField playe3, TextField playe4, Model g) {
        
        if (!playe1.getText().isEmpty()) {
            addPlayer(playe1, g);
        } else {
            Player bot = new Bot("Bot1", Color.RED, 1, new Point(19, 0), true);
            g.addPlayer(bot);
        }
        if (!playe2.getText().isEmpty()) {
            addPlayer(playe2, g);
        } else {
            Player bot1 = new Bot("Bot2", Color.BLUE, 2, new Point(0, 19), true);
            g.addPlayer(bot1);
        }
        if (!playe3.getText().isEmpty()) {
            addPlayer(playe3, g);
        } else {
            Player bot2 = new Bot("Bot3", Color.YELLOW, 3, new Point(19, 19), true);
            g.addPlayer(bot2);
        }

        if (!playe4.getText().isEmpty()) {
            addPlayer(playe4, g);
        } else {
            Player bot3 = new Bot("Bot4", Color.GREEN, 4, new Point(0, 0), true);
            g.addPlayer(bot3);
        }

    }
}

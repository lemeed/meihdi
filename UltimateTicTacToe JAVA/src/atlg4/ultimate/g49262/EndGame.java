
package atlg4.ultimate.g49262;

import atlg4.ultimate.g49262.model.Game;
import atlg4.ultimate.g49262.view.UTTController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The panel when the game is over.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class EndGame {

    @FXML
    Label winner = new Label("");

    @FXML
    Button exit;
    @FXML
    Button replay;
    @FXML
    Button home;

    Game game;

    public EndGame(Game g) {
        game = g;
        this.replay = new Button();

    }

    @FXML

    public void initialize() {

        if (game.getPlayingPlayers().size() == 1) {

            replay.setDisable(false);
        }
        printWinner();

        home.setOnAction((event) -> {
            try {
                initHome();
            } catch (IOException ex) {
                Logger.getLogger(EndGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        exit.setOnAction((event) -> {
            initExit();
        });
        replay.setOnAction((event) -> {
            try {
                if (game.getPlayingPlayers().size() == 1) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/StartGame.fxml"));

                    StartGameController start = new StartGameController(game);
                    loader.setController(start);
                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage s = new Stage();

                    s.setMinHeight(450);
                    s.setMinWidth(650);
                    s.setScene(scene);
                    s.show();

                    Stage st = (Stage) replay.getScene().getWindow();
                    st.close();
                } else {
                    replay();
                }

            } catch (IOException ex) {
                Logger.getLogger(EndGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    /**
     * Allows to init the exit button.
     */
    private void initExit() {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();

    }

    /**
     * Allows to init the home button.
     * @throws IOException 
     */
    private void initHome() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Welcome.fxml"));
        WelcomeController welcome = new WelcomeController(game);
        loader.setController(welcome);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage s = new Stage();

        s.setScene(scene);
        s.show();

        Stage ss = (Stage) home.getScene().getWindow();
        ss.close();
    }

    /**
     * Allows to print the winner of the game.
     */
    private void printWinner() {
        if (game.getPlayingPlayers().size() == 1) {
            winner.setText(game.getPlayingPlayers().get(0).getName() + " IS THE WINNER");

        } else if (game.getWinnerOfTheGame().size() == 1 && game.getPlayingPlayers().size() > 1) {
            winner.setText(game.getWinnerOfTheGame().get(0).getName() + " IS THE WINNER");
        } else {
            winner.setText("It's a draw !");
        }
    }

    /**
     * Allows to replay the game with the same player.
     * @throws IOException 
     */
    private void replay() throws IOException {

        FXMLLoader l = new FXMLLoader(getClass().getResource("view/UTT.fxml"));
        Game g = new Game();
        g.addPlayer(game.getPlayingPlayers().get(0).getName());
        g.addPlayer(game.getPlayingPlayers().get(1).getName());

        UTTController w = new UTTController(g);
        l.setController(w);
        Parent root = l.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();

        s.setScene(scene);
        s.show();

        Stage ss = (Stage) home.getScene().getWindow();
        ss.close();

    }

}

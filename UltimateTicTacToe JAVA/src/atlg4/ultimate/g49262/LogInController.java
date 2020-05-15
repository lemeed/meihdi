package atlg4.ultimate.g49262;

import atlg4.ultimate.g49262.db.DBManager;
import atlg4.ultimate.g49262.exception.DBException;
import atlg4.ultimate.g49262.exception.DTOException;
import atlg4.ultimate.g49262.model.Game;
import atlg4.ultimate.g49262.pers.PlayerDto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represent the logInController view.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class LogInController {

    @FXML
    private TextField newPlayer;

    @FXML
    private Button logIn;

    @FXML
    private Button home;

    private Game game;
    
    private DBManager db ;

    LogInController(Game aGame) {
        game = aGame;
    }

    @FXML
    public void initialize() {

        home.setOnAction((event) -> {
            home();
        });

        logIn.setOnAction((event) -> {
            
            try {
                if (!check()) {
                    game.getDatabase().registerPlayer(newPlayer.getText());
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("REGISTER");
                    alert.setHeaderText("succes :");
                    alert.setContentText("the Player is now register !");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/StartGame.fxml"));
                    WelcomeController c = new WelcomeController(game);
                    loader.setController(c);
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setMinHeight(450);
                    stage.setMinWidth(650);
                    stage.setScene(scene);
                    stage.show();
                    Stage st = (Stage) logIn.getScene().getWindow();
                    st.close();
                } else if (check()) {
                    AlertUTT alert = new AlertUTT(Alert.AlertType.INFORMATION, "REGISTER", "WARNING :", "this player already exists !");
                }
            } catch (IOException ex) {
            } catch (DBException ex) {
                Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DTOException ex) {
                Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void home() {
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("view/Welcome.fxml"));
            WelcomeController w = new WelcomeController(game);

            l.setController(w);
            Parent root = l.load();

            Scene scene = new Scene(root);
            Stage s = new Stage();
            s.setMinHeight(450);
            s.setMinWidth(650);

            s.setScene(scene);
            s.show();

            Stage st = (Stage) home.getScene().getWindow();
            st.close();
        } catch (IOException e) {

        }
    }

    private boolean check() throws DBException, DTOException {
        for (PlayerDto player : game.getDatabase().getAllPlayers()) {
            if (player.getPseudo().equals(newPlayer.getText())) {
                return true;
            }
        }
        return false;
    }

}

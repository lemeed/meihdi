/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262;


import atlg4.ultimate.g49262.model.Game;
import atlg4.ultimate.g49262.db.History;
import atlg4.ultimate.g49262.exception.BusinessException;
import atlg4.ultimate.g49262.exception.DBException;
import atlg4.ultimate.g49262.exception.DTOException;
import atlg4.ultimate.g49262.pers.PlayerDto;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Meihdi El Amouri
 */
public class HistoController   {
    
    @FXML
    private Label namePlayer = new Label("");
    @FXML
    private Label win = new Label("");
    @FXML
    private Label ex_aequo = new Label("");
    @FXML
    private Label defeat= new Label("");
    @FXML
    private Button back= new Button();

    private String pseudonym;
    private Game game;
    private String name;

    HistoController( Game g , String aName ) throws BusinessException, DTOException, DBException {
        System.out.println("eofzfj");
        name = aName;
        game = g;
        System.out.println(getPlayer().getPseudo());
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() throws BusinessException, DTOException, DBException {
        namePlayer.setText(getPlayer().getPseudo());
        win.setText(getPlayer().getNbWin()+"");
        ex_aequo.setText(getPlayer().getNbExAequo()+"");
        defeat.setText(getPlayer().getNbDefeat()+"");
        
         back.setOnAction((event) -> {
             initButton();
        });
        
    }    
    
    /**
     * Allows to initialize the back button.
     */
    public void initButton(){
        back.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Welcome.fxml"));
                WelcomeController c = new WelcomeController(game);
                loader.setController(c);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setMinHeight(450);
                stage.setMinWidth(650);
                stage.setScene(scene);
                stage.show();
                Stage st = (Stage) back.getScene().getWindow();
                st.close();
            } catch (IOException ex) {
            }
        });
    }

    
    
    
     private PlayerDto getPlayer() throws BusinessException, DTOException, DBException{
        for (PlayerDto player : game.getDatabase().getAllPlayers()){
            if (player.getPseudo().equals(name)){
                return player;
            }
        }
        return null;
    }
}

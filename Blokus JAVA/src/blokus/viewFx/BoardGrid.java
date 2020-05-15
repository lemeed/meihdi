package blokus.viewFx;

import blokus.model.BlokusException;

import blokus.model.Model;
import blokus.model.Point;
import blokus.viewFx.SquareGrid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import blokus.util.Observer;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Meihdi El Amouri
 */
public class BoardGrid extends GridPane implements Observer {

    private MouseEvent event;

    /**
     * Constructor of the grid for the board of the game
     *
     * @param g
     */
    public BoardGrid(Model g) {
        super();
        g.registerObserver(this);
        this.setPadding(new Insets(15));
        initialize(g);

    }

    /**
     * Initialize the board view
     *
     * @param g
     */
    private void initialize(Model g) {
        this.getChildren().clear();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                this.add(new SquareGrid(i, j, g), i, j);
            }
        }
        this.addEventHandler(MouseEvent.MOUSE_MOVED, (MouseEvent event) -> {

            g.selectPiece(g.getPreviousPiece(), g.getCurrentPlayer());

            event.consume();
        });

    }

    /**
     * Put a piece in the Blokus board
     *
     * @param g
     * @throws BlokusException
     */
    public void put(Model g) throws BlokusException {
        clearBoard(g);
        SquareGrid sg;
        if (g.getPreviousPiece() != null) {
            for (Node r : this.getChildren()) {
                sg = (SquareGrid) r;
                List<Point> l = g.positionLive();
                for (Point p : l) {
                    if (p.equals(new Point(sg.getXforSquare(), sg.getYforSquare()))) {
                        sg.colorSquare(g);
                    }
                }
            }
        }

    }

    /**
     * Update the game
     *
     * @param g
     */
    @Override
    public void update(Model g) {
        try {
                 if (g.endOfTheGame()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game is over :'(");
            alert.setHeaderText("Hey the game is over ! :)");

            alert.setContentText("The winner is  " + g.getWinner());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            } else {
                Platform.exit();
            }
        }
            put(g);
        } catch (BlokusException ex) {
            Logger.getLogger(BoardGrid.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Clear the board
     *
     * @param g
     */
    private void clearBoard(Model g) {
        SquareGrid sg;
        for (Node r : this.getChildren()) {
            sg = (SquareGrid) r;
            sg.addColor(g);
        }
    }

}

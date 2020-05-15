
package atlg4.ultimate.g49262.db;

import atlg4.ultimate.g49262.exception.DBException;
import atlg4.ultimate.g49262.exception.DTOException;
import atlg4.ultimate.g49262.pers.PlayerDto;
import atlg4.ultimate.g49262.pers.PlayerSel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represent The DATA BASE manager.
 * @author El Amouri Meihdi
 */
public class DBManager {

    private static Connection connection;

    /**
     * The constructor for connect my DB.
     */
    public DBManager() {
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/UltimateTTT", "xxx", "xxx");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: DATA NOT OPENED");
        }
        
    }
    /**
     * Allows to register a player in DB.
     * @param player the player we want to add.
     * @return true if he's registered.
     */
    public boolean registerPlayer(String player) {
    
        boolean registered = false;
        if (!player.equals("") && !isRegisterPlayer(player)) {
            try {
                System.out.println("coucou");
                String query = "insert into PLAYER(NAME_PLAYER)values((?))";
                PreparedStatement statt = connection.prepareStatement(query);
                statt.setString(1, player);
                int a = statt.executeUpdate();
                if (a > 0) {
                    registered = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registered;
    }

    /**
     * Allows to get the connection of DB.
     * @return The connection.
     */
    public static Connection getConnection() {
        return connection;
    }



    /**
     * Allows to know if the Player is already registered.
     * @param player the name of player.
     * @return true if he's registered.
     */
    public boolean isRegisterPlayer(String player) {
        boolean isRegistered = false;
        String query = "Select * from PLAYER ";
        try {

            Statement stat = connection.createStatement();

            ResultSet resultSet = stat.executeQuery(query);

            while (resultSet.next()) {
                if (resultSet.getString("NAME_PLAYER") == player) {
                    isRegistered = true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isRegistered;
    }

    /**
     * Allows to get all registered player.
     * @return the registered player in a observable list.
     */
    public ObservableList<String> getAllRegistredPlayer() {
        ObservableList<String> listString = FXCollections.observableArrayList();

        String query = "Select * from PLAYER ";
        try {

            Statement stat = connection.createStatement();

            ResultSet resultSet = stat.executeQuery(query);

            while (resultSet.next()) {
                listString.add(resultSet.getString("NAME_PLAYER"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listString;
    }

    

   
    /**
     * Allows to update the player for add parameter like wins , defeat or draw.
     * @param cli the client we want to update.
     * @throws DBException 
     */
    public  void updateDb(PlayerDto cli) throws DBException {
       
        
       
        try {
            
            
            java.sql.PreparedStatement update;
            String sql = "Update PLAYER set "
                    
                    + "WINS=?,"
                    + "DRAW=?, "
                    + "LOSS=? "
                    + "where NAME_PLAYER=?";
                    
            update = connection.prepareStatement(sql);
            System.out.println("lol");
            update.setInt(1, cli.getNbWin());
            update.setInt(2, cli.getNbExAequo());
            update.setInt(3, cli.getNbDefeat());
            update.setString(4, cli.getPseudo());
            
            update.executeUpdate();
        } catch (Exception ex) {
            throw new DBException("Player, modification impossible:\n" + ex.getMessage());
        }
    }
    
    /**
     * Allows to get all of players in DB.
     * @return the list of player.
     * @throws DBException
     * @throws DTOException 
     */
    public  List<PlayerDto> getAllPlayers() throws DBException, DTOException {
        List<PlayerDto> clients = getCollection(new PlayerSel(0));
        return clients;
    }
    
    /**
     * Allows to get all of player
     * @param sel
     * @return
     * @throws DBException
     * @throws DTOException 
     */
    public  List<PlayerDto> getCollection(PlayerSel sel) throws DBException, DTOException {
        List<PlayerDto> al = new ArrayList<PlayerDto>();
        try {
            String query = "Select NAME_PLAYER, WINS, DRAW, LOSS FROM PLAYER";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            
            if (sel.getPseudo() != null && !sel.getPseudo().equals("")) {
                
                where = where + " NAME_PLAYER like ? ";
            }
            if (sel.getNbWin()!= 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " WINS = ? ";
            }

            if (sel.getNbExAequo() != 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " DRAW = ? ";
            }

            if (sel.getNbDefeat()!= 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " LOSS = ? ";
            }

            if (where.length() != 0) {
                where = " where " + where + " order by id";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getId() != 0) {
                    stmt.setInt(i, sel.getId());
                    i++;

                }
                if (sel.getPseudo() != null && !sel.getPseudo().equals("")) {
                    stmt.setString(i, sel.getPseudo());
                    i++;
                }
                if (sel.getNbWin()!= 0) {
                    stmt.setInt(i, sel.getNbWin());
                    i++;
                }

                if (sel.getNbExAequo() != 0) {
                    stmt.setInt(i, sel.getNbExAequo());
                    i++;
                }

                if (sel.getNbDefeat()!= 0) {
                    stmt.setInt(i, sel.getNbDefeat());
                    i++;
                }
            } else {
                query = query + " order by NAME_PLAYER";
                stmt = connexion.prepareStatement(query);

            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new PlayerDto( rs.getString("NAME_PLAYER"), rs.getInt("WINS"),
                        rs.getInt("DRAW"), rs.getInt("LOSS")));

            }
        } catch (java.sql.SQLException eSQL) {
            throw new DBException("Instanciation de Player impossible:\nSQLException: " + eSQL.getMessage());
        }
        return al;
    }
    
    
}


#ifndef GAME_H
#define GAME_H
#define GAME_MIN_PLAYER_COUNT 2
#define GAME_MAX_PLAYER_COUNT 4
#include "../board/board.h"
#include "../player/player.h"
#include "../../observer/subject.h"
#include "game_location.h"
#include <vector>

/**
 * @brief the game model
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class game : public nvs::Subject
{
protected:
    /**
     * @brief the board of the game
     */
    const shared_ptr<board> board_ = make_shared<board>();

    /**
     * @brief the players list
     */
    const shared_ptr<vector<player>> players_ = make_shared<vector<player>>();

    /**
     * @brief the current player that will play very soon
     */
    unsigned current_player_index_ {0};

    /**
     * @brief the previous inserted plate row
     */
    unsigned previous_insert_row_ {board::board_size};

    /**
     * @brief the previous inserted plate column
     */
    unsigned previous_insert_column_ {board::board_size};

    /**
     * @brief the current player step
     */
    unsigned current_player_step_ {0};

    /**
     * @brief the minimum players count to reach until consider the model as ready
     */
    unsigned min_players_count_until_ready_ {0};

    /**
     * @brief tell if a plate can be put
     * @param row the row number where we want to put the plate
     * @param column the column number where we want to put the plate
     * @return true if the plate can be putted at the wanted location
     * @throw game_exception if the location is illegal
     */
    bool can_put_plate(const unsigned row, const unsigned column) const;

    /**
     * @brief tell if a player can move at some location
     * @param row the row number where the player wants to move
     * @param column the column number where the player wants to move
     * @param moving_player the player that wants to move
     * @return true if the move is legal, false otherwise
     */
    bool can_player_move(unsigned row, unsigned column, player * moving_player) const;

    /**
     * @brief move ejected players again into the board, if applicable
     * @param ejected the ejected plate from the board
     * @param inserted the inserted plate into the board
     */
    void handle_players_on_ejected_plate(const shared_ptr<plate> ejected, const shared_ptr<plate> inserted) const;
public:
    /**
     * @brief a game constructor
     */
    game();

    /**
     * @brief put a plate at some location
     * @param row the row number where to put the plate
     * @param column the column number where to put the plate
     * @param plate the plate to put
     */
    void put_plate(const unsigned row, const unsigned column, const shared_ptr<plate> plate);

    /**
     * @brief get the current playing player
     * @return the current playing player
     */
    player * get_current_player() const;

    /**
     * @brief set the next playing player
     */
    void next_player();

    /**
     * @brief get the current player step number
     */
    unsigned get_current_player_step() const;

    /**
     * @brief set the step to the next one
     */
    void next_current_player_step();

    /**
     * @brief get the location of a player
     * @param a_player the player to location research
     * @return the player location
     * @throw game_exception if the player can not be located
     */
    game_location get_player_location(player * const a_player) const;

    /**
     * @brief get plate at some location
     * @param row the row number where to search for plate
     * @param column the column number where to search for plate
     * @return the found plate
     */
    shared_ptr<plate> get_plate_at(const unsigned row, const unsigned column) const;

    /**
     * @brief get the location of a goal
     * @param goal the goal to search
     * @return the goal location
     * @throw game_exception if the goal can not be located
     */
    game_location get_goal_location(plate_goal goal) const;

    /**
     * @brief move a player at some location
     * @param row the row number where to move the player
     * @param column the column number where to move the player
     * @param moving_player the player to move
     */
    void player_move(unsigned row, unsigned column, player * moving_player) const;

    /**
     * @brief get the board of the game
     * @return the board of the game
     */
    shared_ptr<board> get_board() const;

    /**
     * @brief tell if the game is over
     * @return true if the game is over, false otherwise
     */
    bool is_over() const;

    /**
     * @brief tell if the game is ready (i.e. players are registered)
     * @return true if the game is ready, false otherwise
     */
    bool is_ready() const;

    /**
     * @brief add a player to the game
     * @param the player to gadd
     */
    void add_player(player * new_player);

    /**
     * @brief get the players list
     * @return the players list
     */
    shared_ptr<vector<player>> get_players() const;

    /**
     * @brief get the winner of the game, if applicable
     * @return the winner of the game if applicable, nullptr otherwise
     */
    const player * get_winner() const;

    /**
     * @brief set the minimum players count to reach until consider the model as ready
     * @param count the minimum count
     */
    void set_min_players_count_until_ready(unsigned count);

    /**
     * a game model destructor
     */
    ~game();
};

#endif // GAME_H

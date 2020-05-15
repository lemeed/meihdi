#ifndef GAME_CONTROLLER_H
#define GAME_CONTROLLER_H
#include <iostream>
#include <memory>

struct control_response;
class view;
class game;

using namespace std;

/**
 * @brief the game controller
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class game_controller
{
private:
    /**
     * @brief the game view
     */
    const view * const view_;
    /**
     * @brief the game model
     */
    const shared_ptr<game> game_;

    /**
     * @brief ask each player to register itself
     */
    void register_players() const;
public:
    /**
     * @brief the game controller default constructor
     * @param game the model ton control
     * @param view the view used to display the model
     */
    game_controller(shared_ptr<game> game, const view * const view);

    /**
     * @brief start the game
     */
    void start_game() const;

    /**
     * @brief handle_player_action
     * @param res the control response describing the player action
     */
    void handle_player_action(control_response res) const;
};

#endif // GAME_CONTROLLER_H

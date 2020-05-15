#ifndef CONSOLE_VIEW_H
#define CONSOLE_VIEW_H
#include "../view.h"
#include "../../controller/control_response.h"


using namespace std;

/**
 * @brief A view in the console for the game model
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class console_view : public view
{
public:
    /**
     * @brief a console view constructor
     * @param a_game the game model to use as display source
     */
    explicit console_view(const shared_ptr<game> a_game);

    /**
     * @see ../view.h
     */
    virtual pair<unsigned, string> ask_user_data(const unsigned number) const override;

    /**
     * @see show the board
     */
    virtual void show_board() const;

    /**
     * @brief print the outside plate to be played
     */
    void print_outside_plate() const;

    /**
     * @brief print the summary of the game
     */
    void print_summary() const;

    /**
     * @brief print the summary of the players (scores, remaining goals, current location)
     */
    void print_players_summary() const;

    /**
     * @brief print the summary of the current player
     */
    void print_current_player_summary() const;

    /**
     * @brief print game over message
     */
    void print_game_over() const;

    /**
     * @brief ask the current player for choosing what to do for its 1st step action
     * @return the action that the current player wants to do
     */
    control_response ask_current_player_action_1st_step() const;

    /**
     * @brief ask the current player for choosing what to do for its 2nd step action
     * @return the action that the current player wants to do
     */
    control_response ask_current_player_action_2nd_step() const;

    /**
     * @brief ask the current player for action
     * @return the action that the current player wants to do
     */
    control_response ask_current_player_action() const;

    /**
     * @brief ask the current player to make a choice between a list
     * @param min the minimum number of a choice
     * @param max the maximum number of a choice
     * @return the selected choice
     */
    int ask_action(const int min, const int max) const;

    /**
     * @brief ask the current player to give a game location parth (either a row or a column)
     * @return the game location part (either a row or a column)
     */
    unsigned ask_location_part() const;

    /**
     * @see ../view.h
     */
    unsigned ask_players_count() const override;

    /**
     * @brief print a separator
     */
    static void print_separator();

    /**
     * @see ../view.h
     */
    void display_alert(const string & message) const override;

    /**
     * @see ../view.h
     */
    void set_controller(shared_ptr<game_controller> controller) override;

    /**
     * @see ../view.h
     */
    void show_game_over() const override;

};

#endif // CONSOLE_VIEW_H

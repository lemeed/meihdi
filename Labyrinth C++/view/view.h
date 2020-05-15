#ifndef VIEW_H
#define VIEW_H
#include "controller/game_controller.h"
#include "../model/game/game.h"
#include <string>

using namespace std;
/**
 * @brief A game view interface
 */
class view
{
protected:
    /**
     * @brief the model to use as display source
     */
    const shared_ptr<game> game_;

    /**
     * @brief the controller of the game
     */
    shared_ptr<game_controller> controller_;
public:
    /**
     * @brief a view constructor
     * @param a_game the game model to use as display source
     */
    explicit view(const shared_ptr<game> a_game);

    /**
     * @brief ask user from some data about him
     * @param number the number of the user being registered
     * @return the age and the name of the user
     */
    virtual pair<unsigned, string> ask_user_data(const unsigned number) const = 0;

    /**
     * @brief ask about players count that will be playing
     */
    virtual unsigned ask_players_count() const = 0;

    /**
     * @brief display an alert to the user
     * @param the description of the warning
     */
    virtual void display_alert(const string & message) const = 0;

    /**
     * @brief show game over message
     */
    virtual void show_game_over() const = 0;

    /**
     * @brief set a reference to the game controller
     * @param controller the controller reference
     */
    virtual void set_controller(shared_ptr<game_controller> controller) = 0;

    /**
     * @brief a view destructor
     */
    virtual ~view();
};

#endif // VIEW_H

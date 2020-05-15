#ifndef PLAYER_H
#define PLAYER_H
#include "../plate/plate_goal.h"
#include "../plate/plate.h"
#include "../plate/plate_fixed.h"
#include "player_color.h"
#include <string>
#include <vector>
#include <iostream>
#include <memory>

using namespace std;

/**
 * @brief a player playing the labyrinth game
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class player
{
private:
    /**
     * @brief the age of the player
     */
    const unsigned age_;

    /**
     * @brief the name of the player
     */
    const string name_;

    /**
     * @brief the color of the player
     */
    const player_color color_;

    /**
     * @brief the goals card that this player must collect
     */
    const shared_ptr<vector<plate_goal>> goals_;

    /**
     * @brief the initial plate where the user were at the beginning of the game
     */
    const shared_ptr<plate_fixed> initial_plate_;

    /**
     * @brief the plate where the user is sitting now
     */
    shared_ptr<plate> sitting_plate_;

public:
    /**
     * @brief a player constructor
     * @param age the age of the player
     * @param name the name of the player
     * @param color the color of the player
     * @param initial_plate the initial plate of the player
     * @param goals the sequence of goals card that this player must collect
     */
    player(const unsigned age, const string & name, const player_color color, const shared_ptr<plate_fixed> initial_plate, const shared_ptr<vector<plate_goal>> goals);

    /**
     * @brief get the goals cards
     * @return the goals cards
     */
    shared_ptr<vector<plate_goal>> get_goals() const;

    /**
     * @brief get the name of the player
     * @return the name of the player
     */
    string get_name() const;

    /**
     * @brief get the player color
     * @return the player color
     */
    player_color get_color() const;

    /**
     * @brief get the age of the player
     */
    unsigned get_age() const;

    /**
     * @brief set the sitting plate
     * @param the plate where the player will sit
     */
    void set_sitting_plate(const shared_ptr<plate> a_plate);

    /**
     * @brief tell if the player is sitting at its initial plate
     * @return true if the player is sitting at its initial plate, false otherwise
     */
    bool is_at_initial_plate() const;

    /**
     * @brief get the initial plate of the player
     * @return the initial plate of the player
     */
    shared_ptr<plate> get_initial_plate() const;

    /**
     * @brief get the plate where the player is sitting now
     * @return the plate where the player is sitting now
     */
    shared_ptr<plate> get_sitting_plate() const;
};

/**
 * @brief print a player to a stream
 * @param os the stream where to print
 * @param a_player the player to print
 * @return the stream
 */
std::ostream & operator<<(std::ostream & os, const player & a_player);

#endif // PLAYER_H

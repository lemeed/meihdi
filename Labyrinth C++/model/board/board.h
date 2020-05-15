#ifndef BOARD_H
#define BOARD_H

#include "../plate/plate.h"
#include "../plate/plate_goal.h"
#include "../plate/plate_mobile.h"
#include <vector>
#include <memory>
#include <random>

using namespace std;

/**
 * @brief the board holding all the plates (both fixed and mobile ones)
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class board
{
public:
    /**
     * @brief the board size (a board is a square)
     */
    static constexpr const unsigned board_size = 7;
private:
    /**
     * @brief the labyrinth made of various plates
     */
    const shared_ptr<vector<vector<shared_ptr<plate>>>> labyrinth_ = make_shared<vector<vector<shared_ptr<plate>>>>();

    /**
     * @brief the outside plate that will be played very soon
     */
    shared_ptr<plate_mobile> outside_plate_;

    /**
     * @brief random engine
     */
    static std::default_random_engine dre;

    /**
     * @brief initialize all fixed plates on the board
     */
    void init_fixed_plates(vector<plate_goal> * const goals_to_consume);

    /**
     * @brief initialize randomly all plates that can be moved
     */
    void init_mobile_plates_randomly(vector<plate_goal> * const goals_to_consume);

    /**
     * @brief get plate at position
     * @param row the row where the plate has to be search from
     * @param column the column where the plate has to be search from
     * @return the found plate, nullptr otherwise
     */
    shared_ptr<plate> get_plate_at(const unsigned row, const unsigned column);

public:
    /**
     * @brief board default constructor
     */
    board();

    /**
     * @brief get the plate being outside the board
     * @return the plate being outside the board
     */
    shared_ptr<plate_mobile> get_outside_plate() const;

    void set_outside_plate(const shared_ptr<plate_mobile> a_plate);

    /**
     * @brief get the labyrinth
     * @return the labyrinth
     */
    shared_ptr<vector<vector<shared_ptr<plate>>>> get_labyrinth() const;

    /**
     * @brief create an array of randomly ordered goals
     * @return an array of randomly ordered goals
     */
    static vector<plate_goal> get_randomly_ordered_goals_to_distribute();

};

/**
 * @brief print board to stream
 * @param os the stream where to print
 * @param b the board to print
 * @return the stream
 */
std::ostream & operator<<(std::ostream & os, board & b);

#endif // BOARD_H

#include "board.h"
#include "../plate/plate_fixed.h"
#include "../plate/plate_mobile.h"
#include "../../util/cli_color.h"
#include <iostream>
#include <iomanip>
#include <memory>
#include <vector>
#include <algorithm>
#include <random>
#include <chrono>

using namespace std;

std::default_random_engine board::dre(static_cast<unsigned>(std::chrono::system_clock::now().time_since_epoch().count()));

/**
 * @see ./board.h
 */
board::board() {
    /*
     * Init board model
     */
    for (unsigned i = 0; i < board_size; i++) {
        vector<shared_ptr<plate>> columns(board_size, nullptr);
        labyrinth_->push_back(columns);
    }

    auto goals_to_distribute = get_randomly_ordered_goals_to_distribute();

    init_fixed_plates(&goals_to_distribute);
    init_mobile_plates_randomly(&goals_to_distribute);
}

/**
 * @see ./board.h
 */
shared_ptr<vector<vector<shared_ptr<plate>>>> board::get_labyrinth() const {
    return labyrinth_;
}

/**
 * @see ./board.h
 */
void board::init_fixed_plates(vector<plate_goal> * const goals_to_consume) {

    vector<vector<shared_ptr<plate>>> & labyrinth = (*labyrinth_);


    /*
     * Init players start plates
     */
    labyrinth[0][0] = make_shared<plate_fixed>(90, L, none);
    labyrinth[0][board_size - 1] = make_shared<plate_fixed>(180, L, none);
    labyrinth[board_size - 1][0] = make_shared<plate_fixed>(0, L, none);
    labyrinth[board_size - 1][board_size - 1] = make_shared<plate_fixed>(270, L, none);

    // Init "T" fixed plates
    plate_goal random_goal;

    // Row 0
    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[0][2] = std::make_shared<plate_fixed>(0, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[0][4] = std::make_shared<plate_fixed>(0, T, random_goal);

    // Row 2
    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[2][0] = std::make_shared<plate_fixed>(270, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[2][2] = std::make_shared<plate_fixed>(270, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[2][4] = std::make_shared<plate_fixed>(0, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[2][6] = std::make_shared<plate_fixed>(90, T, random_goal);

    // Row 4
    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[4][0] = std::make_shared<plate_fixed>(270, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[4][2] = std::make_shared<plate_fixed>(180, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[4][4] = std::make_shared<plate_fixed>(90, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[4][6] = std::make_shared<plate_fixed>(90, T, random_goal);

    // Row 6
    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[6][2] = std::make_shared<plate_fixed>(180, T, random_goal);

    random_goal = goals_to_consume->back();
    goals_to_consume->pop_back();
    labyrinth[6][4] = std::make_shared<plate_fixed>(180, T, random_goal);

    //std::cout << "remaining goals" << goals_to_consume->size() << std::endl;

}

/**
 * @see ./board.h
 */
void board::init_mobile_plates_randomly(vector<plate_goal> * const goals_to_consume) {

    vector<vector<shared_ptr<plate>>> & labyrinth = (*labyrinth_);

    vector<shared_ptr<plate_mobile>> mobiles;

    plate_goal random_goal;


    for (unsigned i = 0; i < 6; i++) {
        random_goal = goals_to_consume->back();
        goals_to_consume->pop_back();

        auto t_plate_ptr = make_shared<plate_mobile>(0, T, random_goal);
        mobiles.push_back(t_plate_ptr);
    }

    for (unsigned i = 0; i < 12; i++) {
        auto i_plate_ptr = make_shared<plate_mobile>(0, I, none);
        mobiles.push_back(i_plate_ptr);
    }



    for (unsigned i = 0; i < 16; i++) {
        if (i < 6) {
            random_goal = goals_to_consume->back();
            goals_to_consume->pop_back();
            auto l_plate_ptr = make_shared<plate_mobile>(0, L, random_goal);
            mobiles.push_back(l_plate_ptr);
        } else {
            auto l_plate_ptr = make_shared<plate_mobile>(0, L, none);
            mobiles.push_back(l_plate_ptr);
        }
    }

    // Shuffle the mobile plates list
    std::shuffle(std::begin(mobiles), std::end(mobiles), board::dre);

    /*
     * Fill the mobile plates in the board
     */
    for (unsigned i = 0; i < board_size; i++) {
        for (unsigned j = 0; j < board_size; j++) {
            plate * p = (*labyrinth_)[i][j].get();
            if (p == nullptr) {
                // If is not an already defined (fixed) plate
                auto pm = mobiles.back();
                mobiles.pop_back();
                labyrinth[i][j] = pm;
            }
        }
    }

    // Set the outside plate
    outside_plate_ = mobiles.back();
    mobiles.pop_back();

    //std::cout << "Displaying board" << std::endl << *this << std::endl;

    //std::cout << "remaining mob" << mobiles.size() << std::endl;
}

/**
 * @see ./board.h
 */
std::vector<plate_goal> board::get_randomly_ordered_goals_to_distribute() {
    unsigned i = 1; // skip "none"
    std::vector<plate_goal> goals_to_distribute(PLATE_GOAL_COUNT);
    std::generate(goals_to_distribute.begin(), goals_to_distribute.end(), [&i]{
        return static_cast<plate_goal>(i++);
    });

    // Shuffle the mobile plates list
    std::shuffle(std::begin(goals_to_distribute), std::end(goals_to_distribute), board::dre);

    return goals_to_distribute;
}

/**
 * @see ./board.h
 */
std::shared_ptr<plate_mobile> board::get_outside_plate() const {
    return outside_plate_;
}

/**
 * @see ./board.h
 */
void board::set_outside_plate(std::shared_ptr<plate_mobile> a_plate) {
    outside_plate_ = a_plate;
}

/**
 * @see ./board.h
 */
std::ostream & operator<<(std::ostream & os, board & b) {
    auto labyrinth = *b.get_labyrinth();
    os << std::endl;

    // print column header (pushable columns)
    os << std::setw(3) << " ";
    for (unsigned i = 0; i < board::board_size; i++) {
        if ((i + 1) % 2 == 0) {
            os << " ↓ ";
        } else {
            os << "   ";
        }
    }

    os << std::endl;

    // print column header (indexes)
    os << std::setw(3) << " ";
    for (unsigned i = 0; i < board::board_size; i++) {
        os << "|" << i << "|";
    }

    os << std::endl;

    for (unsigned i = 0; i < board::board_size; i++) {
        bool is_odd = ((i + 1) % 2 == 0);


        if (is_odd) {
            os << " →"; // print pushable rows header
        } else {
            os << "  ";
        }

        os << i; // print row header

        for (unsigned j = 0; j < board::board_size; j++) {
            plate * const p_ptr = labyrinth[i][j].get();
            if (p_ptr != nullptr) {
                plate p = *p_ptr;
                os << std::setw(2) << p;
            }
        }
        os << i; // print row footer

        if (is_odd) {
            os << "← "; // print pushable rows footer
        } else {
            os << "  ";
        }

        os << std::endl;
    }

    // print column footer (indexes)
    os << std::setw(3) << " ";
    for (unsigned i = 0; i < board::board_size; i++) {
        os << "|" << i << "|";
    }

    os << std::endl;

    // print column footer (pushable columns)
    os << std::setw(3) << " ";
    for (unsigned i = 0; i < board::board_size; i++) {
        if ((i + 1) % 2 == 0) {
            os << " ↑ ";
        } else {
            os << "   ";
        }
    }

    os << std::endl;

    return os;
}

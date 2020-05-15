#include <test/catch2/catch.hpp>
#include "../model/board/board.h"

TEST_CASE("board is well initialized", "[board]") {

    /**
     * @brief Wrapper for private and protected testing purpose
     */
    class : public board {
    } test_board;


    SECTION("T plate are well initialized") {
        unsigned count_mobile = 0;
        unsigned count_fixed = 0;
        for (unsigned row = 0; row < board::board_size; row++) {
            for (unsigned col = 0; col < board::board_size; col++) {
                auto p = test_board.get_labyrinth()->at(row).at(col);
                if (shared_ptr<plate_mobile> pm = dynamic_pointer_cast<plate_mobile>(p)) {
                    if (pm->get_shape() == T) {
                        count_mobile++;
                    }
                } else {
                    if (p->get_shape() == T) {
                        count_fixed++;
                    }
                }
            }
        }

        REQUIRE(count_fixed == 12);
        REQUIRE(count_mobile >= 5);
    }

    SECTION("I plate are well intialized") {
        unsigned count = 0;
        for (unsigned row = 0; row < board::board_size; row++) {
            for (unsigned col = 0; col < board::board_size; col++) {
                plate p = *(test_board.get_labyrinth()->at(row).at(col));
                // If "I" shape with NO goal
                if (p.get_shape() == I && p.get_goal() == none) {
                    count++;
                }
            }
        }

        REQUIRE(count >= 12 - 1); // -1 to handle the outside plate
    }

    SECTION("L plate are well intialized") {
        unsigned count = 0;
        unsigned with_goal_count = 0;
        for (unsigned row = 0; row < board::board_size; row++) {
            for (unsigned col = 0; col < board::board_size; col++) {
                plate p = *(test_board.get_labyrinth()->at(row).at(col));
                if (p.get_shape() == L) {
                    count++;
                    if (p.has_goal()) {
                        with_goal_count++;
                    }
                }
            }
        }

        REQUIRE(count >= 16 - with_goal_count - 1); // -1 to handle the outside plate
        REQUIRE(with_goal_count >= 6 - 1); // -1 to handle the outside plate
    }

    SECTION("outside plate is well initialized") {
        REQUIRE(test_board.get_outside_plate() != nullptr);
    }
}

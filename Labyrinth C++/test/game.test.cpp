#include <test/catch2/catch.hpp>
#include "../model/game/game.h"
#include "../model/game/game_exception.h"

TEST_CASE("game model operates well", "[game]") {

    /**
     * @brief Wrapper for private and protected testing purpose
     */
    class : public game {
    public:
        int wrap_init = 0;

        bool wrap_can_put_plate(const unsigned row, const unsigned column) {
            return can_put_plate(row, column);
        }

        bool wrap_can_player_move(const unsigned row, const unsigned column, player * p) {
            return can_player_move(row, column, p);
        }

        unsigned wrap_get_current_player_index() const {
            return current_player_index_;
        }
    } test_game;


    // Init game model
    // Initialize the game
    auto & labyrinth = *test_game.get_board()->get_labyrinth();
    const unsigned max = 4;
    const unsigned goals_per_player = PLATE_GOAL_COUNT / max;
    auto goals_to_distribute = board::get_randomly_ordered_goals_to_distribute();

    for (unsigned i = 0; i < max; i++) {
        vector<plate_goal>::const_iterator first = goals_to_distribute.begin() + i * goals_per_player;
        vector<plate_goal>::const_iterator last = goals_to_distribute.begin() + (i + 1) * goals_per_player;
        shared_ptr<vector<plate_goal>> player_goals = make_shared<vector<plate_goal>>(first, last);

        pair<unsigned, string> player_data = pair<unsigned, string>(10 + 1, "Player" + to_string(i));
        player_color color = static_cast<player_color>(i);
        shared_ptr<plate> pos;

        // Locate each player on the board edge (according to their colors)
        switch (color) {
        case green:
            pos = labyrinth[board::board_size - 1][0];
            break;
        case blue:
            pos = labyrinth[board::board_size - 1][board::board_size - 1];
            break;
        case yellow:
            pos = labyrinth[0][board::board_size - 1];
            break;
        case red:
            pos = labyrinth[0][0];
        }

        player new_player = player(player_data.first, player_data.second, color, dynamic_pointer_cast<plate_fixed>(pos), player_goals);
        new_player.set_sitting_plate(pos);
        test_game.add_player(&new_player);
    }

    SECTION("game over") {
        REQUIRE(test_game.is_over() == false);
    }

    SECTION("can put plate sanitizer does its job") {

        // Try to put plate from illegal way
        REQUIRE_THROWS_AS(test_game.wrap_can_put_plate(0, 0), game_exception);
        REQUIRE_THROWS_AS(test_game.wrap_can_put_plate(3, 3), game_exception);
        REQUIRE_THROWS_AS(test_game.wrap_can_put_plate(6, 6), game_exception);
        REQUIRE_THROWS_AS(test_game.wrap_can_put_plate(2, 5), game_exception);
        REQUIRE_THROWS_AS(test_game.wrap_can_put_plate(6, 0), game_exception);

        // Try to put plate from legal way
        REQUIRE_NOTHROW(test_game.wrap_can_put_plate(1, 0));
        REQUIRE_NOTHROW(test_game.wrap_can_put_plate(5, 6));
        REQUIRE_NOTHROW(test_game.wrap_can_put_plate(3, 0));
        REQUIRE_NOTHROW(test_game.wrap_can_put_plate(6, 5));
        REQUIRE_NOTHROW(test_game.wrap_can_put_plate(5, 0));
    }

    SECTION("can move player sanitizer does its job") {
        player * a_player = &test_game.get_players()->at(0);
        const plate sitting_plate = *a_player->get_sitting_plate();

        // Move with illegal path
        unsigned target_row = board::board_size - 1, target_col = 0;

        if (!sitting_plate.has_path_down()) {
            target_row++;
        } else if(!sitting_plate.has_path_up()) {
            target_row--;
        } else if(!sitting_plate.has_path_left()) {
            target_col--;
        } else if(!sitting_plate.has_path_right()) {
            target_col++;
        }

        REQUIRE(test_game.wrap_can_player_move(target_row, target_col, a_player) == false);

        // Move with legal path
        target_row = board::board_size - 1;
        target_col = 0;
        if (sitting_plate.has_path_down()) {
            target_row++;
        } else if(sitting_plate.has_path_up()) {
            target_row--;
        } else if(sitting_plate.has_path_left()) {
            target_col--;
        } else if(sitting_plate.has_path_right()) {
            target_col++;
        }

        REQUIRE(test_game.wrap_can_player_move(target_row, target_col, a_player));


    }

    SECTION("player change works well") {
        unsigned curr_player_index = test_game.wrap_get_current_player_index();
        for (unsigned i = 0; i < max; i++) {
            test_game.next_player();
        }
        REQUIRE(test_game.wrap_get_current_player_index() == curr_player_index);
    }

    SECTION("player location getter works well") {
        const game_location loc_player_1 = {board::board_size - 1, 0};
        const game_location cloc_player_1 = test_game.get_player_location(&test_game.get_players()->at(0));
        REQUIRE(loc_player_1.row == cloc_player_1.row);
        REQUIRE(loc_player_1.column == cloc_player_1.column);

        const game_location loc_player_2 = {board::board_size - 1, board::board_size - 1};
        const game_location cloc_player_2 = test_game.get_player_location(&test_game.get_players()->at(1));
        REQUIRE(loc_player_2.row == cloc_player_2.row);
        REQUIRE(loc_player_2.column == cloc_player_2.column);

        const game_location loc_player_3 = {0, board::board_size - 1};
        const game_location cloc_player_3 = test_game.get_player_location(&test_game.get_players()->at(2));
        REQUIRE(loc_player_3.row == cloc_player_3.row);
        REQUIRE(loc_player_3.column == cloc_player_3.column);

        const game_location loc_player_4 = {0, 0};
        const game_location cloc_player_4 = test_game.get_player_location(&test_game.get_players()->at(3));
        REQUIRE(loc_player_4.row == cloc_player_4.row);
        REQUIRE(loc_player_4.column == cloc_player_4.column);
    }

    SECTION("winner getter works well") {
        const player * future_winner = &test_game.get_players()->at(0);
        future_winner->get_goals()->clear();
        REQUIRE(test_game.get_winner() == future_winner);
    }
}



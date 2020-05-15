#include "game_controller.h"
#include "control.h"
#include "control_response.h"
#include "../model/game/game_exception.h"
#include "../view/view.h"
#include "../view/console/console_view.h"
#include "../view/gui/gui_view.h"
#include "../model/plate/plate_mobile.h"
#include <iostream>

/**
 * @see ./game_controller.h
 */
game_controller::game_controller(shared_ptr<game> game, const view * const view) : view_(view), game_(game) {}

/**
 * @see ./game_controller.h
 */
void game_controller::register_players() const {
    const unsigned max = view_->ask_players_count();
    const unsigned goals_per_player = PLATE_GOAL_COUNT / max;

    game_->set_min_players_count_until_ready(max);
    unsigned count = 0;

    auto goals_to_distribute = board::get_randomly_ordered_goals_to_distribute();

    auto & labyrinth = *game_->get_board()->get_labyrinth();

    while (count < max) {

        vector<plate_goal>::const_iterator first = goals_to_distribute.begin() + count * goals_per_player;
        vector<plate_goal>::const_iterator last = goals_to_distribute.begin() + (count + 1) * goals_per_player;
        shared_ptr<vector<plate_goal>> player_goals = make_shared<vector<plate_goal>>(first, last);

        std::pair<unsigned, std::string> player_data = view_->ask_user_data(count + 1);
        //std::pair<unsigned, std::string> player_data = std::pair<unsigned, std::string>(count, "Player" + to_string(count));

        player_color color = static_cast<player_color>(count);


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


        game_->add_player(&new_player);

        count++;
    }
}

/**
 * @see ./game_controller.h
 */
void game_controller::start_game() const {
    register_players();

    // Custom behavior for console mode
    if(const console_view * cv = dynamic_cast<const console_view *>(view_)) {
        while (!game_->is_over()) {
            unsigned previous_step;
            do {
                previous_step = game_->get_current_player_step();
                cv->print_summary();
                cv->print_current_player_summary();
                handle_player_action(cv->ask_current_player_action());
            } while (game_->get_current_player_step() == previous_step);
        }
    }
}

/**
 * @see ./game_controller.h
 */
void game_controller::handle_player_action(control_response res) const {
    auto pm = game_->get_board()->get_outside_plate();

    if (res.game_control == rotate_left) {
        pm->rotate_clockwise_left();
    } else if (res.game_control == rotate_right) {
        pm->rotate_clockwise_right();
    } else if (res.game_control == push_plate) {
        try {
            game_->put_plate(res.location.first, res.location.second, pm);
            game_->next_current_player_step();
        } catch (const game_exception & e) {
            view_->display_alert(e.what());
        }
    } else if (res.game_control == control::move) {
        try {
            game_->player_move(res.location.first, res.location.second, game_->get_current_player());
            game_->next_current_player_step();
        } catch (const game_exception & e) {
            view_->display_alert(e.what());
        }
    } else if (res.game_control == skip) {
        game_->next_current_player_step();
    }

    if (game_->get_current_player_step() == 2) {
        game_->next_player();
        game_->next_current_player_step();
    }

    if (game_->is_over()) {
        view_->show_game_over();
    }
}

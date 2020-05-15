#include "game.h"
#include "game_exception.h"
#include "game_location.h"
#include "../plate/plate.h"
#include "../plate/plate_mobile.h"
#include "path_node.h"
#include <iostream>
#include <stack>
#include <cstring>

/**
 * @see ./game.h
 */
game::game() {}

/**
 * @see ./game.h
 */
bool game::can_put_plate(const unsigned row, const unsigned column) const {

    // check if the wanted move does not cancel the previous one
    if (
            (
                row == previous_insert_row_ &&
                (
                    (column == 0 && previous_insert_column_ == board::board_size - 1) ||
                    (column == board::board_size - 1 && previous_insert_column_ == 0))
                )
            ||
            (
                column == previous_insert_column_ &&
                (
                    (row == 0 && previous_insert_row_ == board::board_size - 1) ||
                    (row == board::board_size - 1 && previous_insert_row_ == 0))
                )
            )
    {
        throw game_exception("you can not put the plate into a lane that will cancel the previous insertion, please try again by selecting another lane");
    }

    auto entry_plate = get_plate_at(row, column);
    if ((dynamic_pointer_cast<plate_mobile>(entry_plate) && ((column == 0 || column == board::board_size - 1) && (row - 1) % 2 == 0)) || ((row == 0 || row == board::board_size - 1) && (column - 1) % 2 == 0)) {
        return true;
    } else {
        throw game_exception("you can not put the plate into a lane that begins with a fixed plate, please try again by selecting another lane");
    }
}

/**
 * @see ./game.h
 */
void game::handle_players_on_ejected_plate(const shared_ptr<plate> ejected, const shared_ptr<plate> inserted) const {
    for (auto & player : *get_players()) {
        if (player.get_sitting_plate() == ejected) {
            player.set_sitting_plate(inserted);
        }
    }
}

/**
 * @see ./game.h
 */
void game::put_plate(const unsigned row, const unsigned column, const shared_ptr<plate> a_mobile_plate) {
    if (can_put_plate(row, column)) {
        const auto max = board::board_size - 1;
        auto & labyrinth = (*board_->get_labyrinth());

        shared_ptr<plate> ejected = nullptr;

        // Shifting a row
        if (column == 0) { // we are pushing from left to right
            ejected = get_plate_at(row, max);
            for (int i = max - 1; i >= 0; i--) {
                cout << i << endl;
                unsigned index = static_cast<unsigned>(i);
                labyrinth[row][index + 1] = labyrinth[row][index];
            }
        }
        else if (column == max) { // we are pushing from right to left
            ejected = get_plate_at(row, 0);
            for (unsigned i = 0; i < max; i++) {
                labyrinth[row][i] = labyrinth[row][i + 1];
            }
        } // Shifting a column
        else if (row == 0) { // we are pushing from top to bottom
            ejected = get_plate_at(max, column);
            for (int i = max - 1; i >= 0; i--) {
                unsigned index = static_cast<unsigned>(i);
                labyrinth[index + 1][column] = labyrinth[index][column];
            }
        }
        else if (row == max) { // we are pushing from bottom to top
            ejected = get_plate_at(0, column);
            for (unsigned i = 0; i < max; i++) {
                labyrinth[i][column] = labyrinth[i + 1][column];
            }
        }

        labyrinth[row][column] = a_mobile_plate; // insert the outside plate
        board_->set_outside_plate(dynamic_pointer_cast<plate_mobile>(ejected)); // keep the ejected plate
        handle_players_on_ejected_plate(ejected, a_mobile_plate); // move players from the ejected plate (if applicable)

        /* keep the last insert to prevent reverse during the next one */
        previous_insert_row_ = row;
        previous_insert_column_ = column;

        notifyObservers();
    }
}

/**
 * @see ./game.h
 */
player * game::get_current_player() const
{
    return &(players_->at(current_player_index_));
}

/**
 * @see ./game.h
 */
void game::next_player()
{
    current_player_index_++;
    if (current_player_index_ > players_->size() - 1) {
        current_player_index_ = 0;
    }

    notifyObservers();
}

/**
 * @see ./game.h
 */
unsigned game::get_current_player_step() const {
    return current_player_step_;
}

/**
 * @brief set the step to the next one
 */
void game::next_current_player_step() {
    current_player_step_++;
    if (current_player_step_ > 2) {
        current_player_step_ = 0;
    }
    notifyObservers();
}

/**
 * @see ./game.h
 */
game_location game::get_player_location(player * const a_player) const {
    for (unsigned i = 0; i < board::board_size; i++) {
        for (unsigned j = 0; j < board::board_size; j++) {
            plate * current_plate_ptr = get_plate_at(i, j).get();
            if (current_plate_ptr == a_player->get_sitting_plate().get()) {
                return {i, j};
            }
        }
    }
    throw game_exception("player could not be located");
}

/**
 * @see ./game.h
 */
game_location game::get_goal_location(plate_goal goal) const {
    for (unsigned i = 0; i < board::board_size; i++) {
        for (unsigned j = 0; j < board::board_size; j++) {
            plate current_plate = *get_plate_at(i, j);
            if (current_plate.get_goal() == goal) {
                return {i, j};
            }
        }
    }
    throw game_exception("goal could not be located");
}

/**
 * @see ./game.h
 */
shared_ptr<plate> game::get_plate_at(const unsigned row, const unsigned column) const {
    return board_->get_labyrinth()->at(row).at(column);
}

/**
 * @see ./game.h
 */
bool game::can_player_move(unsigned target_row, unsigned target_column, player * moving_player) const
{
    bool not_yet_checked[board::board_size][board::board_size];
    memset(not_yet_checked, true, sizeof(not_yet_checked)); // set all plate to not yet checked state

    auto p_location = get_player_location(moving_player); // fetch the wanted player location

    int init_row = static_cast<int>(p_location.row);
    int init_column = static_cast<int>(p_location.column);

    stack<path_node> s;

    path_node start = {init_row, init_column, 0};

    s.push(start);

    while (!s.empty()) {
        path_node el = s.top();
        int row = el.row;
        int column = el.column;
        int direction = el.direction;

        el.direction++;
        s.pop();
        s.push(el);

        unsigned u_row = static_cast<unsigned>(row);
        unsigned u_column = static_cast<unsigned>(column);

        // Match on the wanted location
        if (u_row == target_row && u_column == target_column) {
            return true;
        }

        int s_board_size = static_cast<int>(board::board_size);

        plate current_plate = *get_plate_at(u_row, u_column);

        if (direction == 0) { // checking the up direction
            if (row - 1 >= 0 && current_plate.has_path_up() && (*get_plate_at(u_row - 1, u_column)).has_path_down() && not_yet_checked[row - 1][column]) {
                path_node temp1 {row - 1, column, 0};
                not_yet_checked[row - 1][column] = false;
                s.push(temp1);
            }
        } else if (direction == 1) { // checking the left direction
            if (column - 1 >= 0 && current_plate.has_path_left() && (*get_plate_at(u_row, u_column - 1)).has_path_right() && not_yet_checked[row][column - 1]) {
                path_node temp1 {row, column - 1, 0};
                not_yet_checked[row][column - 1] = false;
                s.push(temp1);
            }
        } else if (direction == 2) { // checking the down direction
            if (row + 1 < s_board_size && current_plate.has_path_down() && (*get_plate_at(u_row + 1, u_column)).has_path_up() && not_yet_checked[row + 1][column]) {
                path_node temp1 {row + 1, column, 0};
                not_yet_checked[row + 1][column] = false;
                s.push(temp1);
            }
        } else if (direction == 3) { // checking the right direction
            if (column + 1 < s_board_size && current_plate.has_path_right() && (*get_plate_at(u_row, u_column + 1)).has_path_left() && not_yet_checked[row][column + 1]) {
                path_node temp1 {row, column + 1, 0};
                not_yet_checked[row][column + 1] = false;
                s.push(temp1);
            }
        } else { // retract back
            not_yet_checked[el.row][el.column] = true;
            s.pop();
        }
    }

    // If the stack is empty and no match is found return false.
    return false;
}

/**
 * @see ./game.h
 */
void game::player_move(unsigned target_row, unsigned target_column, player * moving_player) const
{
    if (can_player_move(target_row, target_column, moving_player)) {
        auto target_plate_ptr = get_plate_at(target_row, target_column);
        auto & target_plate = *target_plate_ptr;
        if (target_plate.has_goal() && target_plate.get_goal() == moving_player->get_goals()->back()) {
            // the player went to a plate holding its next goal on stack
            target_plate.collect_goal();
            moving_player->get_goals()->pop_back();
        }
        moving_player->set_sitting_plate(target_plate_ptr);
        notifyObservers();
    } else {
        throw game_exception("You can not move there, please try again");
    }
}

/**
 * @see ./game.h
 */
shared_ptr<board> game::get_board() const
{
    return board_;
}

/**
 * @see ./game.h
 */
bool game::is_over() const
{
    return get_winner() != nullptr;
}

/**
 * @see ./game.h
 */
bool game::is_ready() const {
    return players_->size() > 0 && players_->size() == min_players_count_until_ready_;
}

/**
 * @see ./game.h
 */
void game::add_player(player * new_player)
{
    players_->push_back(*new_player);
    // Set the first player according to its age
    if (!players_->empty()) {
        if (new_player->get_age() < players_->at(current_player_index_).get_age()) {
            current_player_index_ = static_cast<unsigned>(players_->size() - 1);
        }
    }
    notifyObservers();
}

/**
 * @see ./game.h
 */
shared_ptr<vector<player>> game::get_players() const
{
    return players_;
}

/**
 * @see ./game.h
 */
const player * game::get_winner() const {
    for (const auto & p : *players_) {
        if (p.is_at_initial_plate() && p.get_goals()->empty()) {
            return &p;
        }
    }

    return nullptr;
}

/**
 * @see ./game.h
 */
void game::set_min_players_count_until_ready(unsigned count) {
    min_players_count_until_ready_ = count;
}

/**
 * @see ./game.h
 */
game::~game() {}

/**
 * @see ./game.h
 */
std::ostream & operator<<(std::ostream & os, const game_location & gl) {
    os << "(" << gl.row << ", " << gl.column << ")";
    return os;
}

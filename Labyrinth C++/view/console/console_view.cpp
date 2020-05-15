#include "console_view.h"
#include "model/game/game_exception.h"
#include "controller/control_response.h"
#include "keyboardAndStringConvert/keyboard.hpp"
#include <string>
#include <iostream>
#include <exception>
#include <limits>
#include "util/os.h"
#include "util/cli_color.h"

using namespace std;
using namespace nvs;

/**
 * @see ./console_view.h
 */
console_view::console_view(const shared_ptr<game> a_game) : view(a_game) {}

/**
 * @see ./console_view.h
 */
pair<unsigned, string> console_view::ask_user_data(unsigned number) const {
    cout << "Registration of player #" << number << endl;
    bool got_name = false;
    string name;

    do
    {
        try
        {
            cout << "What is your name ?" << endl;
            name = lineFromKbd<string>();
            got_name = true;
        }
        catch (const exception & e)
        {
            cout << "Error : " << e.what() << endl;
            got_name = false;
        }
    } while (!got_name);

    int age { -1 };
    do
    {
        try
        {
            cout << "How old are you ? (> 0)" << endl;
            age = lineFromKbd<int>();
        }
        catch (const exception & e)
        {
            cout << "Error : " << e.what() << endl;
        }
    } while (age < 1);


    auto data = pair<int, string>(age, name);
    return data;

}

/**
 * @see ./console_view.h
 */
void console_view::show_board() const {
    cout << *(game_->get_board()) << endl;
}

/**
 * @see ./console_view.h
 */
void console_view::print_summary() const {
    cout << "Game summary" << endl;
    print_separator();
    cout << endl;
    print_players_summary();
    cout << endl;
    show_board();
    cout << endl;
    print_outside_plate();
}

/**
 * @see ./console_view.h
 */
void console_view::print_outside_plate() const
{
    cout << "Current outside plate: " << *(game_->get_board()->get_outside_plate()) << endl;
}

/**
 * @see ./console_view.h
 */
void console_view::print_players_summary() const
{
    constexpr const char tab_sep[] = "\t\t";
    auto & players = *game_->get_players();
    cout << "Players board" << endl;
    print_separator();
    print_separator();
    cout << "Player" << tab_sep << "Color" << tab_sep << "Location" << tab_sep << "Left goals" << endl;
    print_separator();
    for (auto & player : players)
    {
        auto player_location = game_->get_player_location(&player);
        cout << player << tab_sep << player.get_color() << tab_sep << player_location << tab_sep << player.get_goals()->size() << endl;
        print_separator();
    }
}

/**
 * @see ./console_view.h
 */
void console_view::print_current_player_summary() const
{
    auto curr_player_ptr = game_->get_current_player();
    auto & current_player = *curr_player_ptr;
    auto player_location = game_->get_player_location(curr_player_ptr);

    cout << current_player << " - this is your turn!" << endl;
    cout << "Your location is at " << player_location << " and your next goal is";

    try
    {
        if (current_player.get_goals()->empty()) {
            cout << " go back to your initial location";
        }
        else
        {
            auto next_player_goal_location = game_->get_goal_location(current_player.get_goals()->back());
            cout << " is at " << next_player_goal_location;
        }
    }
    catch (const game_exception &)
    {
        cout << " on the outside plate";
    }

    cout << endl;
}

/**
 * @see ./console_view.h
 */
void console_view::show_game_over() const
{
    print_separator();
    print_separator();
    cout << "GAME OVER" << endl;
    print_separator();
    print_players_summary();
    print_separator();
    cout << "The winner is " << *(game_->get_winner()) << endl;
}

/**
 * @see ./console_view.h
 */
control_response console_view::ask_current_player_action_1st_step() const
{
    cout << "[1st step]" << endl;
    print_separator();
    //cout << "0. Rotate the outside plate (clockwise left)" << endl;
    cout << "1. Rotate the outside plate (clockwise right)" << endl;
    cout << "2. Push the current outside plate on the board" << endl;
    print_separator();

    int action = ask_action(1, 2);

    control_response res;
    if (action == 1) {
        res.game_control = rotate_right;
    } else if (action == 2) {
        cout << "Row number ?" << endl;
        unsigned row = ask_location_part();

        cout << "Column number ?" << endl;
        unsigned column = ask_location_part();

        res.game_control = push_plate;
        res.location = pair<unsigned, unsigned>(row, column);
    }

    return res;
}

/**
 * @see ./console_view.h
 */
control_response console_view::ask_current_player_action_2nd_step() const
{
    cout << "[2nd step]" << endl;
    print_separator();
    cout << "1. Move" << endl;
    cout << "2. Skip" << endl;
    print_separator();

    int action = ask_action(1, 2);

    control_response res;
    if (action == 1) {

        cout << "Row number ?" << endl;
        unsigned row = ask_location_part();

        cout << "Column number ?" << endl;
        unsigned column = ask_location_part();

        res.game_control = control::move;
        res.location = pair<unsigned, unsigned>(row, column);

    } else if (action == 2) {
        res.game_control = skip;
    }

    return res;
}

/**
 * @see ./console_view.h
 */
control_response console_view::ask_current_player_action() const {
    if (game_->get_current_player_step() == 0) {
        return ask_current_player_action_1st_step();
    } else {
        return ask_current_player_action_2nd_step();
    }
}

/**
 * @see ./console_view.h
 */
int console_view::ask_action(int min, int max) const
{
    int action { -1 };
    do
    {
        try
        {
            cout << "What do you want to do ?" << endl;
            action = lineFromKbd<int>();
        }
        catch (const exception & e)
        {
            cout << "Error : " << e.what() << endl;
        }
    } while (action < min || action > max);
    return action;
}

/**
 * @see ./console_view.h
 */
unsigned console_view::ask_location_part() const
{
    cout << "Must be in [0, " << std::to_string(board::board_size - 1) << "]" << endl;
    int location_part { -1 };
    do
    {
        try
        {
            location_part = lineFromKbd<int>();
        }
        catch (const exception & e)
        {
            cout << "Error : " << e.what() << endl;
        }
    } while (location_part < 0 || location_part > static_cast<int>(board::board_size - 1));
    return static_cast<unsigned>(location_part);
}

/**
 * @see ./console_view.h
 */
unsigned console_view::ask_players_count() const
{
    cout << "How many players will be playing ?" << endl;
    cout << "Must be in [" << GAME_MIN_PLAYER_COUNT << ", " << GAME_MAX_PLAYER_COUNT << "]" << endl;
    int players_count { -1 };
    do
    {
        try
        {
            players_count = lineFromKbd<int>();
        }
        catch (const exception & e)
        {
            cout << "Error : " << e.what() << endl;
        }
    } while (players_count < GAME_MIN_PLAYER_COUNT || players_count > GAME_MAX_PLAYER_COUNT);
    return static_cast<unsigned>(players_count);
}

/**
 * @see ./console_view.h
 */
void console_view::print_separator()
{
    cout << "--------------------------------" << endl;
}

/**
 * @see ./console_view.h
 */
void console_view::display_alert(const string & message) const
{
    const color::modifier error(color::cli_code::bg_red);
    const color::modifier go(color::cli_code::bg_blue);
    const color::modifier reset(color::cli_code::bg_default);

    if (ENABLE_CLI_COLORS) {
        cout << error;
    }

    cerr << message << endl;

    if (ENABLE_CLI_COLORS) {
        cout << go;
    }

    cout << endl << "press enter to continue";

    if (ENABLE_CLI_COLORS) {
        cout << reset;
    }

    cin.ignore(std::numeric_limits<streamsize>::max(), '\n');
}

/**
 * @see ./console_view.h
 */
void console_view::set_controller(shared_ptr<game_controller> controller) {
    controller_ = controller;
}

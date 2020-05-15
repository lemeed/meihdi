#include "../plate/plate.h"
#include "player.h"

/**
 * @see ./player.h
 */
player::player(
        const unsigned age,
        const string & name,
        const player_color color,
        const shared_ptr<plate_fixed> initial_plate,
        const shared_ptr<vector<plate_goal>> goals)
    : age_(age), name_(name), color_(color), goals_(goals), initial_plate_(initial_plate) {}

/**
 * @see ./player.h
 */
unsigned player::get_age() const
{
    return age_;
}

/**
 * @see ./player.h
 */
std::string player::get_name() const
{
    return name_;
}

/**
 * @see ./player.h
 */
player_color player::get_color() const
{
    return color_;
}

/**
 * @see ./player.h
 */
shared_ptr<vector<plate_goal>> player::get_goals() const
{
    return goals_;
}

/**
 * @see ./player.h
 */
void player::set_sitting_plate(const shared_ptr<plate> a_plate)
{
    sitting_plate_ = a_plate;
}

/**
 * @see ./player.h
 */
shared_ptr<plate> player::get_sitting_plate() const
{
    return sitting_plate_;
}

/**
 * @see ./player.h
 */
bool player::is_at_initial_plate() const
{
    return initial_plate_ == sitting_plate_;
}

/**
 * @see ./player.h
 */
shared_ptr<plate> player::get_initial_plate() const {
    return initial_plate_;
}

/**
 * @see ./player.h
 */
std::ostream & operator<<(std::ostream & os, const player & a_player)
{
    os << a_player.get_name() << " (" << a_player.get_age() << ")";
    return os;
}

#ifndef PLATE_H
#define PLATE_H
#include "plate_shape.h"
#include "plate_goal.h"

/*!
 * @brief a plate of the labyrinth game
 * @author Michaluk David
 * @author Meihdi El Amouri
 *
 * class representing a plate being on the board
 * or the outside plate being played very soon by the next player
 *
 */
class plate
{
protected:
    /*!
     * @brief the plate shape giving it its form and available paths
     */
    const plate_shape shape_;

    /*!
     * @brief the optional goal held by the plate
     */
    plate_goal goal_;

    /*!
     * @brief the rotation of the plate
     */
    int rotation_ = 0;

    /*!
     * @brief normalize degrees to be inside "one tour of the circle"
     */
    static int normalize_rotation(int degrees);

public:
    /*!
     * @brief plate default constructor
     */
    plate();

    /*!
     * @brief custom plate constructor
     *
     * @param rotation the initial rotation of the plate
     * @param shape the shape of the plate
     * @param goal optional goal held by the plate
     */
    plate(const int rotation, const plate_shape shape, const plate_goal goal);

    /**
     * @brief get the rotation of the plate
     * @return the rotation of the plate
     */
    int get_rotation() const;

    /**
     * @brief get the shape of the plate
     * @return the shape of the plate
     */
    plate_shape get_shape() const;

    /**
     * @brief get the goal of the plate
     * @return the goal of the plate
     */
    plate_goal get_goal() const;

    /**
     * @brief tell if the plate has a goal
     * @return true if the plate has a goal, false otherwise
     */
    bool has_goal() const;

    /**
     * @brief collect the goal from the player and give it to a player, if applicable
     */
    void collect_goal();

    /*!
     * @brief tell if the plate has exit on the left side
     * @return true if there is an exit, false otherwise
     */
    bool has_path_left() const;

    /*!
     * @brief tell if the plate has exit on the right side
     * @return true if there is an exit, false otherwise
     */
    bool has_path_right() const;

    /*!
     * @brief tell if the plate has exit on the upper side
     * @return true if there is an exit, false otherwise
     */
    bool has_path_up() const;

    /*!
     * @brief tell if the plate has exit on the lower side
     * @return true if there is an exit, false otherwise
     */
    bool has_path_down() const;

    /**
     * @brief a plate desctructor
     */
    virtual ~plate() = default;
};

/**
 * @brief print a plate shape to a stream
 * @param os the stream where to print the shape
 * @param pg the shape to print
 * @return the stream
 */
std::ostream & operator<<(std::ostream & os, const plate_shape pg);

/**
 * @brief print a plate to a stream
 * @param os the stream where to print the plate
 * @param pg the plate to print
 * @return the stream
 */
std::ostream & operator<<(std::ostream & os, const plate & pg);

#endif // PLATE_H

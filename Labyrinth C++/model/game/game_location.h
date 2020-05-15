#ifndef GAME_LOCATION_H
#define GAME_LOCATION_H
#include <iostream>

/**
 * @brief A location on the board
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
struct game_location {
    unsigned row;
    unsigned column;
};

/**
 * @brief print the location into a stream
 * @param os the stream where to print the location
 * @param gl the location to print
 * @return the stream
 */
std::ostream & operator<<(std::ostream & os, const game_location & gl);

#endif // GAME_LOCATION_H

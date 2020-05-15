#ifndef CONTROL_RESPONSE_H
#define CONTROL_RESPONSE_H
#include "control.h"
#include <iostream>

/**
 * @brief a control response from the game controller, may contains optional location parameter send by the user
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
struct control_response {
    control game_control;
    std::pair<unsigned, unsigned> location;
};

#endif // CONTROL_RESPONSE_H

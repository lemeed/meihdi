#ifndef PLATE_FIXED_H
#define PLATE_FIXED_H

#include "plate.h"

/*!
 * @brief a plate on the board that cannot be moved
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class plate_fixed : public plate
{
public:
    /*!
     * @brief the fixed plate default constructor
     */
    plate_fixed();

    /*!
     * @brief custom fixed plate constructor
     *
     * @param rotation the initial rotation of the plate
     * @param shape the shape of the plate
     * @param goal optional goal held by the plate
     */
    plate_fixed(const int rotation, const plate_shape shape, const plate_goal goal);
};

#endif // PLATE_FIXED_H

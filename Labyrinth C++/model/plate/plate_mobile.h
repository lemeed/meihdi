#ifndef PLATE_MOBILE_H
#define PLATE_MOBILE_H

#include "plate.h"

/*!
 * @brief a plate on the board that can be moved
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class plate_mobile : public plate
{
public:
    /*!
     * @brief The mobile plate default constructor
     */
    plate_mobile();

    /*!
     * @brief Custom mobile plate constructor
     *
     * @param rotation the initial rotation of the plate
     * @param shape the shape of the plate
     * @param goal optional goal held by the plate
     */
    plate_mobile(const int rotation, const plate_shape shape, const plate_goal goal);

    /*!
     * @brief rotate the plate clockwise left
     */
    void rotate_clockwise_left();

    /*!
     * @brief rotate the plate clockwise right
     */
    void rotate_clockwise_right();
};

#endif // PLATE_MOBILE_H

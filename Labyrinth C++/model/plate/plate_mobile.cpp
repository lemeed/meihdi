#include "plate_mobile.h"

/**
 * @see ./plate_mobile.h
 */
plate_mobile::plate_mobile() {}

/**
 * @see ./plate_mobile.h
 */
plate_mobile::plate_mobile(int rotation, plate_shape shape, plate_goal goal) : plate(rotation, shape, goal) {}

/**
 * @see ./plate_mobile.h
 */
void plate_mobile::rotate_clockwise_left() {
    rotation_ = plate_mobile::normalize_rotation(rotation_ - 90);
}

/**
 * @see ./plate_mobile.h
 */
void plate_mobile::rotate_clockwise_right() {
    rotation_ = plate_mobile::normalize_rotation(rotation_ + 90);
}

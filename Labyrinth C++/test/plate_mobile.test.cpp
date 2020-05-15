#include <test/catch2/catch.hpp>
#include "../model/plate/plate_mobile.h"

TEST_CASE("mobile plate can be rotated", "[plate_mobile]") {
    plate_mobile test_plate(0, T, none);

    test_plate.rotate_clockwise_right();
    REQUIRE(test_plate.get_rotation() == 90);

    test_plate.rotate_clockwise_left();
    REQUIRE(test_plate.get_rotation() == 0);

    test_plate.rotate_clockwise_right();
    test_plate.rotate_clockwise_right();
    test_plate.rotate_clockwise_right();
    test_plate.rotate_clockwise_right();
    REQUIRE(test_plate.get_rotation() == 0);
}

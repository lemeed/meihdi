#include <test/catch2/catch.hpp>
#include "../model/plate/plate.h"

TEST_CASE("plate rotation can be normalized", "[plate]") {
    /**
     * @brief Wrapper for private and protected testing purpose
     */
    class : public plate {
        public:
        int wrap_normalize_rotation(int degrees) {
            return normalize_rotation(degrees);
        }
    } test_plate;

    REQUIRE(test_plate.wrap_normalize_rotation(-90) == 0);
    REQUIRE(test_plate.wrap_normalize_rotation(360) == 0);
    REQUIRE(test_plate.wrap_normalize_rotation(180) == 180);
}

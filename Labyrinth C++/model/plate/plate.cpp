#include "plate.h"
#include "../../util/os.h"
#include "../../util/cli_color.h"

/**
 * @see ./plate.h
 */
plate::plate() : plate(0, T, none) {}

/**
 * @see ./plate.h
 */
plate::plate(const int rotation, const plate_shape shape, const plate_goal goal) : shape_(shape), goal_(goal) {
    rotation_ = plate::normalize_rotation(rotation);
}

/**
 * @see ./plate.h
 */
int plate::normalize_rotation(int degrees) {
    if (degrees < 0 || degrees > 270) {
        return 0;
    } else {
        return degrees;
    }
}

/**
 * @see ./plate.h
 */
int plate::get_rotation() const {
    return rotation_;
}

/**
 * @see ./plate.h
 */
plate_shape plate::get_shape() const {
    return shape_;
}

/**
 * @see ./plate.h
 */
plate_goal plate::get_goal() const {
    return goal_;
}

/**
 * @see ./plate.h
 */
bool plate::has_goal() const {
    return goal_ != none;
}

/**
 * @see ./plate.h
 */
void plate::collect_goal() {
    goal_ = none;
}

/**
 * @see ./plate.h
 */
bool plate::has_path_left() const {
    int rotation = rotation_ / 90;

    switch (shape_) {
    case plate_shape::L:
        if (rotation == 2 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    case plate_shape::T:
        if (rotation == 0 || rotation == 1 || rotation == 2) {
            return true;
        } else {
            return false;
        }
    case plate_shape::I:
        if (rotation == 1 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    }
    return false;
}

/**
 * @see ./plate.h
 */
bool plate::has_path_right() const {
    int rotation = rotation_ / 90;

    switch (shape_) {
    case plate_shape::L:
        if (rotation == 0 || rotation == 1) {
            return true;
        } else {
            return false;
        }
    case plate_shape::T:
        if (rotation == 0 || rotation == 2 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    case plate_shape::I:
        if (rotation == 1 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    }
    return  false;
}

/**
 * @see ./plate.h
 */
bool plate::has_path_up() const {
    int rotation = rotation_ / 90;

    switch (shape_) {
    case plate_shape::L:
        if (rotation == 0 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    case plate_shape::T:
        if (rotation == 1 || rotation == 2 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    case plate_shape::I:
        if (rotation == 0 || rotation == 2) {
            return true;
        } else {
            return false;
        }
    }
    return false;
}

/**
 * @see ./plate.h
 */
bool plate::has_path_down() const {
    int rotation = rotation_ / 90;

    switch (shape_) {
    case plate_shape::L:
        if (rotation == 1 || rotation == 2) {
            return true;
        } else {
            return false;
        }
    case plate_shape::T:
        if (rotation == 0 || rotation == 1 || rotation == 3) {
            return true;
        } else {
            return false;
        }
    case plate_shape::I:
        if (rotation == 0 || rotation == 2) {
            return true;
        } else {
            return false;
        }
    }
    return false;
}

/**
 * @see ./plate.h
 */
std::ostream & operator<<(std::ostream & os, const plate_shape pg) {
    switch (pg) {
    case L:
        os << "L";
        break;
    case T:
        os << "T";
        break;
    case I:
        os << "I";
        break;
    }
    return os;
}

/**
 * @see ./plate.h
 */
std::ostream & operator<<(std::ostream & os, const plate & p) {
    color::modifier path_open(color::cli_code::fg_green);
    color::modifier path_closed(color::cli_code::fg_red);
    color::modifier reset(color::cli_code::fg_default);

    color::modifier reset_bg(color::cli_code::bg_default);

    /*if (ENABLE_CLI_COLORS) {
        if (p.get_hold_player() != nullptr) {
            switch (p.get_hold_player()->get_color()) {
            case green:
                os << color::modifier(color::cli_code::bg_green);
                break;
            case blue:
                os << color::modifier(color::cli_code::bg_blue);
                break;
            case yellow:
                os << color::modifier(color::cli_code::bg_default);
                break;
            case red:
                os << color::modifier(color::cli_code::bg_red);
                break;
            }
        }
    }*/

    if (p.has_path_left()) {
        if (ENABLE_CLI_COLORS) {
            os << path_open;
        }

        os << "=";

        if (ENABLE_CLI_COLORS) {
            os << reset;
        }
    } else {
        if (ENABLE_CLI_COLORS) {
            os << path_closed;
        }

        os << "|";

        if (ENABLE_CLI_COLORS) {
            os << reset;
        }
    }

    if (ENABLE_CLI_COLORS) {
        os << path_open;
    }

    if (p.has_path_up() && p.has_path_down()) {
        os << "⇕";
    } else if (p.has_path_up()) {
        os << "⇑";
    } else if(p.has_path_down()) {
        os << "⇓";
    } else {
        os << " ";
    }

    if (ENABLE_CLI_COLORS) {
        os << reset;
    }

    if (p.has_path_right()) {
        if (ENABLE_CLI_COLORS) {
            os << path_open;
        }

        os << "=";

        if (ENABLE_CLI_COLORS) {
            os << reset;
        }
    } else {
        if (ENABLE_CLI_COLORS) {
            os << path_closed;
        }

        os << "|";

        if (ENABLE_CLI_COLORS) {
            os << reset;
        }
    }

    /*if (ENABLE_CLI_COLORS) {
        os << reset_bg;
    }*/

    return os;
}

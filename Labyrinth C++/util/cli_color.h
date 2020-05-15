#ifndef CLI_COLOR_H
#define CLI_COLOR_H

#include <ostream>
/*!
 * @brief utility to display colored CLI output (linux only)
 * @link https://stackoverflow.com/questions/2616906/how-do-i-output-coloured-text-to-a-linux-terminal
 */
namespace color {
    /**
     * @brief available cli colors
     */
    enum cli_code {
        fg_red      = 31,
        fg_green    = 32,
        fg_blue     = 34,
        fg_default  = 39,
        bg_red      = 41,
        bg_green    = 42,
        bg_blue     = 44,
        bg_default  = 49
    };
    class modifier {
        cli_code code;
    public:
        explicit modifier(cli_code pCode) : code(pCode) {}
        friend std::ostream&
        operator<<(std::ostream& os, const modifier& mod) {
            return os << "\033[" << mod.code << "m";
        }
    };
}


#endif // CLI_COLOR_H

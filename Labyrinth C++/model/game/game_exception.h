#ifndef GAME_EXCEPTION_H
#define GAME_EXCEPTION_H

#include <exception>
#include <string>

/**
 * @brief a game exception is thrown where some issue happens with the game
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class game_exception: public std::exception {
private:
    /**
     * @brief the description of the issue
     */
    std::string message_;
public:
    /**
     * @brief a game exception constructor
     * @param the issue description
     */
    explicit game_exception(const std::string & message);
    /**
     * @brief the issue description getter
     * @return the issue description
     */
    virtual const char* what() const noexcept {
        return message_.c_str();
    }
};

#endif // GAME_EXCEPTION_H

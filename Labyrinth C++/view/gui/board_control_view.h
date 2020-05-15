#ifndef BOARD_CONTROL_VIEW_H
#define BOARD_CONTROL_VIEW_H

#include "board_view.h"
#include <QWidget>
#include <QPushButton>

class gui_view;

/**
 * @brief the board control view that displays insertion buttons
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class board_control_view : public QWidget
{
    Q_OBJECT
public:
    /**
     * @brief a board control constructor
     * @param g_view the main gui view container
     * @param b_view the inner board view
     * @param parent the parent widget
     */
    explicit board_control_view(gui_view * g_view, board_view * b_view, QWidget *parent = nullptr);

    /**
     * @brief update the view according to model change
     * @param a_game the game model
     */
    void update_view(const shared_ptr<const game> a_game);
private:
    /**
     * @brief insertion buttons list
     */
    vector<QPushButton *> control_buttons;

    /**
     * @brief enable or disable the insertion buttons
     * @param state the state to set for the buttons
     */
    void set_control_buttons_state(bool state);
};

#endif // BOARD_CONTROL_VIEW_H

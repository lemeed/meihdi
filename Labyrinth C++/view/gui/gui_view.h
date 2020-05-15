#ifndef GUI_VIEW_H
#define GUI_VIEW_H

#include "../view.h"
#include "observer/observer.h"
#include "board_view.h"
#include "board_control_view.h"
#include "outside_plate_view.h"
#include "current_player_view.h"
#include "players_board_view.h"
#include "controller/control.h"

/**
 * @brief the main gui view container
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class gui_view : public view, public nvs::Observer
{

private:
    /**
     * @brief the main window
     */
    QWidget * main;

    /**
     * @brief the labyrinth view
     */
    board_view * b_view;

    /**
     * @brief the insertion buttons view
     */
    board_control_view * bc_view;

    /**
     * @brief the outside plate view and editor
     */
    outside_plate_view * op_view;

    /**
     * @brief the current player instructions view
     */
    current_player_view * cp_view;

    /**
     * @brief the players board view
     */
    players_board_view * pb_view;

public:
    /**
     * @brief a main gui view container
     * @param a_game the game model
     */
    explicit gui_view(const shared_ptr<game> a_game);

    /**
     * @brief create a thin line separator
     * @param shape the orientation of the line
     * @return the separator
     */
    static QFrame * make_separator(QFrame::Shape shape = QFrame::HLine);

    /**
     * @see ../view.h
     */
    pair<unsigned, string> ask_user_data(const unsigned number) const override;

    /**
     * @see ../view.h
     */
    unsigned ask_players_count() const override;

    /**
     * @see ../view.h
     */
    void display_alert(const string & message) const override;

    /**
     * @see ../view.h
     */
    void set_controller(shared_ptr<game_controller> controller) override;

    /**
     * @see ../view.h
     */
    void show_game_over() const override;

    /**
     * @brief handle player move callback
     * @param row the row where the player wants to move
     * @param column the column where the player wants to move
     */
    void handle_move(unsigned row, unsigned column) const;

    /**
     * @brief handle player plate insert action callback
     * @param row the row where the player wants to insert a plate
     * @param column the column where the player wants to insert a plate
     */
    void handle_insert(unsigned row, unsigned column) const;

    /**
     * @brief outside plate left rotation callback
     */
    void handle_rotate_left() const;

    /**
     * @brief outside plate right rotation callback
     */
    void handle_rotate_right() const;

    /**
     * @brief outside plate rotation callback
     * @param ctrl the type of rotation
     */
    void handle_rotate(control ctrl) const;

    /**
     * @brief when the gui view is destroyed, it should unregister itself from observers list
     */
    ~gui_view() override;

    /**
     * @brief update the gui interface according to the game model
     * @param subject the watched subject
     */
    void update(const nvs::Subject * subject) override;
};

#endif // GUI_VIEW_H

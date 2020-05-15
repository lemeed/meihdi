#include "gui_view.h"
#include "board_view.h"
#include "board_control_view.h"
#include "outside_plate_view.h"
#include "controller/control_response.h"
#include "model/game/game.h"
#include <sstream>
#include <iostream>
#include <QApplication>
#include <QWidget>
#include <QHBoxLayout>
#include <QPushButton>
#include <QMessageBox>
#include <QInputDialog>
#include <QDir>
#include <QDesktopWidget>

/**
 * @see ./gui_view.h
 */
QFrame * gui_view::make_separator(QFrame::Shape shape) {
    QFrame * line = new QFrame();
    line->setFrameShape(shape); // Horizontal line
    line->setLineWidth(1);
    return line;
}

/**
 * @see ./gui_view.h
 */
gui_view::gui_view(const shared_ptr<game> a_game) : view(a_game), b_view() {
    game_->registerObserver(this);

    main = new QWidget();

    QHBoxLayout * main_layout = new QHBoxLayout();
    main->setLayout(main_layout);

    /*
     * left panel
     */
    b_view = new board_view(this);
    bc_view = new board_control_view(this, b_view);
    main_layout->addWidget(bc_view);

    /*
     * right panel
     */

    //info_view * i_view = new info_view();
    op_view = new outside_plate_view(this);
    cp_view = new current_player_view();
    pb_view = new players_board_view();

    QVBoxLayout * right_panel = new QVBoxLayout();
    right_panel->addWidget(op_view);
    right_panel->addWidget(gui_view::make_separator());
    right_panel->addWidget(cp_view);
    right_panel->addWidget(gui_view::make_separator());
    right_panel->addWidget(pb_view);

    main_layout->addWidget(gui_view::make_separator(QFrame::VLine));
    main_layout->addLayout(right_panel);

    main->setFixedSize(1000, 600);

    update(game_.get());
}

/**
 * @see ./gui_view.h
 */
gui_view::~gui_view() {
    game_->unregisterObserver(this);
}

/**
 * @see ./gui_view.h
 */
pair<unsigned, string> gui_view::ask_user_data(const unsigned number) const {
    pair<unsigned, string> user_data;

    stringstream common_ss, age_ss, name_ss;
    common_ss << "(Player " << number << ") ";

    age_ss << common_ss.str() << "Your age";
    name_ss << common_ss.str() << "Your name";

    bool ok;

    int age;
    do {

        age = QInputDialog::getInt(main, "How old are you ?", age_ss.str().c_str(), 1, 1, 200, 1, &ok);
    } while (!ok);
    user_data.first = static_cast<unsigned>(age);

    QString name;
    do {
        name = QInputDialog::getText(main, "What is your name ?", name_ss.str().c_str(), QLineEdit::Normal, QDir::home().dirName(), &ok);
    } while (!ok || name.isEmpty());
    user_data.second = name.toStdString();

    return user_data;
}

/**
 * @see ./gui_view.h
 */
unsigned gui_view::ask_players_count() const {
    bool ok;
    int count;
    do {
        count = QInputDialog::getInt(main, "How many players will be playing ?", "Players count", 1, GAME_MIN_PLAYER_COUNT, GAME_MAX_PLAYER_COUNT, 1, &ok);
    } while(!ok);
    return static_cast<unsigned>(count);
}

/**
 * @see ./gui_view.h
 */
void gui_view::display_alert(const string & message) const {
    QMessageBox msgBox;
    msgBox.setText(message.c_str());
    msgBox.exec();
}

/**
 * @see ./gui_view.h
 */
void gui_view::set_controller(shared_ptr<game_controller> controller) {
    controller_ = controller;
}

/**
 * @see ./gui_view.h
 */
void gui_view::show_game_over() const {
    stringstream ss;
    ss << "Game over ! The winner is " << *(game_->get_winner()) << ". Congratulations !";
    display_alert(ss.str());
}

/**
 * @see ./gui_view.h
 */
void gui_view::handle_move(unsigned row, unsigned column) const {
    if (game_->get_current_player_step() == 1) {
        controller_->handle_player_action(control_response { control::move, pair<unsigned, unsigned>(row, column) });
    }
}

/**
 * @see ./gui_view.h
 */
void gui_view::handle_insert(unsigned row, unsigned column) const {
    controller_->handle_player_action(control_response { control::push_plate, pair<unsigned, unsigned>(row, column) });
}

/**
 * @see ./gui_view.h
 */
void gui_view::handle_rotate_left() const {
    handle_rotate(rotate_left);
}

/**
 * @see ./gui_view.h
 */
void gui_view::handle_rotate_right() const {
    handle_rotate(rotate_right);
}

/**
 * @see ./gui_view.h
 */
void gui_view::handle_rotate(control ctrl) const {
    control_response res;
    res.game_control = ctrl;

    controller_->handle_player_action(res);
    op_view->update_view(game_);
}

/**
 * @see ./gui_view.h
 */
void gui_view::update(const nvs::Subject *) {
    main->setVisible(game_->is_ready());

    b_view->update_view(game_);
    bc_view->update_view(game_);
    op_view->update_view(game_);
    cp_view->update_view(game_);
    pb_view->update_view(game_);
}

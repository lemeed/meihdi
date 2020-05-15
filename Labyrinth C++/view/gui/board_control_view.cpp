#include "board_control_view.h"
#include "model/board/board.h"
#include "model/plate/plate_goal.h"
#include "plate_view.h"
#include "gui_view.h"
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QGridLayout>
#include <QPushButton>

/**
 * @see ./board_control_view.h
 */
board_control_view::board_control_view(gui_view * g_view, board_view * b_view, QWidget * parent)
    : QWidget(parent),
      control_buttons(board::board_size / 2 * 4)
{
    QVBoxLayout * main_layout = new QVBoxLayout();

    /**
     * @brief init top action buttons
     */
    QGridLayout * top_actions = new QGridLayout();
    top_actions->setSpacing(0);
    for (unsigned i = 0; i < board::board_size; i++) {
        bool is_odd = ((i + 1) % 2 == 0);

        QPushButton * action = new QPushButton();
        action->setFixedSize(plate_view::view_size);

        if (is_odd) {
            action->setText("↓");
            connect(action, & QPushButton::released, [g_view, i]() {
                g_view->handle_insert(0, i);
            });
        } else {
            action->setVisible(false);
        }

        top_actions->addWidget(action, 0, static_cast<int>(i));
        control_buttons.push_back(action);
    }

    QHBoxLayout * sub_layout = new QHBoxLayout();

    /**
     * @brief init left action buttons
     */
    QGridLayout * left_actions = new QGridLayout();
    for (unsigned i = 0; i < board::board_size; i++) {
        bool is_odd = ((i + 1) % 2 == 0);

        QPushButton * action = new QPushButton();
        action->setFixedSize(plate_view::view_size);

        if (is_odd) {
            action->setText("→");
            connect(action, & QPushButton::released, [g_view, i]() {
                g_view->handle_insert(i, 0);
            });
        } else {
            action->setVisible(false);
        }

        left_actions->addWidget(action, static_cast<int>(i), 0);
        control_buttons.push_back(action);
    }


    /**
     * @brief init right action buttons
     */
    QGridLayout * right_actions = new QGridLayout();
    for (unsigned i = 0; i < board::board_size; i++) {
        bool is_odd = ((i + 1) % 2 == 0);

        QPushButton * action = new QPushButton();
        action->setFixedSize(plate_view::view_size);

        if (is_odd) {
            action->setText("←");
            connect(action, & QPushButton::released, [g_view, i]() {
                g_view->handle_insert(i, board::board_size - 1);
            });
        } else {
            action->setVisible(false);
        }

        right_actions->addWidget(action, static_cast<int>(i), 0);
        control_buttons.push_back(action);
    }

    QGridLayout * bottom_actions = new QGridLayout();
    for (unsigned i = 0; i < board::board_size; i++) {
        bool is_odd = ((i + 1) % 2 == 0);

        QPushButton * action = new QPushButton();
        action->setFixedSize(plate_view::view_size);

        if (is_odd) {
            action->setText("↑");
            connect(action, & QPushButton::released, [g_view, i]() {
                g_view->handle_insert(board::board_size - 1, i);
            });
        } else {
            action->setVisible(false);
        }

        bottom_actions->addWidget(action, 0, static_cast<int>(i));
        control_buttons.push_back(action);
    }

    sub_layout->addLayout(left_actions);
    sub_layout->addWidget(b_view);
    sub_layout->addLayout(right_actions);

    main_layout->addLayout(top_actions);
    main_layout->addLayout(sub_layout);
    main_layout->addLayout(bottom_actions);

    setLayout(main_layout);
}

/**
 * @see ./board_control_view.h
 */
void board_control_view::update_view(const shared_ptr<const game> a_game) {
    set_control_buttons_state(a_game->get_current_player_step() == 0);
}

/**
 * @see ./board_control_view.h
 */
void board_control_view::set_control_buttons_state(bool state) {
    for (auto & control_button : control_buttons) {
        if (control_button != nullptr) {
            control_button->setDisabled(!state);
        }
    }
}



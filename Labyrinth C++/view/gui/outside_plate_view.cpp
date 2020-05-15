#include "outside_plate_view.h"
#include "gui_view.h"
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QPushButton>

/**
 * @see ./outside_plate_view.h
 */
outside_plate_view::outside_plate_view(gui_view * g_view, QWidget *parent) : QWidget(parent) {
    QVBoxLayout * main_layout = new QVBoxLayout();
    QLabel * title = new QLabel("Current outside plate");
    title->setFont(QFont("Helvetica", 14, QFont::Bold));
    title->setAlignment(Qt::AlignCenter);
    p_view = new plate_view();

    main_layout->addWidget(title);
    main_layout->addWidget(p_view);

    QHBoxLayout * controls = new QHBoxLayout();
    rotate_left = new QPushButton("R. left");
    connect(rotate_left, & QPushButton::released, [g_view]() {
        g_view->handle_rotate_left();
    });

    rotate_right = new QPushButton("R. right");
    connect(rotate_right, & QPushButton::released, [g_view]() {
        g_view->handle_rotate_right();
    });

    controls->addWidget(rotate_left);
    controls->addWidget(rotate_right);

    main_layout->addLayout(controls);

    setLayout(main_layout);
}

/**
 * @see ./outside_plate_view.h
 */
void outside_plate_view::update_view(const shared_ptr<game> a_game) {
    auto op = a_game->get_board()->get_outside_plate();
    bool should_highlight = plate_view::should_be_highlighted(a_game, op);
    p_view->display_plate(op, nullptr, should_highlight);

    bool should_disable_left, should_disable_right;

    if (a_game->get_current_player_step() != 0) {
        should_disable_left = should_disable_right = true;
    } else {
        int rotation =  op->get_rotation() / 90;
        should_disable_left = rotation == 0;
        should_disable_right = rotation == 3;
    }

    rotate_left->setDisabled(should_disable_left);
    rotate_right->setDisabled(should_disable_right);
}


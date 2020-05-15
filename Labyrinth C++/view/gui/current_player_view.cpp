#include "current_player_view.h"
#include <sstream>
#include <QVBoxLayout>

/**
 * @see ./current_player_view.h
 */
current_player_view::current_player_view(QWidget * parent) : QWidget(parent) {
    QVBoxLayout * main_layout = new QVBoxLayout();

    player_text = new QLabel();
    player_text->setFont(QFont("Helvetica", 18, QFont::Bold));
    player_text->setAlignment(Qt::AlignCenter);
    main_layout->addWidget(player_text);

    action_text = new QLabel();
    action_text->setFont(QFont("Helvetica", 14, QFont::Normal));
    action_text->setAlignment(Qt::AlignCenter);
    main_layout->addWidget(action_text);

    next_target_text = new QLabel();
    next_target_text->setFont(QFont("Helvetica", 8, QFont::Normal, true));
    next_target_text->setAlignment(Qt::AlignCenter);
    next_target_text->setText("Your next goal is highlighted (in orange) on the board");

    QPalette next_target_text_palette;
    next_target_text_palette.setColor(QPalette::WindowText, QColor(255, 165, 0));
    next_target_text->setPalette(next_target_text_palette);

    main_layout->addWidget(next_target_text);

    setLayout(main_layout);
}

/**
 * @see ./current_player_view.h
 */
void current_player_view::update_view(const shared_ptr<const game> a_game) {
    if (a_game->get_players()->size() > 0) {
        player * current_player = a_game->get_current_player();

        stringstream player_text_ss;
        player_text_ss << current_player->get_name();
        player_text_ss << ", this is your turn!";

        player_text->setText(player_text_ss.str().c_str());

        Qt::GlobalColor player_text_color;

        QPalette player_text_palette;
        switch (current_player->get_color()) {
        case green:
            player_text_color = Qt::green;
            break;
        case blue:
            player_text_color = Qt::blue;
            break;
        case red:
            player_text_color = Qt::red;
            break;
        case yellow:
            player_text_color = Qt::yellow;
            break;
        }

        player_text_palette.setColor(QPalette::WindowText, player_text_color);

        player_text->setAutoFillBackground(true);
        player_text->setPalette(player_text_palette);

        if (a_game->get_current_player_step() == 0) {
            action_text->setText("Please push the plate into the board");
            next_target_text->setVisible(false);
        } else {
            action_text->setText("Please move your pawn");
            next_target_text->setVisible(true);
        }
    }
}

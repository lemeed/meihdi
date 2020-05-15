#include "players_board_view.h"
#include "model/player/players_board.h"
#include <QVBoxLayout>
#include <QLabel>

/**
 * @see ./players_board_view.h
 */
players_board_view::players_board_view(QWidget *parent) : QWidget(parent) {
    QLabel * title = new QLabel();
    title->setFont(QFont("Helvetica", 14, QFont::Bold));
    title->setAlignment(Qt::AlignCenter);
    title->setText("Players board");

    table_board = new QTableView();
    table_board->setEditTriggers(QAbstractItemView::NoEditTriggers);

    QVBoxLayout * main_layout = new QVBoxLayout();

    main_layout->addWidget(title);
    main_layout->addWidget(table_board);

    setLayout(main_layout);
}

/**
 * @see ./players_board_view.h
 */
void players_board_view::update_view(const shared_ptr<const game> a_game) {
    players_board * model = new players_board(a_game->get_players());
    table_board->setModel(model);
}

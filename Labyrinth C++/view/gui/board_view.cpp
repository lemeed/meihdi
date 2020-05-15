#include "board_view.h"
#include "model/game/game_exception.h"
#include "model/board/board.h"
#include "plate_view.h"
#include "gui_view.h"
#include <QGridLayout>

using namespace std;

/**
 * @see ./board_view.h
 */
board_view::board_view(gui_view * g_view, QWidget *parent) : QWidget(parent)
{
    constexpr int board_size = static_cast<int>(board::board_size);
    resize(board_size * plate_view::view_size.width(), board_size * plate_view::view_size.height());

    grid = new QGridLayout();

    grid->setSpacing(0);
    grid->setMargin(0);

    setLayout(grid);

    for (unsigned row = 0; row < board::board_size; row++) {
        for (unsigned column = 0; column < board::board_size; column++) {
            plate_view * p_view = new plate_view();

            connect(p_view, & plate_view::clicked_plate, [g_view, row, column]() {
                g_view->handle_move(row, column);
            });

            grid->addWidget(p_view, static_cast<int>(row), static_cast<int>(column));
        }
    }
}

/**
 * @see ./board_view.h
 */
void board_view::update_view(const shared_ptr<game> a_game) {

    if (!a_game->is_ready()) {
        return;
    }

    auto labyrinth = *a_game->get_board()->get_labyrinth();

    for (unsigned row = 0; row < board::board_size; row++) {
        for (unsigned column = 0; column < board::board_size; column++) {
            auto a_plate = labyrinth[row][column];

            player * sitting_player = nullptr;

            for (auto & a_player : *a_game->get_players()) {
                if (a_player.get_sitting_plate() == a_plate) {
                    sitting_player = &a_player;
                    break;
                }
            }

            QLayoutItem * item = grid->itemAtPosition(static_cast<int>(row), static_cast<int>(column));
            QWidget * widget = item->widget();
            plate_view * p_view = dynamic_cast<plate_view *>(widget);

            bool should_highlight = plate_view::should_be_highlighted(a_game, a_plate, row, column);
            p_view->display_plate(a_plate, sitting_player, should_highlight);
        }
    }
}

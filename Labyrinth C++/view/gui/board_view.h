#ifndef GAME_BOARD_VIEW_H
#define GAME_BOARD_VIEW_H

#include "model/game/game.h"
#include <QWidget>
#include <QGridLayout>

class gui_view;

using namespace std;

/**
 * @brief the board view that displays the labyrinth
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class board_view : public QWidget
{
    Q_OBJECT
public:
    /**
     * @brief a board view constructor
     * @param g_view the main gui view container
     * @param parent the parent widget
     */
    explicit board_view(gui_view * g_view, QWidget * parent = nullptr);

    /**
     * @brief update the view according to model change
     * @param a_game the game model
     */
    void update_view(const shared_ptr<game> a_game);
private:
    /**
     * @brief the main internal layout
     */
    QGridLayout * grid;
signals:
public slots:
};

#endif // GAME_BOARD_VIEW_H

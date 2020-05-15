#ifndef PLAYERS_BOARD_VIEW_H
#define PLAYERS_BOARD_VIEW_H

#include "model/game/game.h"
#include <QWidget>
#include <QTableView>

/**
 * @brief a players board view
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class players_board_view : public QWidget
{
    Q_OBJECT
private:
    /**
     * @brief the table view showing the players list
     */
    QTableView * table_board;
public:
    /**
     * @brief a players board view constructor
     * @param parent
     */
    explicit players_board_view(QWidget * parent = nullptr);

    /**
     * @brief update the view according to model change
     * @param a_game the game model
     */
    void update_view(const shared_ptr<const game> a_game);

signals:
public slots:
};

#endif // PLAYERS_BOARD_VIEW_H

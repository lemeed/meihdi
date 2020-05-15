#ifndef CURRENT_PLAYER_VIEW_H
#define CURRENT_PLAYER_VIEW_H

#include "model/game/game.h"
#include <QWidget>
#include <QLabel>

/**
 * @brief the current player instructions view
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class current_player_view : public QWidget
{
    Q_OBJECT
private:
    /**
     * @brief the player name
     */
    QLabel * player_text;

    /**
     * @brief the instructions that the player has to do
     */
    QLabel * action_text;

    /**
     * @brief information about next goal
     */
    QLabel * next_target_text;
public:
    /**
     * @brief a current player instruction view constructor
     * @param parent the parent widget
     */
    explicit current_player_view(QWidget * parent = nullptr);

    /**
     * @brief update the view according to model change
     * @param a_game the game model
     */
    void update_view(const shared_ptr<const game> a_game);
signals:
public slots:
};

#endif // CURRENT_PLAYER_VIEW_H

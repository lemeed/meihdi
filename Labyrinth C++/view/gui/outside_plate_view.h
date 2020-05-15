#ifndef OUTSIDE_PLATE_VIEW_H
#define OUTSIDE_PLATE_VIEW_H

#include "model/game/game.h"
#include "plate_view.h"
#include <QWidget>
#include <QPushButton>

class gui_view;

/**
 * @brief the main gui view container
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class outside_plate_view : public QWidget
{
    Q_OBJECT
private:
    /**
     * @brief the plate view
     */
    plate_view * p_view;

    /**
     * @brief the rotation to left action button
     */
    QPushButton * rotate_left;

    /**
     * @brief the rotation to right action button
     */
    QPushButton * rotate_right;
public:

    /**
     * @brief an outside plate view constructor
     * @param g_view the main gui view container
     * @param parent the parent widget
     */
    explicit outside_plate_view(gui_view * g_view, QWidget * parent = nullptr);

    /**
     * @brief update the view according to model change
     * @param a_game the game model
     */
    void update_view(const shared_ptr<game> a_game);

signals:

private slots:
};

#endif // OUTSIDE_PLATE_VIEW_H

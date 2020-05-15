#ifndef PLATE_VIEW_H
#define PLATE_VIEW_H

#include "model/plate/plate.h"
#include "model/game/game.h"
#include "model/player/player.h"
#include <memory>
#include <QWidget>
#include <QLabel>

using namespace std;

/**
 * @brief a plate view
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class plate_view : public QWidget
{
    Q_OBJECT
public:
    /**
     * @brief a plate view constructor
     * @param parent the parent widget
     */
    explicit plate_view(QWidget * parent = nullptr);

    /**
     * @brief tell if a plate should be highlighted
     * @param a_game the game model
     * @param a_plate the plate to check
     * @param row the row where the plate is located
     * @param column the column where the plate is located
     * @return true if the plate should be highlighted, false otherwise
     */
    static bool should_be_highlighted(shared_ptr<game> a_game, shared_ptr<plate> a_plate, unsigned row = 0, unsigned column = 0);

    /**
     * @brief update the plate view
     * @param a_plate the new plate to display
     * @param sitting_player an eventual sitting player on that plate
     * @param highlight if the plate should be highlighted
     */
    void display_plate(shared_ptr<plate> a_plate, const player * const sitting_player = nullptr, bool highlight = false);

    /**
     * @brief the plate view size
     */
    static const QSize view_size;
protected:
    /**
      * @brief fired event when mouse click is released
      * @param event the event descriptor
      */
     void mouseReleaseEvent(QMouseEvent * event);
private:
    /**
     * @brief the main plate view widget
     */
    QLabel * view;

    /**
     * @brief get the plate source image resource path
     * @param a_plate the plate of which calculate the path
     * @return the plate source image resource path
     */
    static string get_plate_src_path(shared_ptr<plate> a_plate);

    /**
     * @brief get the player source image resource path
     * @param a_player the player of which calculate the path
     * @return the player source image resource path
     */
    static string get_player_src_path(const player * const a_player);

signals:
    /**
     * @brief clicked on plate signal
     */
    void clicked_plate() const;
public slots:
};

#endif // PLATE_VIEW_H

#include "plate_view.h"
#include "model/plate/plate_fixed.h"
#include "model/game/game_exception.h"
#include <QLabel>
#include <QPainter>
#include <QImage>
#include <QHBoxLayout>
#include <sstream>

const QSize plate_view::view_size(50, 50);

/**
 * @see ./plate_view.h
 */
plate_view::plate_view(QWidget *parent) : QWidget(parent) {
    view = new QLabel();
    view->setMinimumWidth(view_size.width());
    view->setFixedSize(50, 50);

    QHBoxLayout * layout = new QHBoxLayout();

    layout->addWidget(view);
    setLayout(layout);

    setSizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
}

/**
 * @see ./plate_view.h
 */
void plate_view::mouseReleaseEvent(QMouseEvent *) {
    emit clicked_plate();
}

/**
 * @see ./plate_view.h
 */
bool plate_view::should_be_highlighted(shared_ptr<game> a_game, shared_ptr<plate> a_plate, unsigned row, unsigned column) {
    if (!a_game->is_ready()) {
        return false;
    }

    auto curr_player_ptr = a_game->get_current_player();
    auto & current_player = *curr_player_ptr;

    try {
        if (current_player.get_goals()->empty()) {
            return current_player.get_initial_plate() == a_plate;
        } else {
            auto next_player_goal_location = a_game->get_goal_location(current_player.get_goals()->back());
            return next_player_goal_location.row == row && next_player_goal_location.column == column;
        }
    } catch (const game_exception &) {
        return a_game->get_board()->get_outside_plate() == a_plate;
    }
}

/**
 * @see ./plate_view.h
 */
void plate_view::display_plate(shared_ptr<plate> a_plate, const player * const sitting_player, bool highlight) {

    view->setStyleSheet(highlight ? "border: 2px solid orange" : "border: none");

    QImage resultImage(view_size, QImage::Format_ARGB32_Premultiplied);

    QImage sourceImage(get_plate_src_path(a_plate).c_str());
    sourceImage = sourceImage.scaled(view_size, Qt::KeepAspectRatio);

    QPainter::CompositionMode mode = QPainter::CompositionMode::CompositionMode_DestinationOver;

    QPainter painter(&resultImage);
    painter.setCompositionMode(QPainter::CompositionMode_Source);

    painter.fillRect(resultImage.rect(), Qt::transparent);

    if (sitting_player != nullptr) {
        QImage destinationImage(get_player_src_path(sitting_player).c_str());
        destinationImage = destinationImage.scaled(view_size, Qt::KeepAspectRatio);

        painter.setCompositionMode(QPainter::CompositionMode_SourceOver);
        painter.drawImage(0, 0, destinationImage);
    }

    painter.setCompositionMode(mode);
    // distinguish fixed plates

    if (dynamic_pointer_cast<plate_fixed>(a_plate)) {
        painter.setOpacity(0.5);
    }

    painter.drawImage(0, 0, sourceImage);
    painter.setCompositionMode(QPainter::CompositionMode_DestinationOver);
    painter.fillRect(resultImage.rect(), Qt::white);
    painter.end();

    view->setPixmap(QPixmap::fromImage(resultImage));
}

/**
 * @see ./plate_view.h
 */
string plate_view::get_plate_src_path(shared_ptr<plate> a_plate) {
    stringstream ss;

    ss << ":/img/tile/tile_";

    switch (a_plate->get_shape()) {
    case L:
        ss << "l";
        break;
    case T:
        ss << "t";
        break;
    case I:
        ss << "i";
        break;
    }

    ss << "_";
    ss << a_plate->get_rotation();
    ss << ".png";

    return ss.str();
}

/**
 * @see ./plate_view.h
 */
string plate_view::get_player_src_path(const player * const a_player) {
    stringstream ss;

    ss << ":/img/pawn/pawn_";

    switch (a_player->get_color()) {
    case green:
        ss << "green";
        break;
    case blue:
        ss << "blue";
        break;
    case yellow:
        ss << "yellow";
        break;
    case red:
        ss << "red";
        break;
    }

    ss << ".png";

    return ss.str();

}

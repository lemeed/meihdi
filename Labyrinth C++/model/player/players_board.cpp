#include "players_board.h"

players_board::players_board(shared_ptr<vector<player>> some_players, QObject * parent) : QAbstractTableModel(parent), players(some_players)
{
}

QVariant players_board::headerData(int section, Qt::Orientation orientation, int role) const {
    if (role == Qt::DisplayRole && orientation == Qt::Horizontal) {
        if (section == 0) {
            return QString("Color");
        } else if (section == 1) {
            return QString("Age");
        } else if (section == 2) {
            return QString("Name");
        } else if (section == 3) {
            return QString("Remaining goals count");
        }
    }
    return QVariant();
}

QVariant players_board::data(const QModelIndex & index, int role) const {
    if (!index.isValid() || role != Qt::DisplayRole) {
        return QVariant();
    }

    unsigned long player_index = static_cast<unsigned long>(index.row());
    player a_player = players->at(player_index);

    if (index.column() == 0) {
        switch (a_player.get_color()) {
        case green:
            return "green";
        case blue:
            return "blue";
        case yellow:
            return "yellow";
        case red:
            return "red";
        }
    } else if (index.column() == 1) {
        return a_player.get_age();
    } else if (index.column() == 2) {
        return a_player.get_name().c_str();
    } else if (index.column() == 3) {
        return std::to_string(a_player.get_goals()->size()).c_str();
    }

    return QVariant();
}

int players_board::rowCount(const QModelIndex & parent) const {
    Q_UNUSED(parent);
    return static_cast<int>(players->size());
}

int players_board::columnCount(const QModelIndex & parent) const {
    Q_UNUSED(parent);
    return 4;
}

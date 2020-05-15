#ifndef PLAYERS_BOARD_H
#define PLAYERS_BOARD_H

#include "model/game/game.h"
#include <QAbstractTableModel>

/**
 * @brief players board model that is compatible with QTableView
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
class players_board : public QAbstractTableModel
{
    Q_OBJECT

public:
    /**
     * @brief players board model constructor
     * @param some_players
     * @param parent the parent widget
     */
    explicit players_board(shared_ptr<vector<player>> some_players, QObject * parent = nullptr);

    /**
     * @brief get the header data for the model
     * @param section the section number
     * @param orientation the orientation of the table
     * @param role the purpose of the call
     * @return the generated header
     */
    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

    /**
     * @brief get the data from the model
     * @param index the index of the data
     * @param role the purpose of the call
     * @return the fetched data
     */
    QVariant data(const QModelIndex & index, int role = Qt::DisplayRole) const override;

    /**
     * @brief get the number of rows
     * @param parent the parent model index
     * @return the row count
     */
    int rowCount(const QModelIndex & parent = QModelIndex()) const override;

    /**
     * @brief get the number of columns
     * @param parent the parent model index
     * @return the column count
     */
    int columnCount(const QModelIndex & parent = QModelIndex()) const override;

private:
    /**
     * @brief players list reference
     */
    shared_ptr<vector<player>> players;
};

#endif // PLAYERS_BOARD_H

TEMPLATE = app
CONFIG += console
CONFIG += c++14
CONFIG -= app_bundle
CONFIG += qt
QT += widgets

include(deployment.pri)
qtcAddDeployment()

QMAKE_CXXFLAGS += -Wall

RESOURCES += \
    labyrinth_res.qrc

HEADERS += \
    controller/control.h \
    controller/control_response.h \
    controller/game_controller.h \
    keyboardAndStringConvert/keyboard.hpp \
    keyboardAndStringConvert/stringConvert.hpp \
    model/board/board.h \
    model/game/game.h \
    model/game/game_exception.h \
    model/game/game_location.h \
    model/game/path_node.h \
    model/plate/plate.h \
    model/plate/plate_fixed.h \
    model/plate/plate_goal.h \
    model/plate/plate_mobile.h \
    model/plate/plate_shape.h \
    model/player/player.h \
    model/player/player_color.h \
    model/player/players_board.h \
    observer/observer.h \
    observer/subject.h \
    test/catch2/catch.hpp \
    util/cli_color.h \
    util/os.h \
    view/console/console_view.h \
    view/gui/board_control_view.h \
    view/gui/board_view.h \
    view/gui/current_player_view.h \
    view/gui/gui_view.h \
    view/gui/outside_plate_view.h \
    view/gui/plate_view.h \
    view/gui/players_board_view.h \
    view/view.h

SOURCES += \
    controller/game_controller.cpp \
    model/board/board.cpp \
    model/game/game.cpp \
    model/game/game_exception.cpp \
    model/plate/plate.cpp \
    model/plate/plate_fixed.cpp \
    model/plate/plate_mobile.cpp \
    model/player/player.cpp \
    model/player/players_board.cpp \
    observer/subject.cpp \
    test/board.test.cpp \
    test/game.test.cpp \
    test/plate.test.cpp \
    test/plate_mobile.test.cpp \
    view/console/console_view.cpp \
    view/gui/board_control_view.cpp \
    view/gui/board_view.cpp \
    view/gui/current_player_view.cpp \
    view/gui/gui_view.cpp \
    view/gui/outside_plate_view.cpp \
    view/gui/plate_view.cpp \
    view/gui/players_board_view.cpp \
    view/view.cpp \
    main.cpp


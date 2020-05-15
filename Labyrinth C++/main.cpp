#include <iostream>
#include <string.h>
#include "./model/game/game.h"
#include "./view/console/console_view.h"
#include "./view/gui/gui_view.h"
#include "./controller/game_controller.h"
#include <QApplication>
#include <QMessageBox>

#define CATCH_CONFIG_RUNNER
#include <test/catch2/catch.hpp>


/**
 * @brief ask the user in what mode he wants to start the game
 * @return true if the game should start in gui mode, false if the game should start in console mode
 */
bool should_start_gui() {
    QMessageBox::StandardButton game_mode_choice = QMessageBox::question(nullptr, "Game mode", "Please click YES to start the game with graphical interface or NO to start the game in the terminal", QMessageBox::Yes | QMessageBox::No);
    return game_mode_choice == QMessageBox::Yes;
}

/**
 * @brief the program entry point
 * @mainpage The labyrinth game for the DEV4 course at ESI Brussels
 * @return 0 if the execution went well, something else otherwise
 *
 * @author Michaluk David
 * @author Meihdi El Amouri
 */
int main(int argc, char* argv[]) {
    constexpr char test_flag[] = "run_tests";
    const bool run_test = argc == 2 && strcmp(argv[1], test_flag) == 0;
    if (run_test) {
        // Catch does not understand our custom flag, we have to run it without it
        char * args[] = {argv[0]};
        return Catch::Session().run(1, args);
    } else {
        QApplication app(argc, argv);
        // start the game either in gui or console mode
        // the model and the controller are the same in both case
        view * gv;
        shared_ptr<game> model = make_shared<game>();
        shared_ptr<game_controller> gc;
        // only the view change
        if (should_start_gui()) {
            gv = new gui_view(model);
        } else {
            gv = new console_view(model);
        }

        gc = make_shared<game_controller>(model, gv);
        gv->set_controller(gc);
        gc->start_game();

        return app.exec();
    }
}


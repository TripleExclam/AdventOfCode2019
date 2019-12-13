package day13;


import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Main entry point for the CSSE2002/7023 PacMan Game.
 * @given
 */
public class Launcher extends Application {

    /**
     * CSSE2002/7023 Pacman
     *
     * Arguments: [FILENAME]
     * @param args from the command line.
     * @given
     */
    public static void main(final String... args) {

        Application.launch();
    }

    /**
     * Runs the main gui with the parameters passed via the commandline
     * @param theStage to render to.
     * @throws IOException when unable to read from file.
     * @given
     */
    @Override
    public void start(Stage theStage) throws IOException {
        IntCodeGame game = new IntCodeGame(theStage);
    }
}

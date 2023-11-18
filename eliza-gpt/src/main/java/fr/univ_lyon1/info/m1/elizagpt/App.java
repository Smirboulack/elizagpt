package fr.univ_lyon1.info.m1.elizagpt;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.controller.MessageController;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     * View positions and resolutions are configured based on screen size.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth() / 3.5; // Divide screen width by 3
        double height = screenBounds.getHeight() / 3.5; // Divide screen height by 3

        Stage stage1 = new Stage();
        Stage stage2 = new Stage();
        Stage stage3 = new Stage();

        JfxView view1 = new JfxView(stage1, width, height);
        JfxView view2 = new JfxView(stage2, width, height);
        JfxView view3 = new JfxView(stage3, width, height);

        // Set positions of stages
        double middleX = (screenBounds.getWidth() - width) / 2;
        stage1.setX(middleX);
        stage1.setY((screenBounds.getHeight() - height) / 2);

        stage2.setX(middleX - width);
        stage2.setY((screenBounds.getHeight() - height) / 2);

        stage3.setX(middleX + width);
        stage3.setY((screenBounds.getHeight() - height) / 2);
        
        // Views are added to a list and passed to the controller
        List<JfxView> views = new ArrayList<JfxView>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        MessageController controller = new MessageController(views);

    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}

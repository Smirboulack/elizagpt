package fr.univ_lyon1.info.m1.elizagpt;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.elizagpt.controller.MessageController;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.view.JfxView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        JfxView view1 = new JfxView(stage, 600, 600);
        JfxView view2 = new JfxView(new Stage(), 400, 400);
        //JfxView view3 = new JfxView(new Stage(), 200, 200);
        MessageProcessor model = new MessageProcessor();
        List<JfxView> view = new ArrayList<JfxView>();
        view.add(view1);
        view.add(view2);
        //view.add(view3);
        MessageController controller = new MessageController(model, view);
        // Second view (uncomment to activate)
        
    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}

package FrontEnd.Windows;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MyWindow {
    private static Stage window;

    public static void launch(){
        window = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root);
        window.setScene(scene);

        window.show();
    }
}

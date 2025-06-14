package visuals;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WindyLandscape  {

    double windSpeed = 5.0; // Link this to your wind model dynamically

    public static void launch(Stage primaryStage) {
        Pane root = new Pane();

        // Background
        ImageView background = new ImageView(new Image("file:testsake.png"));
        background.setFitWidth(800);
        background.setFitHeight(600);
        

        // Tree
        ImageView tree = new ImageView(new Image("file:testsake2.png"));
        tree.setX(300);
        tree.setY(250);
        tree.setFitHeight(600);
        tree.setPreserveRatio(true);

        // Tree sway animation
        Rotate rotate = new Rotate(0, tree.getFitWidth() / 2, tree.getFitHeight());
        tree.getTransforms().add(rotate);

        Timeline sway = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), -5)),
            new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 5)),
            new KeyFrame(Duration.seconds(2), new KeyValue(rotate.angleProperty(), -5))
        );
        sway.setCycleCount(Animation.INDEFINITE);
        sway.setAutoReverse(true);
        sway.play();

        root.getChildren().addAll(background);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Windy Countryside");
        primaryStage.show();
    }

    
}

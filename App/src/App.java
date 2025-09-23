import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class App extends Application {

    // === Constants ===

    /** The fixed size (width and height) of the application window. */
    private static final int SIZE = 400;

    // === UI Components: Text Elements ===
    /** Sample text displayed in the timer section. */
    private static Text myTimerText = new Text("");

    // === UI Components: Input and Buttons ===

    /** Text field where users can enter to-do items. */
    private static TextField myToDoText = new TextField();
    /** Button to add a new to-do item. */
    private static Button myAddButton = new Button("Add");
    /** Button to remove a selected to-do item. */
    private static Button myRemoveButton = new Button("Remove");
    /** Container for the Add and Remove buttons. */
    private static ButtonBar myAddRemoveBar = new ButtonBar();

    // === Layout Containers ===

    /** Horizontal container holding the text field and button bar. */
    private static HBox myToDoTextButton;
    /** Vertical container for the list of tasks. */
    private static VBox myListContainer;
    /** BorderPane container for the timer display. */
    private static BorderPane myTimerContainer;
    /** SplitPane dividing the task list and timer sections horizontally. */
    private static SplitPane myTimerListContainer;
    /** Main SplitPane dividing the screen vertically between content and input. */
    private static SplitPane myMainContainer;

    private static Text mySecectedText;

    private static ArrayList<Integer> myTimes = new ArrayList<>();

    private static ArrayList<Text>  myItems = new ArrayList<>();

    private static LinkedHashMap<Text, Integer> myItemToTime = new LinkedHashMap<>();





    @Override
    public void start(Stage stage) {

        // Add buttons to the ButtonBar
        myAddRemoveBar.getButtons().addAll(myAddButton, myRemoveButton);

        // Create the input bar
        myToDoTextButton = new HBox(10, myToDoText, myAddRemoveBar); // 10px spacing
        myToDoTextButton.setAlignment(Pos.CENTER_LEFT);
        myToDoTextButton.setPadding(new javafx.geometry.Insets(10));

        // Create top split: list and timer
        myListContainer = new VBox();
        myTimerContainer = new BorderPane(myTimerText);
        myTimerListContainer = new SplitPane(myListContainer, myTimerContainer);
        myTimerListContainer.setDividerPositions(0.5);

        // Wrap input bar in a VBox for bottom half
        VBox bottomContainer = new VBox(myToDoTextButton);
        bottomContainer.setPadding(new javafx.geometry.Insets(10));

        // Main vertical split
        myMainContainer = new SplitPane(myTimerListContainer, bottomContainer);
        myMainContainer.setOrientation(Orientation.VERTICAL);
        myMainContainer.setDividerPositions(0.7); // Give more space to top half

        // Set up scene
        Scene scene = new Scene(myMainContainer, SIZE, SIZE);
        stage.setTitle("Time on task");
        stage.setScene(scene);
        stage.setResizable(false);

        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            if (mySecectedText != null && !mySecectedText.isStrikethrough()) {
                int currentTime = myItemToTime.get(mySecectedText);
                myItemToTime.put(mySecectedText, currentTime + 1);
                myTimerText.setText(String.valueOf(myItemToTime.get(mySecectedText)));
            }

        }));
        timer.setCycleCount(Animation.INDEFINITE); // run forever
        timer.play();


        myAddButton.setOnAction(e -> {

            if (!myToDoText.getText().equalsIgnoreCase("")) {
                myItemToTime.put( new Text(myToDoText.getText()), 0);
                Map.Entry<Text, Integer> lastEntry = null;
                for (Map.Entry<Text, Integer> entry : myItemToTime.entrySet()) {
                    lastEntry = entry;
                }

                if (lastEntry != null) {
                    myListContainer.getChildren().add(lastEntry.getKey());

                    lastEntry.getKey().setOnMouseClicked(event -> {
                        Text sourceNode = (Text) event.getSource();

                        if (mySecectedText != null && mySecectedText != sourceNode) {
                            mySecectedText.setStyle(""); // reset style
                        }

                        if (sourceNode == mySecectedText) {
                            sourceNode.setStrikethrough(!sourceNode.isStrikethrough());
                        } else {
                            mySecectedText = sourceNode;
                            myTimerText.setText(String.valueOf(myItemToTime.get(mySecectedText)));
                        }


                        mySecectedText.setStyle("-fx-fill: blue; -fx-font-weight: bold;");

                    });
                }

                myToDoText.setText("");

            }


        });
        myRemoveButton.setOnAction(e -> {
            if (mySecectedText != null) {
                myListContainer.getChildren().remove(mySecectedText);
                myItemToTime.remove(mySecectedText);
            }



        });
        stage.show();


    }



    public static void main(String[] args) {
        launch();
    }


}

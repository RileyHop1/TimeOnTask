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


public class App extends Application {

    /**This is the main container for all the items to make sure they're aligned */
    private static SplitPane myMainContainer;

    private static SplitPane myTimerListContainer;

    private static VBox myListContainer;

    private static BorderPane myTimerContainer;

    private static HBox myToDoTextButton;

    private static ButtonBar myAddRemoveBar = new ButtonBar();

    private static Button myAddButton = new Button("Add");

    private static Button myRemoveButton = new Button("Remove");

    private static TextField myToDoText = new TextField();

    private static final int SIZE = 400;

    private static Text myTestText1 = new Text("Test text1");

    private static Text myTestText2 = new Text("Test text2");




    @Override
    public void start(Stage stage) {

        // Add buttons to the ButtonBar
        myAddRemoveBar.getButtons().addAll(myAddButton, myRemoveButton);

        // Create the input bar
        myToDoTextButton = new HBox(10, myToDoText, myAddRemoveBar); // 10px spacing
        myToDoTextButton.setAlignment(Pos.CENTER_LEFT);
        myToDoTextButton.setPadding(new javafx.geometry.Insets(10));

        // Create top split: list and timer
        myListContainer = new VBox(myTestText1);
        myTimerContainer = new BorderPane(myTestText2);
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
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }


}

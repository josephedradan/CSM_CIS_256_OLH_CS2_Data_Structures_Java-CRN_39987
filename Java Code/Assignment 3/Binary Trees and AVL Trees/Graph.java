import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Graph extends Application {

    private Pane paneRoot;

    private Parent createPrimaryStage() {
        paneRoot = new Pane();

        paneRoot.setPrefSize(1000, 1000);

        placeObjectsOnPane(paneRoot);

        return paneRoot;
    }

    private void placeObjectsOnPane(Pane paneRoot) {

        Vertex test1 = new Vertex("FUCK",100,100);
        Vertex test2 = new Vertex("YOU",200,200);
        Edge test3 = new Edge(test1,test2);


        // TODO: POSITION OF VETEX PANES REQUIRES ANIMATIONS RATHER SLIDE SHOW BULL SHIT RIP
        // THAT IS WHY YOU SEE THE VERTEX IN SOME WEIRD ASS LOCATIONS
        paneRoot.getChildren().addAll(test1.getStackPane(),test2.getStackPane(),test3.getShape());

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene primaryScene = new Scene(createPrimaryStage());
        primaryStage.setScene(primaryScene);
//        primaryStage.setX(100);
//        primaryStage.setY(100);
        primaryStage.show();
    }
}

abstract class GraphShape{
    protected StackPane stackPane = new StackPane();

    public StackPane getGraphPane(){
        return stackPane;
    }
}

class Vertex extends GraphShape{
    private static double RADIUS = 10;

    private Circle circle;
    protected Text text;

    public Vertex(String text, double x, double y) {
        this.circle = new Circle(RADIUS);
        this.text = new Text(text);

        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setCenterX(x);
        circle.setCenterY(y);

        stackPane.getChildren().addAll(circle, this.text);
    }

    public void changeColor(Color color){
        circle.setFill(color);
    }

    public void setPosition(double x, double y){
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public Circle getShape(){
        return circle;
    }
}

class Edge {
    private Line line;

    public Edge(Vertex parent, Vertex child){
        line = new Line();

        line.setStartX(parent.getShape().getCenterX());
        line.setStartY(parent.getShape().getCenterY());

        line.setEndX(child.getShape().getCenterX());
        line.setEndY(child.getShape().getCenterY());
    }

    public Line getShape(){
        return line;
    }
}

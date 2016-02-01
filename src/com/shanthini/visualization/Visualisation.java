package com.shanthini.visualization;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Visualisation extends Application {

	public static void test() {
		 System.out.println("test");
		 launch();
	
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    private void drawShapes(GraphicsContext gc) {
    	
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.fillOval(10, 60, 30, 30);
       
        
    }
}



package simutils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class plotter extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        //Configuring Xaxis and Yaxis
        int lowx,highx,lowy,highy;
        lowx=lowy=0;
        highy=MEM.currentsize;
        highx=Runner.memory_address.size()+1;
        NumberAxis xaxis = new NumberAxis(lowx,highx,1);
        NumberAxis yaxis = new NumberAxis(lowy,highy,1);
        xaxis.setLabel("Cycles");
        yaxis.setLabel("Memory Address");
        //Configuring ScatterChart
        ScatterChart s = new ScatterChart(xaxis,yaxis);
        s.setTitle("scatter plot with cycle number and memory address");

        //Configuring Series and adding data to the series
        XYChart.Series series = new XYChart.Series();
        for(int i =0;i<Runner.memory_address.size();i++){
            series.getData().add(new XYChart.Data(i,Runner.memory_address.get(i)));
        }
        //Adding series to the ScatterChart
        s.getData().add(series);

        //Configuring group and Scene
        Group root = new Group();
        root.getChildren().add(s);
        Scene scene = new Scene(root,600,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ScatterChart Example");
//        primaryStage.show();
        WritableImage image = scene.snapshot(null);
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH.mm.ss");
        Date date = new Date();
        File file = new File(formatter.format(date)+"Chart.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
//        System.out.println("Image Saved");
        Platform.exit();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
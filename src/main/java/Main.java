import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;

public class Main extends Application {
    static Double shag = 100.0;
    static List<List<String>> tasks;
    public static void charting(String stageTitle, HashMap<String, List<Double>> seriesTitleData){
        Stage stage = new Stage();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        seriesTitleData.forEach((key,value)->{
            XYChart.Series series = new XYChart.Series();
            series.setName(key);
            for(int i = 0; i < value.size(); ++i) {
                series.getData().add(i, new XYChart.Data<>(i, value.get(i)));
            }
            lineChart.getData().add(series);
        });

        stage.setTitle(stageTitle);
        Scene scene = new Scene(lineChart,800,600);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String [] args) {
        String srcFile;
        Integer variantNo;
        if(args.length > 0 ) {
            srcFile = args[0];
            try {
                variantNo = Integer.parseInt(args[1]);
            }catch (Exception e){
                variantNo = 1;
            }
            variantNo -= 1;//arrays start at 0
            tasks =  Driver.parseInputFileMacOS(variantNo, srcFile);
            //System.out.println(tasks);
            launch();
        }else {
            System.out.println("You should supply at least the filepath of variants");
            System.exit(-1);
        }
    }
    @Override
    public void start(Stage primaryStage) {

        //variant 13 13	R(5∙10-8)	R(7∙10-8)	N(0.15,19)	Exp(0.001)	TN(1000, 110)

        for (List<String> task: tasks) {
            List<Double> ft = new ArrayList<>(), pt = new ArrayList<>();
            //System.out.println(task);
            for(Double t = 0.; t <= 3000; t += shag){
                HashMap<String, Double> ftpt = Distributions.getFtPt(task, t);
                ft.add(ftpt.get(Keys.FT.getValue()));
                pt.add(ftpt.get(Keys.PT.getValue()));
                //break;
            }
            //System.out.println(task.get(0));
            //System.out.println();
            HashMap<String, List<Double>> seriesTitleData = new HashMap<>();
            seriesTitleData.put("F(t)", ft);
            seriesTitleData.put("P(t)", pt);
            charting(task.get(0) + " распределение", seriesTitleData);
            //break;
        }
    }
}

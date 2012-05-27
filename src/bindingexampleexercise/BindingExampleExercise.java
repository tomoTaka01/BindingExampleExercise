/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bindingexampleexercise;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author tomo
 */
public class BindingExampleExercise extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Example from JavaMaganize");
        // 1行目の「InvalidationListener」スライダー
        Slider sliderA = SliderBuilder.create().max(100).build();
        final DoubleProperty valueA = new SimpleDoubleProperty(0);
        sliderA.valueProperty().bindBidirectional(valueA);  // <-１ スライダーとpropertyをバインド
        final Label labelA = new Label("0.0");
        valueA.addListener(new InvalidationListener() {     // <-2 1でバインド指定したpropertyにリスナーを登録
            @Override
            public void invalidated(Observable o) {
                labelA.setText(((DoubleProperty)o).getValue().toString()); // <-3 propertyの値が変わったらラベルに値を表示
            }
        });
        
        // 2行目の「ChangeListener」スライダー
        Slider sliderB = SliderBuilder.create().max(100).build();
        final DoubleProperty valueB = new SimpleDoubleProperty(0);
        sliderB.valueProperty().bindBidirectional(valueB); // <-4 スライダーとpropertyをバインド
        final Label labelB = new Label("0.0");
        valueB.addListener(new ChangeListener<Number>(){    // <-5 4でバインド指定したpropertyにリスナーを登録
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                labelB.textProperty().setValue(newVal.toString());  // <-6 propertyの値が変わったらラベルに値を表示
            }
        });
        
        // 3行目の「bind/unbind」スライダー
        Slider sliderC = SliderBuilder.create().max(100).build();
        final DoubleProperty valueC = new SimpleDoubleProperty(0);
        sliderC.valueProperty().bindBidirectional(valueC);  // <-7 スライダーとpropertyをバインド
        final Label labelC = new Label();
        CheckBox checkBoxC = CheckBoxBuilder.create().text("bind").selected(false).build();
        checkBoxC.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal.booleanValue()){ // <-8 checkBoxがチェックされた場合
                    labelC.textProperty().bind(valueA.multiply(valueB).add(valueC).asString()); // <-9 7でバインド指定したpropertyにリスナーを登録
                } else {                    // <-10 checkboxがチェックされていない場合
                    labelC.textProperty().unbind();
                }
            }
            
        });
        // GridPaneでレイアウト指定
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);
        // 各列の幅を指定
        grid.getColumnConstraints().add(new ColumnConstraints(150));
        grid.getColumnConstraints().add(new ColumnConstraints(200));
        grid.getColumnConstraints().add(new ColumnConstraints(70));
        grid.getColumnConstraints().add(new ColumnConstraints(200));
        // GridPaneの1行目に各コントロールを追加
        grid.add(new Label("InvalidationListener"), 0, 1);
        grid.add(sliderA, 1, 1);
        grid.add(labelA, 3, 1);
        // GridPaneの2行目に各コントロールを追加
        grid.add(new Label("ChangeListener"), 0, 2);
        grid.add(sliderB, 1, 2);
        grid.add(labelB, 3, 2);
        // GridPaneの3行目に各コントロールを追加
        grid.add(new Label("bind/unbind"), 0, 3);
        grid.add(sliderC, 1, 3);
        grid.add(labelC, 3, 3);
        grid.add(checkBoxC, 2, 3);
        // 画面表示
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }
}

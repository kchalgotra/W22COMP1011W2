package com.example.w22comp1011w2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CameraTableViewController implements Initializable {

    @FXML
    private TableColumn<Camera, Integer> cameraIDColumn;

    @FXML
    private TableColumn<Camera, String> makeColumn;

    @FXML
    private TableColumn<Camera, String> modelColumn;

    @FXML
    private TableColumn<Camera, Double> priceColumn;

    @FXML
    private TableColumn<Camera, Integer> resolutionColumn;

    @FXML
    private TableColumn<Camera, Boolean> slrColumn;

    @FXML
    private TableView<Camera> tableView;

    @FXML
    private TableColumn<Camera, Integer> unitsSoldColumn;

    @FXML
    private Label highestRevenueLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        ArrayList<Camera> cameras = DBUtility.getCamerasFromDB();
        System.out.println("");
        */
        cameraIDColumn.setCellValueFactory(new PropertyValueFactory<>("cameraID"));
        resolutionColumn.setCellValueFactory(new PropertyValueFactory<>("resolution"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        slrColumn.setCellValueFactory(new PropertyValueFactory<>("slr"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        unitsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("unitsSold"));

        tableView.getItems().addAll(DBUtility.getCamerasFromDB());

        highestRevenueLabel.setText("Highest Revenue = " + getHighestRevenueString());

    }

    private String getHighestRevenueString(){

        if (tableView.getItems().size() == 0){
            return "No Cameras in the table";
        }else{
            Camera highRev = tableView.getItems().get(0);
            for(Camera camera : tableView.getItems()){
                double highestRevenue = highRev.getPrice() * highRev.getUnitsSold();
                double cameraRevenue = camera.getPrice() * camera.getUnitsSold();
                if(cameraRevenue>highestRevenue){
                    highRev = camera;
                }

            }
            double highestRevenue = highRev.getPrice() * highRev.getUnitsSold();
            return (String.format("$%.2f, %s", highestRevenue, highRev));
        }
    }
}

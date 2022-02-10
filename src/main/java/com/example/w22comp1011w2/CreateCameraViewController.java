package com.example.w22comp1011w2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.ResolverStyle;
import java.util.Formatter;
import java.util.ResourceBundle;

public class CreateCameraViewController implements Initializable {

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private TextField modelTextField;

    @FXML
    private Spinner<Integer> resolutionSpinner;

    @FXML
    private CheckBox slrCheckBox;

    @FXML
    private TextField priceTextField;

    @FXML
    private Label resultLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultLabel.setText("");
        brandComboBox.getItems().addAll(Camera.getManufacturers());

        //Configure spinner to accept only realistic camera resolution values
        //We will use spinner value factory
        //The constructor takes the minimum, maximum, default and stepUp value
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5,100,20,5);
        resolutionSpinner.setValueFactory(spinnerValueFactory);
        TextField spinnerTextField = resolutionSpinner.getEditor();
        resolutionSpinner.setEditable(true);

        //We can create an anonymous inner class
//        spinnerTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
//                resultLabel.setText("");
//                try{
//                    Integer.parseInt(newValue);
//                }catch (Exception e){
//                    resultLabel.setText("Only Whole numbers allowed for the resolution");
//                    spinnerTextField.setText(oldValue);
//                }
//                resultLabel.setText(String.format("Old value: %s  New Value: %s", oldValue, newValue));
//            }
//        });
        //Lets use a Lambda Expression
        spinnerTextField.textProperty().addListener((obs,oldValue,newValue)->{
            try{
                Integer.parseInt(newValue);
            }catch (Exception e){
                spinnerTextField.setText(oldValue);
            }
        });

        //Update the price text field that it only takes in a double value
        priceTextField.textProperty().addListener((obs, oldValue, newValue)->{
            try{
                Double.parseDouble(newValue);
            }catch(Exception e){
                priceTextField.setText(oldValue);
            }
        });

    }

    @FXML
    private void createCamera(){

            String make = brandComboBox.getSelectionModel().getSelectedItem();
            String model = modelTextField.getText();
            boolean slr = slrCheckBox.isSelected();

            int resolution = -1;
            double price = -1;
            try{
                resolution = resolutionSpinner.getValue();
                price = Double.parseDouble(priceTextField.getText());
            }catch (Exception e){
                resultLabel.setText("Resolution and Price must be numbers only");
            }

            if(resolution !=-1 && price !=-1) {
                try{
                    Camera newCamera = new Camera(make, model, resolution, slr, price);
                    Formatter formatter = new Formatter(new File("camera.txt"));
                    formatter.format("New Camera: %s\n", newCamera);
                    formatter.close();
                    DBUtility.insertCameraIntoDB(newCamera);
                    resultLabel.setText(newCamera.toString());
                }catch (IllegalArgumentException e){
                    resultLabel.setText(e.getMessage());
                }catch (FileNotFoundException e){
                    resultLabel.setText("Error Writing to File: "+e.getMessage());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
}

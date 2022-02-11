package com.example.w22comp1011w2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DBUtility {
    private static String user = "Kashish200471366";
    private static String password = "b11Oph_NCu";
    private static String connectURL = "jdbc:mysql://172.31.22.43:3306/Kashish200471366";

    /**
     * This method will send the camera object to DB and return the cameraID
     */
    public static int insertCameraIntoDB(Camera camera) throws SQLException {
        int cameraID = -1;
        ResultSet resultSet = null;

        String sql = "insert into cameras (make, model, resolution, slr, price) Values (?,?,?,?,?);";

        //This is called a "Try with resources" block. It will autoclose anything in the ()
        try(
                Connection conn = DriverManager.getConnection(connectURL,user,password);
                PreparedStatement ps = conn.prepareStatement(sql, new String[]{"cameraID"})
                )
        {
            //configure the prepared statement to prevent SQL injection attacks
            ps.setString(1, camera.getMake());
            ps.setString(2, camera.getModel());
            ps.setInt(3, camera.getResolution());
            ps.setBoolean(4, camera.isSlr());
            ps.setDouble(5, camera.getPrice());

            //run the command into the DB
            ps.executeUpdate();

            //get the cameraID
            resultSet = ps.getGeneratedKeys();
            while(resultSet.next())
                cameraID = resultSet.getInt(1);


        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (resultSet !=null)
                resultSet.close();

        }

        return cameraID;

    }


    /**
     * This method will return a list of all cameras and their associated number of sales
     */

    public static ArrayList<Camera> getCamerasFromDB(){

        ArrayList<Camera> cameras = new ArrayList<>();

        //query DB and create Camera objects / add them to the list
        String sql = "SELECT cameras.cameraID, make, model, resolution, price, slr, count(salesID) AS 'Units Sold'   \n" +
                "from cameras \n" +
                "INNER JOIN cameraSales ON cameras.cameraID = cameraSales.cameraID\n" +
                "group by cameras.cameraId; ";

        try(
                Connection conn = DriverManager.getConnection(connectURL,user,password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            while (resultSet.next()){
                int cameraID = resultSet.getInt("cameraID");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                int resolution = resultSet.getInt("resolution");
                double price = resultSet.getDouble("price");
                boolean slr = resultSet.getBoolean("slr");
                int unitsSold = resultSet.getInt("Units Sold");


                Camera newCamera = new Camera(cameraID,make,model,resolution,slr,price,unitsSold );
                cameras.add(newCamera);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cameras;

    }
}


package com.example.w22comp1011w2;

import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Formatter;

public class FakeSalesData {
    /**
     * This method will create fake sales
     *
     * insert into  cameraSales (cameraID, dateSold) values (1, '2020-07-15');
     * valid  cameraId = 1-4;
     * valid date = any date upto today
     */
    public static void createSQL(){
        SecureRandom rng = new SecureRandom();

        try(
                Formatter formatter =new Formatter("cameraSales.sql")
                ) {
            //Create the fake dates and write it to the file
            for(int i = 1; i<500; i++){
                int cameraId = rng.nextInt(1,4);
                LocalDate dateSold = LocalDate.now().minusDays(rng.nextInt(1095));
                formatter.format("INSERT INTO cameraSales (cameraID, dateSold) VALUES (%d,'%s');\n",cameraId,dateSold);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createSQL();
    }
}

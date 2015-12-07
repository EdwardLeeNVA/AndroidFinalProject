package com.brooks.charles.finalproject;


import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Leebo_000 on 12/4/2015.
 */
public class ColorInts implements Serializable {
    private int paintRed, paintGreen, paintBlue, backRed, backGreen, backBlue;
    private String fileName = "colors.txt";
    public ColorInts(){

    }
    public void setPaintColor(int r, int g, int b ){
        paintRed = r;
        paintGreen = g;
        paintBlue = b;
    }

    public void setBackColor( int r, int g, int b ){
        backRed = r;
        backGreen = g;
        backBlue = b;
    }
    public int getPaintRed(){
        return paintRed;
    }
    public int getPaintGreen(){
        return paintGreen;
    }
    public int getPaintBlue(){
        return paintBlue;
    }
    public int getBackRed(){
        return backRed;
    }
    public int getBackGreen(){
        return backGreen;
    }
    public int getBackBlue(){
        return backBlue;
    }
    public void saveData(ContextWrapper context){
        //saves the ints to a file
        try {
            Log.w("MainActivity", "start of save try");
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            Log.w("MainActivity", "Output stream made");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Log.w("MainActivity", "Object output made");
            oos.writeObject(this);
            Log.w("MainActivity", "Back" + this.getBackRed() + " " +
                    this.getBackGreen() + " " + this.getBackBlue() + " ");
            Log.w("MainActivity", "Paint" + this.getPaintRed() + " " +
                    this.getPaintGreen() + " " + this.getPaintBlue() );
            Log.w("MainActivity", "object saved");
            oos.close();
        }
        catch(FileNotFoundException fnfe){
            Log.w("MainActivity", "The file is not found");
        }
        catch (IOException ioe){Log.w("MainActivity", ioe.getMessage());}
    }
    public void readData(ContextWrapper context){
        //reads the data from a file for the onCreate method
        Log.w("MainActivity", "Read data activated");
        try {
            Log.w("MainActivity", "Start of Read Try");
            FileInputStream fis = context.openFileInput(fileName);
            Log.w("MainActivity","Object input Stream started");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.w("MainActivity", "Temp is assigned next");
            ColorInts temp;
            temp = (ColorInts) ois.readObject();
            Log.w("MainActivity","Temp Assigned");
            setBackColor(temp.backRed, temp.getBackGreen(), temp.getBackBlue());
            setPaintColor(temp.paintRed, temp.getPaintGreen(), temp.getBackBlue());
            ois.close();
            fis.close();
        }
        catch(FileNotFoundException fnfe){Log.w("MainActivity", "FILE NOT FOUND");
            setBackColor(255, 255, 255);
            setPaintColor(0,0,0);}
        catch (ClassNotFoundException cnfe){Log.w("MainActivity", "ClassNotFound");
            setBackColor(0,0,0);
            setPaintColor(0,0,0);}
        catch(IOException ioe){ioe.printStackTrace();}

    }

}


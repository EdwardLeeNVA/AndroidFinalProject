package com.brooks.charles.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsPage extends AppCompatActivity {
    ColorInts ci = new ColorInts();
    Color tempColor = new Color();
    private SeekBar[] seekBarsArray;
    private int[] currentColors;
    private int rgbMax = 255;
    TextView backPreview, paintPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        seekBarsArray = new SeekBar[6];
        currentColors = new int[6];
        Log.w("MainActivity", "Read should be occured");
        ci.readData(this);
        Log.w("MainActivity", "" + ci.getPaintRed());
        Log.w("MainActivity", "" + ci.getPaintGreen());
        Log.w("MainActivity", "" + ci.getPaintBlue());
        //BrushSliders
        seekBarsArray[0] = (SeekBar) findViewById(R.id.redSliderId);
        seekBarsArray[1] = (SeekBar) findViewById(R.id.greenSliderId);
        seekBarsArray[2] = (SeekBar) findViewById(R.id.blueSliderId);
        //Background sliders
        seekBarsArray[3] = (SeekBar) findViewById(R.id.redBackgroundSliderId);//bring in the sliders and set their max to 256
        seekBarsArray[4] = (SeekBar) findViewById(R.id.greenBackgroundSliderId);//bring in the sliders and set their max to 256
        seekBarsArray[5] = (SeekBar) findViewById(R.id.blueBackgroundSliderId);//bring in the sliders and set their max to 256
        //set max values to 256
        for (int i = 0; i < seekBarsArray.length; i++) {
            seekBarsArray[i].setMax(rgbMax);
        }

        //setting saved colors
        //paint color
        seekBarsArray[0].setProgress(ci.getPaintRed());
        seekBarsArray[1].setProgress(ci.getPaintGreen());
        seekBarsArray[2].setProgress(ci.getPaintBlue());
        //background
        seekBarsArray[3].setProgress(ci.getBackRed());
        seekBarsArray[4].setProgress(ci.getBackGreen());
        seekBarsArray[5].setProgress(ci.getBackBlue());

        SeekBarListener sbh = new SeekBarListener();
        //set listeners onto the 6 sliders
        for (int i = 0; i < seekBarsArray.length; i++) {
            seekBarsArray[i].setOnSeekBarChangeListener(sbh);
        }
        // find the text views so that their color can be set
        backPreview = (TextView) findViewById(R.id.previewBackgroundId);
        paintPreview = (TextView) findViewById(R.id.previewId);
    }

    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            for (int i = 0; i < seekBarsArray.length; i++) {
                currentColors[i] = seekBarsArray[i].getProgress();
                Log.d("MainActivity", i + " progress is: " + seekBarsArray[i].getProgress());
            }
            // Log the progress
            //set textView's color
            ci.setBackColor(currentColors[3], currentColors[4], currentColors[5]);
            ci.setPaintColor(currentColors[0], currentColors[1], currentColors[2]);
            backPreview.setBackgroundColor(tempColor.rgb(ci.getBackRed(), ci.getBackGreen(), ci.getBackBlue()));
            paintPreview.setBackgroundColor(tempColor.rgb(ci.getPaintRed(), ci.getPaintGreen(), ci.getPaintBlue()));
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    }

    /**
     * saves the settings for the bitmap
     */
    public void saveBtmp(View v) {
        //Chuck's set methods goes here
        MainActivity.wb.setBrushColor(ci.getPaintRed(),ci.getPaintGreen(),ci.getPaintBlue());
        MainActivity.wb.setBackGroundColor(ci.getBackRed(),ci.getPaintGreen(),ci.getPaintBlue());
    }

    public void clearWhiteBoard() {
        //Chucks methods go here
    }

    public void closeScreen(View v) {
        Log.w("MainActivity", "Button Pressed");
        ci.saveData(this);

        finish();
    }
}
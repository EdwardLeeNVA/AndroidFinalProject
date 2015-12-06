package com.brooks.charles.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsPage extends AppCompatActivity {
    private SeekBar sbpr, sbpg, sbpb, sbbr, sbbg, sbbb;
    private SeekBar[] seekBarsArray;
    private int[] currentColors;
    private int rgbMax = 256;
    TextView backPreview, paintPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        Log.w("Menu", "SETTINGS CLASS CREATED");
        seekBarsArray = new SeekBar[6];
        currentColors = new int[6];
        //BrushSliders
        seekBarsArray[0] = (SeekBar) findViewById(R.id.redSliderId);
        seekBarsArray[1] = (SeekBar) findViewById(R.id.greenSliderId);
        seekBarsArray[2] = (SeekBar) findViewById(R.id.blueSliderId);
        //Background sliders
        seekBarsArray[3] = (SeekBar) findViewById(R.id.redBackgroundSliderId);//bring in the sliders and set their max to 256
        seekBarsArray[4] = (SeekBar) findViewById(R.id.greenBackgroundSliderId);//bring in the sliders and set their max to 256
        seekBarsArray[5] = (SeekBar) findViewById(R.id.blueBackgroundSliderId);//bring in the sliders and set their max to 256
        //set max values to 256
        for(int i = 0; i < seekBarsArray.length;i++){
            seekBarsArray[i].setMax(rgbMax);
        }
        SeekBarListner sbh = new SeekBarListner();
        //set listeners onto the 6 sliders
        for(int i = 0; i < seekBarsArray.length;i++){
            seekBarsArray[i].setOnSeekBarChangeListener(sbh);
        }
        // find the text views so that their color can be set
        backPreview = (TextView) findViewById(R.id.backgroundId);
        paintPreview = (TextView) findViewById(R.id.previewId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class SeekBarListner implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            for(int i = 0; i< seekBarsArray.length; i++){
                currentColors[i] = seekBarsArray[i].getProgress();
                Log.d("MainActivity", i + "progress is: " + seekBarsArray[i].getProgress());
            }
            // Log the progress
            //set textView's color
            //Color tempColor = new Color(currentColors[3], currentColors[4], currentColors[5]);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
    private void saveData(){
        //saves the ints to a file
    }
    private void readData(){
        //reads the data from a file for the onCreate method
    }


}

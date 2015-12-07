package com.brooks.charles.finalproject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    //the board being drawn on
    static WhiteBoard wb;

    public static int STATUS_BAR_HEIGHT = 24;
    public static int ACTION_BAR_HEIGHT = 56;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.set);
      //  setSupportActionBar(toolbar);

        //get the height of the actionbar
        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        float pixelDensity = metrics.density;
        int actionBarHeight = (int) (pixelDensity * ACTION_BAR_HEIGHT);
        TypedValue tv = new TypedValue();
        if(getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, metrics);

        //get the height of the status bar
        int statusBarHeight = (int) (pixelDensity * STATUS_BAR_HEIGHT);
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if( resourceId !=0 ){
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }

        //create the whiteboard the size of the window
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        wb = new WhiteBoard(this, size.x, size.y - (statusBarHeight + actionBarHeight) * 2);

        //set the listener for the touch on the whiteboard
        wb.setOnTouchListener(this);

        //set the content view of this view
        setContentView(wb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:

                Intent settingsIntent = new Intent( this, SettingsPage.class );

                startActivity(settingsIntent);

                return true;
            case R.id.saveDrawing:
                try{
                    wb.saveImageToPhone(this);
                    Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_LONG).show();
                    return true;
                }catch (IOException e){

                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * capture touches
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //check to see if the person is moving their finger on the whiteboard
        float touchX = event.getX();
        float touchY = event.getY();

        wb.pathOperations(touchX, touchY, event);
        return true;
    }

}

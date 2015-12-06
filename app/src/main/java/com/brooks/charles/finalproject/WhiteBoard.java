package com.brooks.charles.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class WhiteBoard extends View {

    //private paint objects
    private Paint drawnPaint;
    private Path path;
    private Bitmap bm;


    public WhiteBoard(Context context, int width, int height) {
        super(context);



        //set default color to white
        setBackgroundColor(Color.WHITE);
        drawnPaint = new Paint();
        drawnPaint.setAntiAlias(true);
        drawnPaint.setColor(Color.BLACK);
        drawnPaint.setStrokeWidth(5.0f);
        drawnPaint.setStyle(Paint.Style.STROKE);
        drawnPaint.setStrokeJoin(Paint.Join.ROUND);
        drawnPaint.setStrokeCap(Paint.Cap.ROUND);


        //the window we are painting on


        path = new Path();

        //create a bitmap the size of the view
        bm = bm.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.w("WhiteBoard", "IN ON DRAW");
        canvas.drawBitmap(bm, 0, 0, drawnPaint);
        canvas.drawPath(path, drawnPaint);
        //canvas.save();
    }

    /**
     * moves the path when the user touches on the whiteboard
     *
     * @param x
     * @param y
     */
    public void pathOperations(float x, float y, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
        }

        invalidate();
    }

    /**
     * Save the image created to a phone
     *
     * @param activity
     */
    public File saveImageToPhone( Activity activity) throws IOException {
        // get state of external storage
        String storageState = Environment.getExternalStorageState( );


        setDrawingCacheEnabled(true);
        buildDrawingCache();
        //bitmap of the full screen
        Bitmap bm1 = getDrawingCache();

        File fileToWrite = null;
        if( storageState.equals( Environment.MEDIA_MOUNTED ) ) {
            // get external storage directory
            File directory = activity.getExternalFilesDir( Environment.DIRECTORY_PICTURES );

            // generate a unique file name
            Date dateToday = new Date( );
            long ms = SystemClock.elapsedRealtime( );
            String filename = "/" + dateToday + "_" + ms + ".png";
            Log.w("Directory",directory + filename );

            // create a file to write to
            fileToWrite = new File( directory + filename );

            //check to see if there is space
            long  freeSpace = directory.getFreeSpace( ); // in bytes
            int bytesNeeded = bm1.getByteCount(); // in bytes
            if( bytesNeeded * 1.5 < freeSpace ) {
                // there is space for the bitmap
                try {
                    FileOutputStream out = new FileOutputStream( fileToWrite );
                    // write to file
                    boolean result = bm1.compress( Bitmap.CompressFormat.PNG , 100,  out );
                    out.close( );
                    setDrawingCacheEnabled(false);
                    if( result ) {
                        return fileToWrite;
                    }else
                        throw new IOException( "Problem compressing the Bitmap"
                                + " to the output stream" );
                } catch( Exception e ) {
                    throw new IOException( "Problem opening the file for writing" );
                }
            }
            else
                throw new IOException( "Not enough space in external storage"
                        + " to write Bitmap" );
        }
        else
            throw new IOException( "No external storage found" );

    }

    /**
     * set the color of the brush
     */
    public void setBrushColor(int color){
        drawnPaint.setColor(color);
    }
}

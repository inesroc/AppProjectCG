package com.pim.appprojectcg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// https://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog


public class DrawActivity extends AppCompatActivity {

    DrawingView drawView;
    Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = findViewById(R.id.simpleDrawingView1);

        drawView.setDrawingCacheEnabled(true);
        b = drawView.getDrawingCache();

        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Colors");

        // add a list
        String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        builder.setItems(animals,new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog,int which){
                switch (which) {
                    case 0: // horse
                    case 1: // cow
                    case 2: // camel
                    case 3: // sheep
                    case 4: // goat
                }
            }
        });


        Button btnChangeColor = findViewById(R.id.btnChangeColor);
        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }});





        // https://stackoverflow.com/questions/18676311/android-app-how-to-save-a-bitmap-drawing-on-canvas-as-image-check-code/18676403
        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File folder = new File(Environment.getExternalStorageDirectory().toString());
                boolean success = false;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }

                System.out.println(success + "folder");

                File file = new File(Environment.getExternalStorageDirectory().toString() + "/sample.png");

                if (!file.exists()) {
                    try {
                        success = file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(success + "file");


                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);

                    System.out.println(ostream);
                    View targetView = drawView;

                    // myDrawView.setDrawingCacheEnabled(true);
                    //   Bitmap save = Bitmap.createBitmap(myDrawView.getDrawingCache());
                    //   myDrawView.setDrawingCacheEnabled(false);
                    // copy this bitmap otherwise distroying the cache will destroy
                    // the bitmap for the referencing drawable and you'll not
                    // get the captured view
                    //   Bitmap save = b1.copy(Bitmap.Config.ARGB_8888, false);
                    //BitmapDrawable d = new BitmapDrawable(b);
                    //canvasView.setBackgroundDrawable(d);
                    //   myDrawView.destroyDrawingCache();
                    // Bitmap save = myDrawView.getBitmapFromMemCache("0");
                    // myDrawView.setDrawingCacheEnabled(true);
                    //Bitmap save = myDrawView.getDrawingCache(false);
                    Bitmap well = drawView.getBitmap();
                    Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    Canvas now = new Canvas(save);
                    now.drawRect(new Rect(0, 0, 320, 480), paint);
                    now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight()), new Rect(0, 0, 320, 480), null);

                    // Canvas now = new Canvas(save);
                    //myDrawView.layout(0, 0, 100, 100);
                    //myDrawView.draw(now);
                    if (save == null) {
                        System.out.println("NULL bitmap save\n");
                    }
                    save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    //ostream.flush();
                    //ostream.close();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
package com.pim.appprojectcg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new BitmapWorkerTask();

                // create directory if not exist
                final File dir = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));
                Log.d("MYTAG","File "+dir);
                if (!dir.exists()) {
                    Log.d("MYTAG","Make dir");
                    dir.mkdirs();
                }

                File output = new File(dir, "canvasdemo.jpg");
                OutputStream os;

                try {
                    os = new FileOutputStream(output);
                    b.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    final Handler handler = new Handler();

                    //this code will scan the image so that it will appear in your gallery when you open next time
                    MediaScannerConnection.scanFile(DrawActivity.this, new String[]{output.toString()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Toast.makeText(CanvasDemoActivity.this, CanvasDemoActivity.this.getResources().getString(R.string.str_save_image_text) + dir.getPath(), Toast.LENGTH_LONG).show();
                                            Toast.makeText(DrawActivity.this, "myDrawing" + dir.getPath(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                    );
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
    }


    /*private class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            FileOutputStream fos = null;
              try {
                  fos = new FileOutputStream(Environment.getExternalStorageDirectory());
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              }

               b.compress(Bitmap.CompressFormat.PNG, 95, fos);
              return b;
        }

    }*/
}

/*//create directory if not exist
                final File dir = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File output = new File(dir, "canvasdemo.jpg");
                OutputStream os;

                try {
                    os = new FileOutputStream(output);
                    b.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    final Handler handler = new Handler();

                    //this code will scan the image so that it will appear in your gallery when you open next time
                    MediaScannerConnection.scanFile(CanvasDemoActivity.this, new String[]{output.toString()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(CanvasDemoActivity.this, CanvasDemoActivity.this.getResources().getString(R.string.str_save_image_text) + dir.getPath(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                    );
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }*/
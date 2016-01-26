package com.example.nobell.project4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by daeseongkim on 2016. 1. 26..
 */
public class FileManager {
    public static void initialize () {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "ShopAndShop");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static void getImage(final String file_name, final ImageView view) {
        class DownloadFileAsync extends AsyncTask<Void, Void, String> {

            DownloadFileAsync () {
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... param) {
                int count;

                try {
                    String server_address = "http://hjlog.me:3000/uploads/" + file_name;
                    String local_path = "/sdcard/ShopAndShop/" + file_name;
                    Log.e("Server_address", server_address);
                    Log.e("Local_path", local_path);
                    URL url = new URL(server_address);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();

                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(local_path);

                    byte data[] = new byte[1024];

                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {}
                return null;

            }

            @Override
            protected void onPostExecute(String unused) {
                File imgFile = new  File("/sdcard/ShopAndShop/" + file_name);

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    view.setImageBitmap(myBitmap);
                }
            }
        }

        File imgFile = new  File("/sdcard/ShopAndShop/" + file_name);

        if(!imgFile.exists()) {
            new DownloadFileAsync().execute();
        }
        else {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            view.setImageBitmap(myBitmap);
        }
    }

//    private static void downloadTolocal (String file_name) {
//        new DownloadFileAsync(file_name).execute();
//    }
//
//    public static File getFile (String file_name) {
//        File f = new File("/sdcard/ShopAndShop/" + file_name);
//        if (!f.exists()) {
//            downloadTolocal(file_name);
//            return new File("/sdcard/ShopAndShop/" + file_name);
//        }
//        return f;
//    }


//    public static void getImage(final String file_name, final ImageView view) {
//
//        class GetImage extends AsyncTask<Void,Void,Bitmap> {
//
//            @Override
//            protected void onPostExecute(Bitmap b) {
////                imageView.setImageBitmap(b);
//                view.setImageBitmap(b);
//            }
//
//            @Override
//            protected Bitmap doInBackground(Void... params) {
//                String add = "http://hjlog.me:3000/uploads/"+file_name;
//                URL url = null;
//                Bitmap image = null;
//                try {
//                    url = new URL(add);
//                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return image;
//            }
//        }
//
//        GetImage gi = new GetImage();
//        gi.execute();
//    }
}

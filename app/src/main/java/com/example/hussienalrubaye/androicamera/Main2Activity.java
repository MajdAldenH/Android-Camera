package com.example.hussienalrubaye.androicamera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      GridView  ls=(GridView) findViewById(R.id.gridView);




      ArrayList<item> fullsongpath=new  ArrayList<item>();
        // which image properties are we querying
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA

        };

        // content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cur = managedQuery(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );

        Log.i("ListingImages", " query count=" + cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            int bucketColumn = cur.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);

            int dateColumn = cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
            String dateColumnPath  ;

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                dateColumnPath = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
                fullsongpath.add( new item(    dateColumnPath,  bucket));
            } while (cur.moveToNext());

        }
        ls.setAdapter(new MyCustomAdapter(fullsongpath));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // adapter for song list
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<item> fullsongpath ;

        public MyCustomAdapter( ArrayList<item> fullsongpath) {
            this.fullsongpath=fullsongpath;

        }


        @Override
        public int getCount() {
            return fullsongpath.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.imageitem, null);
            final   item s = fullsongpath.get(position);
          ImageView mImageView = (ImageView) myView.findViewById(R.id.image);
 TextView txt=(TextView)myView.findViewById(R.id.text);
          txt.setText(s.name);



            File imgFile = new  File(s.image);

          try{

              if(imgFile.exists()){
                  Bitmap myBitmap  = BitmapFactory.decodeFile(s.image);
                  int h = 10; // height in pixels
                  int w = 10; // width in pixels
                  Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, h, w, true);
                  mImageView.setImageBitmap(scaled );
              }
          }catch (Exception ex){}



            //Bitmap bitmap = BitmapFactory.(s.image, bmOptions);
           // mImageView.setImageBitmap(bitmap);



            return myView;
        }
    }


}

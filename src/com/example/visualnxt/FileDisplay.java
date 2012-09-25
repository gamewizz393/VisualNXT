package com.example.visualnxt;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.lang.Object;
import java.lang.Comparable;
import android.os.Environment;
import java.io.File;


import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.*;
import android.content.*;
import android.provider.MediaStore;
import android.net.*;
import android.widget.*;
import java.util.*;


public class FileDisplay extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);
        
        ListView lv;
        ArrayList<String> FilesInFolder = GetFiles("/sdcard/VisualNXT");
        lv = (ListView)findViewById(R.id.FileList);

        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, FilesInFolder));
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_file_display, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++) 
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }
    
    private void openFile(String filePath,String fileExtension) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(filePath);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getMimeTypeFromExtension(fileExtension);
        intent.setDataAndType(Uri.fromFile(file), type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
        }
}
}

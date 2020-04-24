package com.example.qrscanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    public void clickHistory(View v) {
        setContentView(R.layout.data_views);
        listItem = new ArrayList<>();
        ListView datasList = findViewById(R.id.datasList);
        DBHelper db = new DBHelper(this);

        Cursor c = db.viewData();

        if (c.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()){
                listItem.add(c.getString(1));
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
                datasList.setAdapter(adapter);
            }
        }
    }

    public void goBackToMain(View v) {setContentView(R.layout.activity_main);}

    public void clearAll(View v){
        DBHelper db = new DBHelper(this);
        db.deleteContent(new QRContent());
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "All data is deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //menampilkan hasil scanning :D
        DBHelper db = new DBHelper(this);

        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result:");
        String res = result.getText();
        db.addContent(new QRContent(res));
        builder.setMessage(res);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        setContentView(R.layout.activity_main);

        //lanjutkan scanning QR Code:
    //    mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        finish();
    }
}

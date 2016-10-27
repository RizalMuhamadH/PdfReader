package com.apps.userinarts.pdfcoba;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyAsset();
            }
        });
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void copyAsset() {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;

        String strDir = Environment.DIRECTORY_DOWNLOADS + File.separator + "Pdfs";
        File fileDir = new File(strDir);
        fileDir.mkdirs();
        File file = new File(fileDir, "report.pdf");

        try {
            in = assetManager.open("report.pdf");
            out = new BufferedOutputStream(new FileOutputStream(file));

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse
                ("file://" + Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS)
                        + File.separator + "Pdfs" + "/report.pdf"), "application/pdf");
        startActivity(intent);
    }

}

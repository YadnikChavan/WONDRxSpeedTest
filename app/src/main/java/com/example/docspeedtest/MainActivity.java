package com.example.docspeedtest;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView txtUpload;
    TextView txtDownload;
    Button btnCheckSpeed;
    ConnectivityManager cm;
    NetworkCapabilities nc;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUpload = (TextView) findViewById(R.id.txtUpload);
        txtDownload = (TextView) findViewById(R.id.txtDownload);
        btnCheckSpeed = (Button) findViewById(R.id.btnCheckSpeed);

    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(cm.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        btnCheckSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                nc = cm.getNetworkCapabilities(cm.getActiveNetwork());

                //8 kbps = 1 kb, 1000 kb = 1 mbps
                int downloadSpeedinkbps = (nc.getLinkDownstreamBandwidthKbps() / 8);
                int uploadSpeedinkbps = (nc.getLinkUpstreamBandwidthKbps() / 8);

                int downloadSpeedinmbps = downloadSpeedinkbps/1000;
                int uploadSpeedinmbps = uploadSpeedinkbps/1000;

                txtDownload.setText(downloadSpeedinkbps + "");
                txtUpload.setText(uploadSpeedinkbps + "");
            }
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    private void init() {
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        nc = cm.getNetworkCapabilities(cm.getActiveNetwork());


        try {
            Log.d("INIT", "CONNECTEDD::");
            btnCheckSpeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //8 kbps = 1 kb, 1000 kb = 1 mbps
                    double download = (nc.getLinkDownstreamBandwidthKbps() / 8);
                    double upload = (nc.getLinkUpstreamBandwidthKbps() / 8);

                    Log.d("MainACTIVITY", "DOWN :: " + download);
                    Log.d("MAINACTIVITY", "UPLOAD :: " + upload);

                    txtDownload.setText(download + "");
                    txtUpload.setText(upload + "");
                }
            });

        } catch (Exception ex) {
            Log.wtf("MAINACTIVITY", ex.toString());
        }
    }


}

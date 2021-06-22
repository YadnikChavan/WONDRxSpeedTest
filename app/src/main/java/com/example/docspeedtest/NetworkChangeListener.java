package com.example.docspeedtest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Comman.isConnected(context)){
            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            builder.setMessage("Please connect to internet").setCancelable(false);
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }
}

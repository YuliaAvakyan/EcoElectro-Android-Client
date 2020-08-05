package com.example.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class CallBackFragment extends DialogFragment {
    MainActivity m;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setMessage("Диалог")
        builder.setPositiveButton("ПОЗВОНИТЬ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        String toDial="tel:"+"+79995359226";
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toDial)));
                    }
                })
                .setNeutralButton("ОТМЕНА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CallBackFragment.this.getDialog().cancel();

                    }
                })
                .setNegativeButton("НАПИСАТЬ В TELEGRAM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/InfotechAvl_bot"));

                                                // Verify it resolves
                        PackageManager packageManager = getActivity().getPackageManager();
                        List<ResolveInfo> activities = packageManager.queryIntentActivities(telegram, 0);
                        boolean isIntentSafe = activities.size() > 0;

                        // Start an activity if it's safe
                        if (isIntentSafe) {
                            startActivity(telegram);
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

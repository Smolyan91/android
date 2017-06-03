package com.example.jobcollisions.controller.alert_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.jobcollisions.R;


public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedBundleInstanceState){

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, null) //TODO
                .create();
    }
}

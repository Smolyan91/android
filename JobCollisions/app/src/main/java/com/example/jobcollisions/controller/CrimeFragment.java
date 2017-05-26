package com.example.jobcollisions.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.jobcollisions.R;
import com.example.jobcollisions.model.Crime;

import java.text.SimpleDateFormat;

/**
 * Fragment
 * @author SmolyanovIS
 */

public class CrimeFragment extends Fragment {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
    private Crime mCrime;
    private EditText mEditText;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    /***
     * false -нужно ли вкл заполненное предст в родителя
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_crime, container,false);
        mEditText = (EditText) view.findViewById(R.id.crime_title);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mDateButton.setText(dateFormat.format(mCrime.getDate()));
        mDateButton.setEnabled(false); //TODO разлочить после обвеса событием
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSolvedCheckBox.setChecked(isChecked);
            }
        });
        return view;
    }
}

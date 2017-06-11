package com.example.jobcollisions.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.jobcollisions.R;
import com.example.jobcollisions.controller.alert_dialog.DatePickerFragment;
import com.example.jobcollisions.controller.alert_dialog.TimePickerFragment;
import com.example.jobcollisions.model.Crime;
import com.example.jobcollisions.model.CrimeLab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Fragment
 * @author SmolyanovIS
 */

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm");
    private Crime mCrime;
    private EditText mEditText;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;
    private Button mTimeButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeUUID = (UUID) getArguments()
                .getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(crimeUUID);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getCrimeLab(getActivity())
                .updateCrime(mCrime);
    }

    public static CrimeFragment newInstance(UUID uuid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,uuid);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * По завершению CrimeFragment данные пуляются в БД
     */
    @Override
    public void onPause(){
        super.onPause();
        CrimeLab.getCrimeLab(getActivity()).updateCrime(mCrime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateTextDateOnButton(date);
        }
        if (requestCode == REQUEST_TIME){
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.getDate().setHours(date.getHours());
            mCrime.getDate().setMinutes(date.getMinutes());
            updateTextTimeOnButton(date);
        }
    }

    private void updateTextDateOnButton(Date date) {
        mDateButton.setText(dateFormat.format(date));
    }
    private void updateTextTimeOnButton(Date date) {
        mTimeButton.setText(timeFormat.format(date));
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
        mEditText.setText(mCrime.getTitle());
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
        updateTextDateOnButton(mCrime.getDate());
        //при нажатии выводим AlertDialog с выбором даты
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager,DIALOG_DATE);
            }
        });

        mTimeButton = (Button) view.findViewById(R.id.crime_time);
        updateTextTimeOnButton(mCrime.getDate());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fragmentManager, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCrime.setSolved(mSolvedCheckBox.isChecked());
                mSolvedCheckBox.setChecked(mCrime.isSolved());
            }
        });
        return view;
    }
}

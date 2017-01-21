package com.example.crimeintent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Администратор on 17.01.2017.
 * CrimeFragment — контроллер, взаимодействующий с объектами модели и представления.
 * Его задача — выдача подробной информации о конкретном преступлении и ее обновление при модификации пользователем.
 */

public class CrimeFragment extends Fragment {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton; //для франения даты кнопка(и последующего UI изменения)
    private CheckBox mSolvedCheckBox;   //чек-бокс для отметки решения проблемы
    public static final String ARG_CRIME_ID = "crime_id";

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getInstanceCrimeLab(getActivity()).getCrime(crimeId);
    }

    /***
     * LayoutInflater – это класс, который умеет из содержимого layout-файла создать View-элемент.
     * Метод который это делает называется inflate. Есть несколько реализаций этого метода с различными параметрами.
     * Но все они используют друг друга и результат их выполнения один – View.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_file, container, false);
        //Поле редактирования
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Кнопка даты
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(simpleDateFormat.format(mCrime.getDate()));
        mDateButton.setEnabled(false);  //кнопка не будет реагировать на нажатия(на время)

        //Чек бокс
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}

package com.example.crimeintent.controller;


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

import com.example.crimeintent.R;
import com.example.crimeintent.model.Crime;

/**
 * Created by Администратор on 17.01.2017.
 * CrimeFragment — контроллер, взаимодействующий с объектами модели и представления.
 * Его задача — выдача подробной информации о конкретном преступлении и ее обновление при модификации пользователем.
 */

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton; //для франения даты кнопка(и последующего UI изменения)
    private CheckBox mSolvedCheckBox;   //чек-бокс для отметки решения проблемы


    /***
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
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
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);  //кнопка не будет реагировать на нажатия(на время)

        //Чек бокс
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}

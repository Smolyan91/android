package com.example.crimeintent.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crimeintent.R;
import com.example.crimeintent.model.Crime;

import java.util.List;

/**
 * Created by Администратор on 18.01.2017.
 *Для отображения списка на уровне контроллера создаем данный фрагмент и новую активность CrimeListActivity
 *
 * Обязанности RecyclerView сводятся к переработке виджетов TextView и размещению их на экране.
 * Но виджет RecyclerView не занимается размещением элементов на экране самостоятельно — он поручает
 * эту задачу LayoutManager. Объект LayoutManager управляет позиционированием элементов, а также определяет
 * поведение прокрутки. Таким образом, при отсутствии LayoutManager виджет RecyclerView просто погибнет в тщетной
 * попытке что-нибудь сделать.на выбор предоставляются несколько встроенных вариантов LayoutManagers; другие
 * варианты можно найти в сторонних библиотеках. Мы будем использовать класс LinearLayoutManager, который размещает
 * элементы в вертикальном списке.
 *
 * Виджет RecyclerView не создает ViewHolder самостоятельно. Вместо этого он обращается с запросом к адаптеру (adapter) — объекту
 * контроллера, который находится между RecyclerView и набором данных с информацией, которую должен вывести RecyclerView.
 * Адаптер отвечает за:
 * создание необходимых объектов ViewHolder;
 * связывание ViewHolder с данными из уровня модели.
 * Построение адаптера начинается с определения субкласса RecyclerView.Adapter.
 */

public class CrimeListFragment extends Fragment{

    private RecyclerView mCrimeRecyclerView; // для использования виджетов TextView

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //После создания виджета RecyclerView ему назначается другой объект LayoutManager.
        // Это необходимо для работы виджета RecyclerView.
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    /*
    *   Объекты Crime остаются невидимыми до тех пор, пока не будут определены реализации Adapter и ViewHolder
    */
    private class CrimeHolder extends RecyclerView.ViewHolder{
        public TextView mTitleTextView;

        public CrimeHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    /**
     * Класс RecyclerView взаимодействует с адаптером, когда потребуется создать объект ViewHolder или связать его
     * с объектом Crime. Сам виджет RecyclerView ничего не знает об объекте Crime, но адаптер располагает полной информацией о Crime.
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.mTitleTextView.setText(crime.getmTitle());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}

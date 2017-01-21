package com.example.crimeintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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
    private CrimeAdapter mAdapter;


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //После создания виджета RecyclerView ему назначается другой объект LayoutManager.
        // Это необходимо для работы виджета RecyclerView.
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    /**
     * Метод updateUI, который настраивает пользовательский интерфейс CrimeListFragment.
     */
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstanceCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getmCrimes();
        if (mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /*
    *   Объекты Crime остаются невидимыми до тех пор, пока не будут определены реализации Adapter и ViewHolder
    */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView; //в макете list_item_crime
        private TextView mDateTextView;
        private CheckBox mSolvedChekBox;
        private Crime mCrime;

        public CrimeHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedChekBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            itemView.setOnClickListener(this);
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getmTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedChekBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getmId()); //запуск детализации
            startActivity(intent);
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

        /***
         * Метод onCreateViewHolder вызывается виджетом RecyclerView, когда ему потребуется новое представление
         * для отображения элемента. В этом методе мы создаем объект View и упаковываем его в ViewHolder. RecyclerView
         * пока не ожидает, что представление будет связано с какими-либо данными.
         * Для получения представления мы заполняем макет из стандартной библиотеки Android с именем simple_list_item_1.
         * Этот макет содержит один виджет TextView, оформленный так, чтобы он хорошо смотрелся в списке.
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /**
         * Этот метод связывает представление View объекта ViewHolder с объектом модели. При вызове он получает
         * ViewHolder и позицию в наборе данных. Позиция используется для нахождения правильных данных модели,
         * после чего View обновляется в соответствии с этими данными.
         * В этой реализации эта позиция определяет индекс объекта Crime в массиве. После получения нужного объекта
         * мы связываем его с View, присваивая его заголовок виджету TextView из ViewHolder.
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}

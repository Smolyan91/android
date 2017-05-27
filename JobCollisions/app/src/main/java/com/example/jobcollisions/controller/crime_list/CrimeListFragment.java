package com.example.jobcollisions.controller.crime_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jobcollisions.R;
import com.example.jobcollisions.model.Crime;
import com.example.jobcollisions.model.CrimeLab;

import java.text.SimpleDateFormat;
import java.util.List;

public class CrimeListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    public class CrimeHolder extends RecyclerView.ViewHolder{

        private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;


        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView  =  (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView   =  (TextView) itemView.findViewById(R.id.list_item_date_text_view);
            mSolvedCheckBox = (CheckBox)  itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }
        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(dateFormat.format(mCrime.getDate()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimeList();
        mAdapter = new CrimeAdapter(crimes);
        mRecyclerView.setAdapter(mAdapter);
    }
    public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List crimes){
            mCrimes = crimes;
        }

        /***
         * Метод onCreateViewHolder вызывается виджетом RecyclerView , когда ему потре-
         * буется новое представление для отображения элемента. В этом создается
         * объект View и  упаковывается во  ViewHolder . RecyclerView пока не ожидает, что
         * представление будет связано с какими-либо данными. Для получения представления
         * заполняем макет из стандартной библиотеки Android с именем
         * simple_list_item_1 . Этот макет содержит один виджет TextView ,
         * оформленный так, чтобы он хорошо смотрелся в списке.
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_view, parent, false);
            return new CrimeHolder(view);
        }

        /***
         * onBindViewHolder : этот метод связывает представление View объекта
         * ViewHolder с  объектом модели. При вызове он получает ViewHolder и  позицию
         * в наборе данных. Позиция используется для нахождения правильных данных мо-
         * дели, после чего View обновляется в соответствии с этими данными.
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

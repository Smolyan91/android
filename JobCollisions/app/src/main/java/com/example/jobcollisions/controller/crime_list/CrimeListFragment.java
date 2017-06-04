package com.example.jobcollisions.controller.crime_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jobcollisions.R;
import com.example.jobcollisions.controller.CrimePagerActivity;
import com.example.jobcollisions.model.Crime;
import com.example.jobcollisions.model.CrimeLab;

import java.text.SimpleDateFormat;
import java.util.List;

public class CrimeListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;
    private int mCurrentPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(mCurrentPosition);
        return view;
    }

    private void updateUI(int position){
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getContext());
        List<Crime> crimes = crimeLab.getCrimeList();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_fragment_list, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //для обратного вызова командного меню
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mCurrentPosition);
    }

    public class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
        private SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm");
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private CheckBox mSolvedCheckBox;


        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_text_view);
            mTimeTextView = (TextView) itemView.findViewById(R.id.list_item_crime_time);
            mSolvedCheckBox = (CheckBox)  itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }
        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(dateFormat.format(mCrime.getDate()));
            mTimeTextView.setText(timeFormat.format(mCrime.getDate()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            mCurrentPosition = getAdapterPosition();
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
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
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
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

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }
}

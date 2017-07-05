package com.example.igor.musicbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MusicBoxFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_box_rv, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_music_box_recycler_view);
        //делаем в виде сетки из 3-х столбцов
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        Log.i("INFO", "MusicBoxFragment");
        recyclerView.setAdapter(new MusicAdapter());
        return  view;
    }

    private class MusicHolder extends RecyclerView.ViewHolder{

        private Button mButton;
        public MusicHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.button_music_item, viewGroup, false));
            mButton = (Button) itemView.findViewById(R.id.button_item_music);
        }
    }
    private class MusicAdapter extends RecyclerView.Adapter<MusicHolder>{

        @Override
        public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            Log.i("INFO", "onCreateViewHolder");
            return new MusicHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(MusicHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    public static MusicBoxFragment newInstance(){
        Log.i("INFO", "newMusicBoxFragment.newInstance()");
        return new MusicBoxFragment();
    }
}

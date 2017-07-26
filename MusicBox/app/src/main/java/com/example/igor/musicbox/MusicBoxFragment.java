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

import java.util.List;

public class MusicBoxFragment extends Fragment {

    private BeatBox beatBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("INFO", "MusicBoxFragment.onCreate()");
        beatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_box_rv, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_music_box_recycler_view);
        //делаем в виде сетки из 3-х столбцов
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        Log.i("INFO", "MusicBoxFragment");
        recyclerView.setAdapter(new MusicAdapter(beatBox.getSounds()));
        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beatBox.release();
    }

    private class MusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button mButton;
        private Sound mSound;

        public MusicHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.button_music_item, viewGroup, false));
            mButton = (Button) itemView.findViewById(R.id.button_item_music);
            mButton.setOnClickListener(this);
        }

        public void bindSound(Sound sound){
            mSound = sound;
            mButton.setText(mSound.getNameSound());
        }

        @Override
        public void onClick(View v) {
            beatBox.play(mSound);
            Log.d("D", mSound.getSoundId().toString());
        }
    }
    private class MusicAdapter extends RecyclerView.Adapter<MusicHolder>{

        private List<Sound> soundList;

        public MusicAdapter(List<Sound> soundList) {
            this.soundList = soundList;
        }

        @Override
        public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            Log.i("INFO", "onCreateViewHolder");
            return new MusicHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(MusicHolder holder, int position) {

            Sound sound = soundList.get(position);
            Log.i("Pos ", position + "");
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return soundList.size();
        }
    }

    public static MusicBoxFragment newInstance(){
        Log.i("INFO", "newMusicBoxFragment.newInstance()");
        return new MusicBoxFragment();
    }


}

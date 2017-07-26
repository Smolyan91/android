package com.example.igor.newlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by igor on 26.07.17.
 */

public class NewLauncherFragment extends Fragment {

    public static final String TAG = "NewLauncherFragment";

    private RecyclerView mRecyclerView;

    public static Fragment newInstance(){
        return new NewLauncherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_launcher, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_new_launcher_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("INFO: ", "NewLauncherFragment onCreateView");
        setAdapter();
        return view;
    }

    private void setAdapter(){

        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startIntent,0);
        Log.i(TAG, "Found " + activities.size() + " activities");
    }
}

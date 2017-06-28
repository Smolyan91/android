package com.example.jobcollisions.picture;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jobcollisions.R;

import java.lang.reflect.Array;

import static com.example.jobcollisions.controller.CrimeFragment.PHOTO_FULL;

public class FullImgDialogFragment extends DialogFragment {

    private Context context;
    private ImageView mImageView;
    private Bitmap bitmapFull;
    private byte[] arrayImg;


    public FullImgDialogFragment(){
        context = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.full_img, container, false);
        mImageView = (ImageView) v.findViewById(R.id.full_img_dialog_fragment);
        if (this.arrayImg.length > 0){
            bitmapFull = BitmapFactory.decodeByteArray(this.arrayImg, 0, this.arrayImg.length);
            mImageView.setImageBitmap(bitmapFull);
        }
        return v;
    }

    public static FullImgDialogFragment newInstance(byte[] array){
        FullImgDialogFragment fullImgDialogFragment = new FullImgDialogFragment();
        fullImgDialogFragment.arrayImg = array;
        return fullImgDialogFragment;
    }
}

package com.example.jobcollisions.picture;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){

        //чтение размера изображения на диске
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //Вычисление степени масштабирования
        int simpleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth){
            if (srcHeight > destHeight){
                simpleSize = Math.round(srcHeight / destHeight);
            }else {
                simpleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = simpleSize;

        //чтение данных и создание итогового изображения
        return BitmapFactory.decodeFile(path,options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}

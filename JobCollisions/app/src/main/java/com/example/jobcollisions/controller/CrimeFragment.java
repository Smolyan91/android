package com.example.jobcollisions.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jobcollisions.R;
import com.example.jobcollisions.controller.alert_dialog.DatePickerFragment;
import com.example.jobcollisions.controller.alert_dialog.TimePickerFragment;
import com.example.jobcollisions.model.Crime;
import com.example.jobcollisions.model.CrimeLab;
import com.example.jobcollisions.picture.FullImgDialogFragment;
import com.example.jobcollisions.picture.PictureUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Fragment
 * @author SmolyanovIS
 */

public class CrimeFragment extends Fragment {

    //Contacts
    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

    //For debug LogCat
    public static final String MSG = "MSG ";
    //Constants
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_PHOTO = 3;
    private static final int PHOTO_TAG = 4;
    public static final String PHOTO_FULL = "photoForDialogFragment";

    //Formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm");

    //ViewController
    private Bitmap mBitmapForTranssiveToFuulDialog;
    private Crime mCrime;
    private File mPhotoFile;
    private EditText mEditText;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mReportButton;
    private Button mRequestContactButton;
    private Button mPhoneButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    // Flags and tmp_val
    private static boolean isFullImage = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeUUID = (UUID) getArguments()
                .getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getCrimeLab(getActivity()).getCrime(crimeUUID);
        mPhotoFile = CrimeLab.getCrimeLab(getActivity()).getPhotoFile(mCrime);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getCrimeLab(getActivity())
                .updateCrime(mCrime);
    }

    public static CrimeFragment newInstance(UUID uuid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,uuid);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public String getReport(){
        String solvedString;

        if (mCrime.isSolved()){
            solvedString = getString(R.string.crime_report_solved);
        }else solvedString = getString(R.string.crime_report_unsolved);

        String date = dateFormat.format(mCrime.getDate());
        String suspect = mCrime.getSuspectName();
        if (suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        }else suspect = getString(R.string.crime_report_suspect);

        return getString(R.string.crime_report, mCrime.getTitle(),
                date, solvedString, suspect);
    }

    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()){
            Log.d("UPD_Photo_View", mPhotoFile == null? "null": mPhotoFile.exists()? "yes":"no");
            mPhotoView.setImageDrawable(null);
        }else {
            Bitmap imgBitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(imgBitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateTextDateOnButton(date);
        }else if (requestCode == REQUEST_TIME){
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.getDate().setHours(date.getHours());
            mCrime.getDate().setMinutes(date.getMinutes());
            updateTextTimeOnButton(date);
        }else if (requestCode == REQUEST_CONTACT && data != null){

            Uri contactUri = data.getData();
            String[] queryFields = new String[]{
                    DISPLAY_NAME,
                    CONTACT_ID,
                    HAS_PHONE_NUMBER
            };
            Cursor cursor = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            String phoneNumber = null;
            try {
                if (cursor.getCount() == 0) return;
                cursor.moveToFirst();
                String cont_id = cursor.getString(cursor.getColumnIndex
                                (ContactsContract.Contacts._ID));
                String hasPhone = cursor.getString(cursor.getColumnIndex
                                (ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (Integer.parseInt(hasPhone) > 0){
                    Cursor cursorPhone = null;
                    try {
                        cursorPhone = getActivity().getContentResolver()
                                .query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    PHONE_CONTACT_ID + " = " + cont_id,
                                    null,
                                    null
                        );
                        while (cursorPhone.moveToNext()) {
                            phoneNumber = cursorPhone.getString(cursorPhone
                                            .getColumnIndex(PHONE_NUMBER));
                            Log.i("MSG" , phoneNumber);
                            if (phoneNumber != null) break;
                        }
                    }finally {
                        cursorPhone.close();
                    }
                }
                String nameSuspect = cursor.getString(0);
                mCrime.setSuspectName(nameSuspect);
                mCrime.setPhoneNumber(phoneNumber);
                mRequestContactButton.setText(nameSuspect);
            }finally {
                cursor.close();
            }
        }else if (requestCode == REQUEST_PHOTO){
            updatePhotoView();
        }
    }

    private void updateTextDateOnButton(Date date) {
        mDateButton.setText(dateFormat.format(date));
    }
    private void updateTextTimeOnButton(Date date) {
        mTimeButton.setText(timeFormat.format(date));
    }

    /***
     * false -нужно ли вкл заполненное предст в родителя
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_crime, container,false);

        mPhotoButton = (ImageButton) view.findViewById(R.id.camera_crime_button);

        mPhotoView = (ImageView) view.findViewById(R.id.crime_photo);

        mEditText = (EditText) view.findViewById(R.id.crime_title);
        mEditText.setText(mCrime.getTitle());
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        updateTextDateOnButton(mCrime.getDate());
        //при нажатии выводим AlertDialog с выбором даты
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager,DIALOG_DATE);
            }
        });

        mTimeButton = (Button) view.findViewById(R.id.crime_time);
        updateTextTimeOnButton(mCrime.getDate());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fragmentManager, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCrime.setSolved(mSolvedCheckBox.isChecked());
                mSolvedCheckBox.setChecked(mCrime.isSolved());
            }
        });

        mReportButton = (Button) view.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,getReport());
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                intent = Intent.createChooser(intent, getString(R.string.send_report));
                startActivity(intent);
            }
        });

        final Intent intentPick = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(intentPick, PackageManager.MATCH_DEFAULT_ONLY) == null){
            mRequestContactButton.setEnabled(false);
        }
        //Кнопка получения контакта
        mRequestContactButton = (Button) view.findViewById(R.id.crime_suspect);
        if (mCrime.getSuspectName() != null) {
            mRequestContactButton.setText(mCrime.getSuspectName());
        }
        mRequestContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intentPick, REQUEST_CONTACT);
            }
        });
        //Кнопка звонка
        mPhoneButton = (Button) view.findViewById(R.id.call_to_men);
        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + mCrime.getPhoneNumber()));
                startActivity(intent);
            }
        });

        final Intent captureImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null
                                && captureImg.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(true);

        if (canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImg.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        if (mPhotoFile != null){
            Log.d("debug", mPhotoFile.toString());
            Log.i("Info", "ok");
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(captureImg, REQUEST_PHOTO);
            }
        });

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO разобраться с этим дерьмом
                updatePhotoView();
                if (mPhotoFile==null || !mPhotoFile.exists()) return;   //no photo
                FragmentManager fragmentManager = getFragmentManager();
                ByteArrayOutputStream os = null;
                try {
                    os = new ByteArrayOutputStream();
                    mPhotoView.buildDrawingCache();
                    mBitmapForTranssiveToFuulDialog = mPhotoView.getDrawingCache();
                    mBitmapForTranssiveToFuulDialog.compress(Bitmap.CompressFormat.JPEG, 50, os);
                    FullImgDialogFragment dialogFragment =
                            FullImgDialogFragment.newInstance(os.toByteArray());
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_crime_profile, dialogFragment);
                    fragmentTransaction.commit();
                    //dialogFragment.show(getFragmentManager(), PHOTO_FULL);

                }finally {
                    try {
                        os.close();
                    }catch (IOException e){
                        Log.e(e.fillInStackTrace().getMessage(), "Error byteArray");
                    }
                }
            }
        });
        updatePhotoView();
        return view;
    }
}












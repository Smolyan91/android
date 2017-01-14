package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index_key_QA"; // константа для сохранения Activity при поворотах экрана
    private static final int REQUEST_CODE_CHEAT = 0;

    private static Date date = new Date();

    private int mCurrentIndex = 0; // для индексации массива
    private int mPreviosIndex = 0; //для сохр предыдущего вопроса
    private boolean mIsCheater;
    private boolean isNotRepeat;
    private Map<Integer, Boolean> mapRepeat = new HashMap<>();
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called with time: " + date.getTime());
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater = savedInstanceState.getBoolean(KEY_INDEX, false);
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(false);
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreviosIndex = mCurrentIndex;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                isNotRepeat = false;
                for (Map.Entry<Integer, Boolean> entry: mapRepeat.entrySet()){
                    if (entry.getKey().equals(mQuestionBank[mCurrentIndex].getTextResId())){
                        isNotRepeat = true;
                        try {
                            updateBecauseCheater();
                        }catch (InterruptedException e){
                        }
                    }
                }
                if (!isNotRepeat) updateQuestion();
            }
        });
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mPreviosIndex;
                updateQuestion();
            }
        });
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaneInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_INDEX, mIsCheater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called. Time: " + date.getTime());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    private void updateBecauseCheater()throws InterruptedException{
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        Thread.sleep(8000);
        mQuestionTextView.setText("This question is CHEAT!!");
    }
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPassedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater){
            messageResId = R.string.judgment_toast;
            mapRepeat.put(mQuestionBank[mCurrentIndex].getTextResId(), true);
        }else {
            if (userPassedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}

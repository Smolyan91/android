package com.example.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;

    private Question[] mQuestionBunk = new Question[]{
            new Question(R.string.question_1,true),
            new Question(R.string.question_2,true),
            new Question(R.string.question_3,false),
            new Question(R.string.question_4,false),
            new Question(R.string.question_5,true),
            new Question(R.string.question_6,false),
            new Question(R.string.question_7,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this, R.string.correct_ans, Toast.LENGTH_SHORT).show();
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this, R.string.incorrect_ans, Toast.LENGTH_SHORT).show();
            }
        });
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBunk[mCurrentIndex].getmTextResID();
        mQuestionTextView.setText(question);
    }
}

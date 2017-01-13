package com.example.geoquiz;

/**
 * Created by Администратор on 11.01.2017.
 *
 */

public class Question {

    private int mTextResId; // текст вопроса
    private boolean mAnswerTrue; // правильный ответ


    public Question(int textResId, boolean answerTrue){
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }
}

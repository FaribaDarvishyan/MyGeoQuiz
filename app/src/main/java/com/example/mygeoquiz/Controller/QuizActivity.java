package com.example.mygeoquiz.Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mygeoquiz.Model.Question;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mygeoquiz.R;

public class QuizActivity extends AppCompatActivity {
    private LinearLayout mMainLayout;
    private TextView mTextViewQuestion;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrev;
    private ImageButton mImageButtonLast;
    private ImageButton mImageButtonFirst;
    private TextView mTextViewScore;
    private LinearLayout mScoreLayout;
    private TextView mTextViewFinalScore;
    private ImageButton mImageButtonReset;


    private int mCurrentIndex = 0;
    private int mCurrentScore=0;
    private int mNumOfAnswered=0;
    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };

    /**
     * This method is used to crete ui for activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this method will create the layout
        //inflate: creating object of xml layout
        setContentView(R.layout.activity_quiz);

        //if we want to change logic we must first find the view objects (it must have "id")
        findViews();
        setListeners();
        updateQuestion();
//        checkGameOver();

    }

    @SuppressLint("WrongViewCast")
    private void findViews() {
        mTextViewQuestion = findViewById(R.id.txt_view_question_text);
        mTextViewScore=findViewById(R.id.txt_view_score_text);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mImageButtonNext= findViewById(R.id.im_btn_next);
        mImageButtonPrev = findViewById(R.id.im_btn_prev);
        mImageButtonLast = findViewById(R.id.im_btn_last);
        mImageButtonFirst= findViewById(R.id.im_btn_first);
        mMainLayout=findViewById(R.id.main);
        mScoreLayout=findViewById(R.id.score);
        mTextViewFinalScore=findViewById(R.id.txt_final_score);
        mImageButtonReset=findViewById(R.id.im_btn_reset);

    }

    private void setListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++mNumOfAnswered;
                mButtonTrue.setVisibility(View.INVISIBLE);
                mButtonFalse.setVisibility(View.INVISIBLE);
                mQuestionBank[mCurrentIndex].setIsAnswered(true);
                checkAnswer(true);

            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++mNumOfAnswered;
                mButtonTrue.setVisibility(View.INVISIBLE);
                mButtonFalse.setVisibility(View.INVISIBLE);
                mQuestionBank[mCurrentIndex].setIsAnswered(true);
                checkAnswer(false);

            }
        });

        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();


            }
        });

        mImageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mImageButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=0;
                updateQuestion();
            }
        });
        mImageButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=mQuestionBank.length -1;
                updateQuestion();
            }
        });
        mImageButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScoreLayout.setVisibility(View.GONE);
                resetGame();
            }
        });

    }
    private void resetGame(){
        mCurrentScore=0;
        mCurrentIndex=0;
        mTextViewScore.setText("Score is " + mCurrentScore);
        mButtonTrue.setVisibility(View.VISIBLE);
        mButtonFalse.setVisibility(View.VISIBLE);
        mNumOfAnswered=0;

        for (Question element:mQuestionBank) {
            element.setIsAnswered(false);

        }
        mMainLayout.setVisibility(View.VISIBLE);

    }

    private void updateQuestion() {
        int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
        mTextViewQuestion.setText(questionTextResId);
        if(mQuestionBank[mCurrentIndex].isIsAnswered()==false)
        {
        mButtonTrue.setVisibility(View.VISIBLE);
        mButtonFalse.setVisibility(View.VISIBLE);

        }
        else {
            mButtonTrue.setVisibility(View.INVISIBLE);
            mButtonFalse.setVisibility(View.INVISIBLE);
        }
        checkGameOver();
    }

    private void updateScore(){
        mTextViewScore.setText("Score :" + ++mCurrentScore);
    }

    @SuppressLint("WrongConstant")
    private void checkAnswer(boolean userPressed) {
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
            Toast.makeText(QuizActivity.this, R.string.toast_correct, Toast.LENGTH_LONG)
                    .show();
            updateScore();

        } else {
            Toast.makeText(QuizActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT)
                    .show();

        }

        mButtonTrue.setVisibility(View.INVISIBLE);
        mButtonFalse.setVisibility(View.INVISIBLE);

    }
    private void showFinalScore(){
        mTextViewFinalScore.setText("your score is : "+ mCurrentScore);
        mTextViewFinalScore.setTextSize(30);

    }
    private void checkGameOver(){
        if (mNumOfAnswered==mQuestionBank.length){
            mMainLayout.setVisibility(View.GONE);
            mScoreLayout.setVisibility(View.VISIBLE);
            showFinalScore();



        }

    }
}
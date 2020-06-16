package com.kotlinapps.quizapp.UI.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kotlinapps.quizapp.data.State;

import java.util.List;
import java.util.Random;

public class quizView extends LinearLayout {

    private RadioGroup optionStates;
    private int correctOptionId;

    private optionsClickListener listener;

    public quizView(Context context) {
        super(context);
        initRadios();
    }

    public quizView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRadios();
    }

    private void initRadios(){
        optionStates = new RadioGroup(getContext());
        optionStates.setId(View.generateViewId());
    }
    public interface optionsClickListener{
        void OnClicked(Boolean result);
    }

    public void setOptionsClickListener(optionsClickListener optionsClickListener){
        this.listener = optionsClickListener;
    }

    public void setData(List<State> states){
        Random random = new Random(System.currentTimeMillis());
        int correctOption = random.nextInt(4);

        State correctState = states.get(correctOption);

        TextView questionTextView = new TextView(getContext());
        String question = "What is the Capital of " + correctState.getStateName() + " ?";
        questionTextView.setText(question);
        questionTextView.setPadding(20,20,20,20);
        questionTextView.setTextColor(getResources().getColor(android.R.color.black));
        questionTextView.setTextSize(24);

        this.addView(questionTextView);

        this.addView(optionStates);

        RadioButton[] options = new RadioButton[4];

        options[correctOption] = new RadioButton(getContext());
        options[correctOption].setId(View.generateViewId());
        options[correctOption].setText(correctState.getCapitalName());
        correctOptionId = options[correctOption].getId();


        for(int i = 0,j=0;i<4;i++,j++) {
            if (i == correctOption) {
                optionStates.addView(options[correctOption]);
                continue;
            } else {
                options[i] = new RadioButton(getContext());
                options[i].setId(View.generateViewId());
                options[i].setText(states.get(j).getCapitalName());
                optionStates.addView(options[i]);
            }
            initListener();
        }


    }


    private void initListener(){
        optionStates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(listener!=null){
                    if(checkedId == correctOptionId){
                        listener.OnClicked(true);
                    }
                    else{
                        listener.OnClicked(false);
                    }
                }
            }
        });
    }

    public void reset(){
        optionStates.removeAllViews();
        this.removeAllViews();
    }
}

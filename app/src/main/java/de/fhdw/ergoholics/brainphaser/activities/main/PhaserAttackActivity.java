package de.fhdw.ergoholics.brainphaser.activities.main;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.fhdw.ergoholics.brainphaser.R;

public class PhaserAttackActivity extends AppCompatActivity implements View.OnClickListener {

    //define widget variables
    private TextView timerText;
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonFour;
    private TextView questionText;
    private Button startButton;
    private TextView titleText;

    //define questions and answers in separate arrays - will replace with xml parser methods
    String[] questionArray = new String[]{"Which president is on the United States 1,000 dollar bill?",
            "What building is found on the back of the United States 100 dollar bill?",
            "What year was the two dollar bill last printed in the United States?",
            "What is the spanish word for money?",
            "What is the official currency of Equador?",
            "How much does a United States dollar bill weigh?"};
    String[] buttonOneArray = new String[] {"Grover Cleveland","Lincoln Memorial","2000","peso","Pound","1.5 Grams"};
    String[] buttonTwoArray = new String[] {"Abraham Lincoln","Independence Hall","1998","dinero","Euro","1 Gram"};
    String[] buttonThreeArray = new String[] {"Thomas Jefferson","White House","2003","dinner","Peso","2 Grams"};
    String[] buttonFourArray = new String[] {"Jimmy Carter","Twin Towers","1996","deeniro","United States Dollar","0.5 Grams"};
    boolean[] buttonOneAnswer = new boolean[] {true,false,false,false,false,false};
    boolean[] buttonTwoAnswer = new boolean[] {false,true,false,true,false,true};
    boolean[] buttonThreeAnswer = new boolean[] {false,false,true,false,false,false};
    boolean[] buttonFourAnswer = new boolean[] {false,false,false,false,true,false};
    int arrayValue = 0;

    //define the SharedPreferences object
    private SharedPreferences savedValues;

    //define instance variables that should be saved
    private String buttonOneString = "";
    private String buttonTwoString = "";
    private String buttonThreeString = "";
    private String buttonFourString = "";
    private String questionTextString = "";
    private int currentArrayValue = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phaser_attack);

        //get references to the widgets
        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonFour = (Button) findViewById(R.id.buttonFour);
        questionText = (TextView) findViewById(R.id.questionText);
        startButton = (Button) findViewById(R.id.startButton);
        titleText = (TextView) findViewById(R.id.title);
        timerText = (TextView) findViewById(R.id.timerText);

        //set onClick listener for buttons
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        startButton.setOnClickListener(this);

        //get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("buttonOneString", buttonOne.getText().toString());
        editor.putString("buttonTwoString", buttonTwo.getText().toString());
        editor.putString("buttonThreeString", buttonThree.getText().toString());
        editor.putString("buttonFourString", buttonFour.getText().toString());
        editor.putString("questionTextString", questionText.getText().toString());
        editor.putInt("currentArrayValue", arrayValue);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        //get the instance variables
        buttonOneString = savedValues.getString("buttonOneString", "");
        buttonTwoString = savedValues.getString("buttonTwoString", "");
        buttonThreeString = savedValues.getString("buttonThreeString", "");
        buttonFourString = savedValues.getString("buttonFourString", "");
        questionTextString = savedValues.getString("questionTextString", "");
        currentArrayValue = savedValues.getInt("currentArrayValue", 0);

        //set the instance variables on their widgets
        buttonOne.setText(buttonOneString);
        buttonTwo.setText(buttonTwoString);
        buttonThree.setText(buttonThreeString);
        buttonFour.setText(buttonFourString);
        questionText.setText(questionTextString);
        arrayValue = currentArrayValue;
    }

    public void displayStart() {
        buttonOne.setVisibility(View.VISIBLE);
        buttonTwo.setVisibility(View.VISIBLE);
        buttonThree.setVisibility(View.VISIBLE);
        buttonFour.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.INVISIBLE);
        buttonOne.setText(buttonOneArray[0]);
        buttonTwo.setText(buttonTwoArray[0]);
        buttonThree.setText(buttonThreeArray[0]);
        buttonFour.setText(buttonFourArray[0]);
        questionText.setText(questionArray[0]);
        arrayValue = 0;
        startButton.setText("New Game");
    }

    public void displayNext() {
        buttonOne.setText(buttonOneArray[arrayValue]);
        buttonTwo.setText(buttonTwoArray[arrayValue]);
        buttonThree.setText(buttonThreeArray[arrayValue]);
        buttonFour.setText(buttonFourArray[arrayValue]);
        questionText.setText(questionArray[arrayValue]);
    }

    public boolean checkAnswer(boolean[] array) {
        //endgame, incorrect answer
        if (!array[arrayValue]) {
            //TODO
            return false;
        }
        return true;
    }

    //change to endgame view
    public void displayWinner() {
        //TODO
        buttonOne.setVisibility(View.INVISIBLE);
        buttonTwo.setVisibility(View.INVISIBLE);
        buttonThree.setVisibility(View.INVISIBLE);
        buttonFour.setVisibility(View.INVISIBLE);
        questionText.setVisibility(View.INVISIBLE);
        titleText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                CountDownTimer Count = new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        timerText.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timerText.setText("Game Over!");
                        displayWinner();
                    }
                };

                Count.start();
                displayStart();
                break;
            case R.id.buttonOne:
                if(checkAnswer(buttonOneAnswer)) {
                    arrayValue++;
                    displayNext();
                    break;
                }
                else {
                    displayWinner();
                    break;
                }
            case R.id.buttonTwo:
                if(checkAnswer(buttonTwoAnswer)) {
                    arrayValue++;
                    displayNext();
                    break;
                }
                else {
                    displayWinner();
                    break;
                }
            case R.id.buttonThree:
                if(checkAnswer(buttonThreeAnswer)) {
                    arrayValue++;
                    displayNext();
                    break;
                }
                else {
                    displayWinner();
                    break;
                }
            case R.id.buttonFour:
                if(checkAnswer(buttonFourAnswer)) {
                    arrayValue++;
                    displayNext();
                    break;
                }
                else {
                    displayWinner();
                    break;
                }
        }
    }
}

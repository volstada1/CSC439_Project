package de.fhdw.ergoholics.brainphaser.activities.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

import de.fhdw.ergoholics.brainphaser.R;

public class PlayerTwoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //define variables for the widgets
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonFour;
    private TextView playerTurnText;
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
    boolean playerOneBoolean = true;

    //define the SharedPreferences object
    private SharedPreferences savedValues;

    //define instance variables that should be saved
    private String buttonOneString = "";
    private String buttonTwoString = "";
    private String buttonThreeString = "";
    private String buttonFourString = "";
    private String playerTurnTextString = "";
    private String questionTextString = "";
    private Boolean pOneBoolean;
    private int currentArrayValue = 0;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        //get references to the widgets
        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonFour = (Button) findViewById(R.id.buttonFour);
        playerTurnText = (TextView) findViewById(R.id.playerTurnTextView);
        questionText = (TextView) findViewById(R.id.questionText);
        startButton = (Button) findViewById(R.id.startButton);
        titleText = (TextView) findViewById(R.id.title);

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
        editor.putString("playerTurnTextString", playerTurnText.getText().toString());
        editor.putString("questionTextString", questionText.getText().toString());
        editor.putBoolean("pOneBoolean", playerOneBoolean);
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
        playerTurnTextString = savedValues.getString("playerTurnTextString", "");
        questionTextString = savedValues.getString("questionTextString", "");
        pOneBoolean = savedValues.getBoolean("pOneBoolean", false);
        currentArrayValue = savedValues.getInt("currentArrayValue", 0);

        //set the instance variables on their widgets
        buttonOne.setText(buttonOneString);
        buttonTwo.setText(buttonTwoString);
        buttonThree.setText(buttonThreeString);
        buttonFour.setText(buttonFourString);
        playerTurnText.setText(playerTurnTextString);
        questionText.setText(questionTextString);
        arrayValue = currentArrayValue;
        playerOneBoolean = pOneBoolean;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:
                displayStart();
                break;
            case R.id.buttonOne:
                if(checkAnswer(buttonOneAnswer))
                    break;
                arrayValue++;
                displayNext();
                break;
            case R.id.buttonTwo:
                if(checkAnswer(buttonTwoAnswer))
                    break;
                arrayValue++;
                displayNext();
                break;
            case R.id.buttonThree:
                if(checkAnswer(buttonThreeAnswer))
                    break;
                arrayValue++;
                displayNext();
                break;
            case R.id.buttonFour:
                if(checkAnswer(buttonFourAnswer))
                    break;
                arrayValue++;
                displayNext();
                break;
        }
    }

    public void displayStart() {
        buttonOne.setVisibility(View.VISIBLE);
        buttonTwo.setVisibility(View.VISIBLE);
        buttonThree.setVisibility(View.VISIBLE);
        buttonFour.setVisibility(View.VISIBLE);
        playerTurnText.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.INVISIBLE);
        buttonOne.setText(buttonOneArray[0]);
        buttonTwo.setText(buttonTwoArray[0]);
        buttonThree.setText(buttonThreeArray[0]);
        buttonFour.setText(buttonFourArray[0]);
        questionText.setText(questionArray[0]);
        playerTurnText.setText("Player One's Turn");
        arrayValue = 0;
        playerOneBoolean = true;
        startButton.setText("New Game");
    }

    public void displayNext() {
        buttonOne.setText(buttonOneArray[arrayValue]);
        buttonTwo.setText(buttonTwoArray[arrayValue]);
        buttonThree.setText(buttonThreeArray[arrayValue]);
        buttonFour.setText(buttonFourArray[arrayValue]);
        questionText.setText(questionArray[arrayValue]);
        if(playerOneBoolean)
            playerTurnText.setText("Player One's Turn");
        else
            playerTurnText.setText("Player Two's Turn");
    }

    public boolean checkAnswer(boolean[] array) {
        if (!array[arrayValue]) {
            displayWinner(playerOneBoolean);
            return true;
        }
        playerOneBoolean = !playerOneBoolean;
        return false;
    }

    public void displayWinner(boolean player) {
        if (!playerOneBoolean) {
            playerTurnText.setText("Player One Wins!!!");
        }
        else
            playerTurnText.setText("Player Two Wins!!!");
        buttonOne.setVisibility(View.INVISIBLE);
        buttonTwo.setVisibility(View.INVISIBLE);
        buttonThree.setVisibility(View.INVISIBLE);
        buttonFour.setVisibility(View.INVISIBLE);
        playerTurnText.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.INVISIBLE);
        titleText.setVisibility(View.INVISIBLE);
    }


}

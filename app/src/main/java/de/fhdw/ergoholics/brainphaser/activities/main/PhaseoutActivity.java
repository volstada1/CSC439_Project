package de.fhdw.ergoholics.brainphaser.activities.main;

import de.fhdw.ergoholics.brainphaser.R;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PhaseoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // Widgets
    private Button startButton;
    private TextView questionText;
    private TextView gameMode;
    private TextView instruction;          ////////////////
    private TextView phaseCountText;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;

    // SharedPreferences object
    private SharedPreferences savedValues;

    // Instance variables
    private int currentArrayValue = 0;
    private String questionTextString = "";
    private String phaseCountTextString = "";
    private int currentPhase = 0;
    private int currentQ = 0;
    private int currentCorrectQ = 0;
    private String aString = "";
    private String bString = "";
    private String cString = "";
    private String dString = "";

    // Counter values
    int arrayValue = 0;
    int phaseCount = 0;
    int qCount = 0;
    int correctQCount = 0;
    boolean questionsExhausted = false;

    //define questions and answers in separate arrays - will replace with xml parser methods
    String[] questionArray = new String[]{"Which president is on the United States 1,000 dollar bill?",
            "What building is found on the back of the United States 100 dollar bill?",
            "What year was the two dollar bill last printed in the United States?",
            "What is the spanish word for money?",
            "What is the official currency of Equador?",
            "How much does a United States dollar bill weigh?"};
    String[] buttonOneArray = new String[]{"Grover Cleveland", "Lincoln Memorial", "2000", "peso", "Pound", "1.5 Grams"};
    String[] buttonTwoArray = new String[]{"Abraham Lincoln", "Independence Hall", "1998", "dinero", "Euro", "1 Gram"};
    String[] buttonThreeArray = new String[]{"Thomas Jefferson", "White House", "2003", "dinner", "Peso", "2 Grams"};
    String[] buttonFourArray = new String[]{"Jimmy Carter", "Twin Towers", "1996", "deeniro", "United States Dollar", "0.5 Grams"};
    boolean[] buttonOneAnswer = new boolean[]{true, false, false, false, false, false};
    boolean[] buttonTwoAnswer = new boolean[]{false, true, false, true, false, true};
    boolean[] buttonThreeAnswer = new boolean[]{false, false, true, false, false, false};
    boolean[] buttonFourAnswer = new boolean[]{false, false, false, false, true, false};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phaseout);

        gameMode = (TextView) findViewById(R.id.phaseoutMode);
        startButton = (Button) findViewById(R.id.startButton);
        questionText = (TextView) findViewById(R.id.questionText);
        phaseCountText = (TextView) findViewById(R.id.phaseCountTextView);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);

        instruction = (TextView) findViewById(R.id.instruction);          ////////////////

        startButton.setOnClickListener(this);
        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putInt("currentArrayValue", arrayValue);
        editor.putString("questionTextString", questionText.getText().toString());

        editor.putString("phaseCountTextString", phaseCountText.getText().toString());
        editor.putInt("currentPhase", phaseCount);
        editor.putInt("currentQ", qCount);
        editor.putInt("currentCorrectQ", correctQCount);

        editor.putString("aString", buttonA.getText().toString());
        editor.putString("bString", buttonB.getText().toString());
        editor.putString("cString", buttonC.getText().toString());
        editor.putString("dString", buttonD.getText().toString());
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Retrieve
        currentArrayValue = savedValues.getInt("currentArrayValue", 0);
        questionTextString = savedValues.getString("questionTextString", "");
        currentPhase = savedValues.getInt("currentPhase", 0);
        currentQ = savedValues.getInt("currentQ", 0);
        currentCorrectQ = savedValues.getInt("currentCorrectQ", 0);
        phaseCountTextString = savedValues.getString("phaseCountTextString", "");
        aString = savedValues.getString("aString", "");
        bString = savedValues.getString("bString", "");
        cString = savedValues.getString("cString", "");
        dString = savedValues.getString("dString", "");

        // Set
        arrayValue = currentArrayValue;
        questionText.setText(questionTextString);
        phaseCountText.setText(phaseCountTextString);
        phaseCount = currentPhase;
        qCount = currentQ;
        correctQCount = currentCorrectQ;
        buttonA.setText(aString);
        buttonB.setText(bString);
        buttonC.setText(cString);
        buttonD.setText(dString);
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
            // New game
            case R.id.startButton:
                displayStart();
                break;
            // Check answers; check for end of game or advance question
            case R.id.buttonA:
                checkAnswer(buttonOneAnswer);
                if (phasesExhausted() || questionsExhausted) {
                    endGame();
                    break;
                }
                arrayValue++;
                displayNext();
                break;
            case R.id.buttonB:
                checkAnswer(buttonTwoAnswer);
                if (phasesExhausted() || questionsExhausted) {
                    endGame();
                    break;
                }
                arrayValue++;
                displayNext();
                break;
            case R.id.buttonC:
                checkAnswer(buttonThreeAnswer);
                if (phasesExhausted() || questionsExhausted) {
                    endGame();
                    break;
                }
                arrayValue++;
                displayNext();
                break;
            case R.id.buttonD:
                checkAnswer(buttonFourAnswer);
                if (phasesExhausted() || questionsExhausted) {
                    endGame();
                    break;
                }
                arrayValue++;
                displayNext();
                break;
        }
    }

    // New game
    public void displayStart() {
        // Widget visibility updated
        gameMode.setVisibility(View.INVISIBLE);
        instruction.setVisibility(View.INVISIBLE);          ////////////////
        questionText.setVisibility(View.VISIBLE);
        phaseCountText.setVisibility(View.VISIBLE);
        buttonA.setVisibility(View.VISIBLE);
        buttonB.setVisibility(View.VISIBLE);
        buttonC.setVisibility(View.VISIBLE);
        buttonD.setVisibility(View.VISIBLE);

        // Widget text updated
        startButton.setText("New Game");
        questionText.setText(questionArray[0]);
        phaseCountText.setText("Phases Incurred: 0");
        buttonA.setText(buttonOneArray[0]);
        buttonB.setText(buttonTwoArray[0]);
        buttonC.setText(buttonThreeArray[0]);
        buttonD.setText(buttonFourArray[0]);

        //Counters set to default
        resetValues();
    }

    // Present next question
    public void displayNext() {
        // Question, answer options, and phase count display updated
        questionText.setText(questionArray[arrayValue]);
        phaseCountText.setText("Phases Incurred: " + phaseCount);
        buttonA.setText(buttonOneArray[arrayValue]);
        buttonB.setText(buttonTwoArray[arrayValue]);
        buttonC.setText(buttonThreeArray[arrayValue]);
        buttonD.setText(buttonFourArray[arrayValue]);

        // Question total updated
        qCount++;
    }

    // Update game counters
    public void checkAnswer(boolean[] array) {
        // Incorrect answer records phase, correct answer adds to correct total
        if (!array[arrayValue])
            phaseCount++;
        else
            correctQCount++;
        questionsExhausted(array);
    }

    // Check for third phase/strike
    public boolean phasesExhausted() {
        if (phaseCount == 3)
            return true;
        return false;
    }

    // Determine if current question is last in category
    public void questionsExhausted(boolean[] array) {
        if (array.length - 1 == arrayValue)
            questionsExhausted = true;
    }

    // Game over, phases or questions exhausted
    public void endGame() {
        // Widget visibility updated
        questionText.setVisibility(View.INVISIBLE);
        gameMode.setVisibility(View.INVISIBLE);
        buttonA.setVisibility(View.INVISIBLE);
        buttonB.setVisibility(View.INVISIBLE);
        buttonC.setVisibility(View.INVISIBLE);
        buttonD.setVisibility(View.INVISIBLE);

        // Display final game summary
        if (phaseCount == 1)
            phaseCountText.setText(phaseCount + " Phase!\n" + correctQCount + " of " + qCount + " correct");
        else
            phaseCountText.setText(phaseCount + " Phases!\n" + correctQCount + " of " + qCount + " correct");
        phaseCountText.setVisibility(View.VISIBLE);
        resetValues();
    }

    // Game over, all counters reset
    public void resetValues() {
        arrayValue = 0;
        qCount = 1;
        phaseCount = 0;
        correctQCount = 0;
        questionsExhausted = false;
    }
}
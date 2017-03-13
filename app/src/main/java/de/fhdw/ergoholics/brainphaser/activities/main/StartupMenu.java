package de.fhdw.ergoholics.brainphaser.activities.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import de.fhdw.ergoholics.brainphaser.R;

/**
 * Created by tope0_000 on 3/12/2017.
 */

public class StartupMenu extends AppCompatActivity implements View.OnClickListener {

    //define variables for the widgets
    private Button phaserAttack;
    private Button phaseout;
    private Button suddenPhase;
    private Button categories;

    //define the SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_menu);

        //get references to the widgets
        phaserAttack = (Button) findViewById(R.id.phaserAttack);
        phaseout = (Button) findViewById(R.id.phaseout);
        suddenPhase = (Button) findViewById(R.id.suddenPhase);
        categories = (Button) findViewById(R.id.categories);

        //set onClick listener for buttons
        phaserAttack.setOnClickListener(this);
        phaseout.setOnClickListener(this);
        suddenPhase.setOnClickListener(this);
        categories.setOnClickListener(this);

        //get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phaserAttack:
                break;
            case R.id.phaseout:
                break;
            case R.id.suddenPhase:
                startActivity(new Intent(StartupMenu.this, PlayerTwoActivity.class));
                break;
            case R.id.categories:
                startActivity(new Intent(StartupMenu.this, ProxyActivity.class));
                break;
        }
    }

}

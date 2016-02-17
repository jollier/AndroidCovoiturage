package com.fousduvolant.androidcovoiturage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jollier on 10/02/2016.
 */
public class RegisterActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        final Button annulerButton = (Button) findViewById(R.id.buttonAnnuler);
        annulerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(RegisterActivity.this, LoginDisplayActivity.class);
                //intent.putExtra(EXTRA_LOGIN, loginDisplay.getText().toString());
                //intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());
                //startActivity(intent);
                finish();
            }
        });
    }
}


package com.fousduvolant.androidcovoiturage;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jollier on 24/02/2016.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginDisplayActivity> {
    private LoginDisplayActivity activity;
    private EditText name_edit_text;
    private EditText password_edit_text;
    private Button button_connexion;
    public LoginTest() {
        super(LoginDisplayActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        activity = getActivity();
        name_edit_text= (EditText) activity.findViewById(R.id.email);
        password_edit_text = (EditText) activity.findViewById(R.id.password);
        button_connexion = (Button) activity.findViewById(R.id.button_Connexion);
    }


    public void testZonesLoginPasswordSaisis() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                name_edit_text.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("julien.ollier@berger-levrault.fr");
        getInstrumentation().waitForIdleSync();


        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                password_edit_text.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("toto");
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                button_connexion.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendKeySync(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_ENTER));
        getInstrumentation().waitForIdleSync();

        assertEquals("julien.ollier@berger-levrault.fr", name_edit_text.getText().toString());
    }
}

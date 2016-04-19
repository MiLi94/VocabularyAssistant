package com.limi.andorid.vocabularyassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private AppCompatButton login;
    private SharedPreferences sp;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sp=this.getSharedPreferences();
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        login = (AppCompatButton) findViewById(R.id.btn_login);
        TextView signupView = (TextView) findViewById(R.id.link_signup);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login_check();
            }
        });

        signupView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }


    public void login_check() {
//        Account a1 = new Account("abc@abc.com", "123456");

        if (check_validation()) {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();

            // TODO: Implement your own authentication logic here.

            new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            if (authenticate())
                                login_success();
                            else
                                login_fail();
                            progressDialog.dismiss();
                        }
                    }, 3000);

        } else {
            login_fail();

        }
    }

    private void login_success() {
        login.setEnabled(true);
        finish();
    }

    private void login_fail() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        login.setEnabled(true);
    }

    public boolean check_validation() {
        boolean validation;


        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        //check for email address

        if (TextUtils.isEmpty(emailText)) {
            email.setError(getString(R.string.error_field_required));
            validation = false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError(getString(R.string.error_invalid_email));
            validation = false;
        } else {
            email.setError(null);
            validation = true;
        }


        if (passwordText.isEmpty() || password.length() < 6 || password.length() > 16) {
            password.setError(getString(R.string.error_password_length));

            validation &= false;
        } else {
            password.setError(null);
            validation &= true;
        }


        return validation;

    }

    public boolean authenticate() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        return (emailText.equalsIgnoreCase("aaa@aaa.com") && passwordText.equals("123456"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        // Disable going back to the MainActivity
//        moveTaskToBack(true);
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Login Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.limi.andorid.vocabularyassistant/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Login Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.limi.andorid.vocabularyassistant/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
}

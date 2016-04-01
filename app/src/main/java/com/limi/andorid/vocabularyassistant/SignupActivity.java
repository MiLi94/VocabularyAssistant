package com.limi.andorid.vocabularyassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText nameView;
    private EditText email;
    private EditText password;
    private AppCompatButton signupButton;
    private SharedPreferences sp;
    private Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sp=this.getSharedPreferences();
        setContentView(R.layout.activity_sign_up);
        nameView = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        signupButton = (AppCompatButton) findViewById(R.id.btn_sign_up);
        TextView loginView = (TextView) findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                singup_check();
            }
        });

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void singup_check() {
//        Account a1 = new Account("abc@abc.com", "123456");

        if (check_validation()) {
            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Saving...");
            progressDialog.show();

            // TODO: Implement your own authentication logic here.
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            if (authenticate())
                                signup_success();
                            else
                                signup_fail();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        } else {
            signup_fail();

        }
    }

    private void signup_success() {
        Toast.makeText(getBaseContext(), "Sign up successed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
        finish();
    }

    private void signup_fail() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean check_validation() {
        boolean validation;

        String nameText=nameView.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (TextUtils.isEmpty(nameText)) {
            email.setError(getString(R.string.error_field_required));
            validation = false;

        } else if (validateName(nameText)) {
            email.setError(getString(R.string.error_invalid_name));
            validation = false;
        } else {
            email.setError(null);
            validation = true;
        }

        //check for email address

        if (TextUtils.isEmpty(emailText)) {
            email.setError(getString(R.string.error_field_required));
            validation = false;

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError(getString(R.string.error_invalid_email));
            validation = false;
        } else {
            email.setError(null);
            validation = true;
        }


        if (passwordText.isEmpty() || password.length() < 6 || password.length() > 16) {
            password.setError(getString(R.string.error_password_length));

            validation = false;
        } else {
            password.setError(null);
            validation = true;
        }


        return validation;

    }

    public boolean authenticate() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        return (emailText.equalsIgnoreCase("aaa@aaa.com")&&passwordText.equals("123456"));
    }


    public boolean validateName(String s){
        char name[]=s.toCharArray();
        for (char c:name ){
            if(!(c<='a'&&c>='Z')||(c==' ')) {
                return false;
            }
        }
        return  true;
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

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

}

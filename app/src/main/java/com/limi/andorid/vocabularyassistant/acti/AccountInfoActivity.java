package com.limi.andorid.vocabularyassistant.acti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.limi.andorid.vocabularyassistant.R;
import com.limi.andorid.vocabularyassistant.app.AppConfig;
import com.limi.andorid.vocabularyassistant.app.AppController;
import com.limi.andorid.vocabularyassistant.helper.MySQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountInfoActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private Button changeBtn;
    private MySQLiteHandler mySQLiteHandler;
    private AlertDialog dlg;
    private Button quit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        quit_btn = (Button) findViewById(R.id.back);
        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mySQLiteHandler = new MySQLiteHandler(getApplicationContext());
        HashMap<String, String> usersHashMap = mySQLiteHandler.getUserDetails();
        nameTextView = (TextView) findViewById(R.id.txtName);
        emailTextView = (TextView) findViewById(R.id.txtEmail);
        nameTextView.setText(usersHashMap.get("name"));
        emailTextView.setText(usersHashMap.get("email"));
        changeBtn = (Button) findViewById(R.id.changepsw);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(AccountInfoActivity.this);
                final View textEntryView = factory.inflate(R.layout.changepsw_dialog, null);
                dlg = new AlertDialog.Builder(AccountInfoActivity.this)
                        .setTitle("Change Password")
                        .setView(textEntryView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText pre_psw = (EditText) textEntryView.findViewById(R.id.prepwd);
                                EditText new_psw = (EditText) textEntryView.findViewById(R.id.newpwd);
                                String spre_psw = pre_psw.getText().toString();
                                String snew_psw = new_psw.getText().toString();
                                changePsw(spre_psw, snew_psw);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .create();
                dlg.show();

            }


        });


    }

    private void changePsw(final String prePsw, final String newPsw) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGE_PWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Change Password", "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Change successfully", Toast.LENGTH_LONG).show();
                        dlg.dismiss();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Change", "Login Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                String suser = String.valueOf(MainActivity.currentUserID);
                params.put("pre", prePsw);
                params.put("new", newPsw);
                params.put("userID", suser);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Change Psw");

    }
}

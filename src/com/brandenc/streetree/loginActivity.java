package com.brandenc.streetree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Branden on 6/11/13.
 */
public class loginActivity extends Activity{
    EditText txtUserName;
    EditText txtPassword;
    Button btnLogin;
    Button btnCancel;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtUserName=(EditText)this.findViewById(R.id.txtUname);
        txtPassword=(EditText)this.findViewById(R.id.txtPwd);
        btnLogin=(Button)this.findViewById(R.id.btnLogin);
        btnLogin=(Button)this.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if((txtUserName.getText().toString()).equals(txtPassword.getText().toString()))
                {
                    //successful go to the main activity
                    Toast.makeText(loginActivity.this, "Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(loginActivity.this, settingActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(loginActivity.this, "Invalid Login",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}

package com.example.hp.visamerchant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    public static final String REG_ID = "regId";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerb=(Button)findViewById(R.id.register);

        final EditText fullname=(EditText)findViewById(R.id.fullnameedit);

        final EditText accountNo=(EditText)findViewById(R.id.accountnameedit);

        final EditText contactNo=(EditText)findViewById(R.id.contactnameedit);

        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        SharedPreferences prefs=getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
     String regid=   prefs.getString("regId","");

        if (!TextUtils.isEmpty(regid)) {
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("regId", regid);
            startActivity(i);
            finish();
        }


        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(accountNo.getText().toString())&&!TextUtils.isEmpty(contactNo.getText().toString())&&!TextUtils.isEmpty(fullname.getText().toString()) )
                {

                    if (checkPlayServices()) {

                        // Register Device in GCM Server
                        Intent intent = new Intent(getApplicationContext(), RegistrationIntentService.class);
                       intent.putExtra("fullname",fullname.getText().toString());
                        intent.putExtra("accountno",accountNo.getText().toString());
                        startService(intent);
                    }

                }
                else
                {

                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_LONG).show();
                }


            }
        });








    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("TAG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

package com.example.hp.visamerchant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import org.w3c.dom.Text;

/**
 * Created by hp on 13-03-2016.
 */
public class HomeActivity extends AppCompatActivity {
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        String str = getIntent().getStringExtra("msg");

        // Get Email ID from Shared preferences
         prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String fullname = prefs.getString("fullname", "");
        String accno= prefs.getString("accountno", "");
        TextView name = (TextView) findViewById(R.id.hello);
        TextView account = (TextView) findViewById(R.id.mvisa);

        // Set Title
       /*TextView name = (TextView) findViewById(R.id.name);
        TextView account = (TextView) findViewById(R.id.acc);*/
        ImageView iv=(ImageView)findViewById(R.id.barcode);



        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
       // display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder("ID-Code123",
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.barcode);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }




        name.setText("Hello"+" "+fullname + " !");
        account.setText("Your mVisa Id :"+""+accno);
        // When Message sent from Broadcase Receiver is not empty
        if (str != null) {

            TextView notification=(TextView)findViewById(R.id.notification);
            // Set the message



            notification.setText(str);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
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

        if(id==R.id.logout)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}


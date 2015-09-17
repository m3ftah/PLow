package app.apps.plow.plow;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PrincipalActivity extends Activity {
    private static final String TAG = "LEDOnOff";

    // Button btnOn, btnOff;
    //ImageButton lamp_blue;
    //public static boolean lamp_blue_on = false;
    ImageButton lamp_yellow,microphone_btn;
    public static boolean lamp_yellow_on = false;
    static String on="allumer la lampe";
    static String off="éteindre la lampe";
    static final int check=1111;
    BluetoothRC blrc = null;
    Button b;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "In onCreate()");

        setContentView(R.layout.activity_principal);

        //lamp_blue = (ImageButton) findViewById(R.id.lamp_blue);
        lamp_yellow = (ImageButton) findViewById(R.id.lamp_yellow);
        microphone_btn=(ImageButton)findViewById(R.id.micro_btn);
        blrc = new BluetoothRC(this);

        lamp_yellow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                lamp_yellow_on = !lamp_yellow_on;
                if (lamp_yellow_on) {
                    sendData("3");
                } else {
                    sendData("2");

                }
                String str = reciveData();
                if (str.equals("2"))
                    lamp_yellow.setImageResource(R.drawable.lamp_off);
                else
                    lamp_yellow.setImageResource(R.drawable.lamp_yellow);

            }
        });
        microphone_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak up son");
                startActivityForResult(i,check);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        blrc.onStart();
        String str2 = reciveData();
        if (str2.equals("0")) {
            lamp_yellow_on = false;
            lamp_yellow.setImageResource(R.drawable.lamp_off);
        } else {
            lamp_yellow_on = true;
            lamp_yellow.setImageResource(R.drawable.lamp_yellow);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        blrc.onStop();
    }

    private void sendData(String message) {
        blrc.sendData(message);
    }

    private String reciveData() {
        return blrc.receiveData();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode==check && resultCode==RESULT_OK)
        {
            ArrayList<String> results= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //Toast.makeText(getApplicationContext(),results.get(0),Toast.LENGTH_SHORT).show();
            String str2 ="";
            if(on.contains(results.get(0)))
            {
                sendData("3");
                str2 = reciveData();
            }
            else if(off.contains(results.get(0)))

            {
                sendData("2");
                str2 = reciveData();
            }
            else Toast.makeText(getBaseContext(), "Commande Inexistante",Toast.LENGTH_SHORT).show();

            if (str2.equals("2")) {
                lamp_yellow_on = false;
                lamp_yellow.setImageResource(R.drawable.lamp_off);
            }
            else if (str2.equals("3")){
                lamp_yellow_on = true;
                lamp_yellow.setImageResource(R.drawable.lamp_yellow);
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
package app.plow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class PrincipalActivity extends Activity {
    private static final String TAG = "LEDOnOff";

    ImageButton lamp_yellow,microphone_btn;
    public static boolean lamp_yellow_on = false;
    static String on="allumer la lampe";
    static String off="éteindre la lampe";
    BluetoothRC blrc = null;
    GoogleVoice gvc = null;
    Button b;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "In onCreate()");

        setContentView(R.layout.activity_principal);

        lamp_yellow = (ImageButton) findViewById(R.id.lamp_yellow);
        microphone_btn=(ImageButton)findViewById(R.id.micro_btn);
        blrc = BluetoothRC.getInstance(this);
        gvc = new GoogleVoice(this);

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
                gvc.openMicrophone();

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
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GoogleVoice.check && resultCode==RESULT_OK){
            String result = gvc.onActivityForResult(data);
            String str2 ="";
            if(on.contains(result)){
                sendData("3");
                str2 = reciveData();
            }else
                if(off.contains(result)){
                sendData("2");
                str2 = reciveData();
            }else
                Toast.makeText(getBaseContext(), "Commande Inexistante",Toast.LENGTH_SHORT).show();

            if (str2.equals("2")){
                lamp_yellow_on = false;
                lamp_yellow.setImageResource(R.drawable.lamp_off);
            }
            else if (str2.equals("3")){
                lamp_yellow_on = true;
                lamp_yellow.setImageResource(R.drawable.lamp_yellow);
            }
        }

    }


}
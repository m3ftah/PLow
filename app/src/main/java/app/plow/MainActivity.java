package app.plow;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {

    Button btn_bluetooth;
    boolean enable;
    ImageView start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final BluetoothAdapter bluetooth=BluetoothAdapter.getDefaultAdapter();

        enable=bluetooth.isEnabled();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=(ImageView)findViewById(R.id.imageView1);
        btn_bluetooth=(Button)findViewById(R.id.btn_bluetooth);

        if(bluetooth.isEnabled())btn_bluetooth.setText("desactiver Bluetooth");
        else btn_bluetooth.setText("activer Bluetooth");

        btn_bluetooth.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(bluetooth==null)
                    Toast.makeText(getApplicationContext(),"pas de bluetooth",Toast.LENGTH_SHORT).show();
                else if (!bluetooth.isEnabled())
                {
                    bluetooth.enable();
                    enable=true;
                    btn_bluetooth.setText("desactiver Bluetooth");
                }
                else{ bluetooth.disable();
                    enable=false;
                    btn_bluetooth.setText("activer Bluetooth");}
                ;

            }
        });
        start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (bluetooth.isEnabled()) {
                    Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
                    startActivity(i);
                    finish();
                } else
                    Toast.makeText(getBaseContext(), "Veuillez activer Bluetooth avant", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!enable)btn_bluetooth.setText("activer Bluetooth");
        else btn_bluetooth.setText("desactiver Bluetooth");
    }
}

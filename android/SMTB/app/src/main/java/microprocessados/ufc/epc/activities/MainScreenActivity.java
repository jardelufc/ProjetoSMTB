package microprocessados.ufc.epc.activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import microprocessados.ufc.epc.R;
import microprocessados.ufc.epc.controllers.BluetoothController;
import microprocessados.ufc.epc.controllers.ConnectThread;
import microprocessados.ufc.epc.controllers.FlowController;

public class MainScreenActivity extends AppCompatActivity {

    private FlowController flowController;
    private BluetoothController bluetoothController;
    private FrameLayout card;
    private Handler connectHandler;
    private BluetoothDevice device;
    private TextView infoCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        flowController = new FlowController();


//        flowController.setAquaConsume(10);
        device = (BluetoothDevice) getIntent().getParcelableExtra("Device");

        card = (FrameLayout) findViewById(R.id.card);
        infoCard = (TextView) findViewById(R.id.infoCard);
//        card.setBackgroundColor(Color.parseColor("red"));
        try {
            setHandler();
            bluetoothController = new BluetoothController(connectHandler);
            bluetoothController.connect(device);
        }catch (BluetoothController.NoBluetoothFoundException e1){
            Toast.makeText(this,"O dispositivo não possui Bluetooth. Seu Pobre!",Toast.LENGTH_SHORT).show();
        }catch (BluetoothController.BluetoothDisabledException e2){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    public void gerarNotificacao(){

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent (this, SplashScreenActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Texto Teste");
        builder.setContentTitle("Título");
        builder.setContentText("Descrição");
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo));

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
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

    public void changeCardColor(int color){
        card.setBackgroundColor(color);
    }

    @SuppressLint("HandlerLeak")
    public void setHandler(){
        this.connectHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Houve uma falha ao conectar com o dispositivo", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),
                                "Conectado ao dispositivo com sucesso", Toast.LENGTH_SHORT)
                                .show();
                        break;

                    case 2:
                        // Muito Gelada: 0 a 4
                        // Gelada: 5 a 7
                        // Fria: 8 a 12
                        // Temp. Adega: 13 a 15

                        //O que recebe é o msg.obj
                        //byte[] dado = (byte[]) msg.obj;
                        //int cont = 0;

                        byte[] dado = (byte[]) msg.obj;
                        String temperatura = new String(dado);

                        try{
                            if((Float.parseFloat(temperatura) < 25)){
                                changeCardColor(Color.parseColor("#8BC34A"));
                                //Toast.makeText(getApplicationContext(),
                                //        "Sua cerveja está na temperatura ideal", Toast.LENGTH_SHORT)
                                //        .show();
                            }else{
                                changeCardColor(Color.parseColor("#e27a11"));
                            }
                        }
                        catch (Exception e){

                        }

                        //cont++;
                        infoCard.setText(temperatura);
//                        Toast.makeText(getApplicationContext(),
//                                "RECEBIDO DADO" + msg.obj, Toast.LENGTH_SHORT)
//                                .show();
                        break;
                }
            }
        };
    }



}



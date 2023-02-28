package com.example.whatsapp_notification_spy;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whatsapp_notification_spy.services.NotificationListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView main_LST_msgs;
    private ArrayList<MyMessage> listsArrayList;
    private Message_Adapter message_adapter;


    private BroadcastReceiver myBRD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String sender = intent.getStringExtra("sender");
            String message = intent.getStringExtra("message");
            String time = intent.getStringExtra("time");

            if(message.contains("messages from")){

            }else {
                //last_msg.setText("Sender: " + sender + " Message: " + message);
                MyMessage myMessage = new MyMessage(sender, message, time);
                listsArrayList.add(myMessage);
                listsArrayList.sort(Comparator.comparing(MyMessage::getContact_name));
                message_adapter.notifyDataSetChanged();
            }

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            //service is enabled do something
        } else {
            //service is not enabled try to enabled by calling...
            startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        //last_msg = findViewById(R.id.last_msg);
        main_LST_msgs = findViewById(R.id.main_LST_msgs);
        listsArrayList = new ArrayList<>();
        message_adapter = new Message_Adapter(this, listsArrayList);

        main_LST_msgs.setLayoutManager(new GridLayoutManager(this, 1));
        main_LST_msgs.setHasFixedSize(true);
        main_LST_msgs.setItemAnimator(new DefaultItemAnimator());
        main_LST_msgs.setAdapter(message_adapter);


        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("ptttt", "" + wifiInfo.getPasspointFqdn());
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(NotificationListener.NOTIFICATION_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(myBRD, new IntentFilter(NotificationListener.RADIO_STAION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBRD);

    }
}


package com.example.whatsapp_notification_spy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import com.example.whatsapp_notification_spy.services.NotificationListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView main_LST_msgs;
    private MyDB myDB;
    private ArrayList<MyMessage> listsArrayList;
    private Message_Adapter message_adapter;

    private MaterialToolbar panel_Toolbar_Top;



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
                saveToSP(myMessage);
                listsArrayList.sort(Comparator.comparing(MyMessage::getContact_name));
                message_adapter.notifyItemInserted(listsArrayList.size());
            }

        }
    };

    private void saveToSP(MyMessage message) {
        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);

        myDB.getMessages().add(message);

//        Collections.sort(myDB.getRecords(), new SortByScore());

        String json = new Gson().toJson(myDB);
        MSPV3.getMe().putString("MY_DB", json);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {
            //service is enabled do something
            IntentFilter intentFilter = new IntentFilter(NotificationListener.NOTIFICATION_SERVICE);
            LocalBroadcastManager.getInstance(this).registerReceiver(myBRD, new IntentFilter(NotificationListener.RADIO_STAION));

        } else {
            //service is not enabled try to enabled by calling...
            startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        findViews();
        initButton();

        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);
        listsArrayList = new ArrayList<>(myDB.getMessages());
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

    private void initButton() {
        panel_Toolbar_Top.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                removeAllMessages();
                return false;
            }
        });
    }

    private void removeAllMessages() {
        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);

        myDB.getMessages().clear();
        listsArrayList.clear();
        message_adapter.notifyDataSetChanged();


//        Collections.sort(myDB.getRecords(), new SortByScore());

        String json = new Gson().toJson(myDB);
        MSPV3.getMe().putString("MY_DB", json);
    }

    private void findViews() {
        main_LST_msgs = findViewById(R.id.main_LST_msgs);
        panel_Toolbar_Top = findViewById(R.id.panel_Toolbar_Top);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBRD);

    }
}


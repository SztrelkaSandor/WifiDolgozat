package com.example.wifidolgozat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    Button btnOn,btnOff,btnInfo;
    TextView textView;
    WifiManager wifiManager;
    WifiInfo wifiInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnOn.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                textView.setText("Nincs Jogosultsági állapot");
                Intent panel=new Intent(Settings.Panel.ACTION_WIFI);
                startActivityForResult(panel,0);
                return;
            }
        wifiManager.setWifiEnabled(true);
        textView.setText("Wifi Bekapcsolva");
        });
        btnOff.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                textView.setText("Nincs Jogosultsági állapot");
                Intent panel=new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                startActivityForResult(panel,0);
                return;
            }
            wifiManager.setWifiEnabled(false);
            textView.setText("Wifi kikapcsolva");
        });
        btnInfo.setOnClickListener(v->{
            ConnectivityManager connectivityManager=
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo.isConnected()){
            int ip_int=wifiInfo.getIpAddress();
            String ip= Formatter.formatIpAddress(ip_int);
            textView.setText("IP cím: "+ip);
            }else {
                textView.setText("nem csatlakoztál a wifi hálozathoz");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if (wifiManager.getWifiState()==WifiManager.WIFI_STATE_DISABLING ||
                    wifiManager.getWifiState()==WifiManager.WIFI_STATE_DISABLED ){
                textView.setText("Wifi kikapcsolva");
            }else if (wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLING ||
                    wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED ){
                textView.setText("Wifi bekapcsolva");
            }
        }
    }

    private void init() {
        btnOn=findViewById(R.id.WifiBe_btn);
        btnOff=findViewById(R.id.WifiKi_btn);
        btnInfo=findViewById(R.id.WifiInfo_btn);
        textView=findViewById(R.id.text_info);
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo=wifiManager.getConnectionInfo();
    }
}
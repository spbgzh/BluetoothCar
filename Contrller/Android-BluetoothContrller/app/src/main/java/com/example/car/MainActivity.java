package com.example.car;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.DialogInterface.OnClickListener;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    final String deep_purple = "#9933cc";
    final String normal_purple ="#6600cc";
    private Context ac_context = this;
    //布尔
    private boolean matched = true;
    //基本组件
    private ListView blue_tooth_list;
    private ArrayAdapter<String> arrayAdapter = null;
    private Button skip;
    private Button scan;
    private ListView matched_bluetooth_list;
    private ArrayAdapter<String> arrayAdapter2 = null;
    private Button cancel;

    //蓝牙组件
    private BluetoothServerSocket btServerSocket = null;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice car = null;
    private BluetoothSocket btSocket = null;
    static OutputStream outputStream = null;
    //广播
    BroadcastReceiver broadcastReceiver;
    // 创建Rfcomm通道的UUDI码
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final String NAME = "BLUE_TOOTH_CAR";
    boolean isConnected = false;

    //结果组
    //已配对
    Set<BluetoothDevice> pairedDevices;
    List<String> boundString = new ArrayList<>();
    //新的
    List<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    List<String> devicesString = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //基本组件
        skip = (Button) findViewById(R.id.skip);
        blue_tooth_list = (ListView) findViewById(R.id.list_bluetooth);
        scan = (Button) findViewById(R.id.scan);
        matched_bluetooth_list = (ListView)findViewById(R.id.match_bluetooth_list);
        cancel = (Button)findViewById(R.id.cancel_blue_tooth);
        //蓝牙组件
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //广播
        broadcastReceiver= new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    if(deviceName != null && !devicesString.contains(deviceName + " " + deviceHardwareAddress)) {
                        bluetoothDevices.add(device);
                        devicesString.add(deviceName + " " + deviceHardwareAddress);
                        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_list_item_1, devicesString);
                        blue_tooth_list.setAdapter(arrayAdapter);
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);
        //跳转页面
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip();
            }
        });
        //蓝牙扫描
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });
        //关闭蓝牙
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                }
            }
        });
        //按钮效果
        skip.setOnTouchListener(this);
        scan.setOnTouchListener(this);
        cancel.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.scan:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    scan.setBackgroundColor(Color.parseColor(deep_purple));
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    scan.setBackgroundColor(Color.parseColor(normal_purple));
                }
                break;
            case R.id.cancel_blue_tooth:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    cancel.setBackgroundColor(Color.parseColor(deep_purple));
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    cancel.setBackgroundColor(Color.parseColor(normal_purple));
                }
                break;
            case R.id.skip:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    skip.setBackgroundColor(Color.parseColor(deep_purple));
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    skip.setBackgroundColor(Color.parseColor(normal_purple));
                }
                break;
        }
        return false;
    }

    /**
     * Redirection to controller
     */
    public void skip(){
        Log.i("Redirection","Now redirecting to control page");
        Intent intent = new Intent(this,ControlActivity.class);
        this.startActivity(intent);
    }

    /**
     * Scanning nearby bluetooth
     */
    public void scan(){
        Log.i("Scanner","Scanner running and trying to find nearby bluetooth");
        //判断设备是否存在蓝牙
        if(bluetoothAdapter == null){
            Log.e("Bluetooth","No bluetooth on device");
            return;
        }
        //如果蓝牙没有打开，那么打开蓝牙
        //需要CONNECT权限
        if(!bluetoothAdapter.isEnabled()){
            Log.i("Bluetooth","Bluetooth not opened, now try to open it");
            if(bluetoothAdapter.enable()){
                Log.i("Bluetooth","Success");
            }else {
                Log.i("Bluetooth","Failed");
            }
        }else {
            Log.i("Bluetooth", "Bluetooth is opened");
        }
        //已配对
        if(matched) {
            pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice bd : pairedDevices) {
                    String a = bd.getName();
                    String b = bd.getAddress();
                    String r = a + " " + b;
                    boundString.add(r);
                }
                arrayAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1, boundString);
                matched_bluetooth_list.setAdapter(arrayAdapter2);
            }
            matched = false;
        }
        //发现新设备
        bluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onResume() {
        super.onResume();
        blue_tooth_list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bluetoothAdapter.cancelDiscovery();
                car = bluetoothAdapter.getRemoteDevice(bluetoothDevices.get(i).getAddress());
                AlertDialog alertDialog = new AlertDialog.Builder(ac_context).setTitle("Bluetooth connection")
                        .setMessage("Do you want to connect to this device")
                        .setPositiveButton("Yes", new OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //可被检测
                                Intent discoverableIntent =
                                        new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
                                startActivity(discoverableIntent);
                                connect_car();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                another_test();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        try {
            btSocket.close();
        }catch (IOException E){
            Log.e("Socket","Can't close old connection");
        }
        super.onDestroy();
    }

    private void another_test(){
        new AlertDialog.Builder(ac_context).setTitle("Uuid").setMessage(Arrays.toString(car.getUuids())).create().show();
    }

    private void connect_car(){
        new ConnectThread().start();
    }
    private class ConnectThread extends Thread{
        public ConnectThread(){
            BluetoothSocket tmp = null;
            try{
                tmp = car.createRfcommSocketToServiceRecord(MY_UUID);
            }catch (IOException i){
                Log.e("Socket_err","Failed to create a socket");
            }
            btSocket = tmp;
        }

        public void run(){
            while(true) {
                try {
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
                    btSocket.connect();
                } catch (IOException connectException) {

                }
                if(btSocket!=null&&btSocket.isConnected()){
                    break;
                }
                try {
                    Thread.sleep(200);
                }catch (InterruptedException i){
                    Log.e("Sleeping","Can't sleep");
                }
            }
            try {
                outputStream = btSocket.getOutputStream();
            }catch (IOException e){
                Log.e("Socket_err","Could not open stream");
            }
        }
    }
    /*private class ConnectAsServerThread extends Thread{
        private BluetoothDevice device;
        private OutputStream outputStream;
        private final BluetoothServerSocket serverSocket;
        public ConnectAsServerThread(){
            device = car;
            BluetoothServerSocket tmp = null;
            try{
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Blue_car",MY_UUID);
            }catch (IOException i){
                Log.e("Connection","Can't connect to car as Server");
            }
            btServerSocket = tmp;
            serverSocket = tmp;
        }

        @Override
        public void run() {
            BluetoothSocket s;
            while(true){
                try{
                    s = serverSocket.accept();
                }catch (IOException e){
                    Log.e("Connection","Failed to open a socket");
                    break;
                }
                if(s!=null){
                    try {
                        outputStream = s.getOutputStream();
                    }catch (IOException e){
                        Log.e("Connection","Can't get stream");
                    }
                    break;
                }
            }
        }
        public void cancel(){
            try{
                serverSocket.close();
            }catch (IOException i){
                Log.e("Connection","Failed to close connection as server");
            }
        }
    }*/
}
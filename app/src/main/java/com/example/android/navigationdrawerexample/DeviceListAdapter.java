package com.example.android.navigationdrawerexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;

/**
 * Created by Navin on 2/3/2015.
 */
public class DeviceListAdapter extends ArrayAdapter<Device> {

    private final Context context;
    private final ArrayList<Device> devices;
    MqttClient client;

    public DeviceListAdapter(Context context, ArrayList<Device> devices) {
        super(context, android.R.layout.simple_list_item_activated_1,devices);
        this.context = context;
        this.devices = devices;
    }

    @Override
    public View getView(int position , View convertView, ViewGroup parent) {

       /* Device item = getItem(position);
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.device_item,parent,false);
        }

         TextView devName = (TextView) convertView.findViewById(R.id.name);
         Switch devSwitch = (Switch) convertView.findViewById(R.id.switch1);
         devName.setText(item.deviceName);
        devSwitch.setChecked(item.deviceSwitch);*/
        ViewHolder viewHolder =null;
        final Device item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.device_item,null);
            viewHolder = new ViewHolder();
            viewHolder.devName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.devSwitch = (Switch) convertView.findViewById(R.id.switch1);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.devName.setText(item.deviceName);
        viewHolder.devSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        MemoryPersistence memoryPersistence = new MemoryPersistence();
                                        client = new MqttClient("tcp://iot.eclipse.org:1883", "HomeAutomation", memoryPersistence);
                                        client.connect();
                                        Log.d("MQTT", "Client is Connected");
                                        MqttMessage message = new MqttMessage();
                                        message.setPayload(item.deviceMessageOn.getBytes());
                                        client.publish(item.deviceTopic, message);
                                        Log.d("MQTT", "Message Published");
                                        client.disconnect();
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                            Toast.makeText(getContext(), "Message \"" + item.deviceMessageOn + "\" is Published", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        MemoryPersistence memoryPersistence = new MemoryPersistence();
                                        client = new MqttClient("tcp://iot.eclipse.org:1883", "HomeAutomation", memoryPersistence);
                                        client.connect();
                                        Log.d("MQTT", "Client is Connected");
                                        MqttMessage message = new MqttMessage();
                                        message.setPayload(item.deviceMessageOff.getBytes());
                                        client.publish(item.deviceTopic, message);
                                        Log.d("MQTT", "Message Published");
                                        client.disconnect();
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                            Toast.makeText(getContext(), "Message \"" + item.deviceMessageOff + "\" is Published", Toast.LENGTH_SHORT).show();
                        }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView devName;
        Switch devSwitch;
    }
}

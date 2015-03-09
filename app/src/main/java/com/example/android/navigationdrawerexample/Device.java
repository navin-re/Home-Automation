package com.example.android.navigationdrawerexample;

import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Navin on 1/3/2015.
 */
public class Device {
    public String deviceTopic;
    public String deviceName;
    public String deviceMessageOn;
    public String deviceMessageOff;
    public final boolean deviceSwitch;

    public Device(String topic, String name, String messageOn, String messageOff, boolean deviceSwitch) {
       this.deviceTopic = topic;
        this.deviceName = name;
        this.deviceMessageOn = messageOn;
        this.deviceMessageOff = messageOff;
        this.deviceSwitch = deviceSwitch;
    }

}

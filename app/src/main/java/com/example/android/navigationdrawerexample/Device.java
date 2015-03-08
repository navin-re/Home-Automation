package com.example.android.navigationdrawerexample;

import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Navin on 1/3/2015.
 */
public class Device {
    public String deviceName;
    public String deviceMessage;
    public final boolean deviceSwitch;

    public Device(String name,String message, boolean deviceSwitch) {
        this.deviceName = name;
        this.deviceMessage = message;
        this.deviceSwitch = deviceSwitch;
    }

}

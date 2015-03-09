package com.example.android.navigationdrawerexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navin on 1/3/2015.
 */
public class DeviceList extends ListFragment {

    public  static final  String ARG_ROOM_POSTION = "room_number";
    public  static final  String ARG_DEVICE_POSTION = "device_number";
    public  static final  String ARG_DEVICE_NAME = "device_name";
    public  static final  String ARG_DEVICE_TOPIC = "device_topic";
    public  static final  String ARG_DEVICE_MESSAGE_ON = "device_message_on";
    public  static final  String ARG_DEVICE_MESSAGE_OFF = "device_name_off";
    public static  final String MODIFY_DEVICE = "modify_device";
    public static ArrayList<Device> devices;
    public static int posRoom;
   public static ArrayAdapter arrayAdapter;

/*    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        int position = getArguments().getInt(ARG_DEVICE_POSTION);
        devices = MainActivity.rooms.getRoom(position);
        setListAdapter(new DeviceListAdapter(getActivity(),devices));

    }   */

    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);
        posRoom = getArguments().getInt(ARG_ROOM_POSTION);
        devices = MainActivity.rooms.getRoom(posRoom);
        final ArrayAdapter adapter =new DeviceListAdapter(getActivity(), devices);
        this.arrayAdapter = adapter;
        setListAdapter(adapter);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragment options = new optionsDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(ARG_DEVICE_POSTION,position);
                options.setArguments(bundle);
                options.show(getFragmentManager(),"Options");
                return false;
            }
        });
    }

    @Override
    public  void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static class optionsDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceBundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final int devPos = getArguments().getInt(ARG_DEVICE_POSTION);
            builder.setTitle("Options").setItems(R.array.Options,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   if(which == 0) {
                       DialogFragment modify = new AddDeviceFragment();
                       Bundle args = new Bundle();
                       args.putInt(ARG_DEVICE_POSTION,devPos);
                       args.putBoolean(MODIFY_DEVICE,true);
                       args.putCharSequence(ARG_DEVICE_TOPIC,devices.get(devPos).deviceTopic);
                       args.putCharSequence(ARG_DEVICE_NAME,devices.get(devPos).deviceName);
                       args.putCharSequence(ARG_DEVICE_MESSAGE_ON,devices.get(devPos).deviceMessageOn);
                       args.putCharSequence(ARG_DEVICE_MESSAGE_OFF,devices.get(devPos).deviceMessageOff);
                       modify.setArguments(args);
                       modify.show(getFragmentManager(),"Modify");
                   }
                 else {
                       MainActivity.rooms.removeDevice(devPos);
                       arrayAdapter.notifyDataSetChanged();
                   }
                }
            });
            return builder.create();
        }
    }

}

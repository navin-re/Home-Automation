package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by navin on 9/2/15.
 */
public class AddDeviceFragment extends DialogFragment  {

    public  static final  String ARG_DEVICE_POSTION = "device_number";
    public  static final  String ARG_DEVICE_NAME = "device_name";
    public  static final  String ARG_DEVICE_MESSAGE = "device_message";
    public static  final String MODIFY_DEVICE = "modify_device";
    public  static final  String ARG_DEVICE_TOPIC = "device_topic";
    public  static final  String ARG_DEVICE_MESSAGE_ON = "device_message_on";
    public  static final  String ARG_DEVICE_MESSAGE_OFF = "device_name_off";

	public interface AddDeviceListener {
		public void onAddDevicePostive(DialogFragment dialog,boolean modify,int position);
		public void onAddDeviceNegative(DialogFragment dialog);
	}

	public AddDeviceFragment() {
	}

	AddDeviceListener mListener;
    boolean modify = false;
    int devPostion;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (AddDeviceListener) activity;
            devPostion = getArguments().getInt(ARG_DEVICE_POSTION);
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
							     + " must implement Add Device Listener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        modify = getArguments().getBoolean(MODIFY_DEVICE);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater =getActivity().getLayoutInflater();
        View thisView =inflater.inflate(R.layout.device_add_layout, null);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
        if(modify) {
            TextView devTopic = (TextView) thisView.findViewById(R.id.topic);
            TextView devName = (TextView) thisView.findViewById(R.id.dev_name);
            TextView devMessageOn = (TextView) thisView.findViewById(R.id.dev_message_on);
            TextView devMessageOff = (TextView) thisView.findViewById(R.id.dev_message_off);
            devTopic.setText(getArguments().getCharSequence(ARG_DEVICE_TOPIC));
            devName.setText(getArguments().getCharSequence(ARG_DEVICE_NAME));
            devMessageOn.setText(getArguments().getCharSequence(ARG_DEVICE_MESSAGE_ON));
            devMessageOff.setText(getArguments().getCharSequence(ARG_DEVICE_MESSAGE_OFF));
        }
		builder.setView(thisView)
				// Add action buttons
			.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onAddDevicePostive(AddDeviceFragment.this, modify, devPostion);
            }
        })
			.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					mListener.onAddDeviceNegative(AddDeviceFragment.this);
				}
			});
		return builder.create();
	}





}

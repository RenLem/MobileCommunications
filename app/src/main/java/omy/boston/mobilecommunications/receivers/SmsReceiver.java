package omy.boston.mobilecommunications.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            String messageOne = "";

            for (int i = 0; i<pdus.length; i++){
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if (i == 0){
                    messageOne += messages[i].getOriginatingAddress() + ": ";
                }
                messageOne += messages[i].getMessageBody().toString();
            }


            Toast.makeText(context, messageOne, Toast.LENGTH_LONG).show();
        }
    }
}

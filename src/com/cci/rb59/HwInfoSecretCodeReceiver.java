package com.cci.rb59;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HwInfoSecretCodeReceiver extends BroadcastReceiver {
    private final static String TAG = "HWVersionSecretCodeReceiver";
    private final static String HW_INFO_SECRET_CODE = "*#888#*";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "action :" + action);
        Log.e("joseph", "action :" + action);
        if (action.equals("com.cei.customer.secretcode")) {
            Log.e("joseph", "intent.getStringExtra(customer.secretcode):" + intent.getStringExtra("customer.secretcode"));
            if (intent.getStringExtra("customer.secretcode") != null
                    && intent.getStringExtra("customer.secretcode").equals(HW_INFO_SECRET_CODE)) {
                Intent i = new Intent(context, HwInfoListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}

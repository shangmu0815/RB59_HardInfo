package com.cci.rb59;

import android.app.ListActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HwInfoListActivity extends ListActivity {
    private static final String TAG = "HwInfoListActivity";
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    HashMap<String, String> lcdNameInfoHashMap = new HashMap<String,String>();
    private final String[] titleArr = new String[] {
            "lcd name",
            "ctp driver",
            "main camera",
            "sub camera",
            "flash driver",
            "EMMC name",
            "GSensor name",
            "MSensor name",
            "ALSPS name",
            "Gyro name",
            "wifi name",
            "bt name",
            "FM name",
            "OTP"
   };
   private final String[] subTitleArr1 = new String[] {
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
            " | ",
    };
   private final String[] subTitleArr2 = new String[] {
           readLcdNameFilePathInfo(LCD_NAME_INFO),
           readFilePathInfo(CTP_DRIVER_TEST_INFO),
           readCameraFilePathInfo(MAIN_CAMERA_INFO),
           readCameraFilePathInfo(SUB_CAMERA_INFO),
           "XXXX",
           readEMMCNameFilePathInfo(EMMC_NAME_INFO),
           "Jonny",
           "Not found",
           "STK3311",
           "Not spuuprt Gyro",
           "WCN3610",
           "WCN3610",
           "WCN3610",
           "001"
   };

   private static final String LCD_NAME_INFO = "/sys/class/graphics/fb0/msm_fb_panel_info";
   private static final String CTP_DRIVER_TEST_INFO = "/sys/bus/i2c/devices/5-0015/elan_ktf/fw_version";//
   private static final String CTP_DRIVER_INFO = "/sys/bus/i2c/devices/5-0010/elan_ktf2k/fw_version";
   private static final String MAIN_CAMERA_INFO = "/sys/class/video4linux/video1/device/CheckCameraID";
   private static final String SUB_CAMERA_INFO = "/sys/class/video4linux/video2/device/CheckCameraID";
   private static final String FLASH_DRIVER_INFO = "XXXX";
   private static final String EMMC_NAME_INFO = "/sys/class/mmc_host/mmc0/mmc0:0001/manfid";
   private static final String GSENSOR_NAME_INFO = "/sys/class/mmc_host/mmc0/mmc0:0001/manfid";

   @Override
   public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.e("joseph", "HwInfoListActivity onCreate");
            list = getData();
            MyAdapter adapter = new MyAdapter(this);
            setListAdapter(adapter);
   }
   private List<Map<String,Object>> getData() {
       List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
       for(int i=0;i<titleArr.length;i++) {
                HashMap<String,Object> item = new HashMap<String,Object>();
                item.put("hardwarename", titleArr[i]);
                item.put("separate", subTitleArr1[i]);
                item.put("hardwareinfo", subTitleArr2[i]);
                list2.add(item);
       }
       return list2;
   }
   public final class MyView {
       public TextView hardwarename;
       public TextView separate;
       public TextView hardwareinfo;
   }
   
   public class MyAdapter extends BaseAdapter {
       private LayoutInflater inflater;
       public MyAdapter(Context context) {
                inflater = LayoutInflater.from(context);
       }

       @Override
       public int getCount() {
                // TODO Auto-generated method stub
                Log.e("joseph", "--list.size():"+list.size());
                return list.size();

       }

       @Override
       public Object getItem(int position) {
                // TODO Auto-generated method stub
                return null;
       }

       @Override
       public long getItemId(int position) {
                // TODO Auto-generated method stub
                return 0;
       }

       @Override
       public View getView(final int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                MyView myviews = null;
                myviews = new MyView();
                Log.e("joseph", "getView-position:"+position);
                convertView = inflater.inflate(R.layout.hw_info, null);
                myviews.hardwarename = (TextView) convertView.findViewById(R.id.textView1);
                myviews.separate = (TextView) convertView.findViewById(R.id.textView2);
                myviews.hardwareinfo = (TextView) convertView.findViewById(R.id.textView3);
//                myviews.bt = (Button) convertView.findViewById(R.id.button1);
//                myviews.img = (ImageView) convertView.findViewById(R.id.imageView1);

                myviews.hardwarename.setText((String) list.get(position).get("hardwarename"));
                myviews.separate.setText((String) list.get(position).get("separate"));
                myviews.hardwareinfo.setText((String) list.get(position).get("hardwareinfo"));
//                myviews.img.setImageResource(imgArr[position]);
               
//                myviews.bt.setOnClickListener(new View.OnClickListener() {
//                         @Override
//                         public void onClick(View v) {
//                                  Toast.makeText(Basic.this, titleArr[position], Toast.LENGTH_SHORT).show();
//                         }
//                });
                return convertView;
       }
   }
   public String readFilePathInfo(String filePath) {
       try {
           BufferedReader br = new BufferedReader(new InputStreamReader(
                   new FileInputStream(new File(filePath))));
           String filePathInfo = br.readLine();
           br.close();
           return filePathInfo;
       } catch (Exception e) {
           e.printStackTrace();
           return "";
       }
   }
   
   public String readCameraFilePathInfo(String filePath) {
           String camerafilePathInfo = readFilePathInfo(filePath);
           Log.e("joseph", "readCamerFilePathInfo-333-indexOf:"+camerafilePathInfo.indexOf("ID: "));
           Log.e("joseph", "readCamerFilePathInfo-substring(4):"+camerafilePathInfo.substring(4));
           String[] afterSplitCamerafilePathInfo;
           Log.e("joseph", "readCamerFilePathInfo-0-camerafilePathInfo:"+camerafilePathInfo);
           //filePathInfo.replaceAll("ID: ","");
           afterSplitCamerafilePathInfo = camerafilePathInfo.split(": ");
           Log.e("joseph", "readCamerFilePathInfo-6-afterSplitCamerafilePathInfo[1]:"+afterSplitCamerafilePathInfo[1]);
           return afterSplitCamerafilePathInfo[1];
   }

   public String readEMMCNameFilePathInfo(String filePath) {
       String filePathInfo = readFilePathInfo(filePath);
        if (filePathInfo.equals("0x000015")) {
           filePathInfo = "SAMSUNG (KMQ72000SM-B316)";
        } else if (filePathInfo.equals("0x000090")) {
           filePathInfo = "HYNIX (H9TQ64A8GTMCUR-KUM)";
        } else if (filePathInfo.equals("0x000070")) {
           filePathInfo = "KSI (08EMCP08-EL3BS100-R09)";
        } else {
           filePathInfo = "";
        }
       return filePathInfo;
   }
   
   public String readFilePathInfoAll(String filePath) {
       try {
           String catFilePath = filePath;
           StringBuffer output = new StringBuffer();
           File file = new File(catFilePath);
           BufferedReader br = new BufferedReader(new InputStreamReader(
                   new FileInputStream(file)));
           String str;
           while ((str = br.readLine()) != null)
               output.append(str + "\n");
           br.close();
           return output.toString();
       } catch (Exception e) {
           Log.e(TAG,"readFilePathInfoAll exception:" + e.toString());
           return "";
       }
   }
   
    public String readLcdNameFilePathInfo(String filePath) {
        Log.e("joseph", "readLcdNameFilePathInfo-filePath:"+filePath);
        String readLcdNameFilePathInfo = "";
        String catFilePath = filePath;
        File file = new File(catFilePath);
        BufferedReader br = null;
        String lcdNameInfo;
        String[] afterSplitLcdNameInfo;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    file)));
            while ((lcdNameInfo = br.readLine()) != null) {
                if (lcdNameInfo.contains("=")) {
                    afterSplitLcdNameInfo = lcdNameInfo.split("=");
                    Log.e("joseph", "readLcdNameFilePathInfo-afterSplitLcdNameInfo[0]:"+afterSplitLcdNameInfo[0]+"-afterSplitLcdNameInfo[1]:"+afterSplitLcdNameInfo[1]);
                    lcdNameInfoHashMap.put(afterSplitLcdNameInfo[0], afterSplitLcdNameInfo[1]);
                }
            }
            if (lcdNameInfoHashMap.containsKey("panel_name")) {
                readLcdNameFilePathInfo = (String) lcdNameInfoHashMap.get("panel_name");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG,"readLcdNameFilePathInfo exception:" + e.toString());
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Log.e("joseph", "-0-lcdNameInfoHashMap.size():"+lcdNameInfoHashMap.size());
            lcdNameInfoHashMap.clear();
            Log.e("joseph", "-1-lcdNameInfoHashMap.size():"+lcdNameInfoHashMap.size());
        }
        Log.e("joseph", "readLCDNameFilePathInfo-:" + readLcdNameFilePathInfo
                + "--");
        return readLcdNameFilePathInfo;
    }
   
}

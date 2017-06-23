package org.apache.cordova.diyi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.ionicframework.demo1367114.SigningActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by admin on 2016/11/25.
 */

public class DiyiPlugin extends CordovaPlugin {

  private CallbackContext callbackContext;
  private int QMrequestCode=1888;
  @Override
  public boolean execute(String action, JSONArray args,
                         CallbackContext callbackContext) throws JSONException {

//    if (this.cordova.getActivity().isFinishing())
//      return true;

    if (action.equals("diyiciFangFaMing")) {
      // 获取传递返回的参数
      this.callbackContext = callbackContext;

      //获取参数
      String out_trade_no = args.getString(0);
      Toast.makeText(cordova.getActivity(), out_trade_no+"====", Toast.LENGTH_SHORT).show();
      Intent intent=new Intent(cordova.getActivity().getApplicationContext(), SigningActivity.class);
      cordova.startActivityForResult(this,intent,QMrequestCode);
//      cordova.getActivity().startActivity(intent);
      Log.e("======", "startActivityForResult: ");
      callbackContext.success("人生如梦");
      return true;//表示是否返回参数

    }

    return false;

//    return super.execute(action, args, callbackContext);
  }

//  @Override
//  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//    Log.e("======", "requestCode: "+requestCode+ "  resultCode"+resultCode);
//        if (requestCode==QMrequestCode){
//            Log.e("TAG", "onActivityResult: "+intent.getStringExtra("qm_path") );
//        }
//    }

    public void test(){
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    Log.e("------", "onActivityResult: requestCode="+requestCode);
  }

  @Override
  public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
    super.onRestoreStateForActivityResult(state, callbackContext);
    Log.e("-------", "onRestoreStateForActivityResult: ");
  }
}

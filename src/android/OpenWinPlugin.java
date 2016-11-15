package com.ktb.plugin;

import android.app.Activity;
import android.content.Intent;

import com.ktb.activity.WebViewActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：必顾健康
 * 类描述：
 * 创建人：HONGYU.LIU
 * 创建时间：2016/6/21 15:52
 * 修改人：HONGYU.LIU
 * 修改时间：2016/6/21 15:52
 * 修改备注：
 */
public class OpenWinPlugin extends CordovaPlugin {
    Activity context;
    private static final String redirect_Action = "openWin";//页面跳转
    private static final String closeWin = "closeWin";//关闭当前页面
    private static final String closeToWin = "closeToWin";//关闭当前页面，并跳转回指定页面

    public static final int webRequestCode = 1001;
    public static final int webResultCode = 1000;


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        context = cordova.getActivity();
    }

    @Override //插件的执行方法，
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (context == null)
            context = cordova.getActivity();
        if (action.equals(redirect_Action)) {//页面跳转
            return redirect(args, callbackContext);
        } else if (action.equals(closeWin)) {//关闭当前页面
            return colseWin(args, callbackContext);
        } else if (action.equals(closeToWin)) {//关闭当前页面，并跳转回指定页面
            return closeToWin(args, callbackContext);
        } else {
            return false;
        }
    }


    private Boolean redirect(JSONArray args, final CallbackContext callbackContext) {
        Intent intent;
        if (args != null && !(args.toString()).equals("[]")) {
            JSONObject argJson = null;//
            try {
                argJson = args.getJSONObject(0);
                if (isNotNull(argJson)) {
                    intent = new Intent(context, WebViewActivity.class);
                    if (argJson.has("pageParam")) {
                        String pageParam = argJson.getString("pageParam");
                        intent.putExtra("pageParam", pageParam);
                    }
                    String url = argJson.getString("url");
                    intent.putExtra("redirect_url", url);
                    cordova.startActivityForResult(this, intent, webRequestCode);
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    private Boolean colseWin(JSONArray args, final CallbackContext callbackContext) {
        cordova.getActivity().finish();
        return true;
    }


    private Boolean closeToWin(JSONArray args, final CallbackContext callbackContext) {
        if (isNotNull(args) && !(args.toString()).equals("[]")) {
            JSONObject argJson = null;//请求地址
            try {
                argJson = args.getJSONObject(0);
                if (isNotNull(argJson)) {
                    String pageParam = "";
                    if (argJson.has("url")) {
                        pageParam = argJson.getString("url");
                    }
                    Intent intent = cordova.getActivity().getIntent();
                    intent.putExtra("url", pageParam);
                    cordova.getActivity().setResult(webResultCode, intent);
                    cordova.getActivity().finish();
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isSynch(String action) {
        return true;
    }


    public static boolean isNull(Object o) {
        if (o != null && o.equals("null"))
            o = null;
        return o == null ? true : false;
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == webRequestCode && resultCode == webResultCode) {
            String toWebUrl = intent.getExtras().getString("url");
            String webUrl = ((WebViewActivity) cordova.getActivity()).webUrl;
            if (!toWebUrl.equals(webUrl))
                cordova.getActivity().finish();
        }
    }
}

package com.zerochip.remotehomemonitor;

import com.zerochip.util.GetNetWorkState;
import com.zerochip.util.SimpleTextToSpeech;
import com.zerochip.util.WorkContext;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

public class LoginActivity extends Activity
{
    private WorkContext mWorkContext = null;
    private float mTtsSpeechRate = 1.0f; // 朗读速率
    private Handler mHandler = new Handler();
    private static final String TAG = "com.zerochip.remotehomemonitor.LoginActivity";
    public static final boolean DEBUG = false;

    public boolean CheckNeedSetup()
    {
        if (mWorkContext.mPreferences != null)
        {
            if (DEBUG)
                Log.e(TAG,
                        "mWorkContext.mPreferences != null + "
                                + mWorkContext.mPreferences
                                        .getBoolean(
                                                mWorkContext.configNeedRunSetupWizardString,
                                                true));
            return mWorkContext.mPreferences.getBoolean(
                    mWorkContext.configNeedRunSetupWizardString, true);
        }
        else
        {
            if (DEBUG)
                Log.e(TAG, "mWorkContext.mPreferences null error");
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mWorkContext = new WorkContext();
        mWorkContext.mContext = this;
        mWorkContext.mActivity = this;
        // TODO Auto-generated method stub
        mWorkContext.mHandler = mHandler;
        mWorkContext.mResources = getResources();
        mWorkContext.mGetNetWorkState = new GetNetWorkState(
                mWorkContext.mContext);
        mWorkContext.mSimpleTextToSpeech = new SimpleTextToSpeech(
                mWorkContext.mContext, mTtsSpeechRate);
        mWorkContext.mPreferences = getSharedPreferences(
                mWorkContext.configFileNameString, Activity.MODE_WORLD_READABLE
                        + Activity.MODE_WORLD_WRITEABLE);
        setContentView(R.layout.activity_login);
        boolean NeedSetupUserInfo = CheckNeedSetup();
        // 如果是第一次打开这个APK。就提示用户设置用户名，密码。增加帐号等；
        if (NeedSetupUserInfo)
        {
            this.startActivity(new Intent(this.getApplicationContext(),
                    SetupWizardActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
}

package com.snowboyreact.hotword;

/**
 * Created by j.cardwell on 2/1/18.
 * Special thanks to https://blog.fossasia.org/hotword-detection-in-susi-android-app-using-snowboy/
 */

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import android.os.Handler;
import android.os.Message;

import ai.kitt.snowboy.MsgEnum;
import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.audio.RecordingThread;
import ai.kitt.snowboy.AppResCopy;

public class EMSnowboyModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext mReactContext;
    private RecordingThread recordingThread;
    private int preVolume = -1;

    private static final String TAG = "Snowboy";

    public EMSnowboyModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Hotword";
    }

    @ReactMethod
    public void initHotword() {
        if (ActivityCompat.checkSelfPermission(mReactContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mReactContext,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            AppResCopy.copyResFromAssetsToSD(mReactContext);

            recordingThread = new RecordingThread(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    MsgEnum message = MsgEnum.getMsgEnum(msg.what);
                    String messageText = (String) msg.obj;

                    Log.v(TAG, "Handle Message");


                    switch(message) {
                        case MSG_ACTIVE:
                            //HOTWORD DETECTED. NOW DO WHATEVER YOU WANT TO DO HERE
                            Log.v(TAG, "MSG_ACTIVE");
                            break;
                        case MSG_INFO:
                            Log.v(TAG, "MSG_INFO");
                            break;
                        case MSG_VAD_SPEECH:
                            Log.v(TAG, "MSG_VAD_SPEECH");
                            break;
                        case MSG_VAD_NOSPEECH:
                            Log.v(TAG, "MSG_VAD_NOSPEECH");
                            break;
                        case MSG_ERROR:
                            Log.v(TAG, "MSG_ERROR");
                            break;
                        default:
                            super.handleMessage(msg);
                            Log.v(TAG, "DEFAULT");
                            break;
                    }
                }
            }, new AudioDataSaver());

        }

    }

    @ReactMethod
    public void start() {
        Log.v(TAG, "Start recording");

        if(recordingThread !=null) {
            recordingThread.startRecording();
        }
    }

    @ReactMethod
    public void stop() {
        Log.v(TAG, "Stop recording");

        if(recordingThread !=null){
            recordingThread.stopRecording();
        }
    }
}
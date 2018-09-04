package com.sz.ljs.common.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by liujs
 * describe content:音频播报
 * Created by Date  2018/7/24 0024.
 */

public class MediaPlayerUtils {
    private static int mVolumeValue = 0;


    //TODO 设置音量值
    public static void setRingVolume(boolean isMax,Context mContext) {
        AudioManager mAudioManager = (AudioManager) mContext.
                getSystemService(Context.AUDIO_SERVICE);
        if (isMax) {
            mVolumeValue = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 80, 0);
        } else {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeValue, 0);
        }
    }

}

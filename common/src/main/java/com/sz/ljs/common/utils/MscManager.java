package com.sz.ljs.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by liujs
 * describe content:创建科大讯飞语音
 * Created by Date  2018/7/24 0024.
 * 合成语音发言人列表:
 * 小燕	青年  女声	中英文 （普通话）	xiaoyan
 * 小宇	青年  男声	中英文 （普通话）	xiaoyu
 * 凯瑟琳	青年 女声	英文	catherine
 * 亨利	青年 男声	英文	henry
 * 玛丽	青年  女声	英文	vimary
 * 小研	青年  女声	中英文 (普通话）	vixy
 * 小琪	青年 女声	中英文 （普通话）	xiaoqi
 * 小峰	青年  男声	中英文 （普通话）	vixf
 * 小梅	青年 女声	中英文 （粤语）	     xiaomei
 * 小莉	青年  女声	中英文 （台湾普通话）vixl
 * 晓琳	青年 女声	中英文 （台湾普通话）xiaolin
 * 小蓉	青年 女声	汉语  （四川话）	 xiaorong
 * 小芸	青年 女声	汉语 （东北话）	    vixyun
 * 小倩	青年 女声	汉语 （东北话）		xiaoqian
 * 小坤	青年 男声	汉语 （河南话）	 	xiaokun
 * 小强	青年 男声	汉语 （湖南话）	 	xiaoqiang
 * 小莹	青年 女声	汉语 （陕西话）	    vixying
 * 小新	童年 男声	汉语 （普通话）		xiaoxin
 * 楠楠	童年  女声	汉语 （普通话）	    nannan
 * 老孙	老年 男声	汉语  （普通话）	vils
 */

public class MscManager {
    private Context mContext;
    private String TAG = "MscManager";
    private SpeechSynthesizer speechSynthesizer;
    private static MscManager instance;
    private String[] voice_Spokesman_List = {"xiaoyan", "xiaoyu", "catherine", "henry", "vimary", "xiaoqi", "xiaomei", "xiaolin", "xiaorong", "xiaoqian", "xiaokun",
            "xiaoqiang", "vixying", "xiaoxin", "nannan", "vils"};

    public static MscManager getInstance() {
        if (instance == null) {
            synchronized (MscManager.class) {
                if (null == instance) {
                    instance = new MscManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context, int SpokesmanNumber) {
        this.mContext = context;
        initTts(SpokesmanNumber);//默认小燕
    }

    private void initTts(int SpokesmanNumber) {
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, null);
        if (SpokesmanNumber < voice_Spokesman_List.length) {
            //数组中有的发言人
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voice_Spokesman_List[SpokesmanNumber]);//设置发音人
        } else {
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        }
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }


    /**
     * 普通语音
     *
     * @param content 播放内容
     */
    public void speech(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        int code = speechSynthesizer.startSpeaking(content, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(mContext, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

}

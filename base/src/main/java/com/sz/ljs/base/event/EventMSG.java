package com.sz.ljs.base.event;

/**
 * Created by 刘劲松 on 2018/8/8.
 * 视图更新事件类
 */

public class EventMSG {
    public EventID  what;
    public String message;
    public Object obj;
    public int     are1;
    public int     are2;

    public EventMSG(EventID what){
        this.what = what;
    }
}

package com.sz.ljs.common.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.orhanobut.logger.Logger;
import com.sz.ljs.common.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by 刘劲松 on 2018/8/8.
 */

public class HDateGsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if(json == null){
            return null;
        }else{
            try {
                String str = json.getAsString();
                if(!StringUtils.isEmpty(str)) {
                    String time = str.substring(6, str.length() - 2);
                    Long value = Long.valueOf(time);
                    Date date = new Date(value);
                    return date;
                }else{
                    return null;
                }
            }catch (Exception e){
                Logger.e(e.getMessage());
                return null;
            }
        }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        String value = "";
        if(src != null){
            value = String.valueOf(src.getTime());
        }
        Logger.i("serialize() " + value);
        return new JsonPrimitive(value);
    }

    public static Gson createGson(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new HDateGsonAdapter())
                .create();
        return gson;
    }
}

package com.adl.axp.analytics.schedular.domain;

import com.adl.axp.analytics.schedular.domain.RestInfo;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestCallDTO extends RestInfo implements Cloneable {
    private String bodyValue="";
    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}

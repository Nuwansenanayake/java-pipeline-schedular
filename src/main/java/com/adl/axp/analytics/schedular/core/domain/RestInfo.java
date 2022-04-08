package com.adl.axp.analytics.schedular.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestInfo  {
    private String id;
    private String name;
    private String uri;
    private String method;
    private String filepath;
    private String cron;
    private int deltadays=0;
    private int runonceafterinmins=0;
    private boolean enabled=true;
    private int indexpartitionval=1;

    @Override
    public String toString(){
        return new com.google.gson.Gson().toJson(this);
    }

}

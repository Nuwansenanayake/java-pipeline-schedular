package com.adl.axp.analytics.schedular.application.ports;

import com.adl.axp.analytics.schedular.domain.RestCallDTO;

public interface RestService {
    public void scheduleDynamically(RestCallDTO restInfo);
}

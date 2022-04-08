package com.adl.axp.analytics.schedular.application.ports;

import com.adl.axp.analytics.schedular.domain.RestCallDTO;
import org.slf4j.Logger;

public interface LogUtil {
    void logAppDebugLogs(Logger logger, RestCallDTO restCallDTO, String msg);

    void logInfoDefault(Logger logger, RestCallDTO restCallDTO);

    void logError(Logger logger, RestCallDTO restCallDTO, String msg);

    void logInfo(Logger logger, RestCallDTO restCallDTO, String msg);
}

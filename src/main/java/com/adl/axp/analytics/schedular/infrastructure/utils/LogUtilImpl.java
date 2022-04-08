package com.adl.axp.analytics.schedular.infrastructure.utils;


import com.adl.axp.analytics.schedular.core.ports.LogUtil;
import com.adl.axp.analytics.schedular.core.domain.RestCallDTO;
import com.adl.axp.analytics.schedular.infrastructure.config.AppPropertiesConfig;
import com.adl.axp.analytics.schedular.infrastructure.config.LogPropertyConfig;
//import com.adl.axp.analytics.schedular.application.ports.RestService;
import com.adl.axp.analytics.schedular.core.ports.TemplateService;
import com.adl.axp.analytics.schedular.core.ports.TextFileService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogUtilImpl implements LogUtil {

    @Autowired
    AppPropertiesConfig appPropertiesConfig;
    @Autowired
    LogPropertyConfig logPropertyConfig;
    @Autowired
    TextFileService textFileService;
    @Autowired
    TemplateService templateService;


    @Override
    public void logAppDebugLogs(Logger logger, RestCallDTO restCallDTO, String msg) {
        //these logs will be printed only if the property is set to true
        if (appPropertiesConfig.isLogappdebuglogs())
            logInfo(logger, restCallDTO, msg);
    }

    @Override
    public void logInfoDefault(Logger logger, RestCallDTO restCallDTO) {
        this.logInfo(logger, restCallDTO, restCallDTO.getName());
    }

    @Override
    public void logError(Logger logger, RestCallDTO restCallDTO, String msg) {
        logger.error(getLogMessage(restCallDTO, msg));
    }

    @Override
    public void logInfo(Logger logger, RestCallDTO restCallDTO, String msg) {
        logger.info(getLogMessage(restCallDTO, msg));
    }

    private String getLogMessage(RestCallDTO restCallDTO, String msg) {
        return new StringBuilder()
                .append("Job")
                .append(" ")
                .append(restCallDTO.getId())
                .append(" : ")
                .append(msg)
                .toString();
    }
}

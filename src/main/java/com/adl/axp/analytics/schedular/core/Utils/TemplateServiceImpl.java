package com.adl.axp.analytics.schedular.infrastructure.Utils;

import com.adl.axp.analytics.schedular.core.ports.LogUtil;
import com.adl.axp.analytics.schedular.core.ports.TemplateService;
import com.adl.axp.analytics.schedular.core.domain.RestCallDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public  class TemplateServiceImpl implements TemplateService {

    Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);
    @Autowired
    LogUtil logUtil;

    @Override
    public String transformTemplateString(RestCallDTO restCallDTO, String template, String search, String replace){
        logUtil.logAppDebugLogs(logger,restCallDTO,"Template In: "+template);
        template=template.replace(search,replace);
        logUtil.logAppDebugLogs(logger,restCallDTO,"Template Out: "+template);
        return template;
    }

    @Override
    public String transformTemplate(RestCallDTO restCallDTO, String template) {

        template = transformTemplateDates(restCallDTO,  template, restCallDTO.getDeltadays(),restCallDTO.getIndexpartitionval());
        return template;
    }

    @Override
    public String transformTemplateDates(RestCallDTO restCallDTO, String template, int deltadays, int indexPartitionVal) {
        LocalDateTime date= LocalDateTime.now().minusDays(deltadays);
        logUtil.logAppDebugLogs(logger,restCallDTO,"Template Setting Time: "+date.toString());

        template=template.replace("{dd}",String.format("%02d",date.getDayOfMonth()))
                .replace("{mm}",String.format("%02d",date.getMonthValue()))
                .replace("{yyyy}",String.format("%02d",date.getYear()));

        template = fillPartionTemplate(restCallDTO, template, date);
        return template;
    }

    private String fillPartionTemplate(RestCallDTO restCallDTO, String template, LocalDateTime date) {
        int partitionNumber = date.getDayOfMonth()/restCallDTO.getIndexpartitionval();
        template=template.replace("{partition}",String.format("%02d",partitionNumber+1));
        return template;
    }
}


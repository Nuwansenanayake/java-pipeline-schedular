package com.adl.axp.analytics.schedular.core.usecases;

import com.adl.axp.analytics.schedular.core.ports.RestService;
import com.adl.axp.analytics.schedular.core.domain.RestCallDTO;
import com.adl.axp.analytics.schedular.core.domain.RestInfo;
import com.adl.axp.analytics.schedular.infrastructure.repository.TextFileServiceImpl;
import com.adl.axp.analytics.schedular.infrastructure.repository.YamlFileServiceImpl;
import com.adl.axp.analytics.schedular.core.ports.LogUtil;
import com.adl.axp.analytics.schedular.core.ports.TemplateService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {
    static Logger LOGGER = LoggerFactory.getLogger(SchedulerServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private YamlFileServiceImpl yamlFileService;
    @Autowired
    private RestService restService;
    @Autowired
    private TextFileServiceImpl textFileService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    LogUtil logUtil;



//    @Autowired
//    private ExecuteScheduledRestCallUsercase executeScheduledRestCallUsercase;


    public ScheduledTaskRegistrar addTriggerTasks(ScheduledTaskRegistrar scheduledTaskRegistrar)  {

        //Jobs with cron configuration will go through this pipeline
        List<RestInfo> restInfoList =yamlFileService.readYaml();
        if ( restInfoList==null)
            return scheduledTaskRegistrar;

        restInfoList.stream()
                .filter(restInfo -> restInfo.isEnabled())
                .filter((restInfo -> restInfo.getCron()!=null))
                .map(r -> modelMapper.map(r, RestCallDTO.class) )
                .map(this::getFileContent)
                .forEach( restInfo -> addRestTasks(restInfo,scheduledTaskRegistrar));
        return scheduledTaskRegistrar;
    }

    private RestCallDTO getFileContent(RestCallDTO restCallDTO) {
        if (!(restCallDTO.getFilepath()==null || restCallDTO.getFilepath().isEmpty()))
            restCallDTO.setBodyValue(textFileService.readfile(restCallDTO.getFilepath()));
        return restCallDTO;
    }

    private void addRestTasks(RestCallDTO restInfo, ScheduledTaskRegistrar scheduledTaskRegistrar) {
        LOGGER.info("Job "+restInfo.getId()+" : Schedular Task Added : " +restInfo.getName());

        scheduledTaskRegistrar.addTriggerTask(
                () -> scheduleDynamically(restInfo)
                , new CronTrigger( restInfo.getCron()));
    }

    private void scheduleDynamically(RestCallDTO restInfo){
        try {
//            executeScheduledRestCallUsercase.execute(transformTemplates(restInfo));
            restService.scheduleDynamically(transformTemplates(restInfo));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private RestCallDTO transformTemplates(RestCallDTO restCallDTO) throws CloneNotSupportedException {
        LOGGER.info("Job "+restCallDTO.getId()+" : Schedular Task Executed : " +restCallDTO.getName()+" - "+restCallDTO.getUri());

        RestCallDTO restCallDTOClone = (RestCallDTO) restCallDTO.clone();

        restCallDTOClone.setUri(templateService.transformTemplate(restCallDTO,restCallDTO.getUri() ));
        restCallDTOClone.setBodyValue(templateService.transformTemplate(restCallDTO,restCallDTO.getBodyValue()));

        logUtil.logAppDebugLogs(LOGGER,restCallDTO,"Template Out: "+restCallDTO.toString());

        return restCallDTOClone;
    }

}

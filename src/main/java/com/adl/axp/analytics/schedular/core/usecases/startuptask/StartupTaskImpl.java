package com.adl.axp.analytics.schedular.core.usecases;

import com.adl.axp.analytics.schedular.core.domain.RestCallDTO;
import com.adl.axp.analytics.schedular.core.domain.RestInfo;
import com.adl.axp.analytics.schedular.core.ports.LogUtil;
import com.adl.axp.analytics.schedular.core.ports.TemplateService;
import com.adl.axp.analytics.schedular.infrastructure.gateway.RestServiceImpl;
import com.adl.axp.analytics.schedular.infrastructure.job.SystemStartRestTask;
import com.adl.axp.analytics.schedular.infrastructure.repository.TextFileServiceImpl;
import com.adl.axp.analytics.schedular.infrastructure.repository.YamlFileServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class StartupTaskImpl implements StartupTask {
    static Logger LOGGER = LoggerFactory.getLogger(SystemStartRestTask.class);
    @Autowired
    LogUtil logUtil;

    @Autowired
    private TemplateService templateService;
    @Autowired
    private TextFileServiceImpl textFileService;
    @Autowired
    private YamlFileServiceImpl yamlFileService;
    @Autowired
    private RestServiceImpl restService;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void configureTasks() {

        List<RestInfo> restInfoList =yamlFileService.readYaml();

        if ( restInfoList==null)
            return;

        //Jobs with no cron configuration will go through this pipeline
        List<RestCallDTO> restCallDTOS  =restInfoList.stream()
                .filter(restInfo -> restInfo.isEnabled())
                .filter((restInfo -> restInfo.getCron()==null))
                .map(r -> modelMapper.map(r,RestCallDTO.class) )
                .map(this::addRestBodyContent)
                .map(this::uriApplyTemplate)
                .collect(Collectors.toList());

        restCallDTOS.stream()
                //filter run after given time
                .filter(restInfo -> 0==restInfo.getRunonceafterinmins())
                .forEach(restService::scheduleDynamically);

        restCallDTOS.stream()
                //filter run immidiately
                .filter(restInfo -> 0!=restInfo.getRunonceafterinmins())
                .forEach(this::createDelayedTask);

    }

    private void createDelayedTask(RestCallDTO restInfo) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                restService.scheduleDynamically(restInfo);
            }
        };
        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(timerTask, restInfo.getRunonceafterinmins(), TimeUnit.MINUTES);
//        LOGGER.info("Job "+restInfo.getId()+" : System Delayed Startup Task Added : " +restInfo.getName());
        logUtil.logInfo(LOGGER,restInfo,"System Delayed Startup Task Added : " +restInfo.getName());
    }

    private RestCallDTO uriApplyTemplate(RestCallDTO restInfo) {
        restInfo.setUri(templateService.transformTemplate(restInfo,restInfo.getUri() ));
        return restInfo;
    }

    private RestCallDTO addRestBodyContent(RestCallDTO restInfo) {

        if(restInfo.getFilepath()!=null && !restInfo.getFilepath().isEmpty())
            restInfo.setBodyValue(prepareRestBodyFromFile(restInfo,restInfo.getFilepath(),restInfo.getDeltadays()));
        else
            restInfo .setBodyValue("");
        return restInfo;
    }

    private String prepareRestBodyFromFile(RestCallDTO restInfo,String filePath, int deltadays){
        String fileString = textFileService.readfile(filePath);
        return templateService.transformTemplate(restInfo,fileString);
    }

}

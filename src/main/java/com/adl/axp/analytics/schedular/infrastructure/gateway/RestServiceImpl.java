package com.adl.axp.analytics.schedular.adaptor;

import com.adl.axp.analytics.schedular.application.ports.RestService;
import com.adl.axp.analytics.schedular.domain.RestCallDTO;
import com.adl.axp.analytics.schedular.schedular2.Utils.LogUtil;
import com.adl.axp.analytics.schedular.schedular2.config.AppPropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestServiceImpl implements RestService {
    private static Logger LOGGER = LoggerFactory.getLogger(RestServiceImpl.class);

    @Autowired
    private WebClient webClientConfigured;
    @Autowired
    private AppPropertiesConfig appPropertiesConfig;
    @Autowired
    LogUtil logUtil;

    @Override
    public void scheduleDynamically(RestCallDTO restInfo){
        logUtil.logInfoDefault(LOGGER,restInfo);

        String uri = restInfo.getUri();
        if("post".equalsIgnoreCase(restInfo.getMethod())) {
            makePostRequest(restInfo);
        }
        else if("get".equalsIgnoreCase(restInfo.getMethod())) {
            makeGetRequest(restInfo);
        }
        else if("delete".equalsIgnoreCase(restInfo.getMethod())) {
            makeDeleteRequest(restInfo);
        }
        else if("put".equalsIgnoreCase(restInfo.getMethod())) {
            makePutRequest(restInfo);
        }
    }

    public void makePostRequest(RestCallDTO dto ) {
        WebClient.RequestHeadersSpec<?> responseSpec = webClientConfigured
                .post()
                .uri(dto.getUri())
                .bodyValue(dto.getBodyValue()) ;

        makeRequest(dto,responseSpec);
    }

    public void makePutRequest(RestCallDTO dto) {
        WebClient.RequestHeadersSpec<?> responseSpec = webClientConfigured
                .put()
                .uri(dto.getUri())
                .bodyValue(dto.getBodyValue());

        makeRequest(dto,responseSpec);
    }

    public void makeGetRequest(RestCallDTO dto) {
        WebClient.RequestHeadersSpec<?> responseSpec = webClientConfigured
                .get()
                .uri(dto.getUri());
        makeRequest(dto,responseSpec);

    }

    public void makeDeleteRequest(RestCallDTO dto) {
        WebClient.RequestHeadersSpec<?> responseSpec = webClientConfigured
                .delete()
                .uri(dto.getUri());
        makeRequest(dto,responseSpec);

    }

    public void makeRequest(RestCallDTO dto,WebClient.RequestHeadersSpec<?> responseSpec){

        logUtil.logAppDebugLogs(LOGGER, dto,dto.toString());

        //if property set to true will not do a true rest call
        if( appPropertiesConfig.isMockrestcalls() ) {
            logUtil.logInfo(LOGGER,dto,"Rest call mocked. Set the property to false to make the real call");
            return;
        }

        Mono<String> response1 = responseSpec.exchangeToMono(response -> {

            if (response.statusCode().isError()) {
                logUtil.logInfo(LOGGER,dto,"Error "+response.statusCode().getReasonPhrase());
            }
            return response.bodyToMono(String.class);

        });
        logUtil.logInfo(LOGGER,dto,response1.block());
    }
}

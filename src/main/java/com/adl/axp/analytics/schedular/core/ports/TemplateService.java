package com.adl.axp.analytics.schedular.application.ports;

import com.adl.axp.analytics.schedular.domain.RestCallDTO;

public interface TemplateService {
    String transformTemplateString(RestCallDTO restCallDTO, String template, String search, String replace);

    String transformTemplate(RestCallDTO restCallDTO, String template);

    String transformTemplateDates(RestCallDTO restCallDTO, String template, int deltadays, int indexPartitionVal);
}

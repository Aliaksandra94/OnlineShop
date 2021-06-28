package com.moroz.test_task.controller.operationNotes;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class OperationNotesResourcesReader implements springfox.documentation.spi.service.OperationBuilderPlugin {

    private final DescriptionResolver descriptions;

    @Autowired
    public OperationNotesResourcesReader(DescriptionResolver descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void apply(OperationContext context) {
        try {
            String apiRoleAccessNoteText = "Endpoint Access Authorities & Rules: Null";
            Optional<PreAuthorize> preAuthorizeAnnotation = context.findAnnotation(PreAuthorize.class);
            if (preAuthorizeAnnotation.isPresent()) {
                apiRoleAccessNoteText = "Endpoint Access Authorities & Rules: " + preAuthorizeAnnotation.get().value();
            }
            context.operationBuilder().notes(descriptions.resolve(apiRoleAccessNoteText));
        } catch (Exception e) {
            new StringBuilder().append("Error when creating swagger documentation for security roles: ").append(e).toString();
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}
package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    /**
     * Generate message string.
     *
     * @param template the template
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        Map<String, String> attributesFromClient = client.getParams();
        List<String> attributesFromTemplate = getAttributesFromTemplate(template);

        if (attributesFromClient.size() < attributesFromTemplate.size()
                || !attributesFromTemplate.stream().allMatch(attributesFromClient::containsKey)) {
            throw new IllegalStateException("Not all attributes have been passed from client");
        }
        return replaceAttributeByValue(template, attributesFromClient);
    }

    private List<String> getAttributesFromTemplate(Template template) {
        List<String> variables = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\{.*}");
        Matcher matcher = pattern.matcher(template.getBasicMessageTemplate());

        while (matcher.find()) {
            variables.add(matcher.group());
        }
        return variables;
    }

    private String replaceAttributeByValue(Template template, Map<String, String> attributesMap) {
        String output = template.getBasicMessageTemplate();
        Pattern p = Pattern.compile("^#\\{(.*)}$");
        for (String key : attributesMap.keySet()) {
            Matcher matcher = p.matcher(key);
            if (matcher.find()){
                String tokenKey = matcher.group(1);
                String pattern = "#\\{" + tokenKey + "}";
                output = output.replaceAll(pattern, attributesMap.get(key));
            }
        }
        return output;
    }
}
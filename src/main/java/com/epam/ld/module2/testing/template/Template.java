package com.epam.ld.module2.testing.template;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Template.
 */
public class Template {

    Map<String, String> attributeMap = new HashMap<>();

    public String getBasicMessageTemplate() {
        return """ 
                Subject: #{subject}
                                
                Hello #{name}
                Text: #{txt}
                                
                Best Regards
                """;
    }
}
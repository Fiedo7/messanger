package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {

    TemplateEngine templateEngine;

    String StringTemplate = """
            Subject: #{subject}
                                
            Hello #{name}
            Text: #{txt}
                                
            Best Regards
            """;

    @BeforeEach
    public void setUp() {
        templateEngine = new TemplateEngine();
    }

    @Test
    public void givenTemplate_whenGenerateMessage_ThenMessageSameAsTemplate() {
        Template template = mock(Template.class);
        when(template.getBasicMessageTemplate()).thenReturn(StringTemplate);
        String s = templateEngine.generateMessage(template, mock(Client.class));
        assertEquals(StringTemplate, s);
    }
}

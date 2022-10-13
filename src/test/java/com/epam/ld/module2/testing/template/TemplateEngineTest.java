package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.client.Client;
import com.epam.ld.module2.testing.client.ConsoleClientCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    Map<String, String> attrs = Map.of("#{subject}", "tst", "#{name}", "name", "#{txt}", "txt");

    @BeforeEach
    public void setUp() {
        templateEngine = new TemplateEngine();
    }

    @Test
    public void givenTemplate_whenGenerateMessage_ThenMessageSameAsTemplate() {
        Template template = mock(Template.class);
        Client client = mock(Client.class);
        when(template.getBasicMessageTemplate()).thenReturn("test");
        when(client.getParams()).thenReturn(Map.of());
        String message = templateEngine.generateMessage(template, mock(Client.class));
        assertEquals(message, "test");
    }

    @Test
    public void givenTemplate_whenConsoleClientAndPassedNotAllParams_ThenThrowException() {
        Template template = mock(Template.class);
        ConsoleClientCreator consoleClientCreator = mock(ConsoleClientCreator.class);
        Map<String, String> attributesMapFromConsole = new HashMap<>();
        attributesMapFromConsole.put("#{subject}", "Testing topic");
        attributesMapFromConsole.put("#{name}", "Testing name");

        when(template.getBasicMessageTemplate()).thenReturn(StringTemplate);
        when(consoleClientCreator.getParams()).thenReturn(attributesMapFromConsole);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> templateEngine.generateMessage(template, consoleClientCreator));
        assertEquals("Not all attributes have been passed from client", illegalStateException.getMessage());
    }

    @Test
    public void givenTemplate_whenConsoleClientAndPassedNotAllAttrMatch_ThenThrowException() {
        Template template = mock(Template.class);
        ConsoleClientCreator consoleClientCreator = mock(ConsoleClientCreator.class);
        Map<String, String> attributesMapFromConsole = new HashMap<>();
        attributesMapFromConsole.put("#{subject}", "Testing topic");
        attributesMapFromConsole.put("#{name}", "Testing name");
        attributesMapFromConsole.put("#{not}", "Not_match");

        when(template.getBasicMessageTemplate()).thenReturn(StringTemplate);
        when(consoleClientCreator.getParams()).thenReturn(attributesMapFromConsole);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> templateEngine.generateMessage(template, consoleClientCreator));
        assertEquals("Not all attributes have been passed from client", illegalStateException.getMessage());
    }

    @Test
    public void givenTemplate_whenConsoleClientAndPassedAllMatchingAttributes_ThenThrowException() {
        Template template = mock(Template.class);
        ConsoleClientCreator consoleClientCreator = mock(ConsoleClientCreator.class);
        Map<String, String> attributesMapFromConsole = new HashMap<>();
        attributesMapFromConsole.put("#{subject}", "Testing topic");
        attributesMapFromConsole.put("#{name}", "Testing name");
        attributesMapFromConsole.put("#{txt}", "Text");

        when(template.getBasicMessageTemplate()).thenReturn(StringTemplate);
        when(consoleClientCreator.getParams()).thenReturn(attributesMapFromConsole);
        String message = templateEngine.generateMessage(template, consoleClientCreator);
        assertEquals(message, StringTemplate);
    }

    @Test
    public void givenTemplate_whenConsoleClientAndPassedMoreAttributesAllMatching_thenReturnTemplate() {
        Template template = mock(Template.class);
        ConsoleClientCreator consoleClientCreator = mock(ConsoleClientCreator.class);
        Map<String, String> attributesMapFromConsole = new HashMap<>();
        attributesMapFromConsole.put("#{subject}", "Testing topic");
        attributesMapFromConsole.put("#{name}", "Testing name");
        attributesMapFromConsole.put("#{txt}", "Text");
        attributesMapFromConsole.put("#{additional}", "Does not matter");

        when(template.getBasicMessageTemplate()).thenReturn(StringTemplate);
        when(consoleClientCreator.getParams()).thenReturn(attributesMapFromConsole);
        String message = templateEngine.generateMessage(template, consoleClientCreator);
        assertEquals(message, StringTemplate);
    }
}
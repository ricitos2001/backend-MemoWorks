package com.example.catalog.services.email;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {

    @Test
    public void sendSimpleEmail_callsMailSender() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        SpringTemplateEngine templateEngine = mock(SpringTemplateEngine.class);
        EmailServiceImpl service = new EmailServiceImpl(mailSender, templateEngine);

        // set fromAddress via reflection if needed, but we'll skip and just ensure no exception
        service.sendSimpleEmail("test@example.com", "Asunto", "Cuerpo");

        // verify that JavaMailSender.send(SimpleMailMessage) was called
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}


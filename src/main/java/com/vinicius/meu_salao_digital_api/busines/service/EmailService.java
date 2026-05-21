package com.vinicius.meu_salao_digital_api.busines.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCodigo(String email, String codigo, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Validação - Lumien App");
        message.setText("""
                Olá, %s!
                
                Seu código de validação é: %s
                
                Esse código expira em 10 minutos.
                
                Caso não tenha solicitado, ignore este e-mail.
                """.formatted(nome, codigo));
        mailSender.send(message);
    }

//    public void enviarTeste() {
//        enviarCodigo("comovcfaraologin2018@gmail.com", "123456");
//    }

}

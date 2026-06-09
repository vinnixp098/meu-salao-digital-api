package com.vinicius.meu_salao_digital_api.busines.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    public void enviarCodigo(String email, String codigo, String nome) {


        Resend resend = new Resend(apiKey);
        String html = """
    <div style="font-family: Arial, Helvetica, sans-serif; max-width: 600px; margin: 0 auto; padding: 24px; background-color: #f8f9fa; border-radius: 12px;">
        
        <div style="text-align: center; margin-bottom: 24px;">
            <h2 style="color: #2c3e50; margin: 0;">
                Código de verificação
            </h2>
        </div>

        <p style="font-size: 16px; color: #333;">
            Olá, <strong>%s</strong>!
        </p>

        <p style="font-size: 15px; color: #555; line-height: 1.6;">
            Utilize o código abaixo para realizar a etapa de verificação:
        </p>

        <div style="text-align: center; margin: 32px 0;">
            <span style="
                display: inline-block;
                background-color: #111827;
                color: white;
                font-size: 32px;
                font-weight: bold;
                letter-spacing: 8px;
                padding: 16px 32px;
                border-radius: 10px;
            ">
                %s
            </span>
        </div>

        <p style="font-size: 14px; color: #666;">
            ⏳ Este código expira em <strong>10 minutos</strong>.
        </p>

        <p style="font-size: 14px; color: #666;">
            Caso você não tenha solicitado o código, ignore este e-mail com segurança.
        </p>

        <hr style="border: none; border-top: 1px solid #ddd; margin: 24px 0;" />

        <p style="font-size: 12px; color: #999; text-align: center;">
            Este é um e-mail automático. Não responda esta mensagem.
        </p>

    </div>
    """
                .formatted(nome, codigo);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Lumien App <onboarding@resend.dev>")
                .to(email)
                .subject("Código de verificação")
                .html(html)
                .build();

        try {
            CreateEmailResponse response = resend.emails().send(params);
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

//    public void enviarTeste() {
//        enviarCodigo("comovcfaraologin2018@gmail.com", "123456");
//    }

}

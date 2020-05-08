package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.util.LoggerUtil;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailService {

    private static boolean carregado;

    private static Properties mailProperties;

    private void carregaConfig() throws InfraestruturaException {

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mail.properties")) {

            mailProperties = new Properties();

            if (inputStream == null)
                throw new InfraestruturaException("Não foi possível carregar o arquivo de configuração do serviço " +
                        "de e-mail (mail.properties)");

            mailProperties.load(inputStream);

            carregado = true;

        } catch (IOException e) {
            carregado = false;
            throw new InfraestruturaException(e.getMessage(), e);
        }
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void enviarEmail(final String emailDest, final String assunto, final String corpo) throws InfraestruturaException {

        try {

            if (!carregado)  carregaConfig();


            Properties properties = new Properties();

            properties.putAll(mailProperties);

            final String mailRemetente = mailProperties.getProperty("mail.user");
            final String passRemetente = mailProperties.getProperty("mail.pass");

            Session session = Session.getInstance(mailProperties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailRemetente, passRemetente);
                        }
                    });

            Transport transport = session.getTransport();
            InternetAddress addressFrom = new InternetAddress(mailRemetente);

            MimeMessage message = new MimeMessage(session);
            message.setSender(addressFrom);
            message.setSubject(assunto);
            message.setContent(corpo, "text/plain");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDest));

            transport.connect();
            Transport.send(message);
            transport.close();

        } catch (Exception ex) {
            LoggerUtil.error(ex);
            throw new InfraestruturaException(ex.getMessage(), ex);
        }
    }
}

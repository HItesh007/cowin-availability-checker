package com.hitesh.cowin.email;

import com.hitesh.cowin.utility.AESUtility;
import org.apache.commons.lang3.StringUtils;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EmailHelper {

    public static void emailReport(String htmlAsString) {
        if (!StringUtils.isBlank(htmlAsString)) {
            try {
                Email email = EmailBuilder
                        .startingBlank()
                        .to("hiteshprajapati1992@gmail.com")
                        .from("cowin-notification@no-reply.com")
                        .withSubject("CoWin Slot Availability Report")
                        .withHeader("X-Priority", 1)
                        .withHTMLText(htmlAsString)
                        .buildEmail();

                Mailer mailer = MailerBuilder
                        .withSMTPServerHost(EmailConstants.SMTP_HOST)
                        .withSMTPServerPort(EmailConstants.SMTP_PORT)
                        .withSMTPServerUsername(EmailConstants.EMAIL_USERNAME)
                        .withSMTPServerPassword(AESUtility.decrypt(EmailConstants.EMAIL_TOKEN, EmailConstants.EMAIL_SECRET, EmailConstants.EMAIL_SALT))
                        .withTransportStrategy(TransportStrategy.SMTP)
                        .withDebugLogging(false)
                        .trustingAllHosts(true)
                        .buildMailer();

                mailer.sendMail(email);
                System.out.println("Email sent");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

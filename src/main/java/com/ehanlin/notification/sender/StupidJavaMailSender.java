package com.ehanlin.notification.sender;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ehanlin.notification.message.Email;

/**
 * 簡單的 JavaMail 發信器實作
 *
 * @author rodick_huang
 *
 */
public class StupidJavaMailSender implements Sender<Email> {

    /**
     * 信件發送設定
     */
    private Session session;

    /**
     * 使用 SMTP 服務供應商的帳號密碼建構一個 Sender
     *
     * @param host
     * @param account
     * @param password
     */
    public StupidJavaMailSender(String host, final String account, final String password) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "25");

        // 使用帳號密碼認證SMTP伺服器認證
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        };

        session = Session.getDefaultInstance(props, auth);
    }

    /**
     * 使用現成的 JavaMail Session 建構
     *
     * @param session
     */
    public StupidJavaMailSender(Session session) {
        this.session = session;
    }

    /**
     * 公開的 send() 方法
     *
     * @param email 任何實作 Email 界面的實體
     */
    public void send(Email email) {
        String subject = email.getSubject();
        String text = email.getText();
        String from = email.getFrom();
        String to = email.getTo();
        send(subject, text, from, to);
    }

    /**
     * JavaMail API based send
     */
    private void send(String subject, String text, String from, String to) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setContent(text, "text/html;charset=UTF-8");
            Transport.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: notify manager.
        }
    }

}

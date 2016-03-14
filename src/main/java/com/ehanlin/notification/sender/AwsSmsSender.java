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
 * 用 Aws Sms 轉發信件，可強制指定信件來源
 * @author rodick_huang
 */
public class AwsSmsSender implements Sender<Email> {

    /**
     * 信件發送設定
     */
    private Session session;

    /**
     * 發送人信箱位址
     */
    private String from;

    /**
     * 發送人名稱
     */
    private String fromName;

    /**
     * 使用 SMTP 服務供應商的帳號密碼建構一個 Sender
     *
     * @param host
     * @param account
     * @param password
     */
    public AwsSmsSender(final String host, String from, String fromName, final String account, final String password) {
        this.from = from;
        this.fromName = fromName;
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.starttls.enable", "true");

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
    public AwsSmsSender(Session session) {
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
        String to = email.getTo();
        send(subject, text, from, to);
    }

    /**
     * JavaMail API based send
     */
    private void send(String subject, String text, String from, String to) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from, fromName, "UTF-8"));
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

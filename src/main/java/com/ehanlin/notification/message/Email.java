package com.ehanlin.notification.message;

/**
 * <h1>最基本的郵件界面</h1>
 * <p>定義一封郵件至少必須要能夠存取以下屬性</p>
 * <ul>
 * 	 <li>subject(主題)</li>
 *   <li>text(內文)</li>
 *   <li>from(寄件人)</li>
 *   <li>to(收件人)</li>
 * </ul>
 *
 * @author rodick_huang
 */
public interface Email extends Deliverable {

    String getSubject();

    void setSubject(String subject);

    String getText();

    void setText(String text);

    String getFrom();

    void setFrom(String from);

    String getTo();

    void setTo(String to);

    /**
     * <h1>郵件類型</h1>
     *
     * @author rodick_huang
     */
    public enum Type {

        /**
         * 註冊
         */
        REGISTER,

        /**
         * 密碼重設
         */
        PASSWORD_RESET,

        /**
         * 修改帳號
         */
        EDIT_EMAIL,

        /**
         * 朋友推薦
         */
        REFERRAL,

        /**
         * 聯絡我們
         */
        CONTACT_US;

    }

}

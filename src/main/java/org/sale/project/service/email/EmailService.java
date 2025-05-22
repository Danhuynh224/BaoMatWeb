package org.sale.project.service.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.RandomStringUtils;
import org.sale.project.entity.*;
import org.sale.project.enums.StatusOrder;

import lombok.experimental.FieldDefaults;
import org.sale.project.service.VoucherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    private final VoucherService voucherService;
    @NonFinal
    @Value("${name.host}")
    String NAME_HOST;




    JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        // Tạo một MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        System.out.println(NAME_HOST);
        // Sử dụng MimeMessageHelper để thêm nội dung HTML
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true để kích hoạt nội dung HTML
        helper.setFrom("syloftcloth@gmail.com");

        // Gửi email
        mailSender.send(mimeMessage);
    }

    public void sendAdminPasswordEmail(String toEmail, String plainPassword) throws MessagingException {
        String subject = "Tài khoản Quản trị đã được tạo";

        String htmlContent = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<p>Tài khoản quản trị viên của bạn đã được tạo thành công.</p>" +
                "<p><strong>Email đăng nhập:</strong> " + toEmail + "</p>" +
                "<p><strong>Mật khẩu tạm thời:</strong> <span style='color: red; font-weight: bold;'>" + plainPassword + "</span></p>" +
                "<hr>" +
                "<p style='font-size: 12px; color: #888;'>Email này được gửi tự động, vui lòng không trả lời.</p>" +
                "</body>" +
                "</html>";

        sendHtmlEmail(toEmail, subject, htmlContent);
    }


    public String MailOrder(List<OrderDetail> details, Order order) throws UnsupportedEncodingException {
        List<String> itemOrders = new ArrayList<>();
        for (OrderDetail detail : details) {

            String nameProductSendEmail = URLEncoder.encode( detail.getProductVariant().getProduct().getName(),  "UTF-8").replace("+", "%20");

            itemOrders.add( "<tr>\n" +
                    "                      <td\n" +
                    "                        align=\"left\"\n" +
                    "                        class=\"esdev-adapt-off\"\n" +
                    "                        data-custom-paddings=\"true\"\n" +
                    "                        style=\"\n" +
                    "                          margin: 0;\n" +
                    "                          padding-top: 10px;\n" +
                    "                          padding-right: 20px;\n" +
                    "                          padding-bottom: 10px;\n" +
                    "                          padding-left: 20px;\n" +
                    "                        \"\n" +
                    "                      >\n" +
                    "                        <table\n" +
                    "                          cellpadding=\"0\"\n" +
                    "                          cellspacing=\"0\"\n" +
                    "                          class=\"esdev-mso-table\"\n" +
                    "                          role=\"none\"\n" +
                    "                          style=\"\n" +
                    "                            mso-table-lspace: 0pt;\n" +
                    "                            mso-table-rspace: 0pt;\n" +
                    "                            border-collapse: collapse;\n" +
                    "                            border-spacing: 0px;\n" +
                    "                            width: 560px;\n" +
                    "                          \"\n" +
                    "                        >\n" +
                    "                          <tr>\n" +
                    "                            <td\n" +
                    "                              valign=\"top\"\n" +
                    "                              class=\"esdev-mso-td\"\n" +
                    "                              style=\"padding: 0; margin: 0\"\n" +
                    "                            >\n" +
                    "                              <table\n" +
                    "                                cellpadding=\"0\"\n" +
                    "                                cellspacing=\"0\"\n" +
                    "                                align=\"left\"\n" +
                    "                                class=\"es-left\"\n" +
                    "                                role=\"none\"\n" +
                    "                                style=\"\n" +
                    "                                  mso-table-lspace: 0pt;\n" +
                    "                                  mso-table-rspace: 0pt;\n" +
                    "                                  border-collapse: collapse;\n" +
                    "                                  border-spacing: 0px;\n" +
                    "                                  float: left;\n" +
                    "                                \"\n" +
                    "                              >\n" +
                    "                                <tr>\n" +
                    "                                  <td\n" +
                    "                                    align=\"center\"\n" +
                    "                                    class=\"es-m-p0r\"\n" +
                    "                                    style=\"padding: 0; margin: 0; width: 110px\"\n" +
                    "                                  >\n" +
                    "                                    <table\n" +
                    "                                      cellpadding=\"0\"\n" +
                    "                                      cellspacing=\"0\"\n" +
                    "                                      width=\"100%\"\n" +
                    "                                      role=\"presentation\"\n" +
                    "                                      style=\"\n" +
                    "                                        mso-table-lspace: 0pt;\n" +
                    "                                        mso-table-rspace: 0pt;\n" +
                    "                                        border-collapse: collapse;\n" +
                    "                                        border-spacing: 0px;\n" +
                    "                                      \"\n" +
                    "                                    >\n" +
                    "                                      <tr>\n" +
                    "                                        <td\n" +
                    "                                          align=\"center\"\n" +
                    "                                          style=\"\n" +
                    "                                            padding: 0;\n" +
                    "                                            margin: 0;\n" +
                    "                                            font-size: 0px;\n" +
                    "                                          \"\n" +
                    "                                        >\n" +
                    "                                          <img\n" +
                    "                                            src=\""+NAME_HOST+"images/product/"+ nameProductSendEmail + "/"+ detail.getProductVariant().getImage()+"\"\n" +

                    "                                            alt=\"\"\n" +
                    "                                            width=\"110\"\n" +
                    "                                            class=\"adapt-img\"\n" +
                    "                                            style=\"\n" +
                    "                                              display: block;\n" +
                    "                                              font-size: 14px;\n" +
                    "                                              border: 0;\n" +
                    "                                              outline: none;\n" +
                    "                                              text-decoration: none;\n" +
                    "                                            \"\n" +
                    "                                            height=\"auto\"\n" +
                    "                                          />\n" +
                    "                                        </td>\n" +
                    "                                      </tr>\n" +
                    "                                    </table>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </table>\n" +
                    "                            </td>\n" +
                    "                            <td style=\"padding: 0; margin: 0; width: 20px\"></td>\n" +
                    "                            <td\n" +
                    "                              valign=\"top\"\n" +
                    "                              class=\"esdev-mso-td\"\n" +
                    "                              style=\"padding: 0; margin: 0\"\n" +
                    "                            >\n" +
                    "                              <table\n" +
                    "                                cellpadding=\"0\"\n" +
                    "                                cellspacing=\"0\"\n" +
                    "                                align=\"left\"\n" +
                    "                                class=\"es-left\"\n" +
                    "                                role=\"none\"\n" +
                    "                                style=\"\n" +
                    "                                  mso-table-lspace: 0pt;\n" +
                    "                                  mso-table-rspace: 0pt;\n" +
                    "                                  border-collapse: collapse;\n" +
                    "                                  border-spacing: 0px;\n" +
                    "                                  float: left;\n" +
                    "                                \"\n" +
                    "                              >\n" +
                    "                                <tr>\n" +
                    "                                  <td\n" +
                    "                                    align=\"center\"\n" +
                    "                                    style=\"padding: 0; margin: 0; width: 254px\"\n" +
                    "                                  >\n" +
                    "                                    <table\n" +
                    "                                      cellpadding=\"0\"\n" +
                    "                                      cellspacing=\"0\"\n" +
                    "                                      width=\"100%\"\n" +
                    "                                      role=\"presentation\"\n" +
                    "                                      style=\"\n" +
                    "                                        mso-table-lspace: 0pt;\n" +
                    "                                        mso-table-rspace: 0pt;\n" +
                    "                                        border-collapse: collapse;\n" +
                    "                                        border-spacing: 0px;\n" +
                    "                                      \"\n" +
                    "                                    >\n" +
                    "                                      <tr>\n" +
                    "                                        <td\n" +
                    "                                          align=\"left\"\n" +
                    "                                          style=\"padding: 0; margin: 0\"\n" +
                    "                                        >\n" +
                    "                                          <p\n" +
                    "                                            style=\"\n" +
                    "                                              margin: 0;\n" +
                    "                                              mso-line-height-rule: exactly;\n" +
                    "                                              font-family: arial,\n" +
                    "                                                'helvetica neue', helvetica,\n" +
                    "                                                sans-serif;\n" +
                    "                                              line-height: 21px;\n" +
                    "                                              letter-spacing: 0;\n" +
                    "                                              color: #333333;\n" +
                    "                                              font-size: 14px;\n" +
                    "                                            \"\n" +
                    "                                          >\n" +
                    "                                            <strong>"+detail.getProductVariant().getProduct().getName()+"</strong>\n" +
                    "                                          </p>\n" +
                    "                                        </td>\n" +
                    "                                      </tr>\n" +
                    "                                      <tr>\n" +
                    "                                        <td\n" +
                    "                                          align=\"left\"\n" +
                    "                                          style=\"\n" +
                    "                                            padding: 0;\n" +
                    "                                            margin: 0;\n" +
                    "                                            padding-top: 5px;\n" +
                    "                                          \"\n" +
                    "                                        >\n" +
                    "                                          <p\n" +
                    "                                            style=\"\n" +
                    "                                              margin: 0;\n" +
                    "                                              mso-line-height-rule: exactly;\n" +
                    "                                              font-family: arial,\n" +
                    "                                                'helvetica neue', helvetica,\n" +
                    "                                                sans-serif;\n" +
                    "                                              line-height: 21px;\n" +
                    "                                              letter-spacing: 0;\n" +
                    "                                              color: #333333;\n" +
                    "                                              font-size: 14px;\n" +
                    "                                            \"\n" +
                    "                                          >\n" +
                    "                                            Kích thước: "+detail.getProductVariant().getSize().getName()+"<br />Màu sắc: "+detail.getProductVariant().getColor().getName()+"\n" +
                    "                                          </p>\n" +
                    "                                        </td>\n" +
                    "                                      </tr>\n" +
                    "                                    </table>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </table>\n" +
                    "                            </td>\n" +
                    "                            <td style=\"padding: 0; margin: 0; width: 20px\"></td>\n" +
                    "                            <td\n" +
                    "                              valign=\"top\"\n" +
                    "                              class=\"esdev-mso-td\"\n" +
                    "                              style=\"padding: 0; margin: 0\"\n" +
                    "                            >\n" +
                    "                              <table\n" +
                    "                                cellpadding=\"0\"\n" +
                    "                                cellspacing=\"0\"\n" +
                    "                                align=\"left\"\n" +
                    "                                class=\"es-left\"\n" +
                    "                                role=\"none\"\n" +
                    "                                style=\"\n" +
                    "                                  mso-table-lspace: 0pt;\n" +
                    "                                  mso-table-rspace: 0pt;\n" +
                    "                                  border-collapse: collapse;\n" +
                    "                                  border-spacing: 0px;\n" +
                    "                                  float: left;\n" +
                    "                                \"\n" +
                    "                              >\n" +
                    "                                <tr>\n" +
                    "                                  <td\n" +
                    "                                    align=\"center\"\n" +
                    "                                    style=\"padding: 0; margin: 0; width: 68px\"\n" +
                    "                                  >\n" +
                    "                                    <table\n" +
                    "                                      cellpadding=\"0\"\n" +
                    "                                      cellspacing=\"0\"\n" +
                    "                                      width=\"100%\"\n" +
                    "                                      role=\"presentation\"\n" +
                    "                                      style=\"\n" +
                    "                                        mso-table-lspace: 0pt;\n" +
                    "                                        mso-table-rspace: 0pt;\n" +
                    "                                        border-collapse: collapse;\n" +
                    "                                        border-spacing: 0px;\n" +
                    "                                      \"\n" +
                    "                                    >\n" +
                    "                                      <tr>\n" +
                    "                                        <td\n" +
                    "                                          align=\"center\"\n" +
                    "                                          style=\"padding: 0; margin: 0\"\n" +
                    "                                        >\n" +
                    "                                          <p\n" +
                    "                                            style=\"\n" +
                    "                                              margin: 0;\n" +
                    "                                              mso-line-height-rule: exactly;\n" +
                    "                                              font-family: arial,\n" +
                    "                                                'helvetica neue', helvetica,\n" +
                    "                                                sans-serif;\n" +
                    "                                              line-height: 21px;\n" +
                    "                                              letter-spacing: 0;\n" +
                    "                                              color: #333333;\n" +
                    "                                              font-size: 14px;\n" +
                    "                                            \"\n" +
                    "                                          >\nSL:" + detail.getQuantity()+
                    "                                            \n" +
                    "                                          </p>\n" +
                    "                                        </td>\n" +
                    "                                      </tr>\n" +
                    "                                    </table>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </table>\n" +
                    "                            </td>\n" +
                    "                            <td style=\"padding: 0; margin: 0; width: 20px\"></td>\n" +
                    "                            <td\n" +
                    "                              valign=\"top\"\n" +
                    "                              class=\"esdev-mso-td\"\n" +
                    "                              style=\"padding: 0; margin: 0\"\n" +
                    "                            >\n" +
                    "                              <table\n" +
                    "                                cellpadding=\"0\"\n" +
                    "                                cellspacing=\"0\"\n" +
                    "                                align=\"right\"\n" +
                    "                                class=\"es-right\"\n" +
                    "                                role=\"none\"\n" +
                    "                                style=\"\n" +
                    "                                  mso-table-lspace: 0pt;\n" +
                    "                                  mso-table-rspace: 0pt;\n" +
                    "                                  border-collapse: collapse;\n" +
                    "                                  border-spacing: 0px;\n" +
                    "                                  float: right;\n" +
                    "                                \"\n" +
                    "                              >\n" +
                    "                                <tr>\n" +
                    "                                  <td\n" +
                    "                                    align=\"left\"\n" +
                    "                                    style=\"padding: 0; margin: 0; width: 68px\"\n" +
                    "                                  >\n" +
                    "                                    <table\n" +
                    "                                      cellpadding=\"0\"\n" +
                    "                                      cellspacing=\"0\"\n" +
                    "                                      width=\"100%\"\n" +
                    "                                      role=\"presentation\"\n" +
                    "                                      style=\"\n" +
                    "                                        mso-table-lspace: 0pt;\n" +
                    "                                        mso-table-rspace: 0pt;\n" +
                    "                                        border-collapse: collapse;\n" +
                    "                                        border-spacing: 0px;\n" +
                    "                                      \"\n" +
                    "                                    >\n" +
                    "                                      <tr>\n" +
                    "                                        <td\n" +
                    "                                          align=\"right\"\n" +
                    "                                          style=\"padding: 0; margin: 0\"\n" +
                    "                                        >\n" +
                    "                                          <p\n" +
                    "                                            style=\"\n" +
                    "                                              margin: 0;\n" +
                    "                                              mso-line-height-rule: exactly;\n" +
                    "                                              font-family: arial,\n" +
                    "                                                'helvetica neue', helvetica,\n" +
                    "                                                sans-serif;\n" +
                    "                                              line-height: 21px;\n" +
                    "                                              letter-spacing: 0;\n" +
                    "                                              color: #333333;\n" +
                    "                                              font-size: 14px;\n" +
                    "                                            \"\n" +
                    "                                          >\n" +
                    "                                            <strong>"+ (long)detail.getPrice()+" VND</strong>\n" +
                    "                                          </p>\n" +
                    "                                        </td>\n" +
                    "                                      </tr>\n" +
                    "                                    </table>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </table>\n" +
                    "                            </td>\n" +
                    "                          </tr>\n" +
                    "                        </table>\n" +
                    "                      </td>\n" +
                    "                    </tr>");
        }
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html\n" +
                "  dir=\"ltr\"\n" +
                "  xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "  xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "  lang=\"vi\"\n" +
                ">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\" />\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\" />\n" +
                "    <title>New Message</title>\n" +
                "    <!--[if (mso 16)\n" +
                "      ]><style type=\"text/css\">\n" +
                "        a {\n" +
                "          text-decoration: none;\n" +
                "        }\n" +
                "      </style><!\n" +
                "    [endif]-->\n" +
                "    <!--[if gte mso 9\n" +
                "      ]><style>\n" +
                "        sup {\n" +
                "          font-size: 100% !important;\n" +
                "        }\n" +
                "      </style><!\n" +
                "    [endif]-->\n" +
                "    <!--[if gte mso 9\n" +
                "      ]><noscript>\n" +
                "        <xml>\n" +
                "          <o:OfficeDocumentSettings>\n" +
                "            <o:AllowPNG></o:AllowPNG> <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "          </o:OfficeDocumentSettings>\n" +
                "        </xml>\n" +
                "      </noscript>\n" +
                "    <![endif]-->\n" +
                "    <style type=\"text/css\">\n" +
                "      .rollover:hover .rollover-first {\n" +
                "        max-height: 0px !important;\n" +
                "        display: none !important;\n" +
                "      }\n" +
                "      .rollover:hover .rollover-second {\n" +
                "        max-height: none !important;\n" +
                "        display: block !important;\n" +
                "      }\n" +
                "      .rollover span {\n" +
                "        font-size: 0px;\n" +
                "      }\n" +
                "      u + .body img ~ div div {\n" +
                "        display: none;\n" +
                "      }\n" +
                "      #outlook a {\n" +
                "        padding: 0;\n" +
                "      }\n" +
                "      span.MsoHyperlink,\n" +
                "      span.MsoHyperlinkFollowed {\n" +
                "        color: inherit;\n" +
                "        mso-style-priority: 99;\n" +
                "      }\n" +
                "      a.es-button {\n" +
                "        mso-style-priority: 100 !important;\n" +
                "        text-decoration: none !important;\n" +
                "      }\n" +
                "      a[x-apple-data-detectors],\n" +
                "      #MessageViewBody a {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: none !important;\n" +
                "        font-size: inherit !important;\n" +
                "        font-family: inherit !important;\n" +
                "        font-weight: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "      }\n" +
                "      .es-desk-hidden {\n" +
                "        display: none;\n" +
                "        float: left;\n" +
                "        overflow: hidden;\n" +
                "        width: 0;\n" +
                "        max-height: 0;\n" +
                "        line-height: 0;\n" +
                "        mso-hide: all;\n" +
                "      }\n" +
                "      @media only screen and (max-width: 600px) {\n" +
                "        .es-m-p0r {\n" +
                "          padding-right: 0px !important;\n" +
                "        }\n" +
                "        .es-m-p0l {\n" +
                "          padding-left: 0px !important;\n" +
                "        }\n" +
                "        .es-p-default {\n" +
                "        }\n" +
                "        *[class=\"gmail-fix\"] {\n" +
                "          display: none !important;\n" +
                "        }\n" +
                "        p,\n" +
                "        a {\n" +
                "          line-height: 150% !important;\n" +
                "        }\n" +
                "        h1,\n" +
                "        h1 a {\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        h2,\n" +
                "        h2 a {\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        h3,\n" +
                "        h3 a {\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        h4,\n" +
                "        h4 a {\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        h5,\n" +
                "        h5 a {\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        h6,\n" +
                "        h6 a {\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        .es-header-body p {\n" +
                "        }\n" +
                "        .es-content-body p {\n" +
                "        }\n" +
                "        .es-footer-body p {\n" +
                "        }\n" +
                "        .es-infoblock p {\n" +
                "        }\n" +
                "        h1 {\n" +
                "          font-size: 36px !important;\n" +
                "          text-align: left;\n" +
                "        }\n" +
                "        h2 {\n" +
                "          font-size: 26px !important;\n" +
                "          text-align: left;\n" +
                "        }\n" +
                "        h3 {\n" +
                "          font-size: 20px !important;\n" +
                "          text-align: left;\n" +
                "        }\n" +
                "        h4 {\n" +
                "          font-size: 24px !important;\n" +
                "          text-align: left;\n" +
                "        }\n" +
                "        h5 {\n" +
                "          font-size: 20px !important;\n" +
                "          text-align: left;\n" +
                "        }\n" +
                "        h6 {\n" +
                "          font-size: 16px !important;\n" +
                "          text-align: left;\n" +
                "        }\n" +
                "        .es-header-body h1 a,\n" +
                "        .es-content-body h1 a,\n" +
                "        .es-footer-body h1 a {\n" +
                "          font-size: 36px !important;\n" +
                "        }\n" +
                "        .es-header-body h2 a,\n" +
                "        .es-content-body h2 a,\n" +
                "        .es-footer-body h2 a {\n" +
                "          font-size: 26px !important;\n" +
                "        }\n" +
                "        .es-header-body h3 a,\n" +
                "        .es-content-body h3 a,\n" +
                "        .es-footer-body h3 a {\n" +
                "          font-size: 20px !important;\n" +
                "        }\n" +
                "        .es-header-body h4 a,\n" +
                "        .es-content-body h4 a,\n" +
                "        .es-footer-body h4 a {\n" +
                "          font-size: 24px !important;\n" +
                "        }\n" +
                "        .es-header-body h5 a,\n" +
                "        .es-content-body h5 a,\n" +
                "        .es-footer-body h5 a {\n" +
                "          font-size: 20px !important;\n" +
                "        }\n" +
                "        .es-header-body h6 a,\n" +
                "        .es-content-body h6 a,\n" +
                "        .es-footer-body h6 a {\n" +
                "          font-size: 16px !important;\n" +
                "        }\n" +
                "        .es-menu td a {\n" +
                "          font-size: 12px !important;\n" +
                "        }\n" +
                "        .es-header-body p,\n" +
                "        .es-header-body a {\n" +
                "          font-size: 14px !important;\n" +
                "        }\n" +
                "        .es-content-body p,\n" +
                "        .es-content-body a {\n" +
                "          font-size: 16px !important;\n" +
                "        }\n" +
                "        .es-footer-body p,\n" +
                "        .es-footer-body a {\n" +
                "          font-size: 14px !important;\n" +
                "        }\n" +
                "        .es-infoblock p,\n" +
                "        .es-infoblock a {\n" +
                "          font-size: 12px !important;\n" +
                "        }\n" +
                "        .es-m-txt-c,\n" +
                "        .es-m-txt-c h1,\n" +
                "        .es-m-txt-c h2,\n" +
                "        .es-m-txt-c h3,\n" +
                "        .es-m-txt-c h4,\n" +
                "        .es-m-txt-c h5,\n" +
                "        .es-m-txt-c h6 {\n" +
                "          text-align: center !important;\n" +
                "        }\n" +
                "        .es-m-txt-r,\n" +
                "        .es-m-txt-r h1,\n" +
                "        .es-m-txt-r h2,\n" +
                "        .es-m-txt-r h3,\n" +
                "        .es-m-txt-r h4,\n" +
                "        .es-m-txt-r h5,\n" +
                "        .es-m-txt-r h6 {\n" +
                "          text-align: right !important;\n" +
                "        }\n" +
                "        .es-m-txt-j,\n" +
                "        .es-m-txt-j h1,\n" +
                "        .es-m-txt-j h2,\n" +
                "        .es-m-txt-j h3,\n" +
                "        .es-m-txt-j h4,\n" +
                "        .es-m-txt-j h5,\n" +
                "        .es-m-txt-j h6 {\n" +
                "          text-align: justify !important;\n" +
                "        }\n" +
                "        .es-m-txt-l,\n" +
                "        .es-m-txt-l h1,\n" +
                "        .es-m-txt-l h2,\n" +
                "        .es-m-txt-l h3,\n" +
                "        .es-m-txt-l h4,\n" +
                "        .es-m-txt-l h5,\n" +
                "        .es-m-txt-l h6 {\n" +
                "          text-align: left !important;\n" +
                "        }\n" +
                "        .es-m-txt-r img,\n" +
                "        .es-m-txt-c img,\n" +
                "        .es-m-txt-l img {\n" +
                "          display: inline !important;\n" +
                "        }\n" +
                "        .es-m-txt-r .rollover:hover .rollover-second,\n" +
                "        .es-m-txt-c .rollover:hover .rollover-second,\n" +
                "        .es-m-txt-l .rollover:hover .rollover-second {\n" +
                "          display: inline !important;\n" +
                "        }\n" +
                "        .es-m-txt-r .rollover span,\n" +
                "        .es-m-txt-c .rollover span,\n" +
                "        .es-m-txt-l .rollover span {\n" +
                "          line-height: 0 !important;\n" +
                "          font-size: 0 !important;\n" +
                "          display: block;\n" +
                "        }\n" +
                "        .es-spacer {\n" +
                "          display: inline-table;\n" +
                "        }\n" +
                "        a.es-button,\n" +
                "        button.es-button {\n" +
                "          font-size: 20px !important;\n" +
                "          padding: 10px 20px 10px 20px !important;\n" +
                "          line-height: 120% !important;\n" +
                "        }\n" +
                "        a.es-button,\n" +
                "        button.es-button,\n" +
                "        .es-button-border {\n" +
                "          display: inline-block !important;\n" +
                "        }\n" +
                "        .es-m-fw,\n" +
                "        .es-m-fw.es-fw,\n" +
                "        .es-m-fw .es-button {\n" +
                "          display: block !important;\n" +
                "        }\n" +
                "        .es-m-il,\n" +
                "        .es-m-il .es-button,\n" +
                "        .es-social,\n" +
                "        .es-social td,\n" +
                "        .es-menu {\n" +
                "          display: inline-block !important;\n" +
                "        }\n" +
                "        .es-adaptive table,\n" +
                "        .es-left,\n" +
                "        .es-right {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "        .es-content table,\n" +
                "        .es-header table,\n" +
                "        .es-footer table,\n" +
                "        .es-content,\n" +
                "        .es-footer,\n" +
                "        .es-header {\n" +
                "          width: 100% !important;\n" +
                "          max-width: 600px !important;\n" +
                "        }\n" +
                "        .adapt-img {\n" +
                "          width: 100% !important;\n" +
                "          height: auto !important;\n" +
                "        }\n" +
                "        .es-mobile-hidden,\n" +
                "        .es-hidden {\n" +
                "          display: none !important;\n" +
                "        }\n" +
                "        .es-desk-hidden {\n" +
                "          width: auto !important;\n" +
                "          overflow: visible !important;\n" +
                "          float: none !important;\n" +
                "          max-height: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "        }\n" +
                "        tr.es-desk-hidden {\n" +
                "          display: table-row !important;\n" +
                "        }\n" +
                "        table.es-desk-hidden {\n" +
                "          display: table !important;\n" +
                "        }\n" +
                "        td.es-desk-menu-hidden {\n" +
                "          display: table-cell !important;\n" +
                "        }\n" +
                "        .es-menu td {\n" +
                "          width: 1% !important;\n" +
                "        }\n" +
                "        table.es-table-not-adapt,\n" +
                "        .esd-block-html table {\n" +
                "          width: auto !important;\n" +
                "        }\n" +
                "        .h-auto {\n" +
                "          height: auto !important;\n" +
                "        }\n" +
                "      }\n" +
                "      @media screen and (max-width: 384px) {\n" +
                "        .mail-message-content {\n" +
                "          width: 414px !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    class=\"body\"\n" +
                "    style=\"\n" +
                "      width: 100%;\n" +
                "      height: 100%;\n" +
                "      -webkit-text-size-adjust: 100%;\n" +
                "      -ms-text-size-adjust: 100%;\n" +
                "      padding: 0;\n" +
                "      margin: 0;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      dir=\"ltr\"\n" +
                "      class=\"es-wrapper-color\"\n" +
                "      lang=\"vi\"\n" +
                "      style=\"background-color: #fafafa\"\n" +
                "    >\n" +
                "      <!--[if gte mso 9\n" +
                "        ]><v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "          <v:fill type=\"tile\" color=\"#fafafa\"></v:fill> </v:background\n" +
                "      ><![endif]-->\n" +
                "      <table\n" +
                "        width=\"100%\"\n" +
                "        cellspacing=\"0\"\n" +
                "        cellpadding=\"0\"\n" +
                "        class=\"es-wrapper\"\n" +
                "        role=\"none\"\n" +
                "        style=\"\n" +
                "          mso-table-lspace: 0pt;\n" +
                "          mso-table-rspace: 0pt;\n" +
                "          border-collapse: collapse;\n" +
                "          border-spacing: 0px;\n" +
                "          padding: 0;\n" +
                "          margin: 0;\n" +
                "          width: 100%;\n" +
                "          height: 100%;\n" +
                "          background-repeat: repeat;\n" +
                "          background-position: center top;\n" +
                "          background-color: #fafafa;\n" +
                "        \"\n" +
                "      >\n" +
                "        <tr>\n" +
                "          <td valign=\"top\" style=\"padding: 0; margin: 0\">\n" +
                "            <table\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              align=\"center\"\n" +
                "              class=\"es-header\"\n" +
                "              role=\"none\"\n" +
                "              style=\"\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                border-collapse: collapse;\n" +
                "                border-spacing: 0px;\n" +
                "                width: 100%;\n" +
                "                table-layout: fixed !important;\n" +
                "                background-color: transparent;\n" +
                "                background-repeat: repeat;\n" +
                "                background-position: center top;\n" +
                "              \"\n" +
                "            >\n" +
                "              <tr>\n" +
                "                <td align=\"center\" style=\"padding: 0; margin: 0\">\n" +
                "                  <table\n" +
                "                    bgcolor=\"#ffffff\"\n" +
                "                    align=\"center\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    class=\"es-header-body\"\n" +
                "                    role=\"none\"\n" +
                "                    style=\"\n" +
                "                      mso-table-lspace: 0pt;\n" +
                "                      mso-table-rspace: 0pt;\n" +
                "                      border-collapse: collapse;\n" +
                "                      border-spacing: 0px;\n" +
                "                      background-color: transparent;\n" +
                "                      width: 600px;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        align=\"left\"\n" +
                "                        data-custom-paddings=\"true\"\n" +
                "                        style=\"\n" +
                "                          margin: 0;\n" +
                "                          padding-top: 10px;\n" +
                "                          padding-right: 20px;\n" +
                "                          padding-bottom: 10px;\n" +
                "                          padding-left: 20px;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        <table\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          width=\"100%\"\n" +
                "                          role=\"none\"\n" +
                "                          style=\"\n" +
                "                            mso-table-lspace: 0pt;\n" +
                "                            mso-table-rspace: 0pt;\n" +
                "                            border-collapse: collapse;\n" +
                "                            border-spacing: 0px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              valign=\"top\"\n" +
                "                              align=\"center\"\n" +
                "                              class=\"es-m-p0r\"\n" +
                "                              style=\"padding: 0; margin: 0; width: 560px\"\n" +
                "                            >\n" +
                "                              <table\n" +
                "                                cellpadding=\"0\"\n" +
                "                                cellspacing=\"0\"\n" +
                "                                width=\"100%\"\n" +
                "                                role=\"presentation\"\n" +
                "                                style=\"\n" +
                "                                  mso-table-lspace: 0pt;\n" +
                "                                  mso-table-rspace: 0pt;\n" +
                "                                  border-collapse: collapse;\n" +
                "                                  border-spacing: 0px;\n" +
                "                                \"\n" +
                "                              >\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"\n" +
                "                                      padding: 0;\n" +
                "                                      margin: 0;\n" +
                "                                      padding-bottom: 20px;\n" +
                "                                      font-size: 0px;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <img\n" +
                "                                      src=\"https://fljlqug.stripocdn.email/content/guids/CABINET_6d9037e673f17e0b294bceb5fd52caf7b61eeb5d6906aa70203b7980eb5bd326/images/logo.png\"\n" +
                "                                      alt=\"\"\n" +
                "                                      width=\"300\"\n" +
                "                                      title=\"Logo\"\n" +
                "                                      class=\"adapt-img\"\n" +
                "                                      style=\"\n" +
                "                                        display: block;\n" +
                "                                        font-size: 12px;\n" +
                "                                        border: 0;\n" +
                "                                        outline: none;\n" +
                "                                        text-decoration: none;\n" +
                "                                      \"\n" +
                "                                      height=\"225\"\n" +
                "                                    />\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              align=\"center\"\n" +
                "              class=\"es-content\"\n" +
                "              role=\"none\"\n" +
                "              style=\"\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                border-collapse: collapse;\n" +
                "                border-spacing: 0px;\n" +
                "                width: 100%;\n" +
                "                table-layout: fixed !important;\n" +
                "              \"\n" +
                "            >\n" +
                "              <tr>\n" +
                "                <td align=\"center\" style=\"padding: 0; margin: 0\">\n" +
                "                  <table\n" +
                "                    bgcolor=\"#ffffff\"\n" +
                "                    align=\"center\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    class=\"es-content-body\"\n" +
                "                    role=\"none\"\n" +
                "                    style=\"\n" +
                "                      mso-table-lspace: 0pt;\n" +
                "                      mso-table-rspace: 0pt;\n" +
                "                      border-collapse: collapse;\n" +
                "                      border-spacing: 0px;\n" +
                "                      background-color: #ffffff;\n" +
                "                      width: 600px;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        align=\"left\"\n" +
                "                        data-custom-paddings=\"true\"\n" +
                "                        style=\"padding: 20px; margin: 0\"\n" +
                "                      >\n" +
                "                        <table\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          width=\"100%\"\n" +
                "                          role=\"none\"\n" +
                "                          style=\"\n" +
                "                            mso-table-lspace: 0pt;\n" +
                "                            mso-table-rspace: 0pt;\n" +
                "                            border-collapse: collapse;\n" +
                "                            border-spacing: 0px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              align=\"center\"\n" +
                "                              valign=\"top\"\n" +
                "                              style=\"padding: 0; margin: 0; width: 560px\"\n" +
                "                            >\n" +
                "                              <table\n" +
                "                                cellpadding=\"0\"\n" +
                "                                cellspacing=\"0\"\n" +
                "                                width=\"100%\"\n" +
                "                                role=\"presentation\"\n" +
                "                                style=\"\n" +
                "                                  mso-table-lspace: 0pt;\n" +
                "                                  mso-table-rspace: 0pt;\n" +
                "                                  border-collapse: collapse;\n" +
                "                                  border-spacing: 0px;\n" +
                "                                \"\n" +
                "                              >\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"\n" +
                "                                      padding: 0;\n" +
                "                                      margin: 0;\n" +
                "                                      padding-bottom: 10px;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <h1\n" +
                "                                      class=\"es-m-txt-c\"\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        font-size: 46px;\n" +
                "                                        font-style: normal;\n" +
                "                                        font-weight: bold;\n" +
                "                                        line-height: 46px;\n" +
                "                                        color: #333333;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      Cảm ơn bạn đã mua hàng của tôi\n" +
                "                                    </h1>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"center\"\n" +
                "                                    class=\"es-m-p0r es-m-p0l\"\n" +
                "                                    style=\"\n" +
                "                                      margin: 0;\n" +
                "                                      padding-bottom: 20px;\n" +
                "                                      padding-top: 5px;\n" +
                "                                      padding-right: 40px;\n" +
                "                                      padding-left: 40px;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <p\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        line-height: 21px;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        color: #333333;\n" +
                "                                        font-size: 14px;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      Cảm ơn bạn đã lựa chọn mua hàng của\n" +
                "                                      StyLoft vui lòng kiểu tra lại đơn hàng nếu\n" +
                "                                      có thắc mắc gì vui lòng liên hệ với chúng\n" +
                "                                      tôi tại<br /><a\n" +
                "                                        target=\"_blank\"\n" +
                "                                        href=\"\"\n" +
                "                                        style=\"\n" +
                "                                          mso-line-height-rule: exactly;\n" +
                "                                          text-decoration: underline;\n" +
                "                                          color: #5c68e2;\n" +
                "                                          font-size: 14px;\n" +
                "                                        \"\n" +
                "                                        >Facebook: StyLoft</a\n" +
                "                                      >\n" +
                "                                    </p>\n" +
                "                                    <p\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        line-height: 21px;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        color: #333333;\n" +
                "                                        font-size: 14px;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <a\n" +
                "                                        target=\"_blank\"\n" +
                "                                        href=\""+NAME_HOST+"\"\n" +
                "                                        style=\"\n" +
                "                                          mso-line-height-rule: exactly;\n" +
                "                                          text-decoration: underline;\n" +
                "                                          color: #5c68e2;\n" +
                "                                          font-size: 14px;\n" +
                "                                        \"\n" +
                "                                        >Tiếp tục mua hàng</a\n" +
                "                                      >\n" +
                "                                      và đừng quên đánh giá sản phẩm nhé !\n" +
                "                                    </p>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              align=\"center\"\n" +
                "              class=\"es-content\"\n" +
                "              role=\"none\"\n" +
                "              style=\"\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                border-collapse: collapse;\n" +
                "                border-spacing: 0px;\n" +
                "                width: 100%;\n" +
                "                table-layout: fixed !important;\n" +
                "              \"\n" +
                "            >\n" +
                "              <tr>\n" +
                "                <td align=\"center\" style=\"padding: 0; margin: 0\">\n" +
                "                  <table\n" +
                "                    bgcolor=\"#ffffff\"\n" +
                "                    align=\"center\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    class=\"es-content-body\"\n" +
                "                    role=\"none\"\n" +
                "                    style=\"\n" +
                "                      mso-table-lspace: 0pt;\n" +
                "                      mso-table-rspace: 0pt;\n" +
                "                      border-collapse: collapse;\n" +
                "                      border-spacing: 0px;\n" +
                "                      background-color: #ffffff;\n" +
                "                      width: 600px;\n" +
                "                    \"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        align=\"left\"\n" +
                "                        data-custom-paddings=\"true\"\n" +
                "                        style=\"\n" +
                "                          margin: 0;\n" +
                "                          padding-right: 20px;\n" +
                "                          padding-bottom: 10px;\n" +
                "                          padding-left: 20px;\n" +
                "                          padding-top: 20px;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        <table\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          width=\"100%\"\n" +
                "                          role=\"none\"\n" +
                "                          style=\"\n" +
                "                            mso-table-lspace: 0pt;\n" +
                "                            mso-table-rspace: 0pt;\n" +
                "                            border-collapse: collapse;\n" +
                "                            border-spacing: 0px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              align=\"center\"\n" +
                "                              valign=\"top\"\n" +
                "                              style=\"padding: 0; margin: 0; width: 560px\"\n" +
                "                            >\n" +
                "                              <table\n" +
                "                                cellpadding=\"0\"\n" +
                "                                cellspacing=\"0\"\n" +
                "                                width=\"100%\"\n" +
                "                                role=\"presentation\"\n" +
                "                                style=\"\n" +
                "                                  mso-table-lspace: 0pt;\n" +
                "                                  mso-table-rspace: 0pt;\n" +
                "                                  border-collapse: collapse;\n" +
                "                                  border-spacing: 0px;\n" +
                "                                \"\n" +
                "                              >\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"left\"\n" +
                "                                    style=\"padding: 0; margin: 0\"\n" +
                "                                  >\n" +
                "                                    <h2\n" +
                "                                      class=\"es-m-txt-l\"\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        font-size: 26px;\n" +
                "                                        font-style: normal;\n" +
                "                                        font-weight: bold;\n" +
                "                                        line-height: 31.2px;\n" +
                "                                        color: #333333;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      Đơn hàng của bạn:\n" +
                "                                    </h2>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                String.join("\n", itemOrders)+
                "                    <tr>\n" +
                "                      <td\n" +
                "                        align=\"left\"\n" +
                "                        class=\"esdev-adapt-off\"\n" +
                "                        data-custom-paddings=\"true\"\n" +
                "                        style=\"\n" +
                "                          padding: 0;\n" +
                "                          margin: 0;\n" +
                "                          padding-right: 20px;\n" +
                "                          padding-left: 20px;\n" +
                "                          padding-bottom: 20px;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        <table\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          width=\"100%\"\n" +
                "                          role=\"none\"\n" +
                "                          style=\"\n" +
                "                            mso-table-lspace: 0pt;\n" +
                "                            mso-table-rspace: 0pt;\n" +
                "                            border-collapse: collapse;\n" +
                "                            border-spacing: 0px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              align=\"center\"\n" +
                "                              class=\"es-m-p0r\"\n" +
                "                              style=\"padding: 0; margin: 0; width: 560px\"\n" +
                "                            >\n" +
                "                              <table\n" +
                "                                cellpadding=\"0\"\n" +
                "                                cellspacing=\"0\"\n" +
                "                                width=\"100%\"\n" +
                "                                style=\"\n" +
                "                                  mso-table-lspace: 0pt;\n" +
                "                                  mso-table-rspace: 0pt;\n" +
                "                                  border-collapse: collapse;\n" +
                "                                  border-spacing: 0px;\n" +
                "                                  border-top: 2px solid #efefef;\n" +
                "                                \"\n" +
                "                                role=\"presentation\"\n" +
                "                              >\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"right\"\n" +
                "                                    style=\"\n" +
                "                                      padding: 0;\n" +
                "                                      margin: 0;\n" +
                "                                      padding-top: 15px;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <h3\n" +
                "                                      class=\"es-m-txt-r\"\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        font-size: 20px;\n" +
                "                                        font-style: normal;\n" +
                "                                        font-weight: bold;\n" +
                "                                        line-height: 24px;\n" +
                "                                        color: #333333;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      Tổng tiền:&nbsp;<strong>"+(long) order.getTotal() +" VND </strong>\n" +
                "                                    </h3>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <table\n" +
                "              cellpadding=\"0\"\n" +
                "              cellspacing=\"0\"\n" +
                "              align=\"center\"\n" +
                "              class=\"es-footer\"\n" +
                "              role=\"none\"\n" +
                "              style=\"\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                border-collapse: collapse;\n" +
                "                border-spacing: 0px;\n" +
                "                width: 100%;\n" +
                "                table-layout: fixed !important;\n" +
                "                background-color: transparent;\n" +
                "                background-repeat: repeat;\n" +
                "                background-position: center top;\n" +
                "              \"\n" +
                "            >\n" +
                "              <tr>\n" +
                "                <td align=\"center\" style=\"padding: 0; margin: 0\">\n" +
                "                  <table\n" +
                "                    align=\"center\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    class=\"es-footer-body\"\n" +
                "                    style=\"\n" +
                "                      mso-table-lspace: 0pt;\n" +
                "                      mso-table-rspace: 0pt;\n" +
                "                      border-collapse: collapse;\n" +
                "                      border-spacing: 0px;\n" +
                "                      background-color: transparent;\n" +
                "                      width: 640px;\n" +
                "                    \"\n" +
                "                    role=\"none\"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        align=\"left\"\n" +
                "                        data-custom-paddings=\"true\"\n" +
                "                        style=\"\n" +
                "                          margin: 0;\n" +
                "                          padding-right: 20px;\n" +
                "                          padding-left: 20px;\n" +
                "                          padding-bottom: 20px;\n" +
                "                          padding-top: 20px;\n" +
                "                        \"\n" +
                "                      >\n" +
                "                        <table\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          width=\"100%\"\n" +
                "                          role=\"none\"\n" +
                "                          style=\"\n" +
                "                            mso-table-lspace: 0pt;\n" +
                "                            mso-table-rspace: 0pt;\n" +
                "                            border-collapse: collapse;\n" +
                "                            border-spacing: 0px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          <tr>\n" +
                "                            <td\n" +
                "                              align=\"left\"\n" +
                "                              style=\"padding: 0; margin: 0; width: 600px\"\n" +
                "                            >\n" +
                "                              <table\n" +
                "                                cellpadding=\"0\"\n" +
                "                                cellspacing=\"0\"\n" +
                "                                width=\"100%\"\n" +
                "                                role=\"presentation\"\n" +
                "                                style=\"\n" +
                "                                  mso-table-lspace: 0pt;\n" +
                "                                  mso-table-rspace: 0pt;\n" +
                "                                  border-collapse: collapse;\n" +
                "                                  border-spacing: 0px;\n" +
                "                                \"\n" +
                "                              >\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"\n" +
                "                                      padding: 0;\n" +
                "                                      margin: 0;\n" +
                "                                      padding-top: 15px;\n" +
                "                                      padding-bottom: 15px;\n" +
                "                                      font-size: 0;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <table\n" +
                "                                      cellpadding=\"0\"\n" +
                "                                      cellspacing=\"0\"\n" +
                "                                      class=\"es-table-not-adapt es-social\"\n" +
                "                                      role=\"presentation\"\n" +
                "                                      style=\"\n" +
                "                                        mso-table-lspace: 0pt;\n" +
                "                                        mso-table-rspace: 0pt;\n" +
                "                                        border-collapse: collapse;\n" +
                "                                        border-spacing: 0px;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <tr>\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          style=\"\n" +
                "                                            padding: 0;\n" +
                "                                            margin: 0;\n" +
                "                                            padding-right: 40px;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <img\n" +
                "                                            title=\"Facebook\"\n" +
                "                                            src=\"https://fljlqug.stripocdn.email/content/assets/img/social-icons/logo-black/facebook-logo-black.png\"\n" +
                "                                            alt=\"Fb\"\n" +
                "                                            width=\"32\"\n" +
                "                                            height=\"32\"\n" +
                "                                            style=\"\n" +
                "                                              display: block;\n" +
                "                                              font-size: 14px;\n" +
                "                                              border: 0;\n" +
                "                                              outline: none;\n" +
                "                                              text-decoration: none;\n" +
                "                                            \"\n" +
                "                                          />\n" +
                "                                        </td>\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          style=\"\n" +
                "                                            padding: 0;\n" +
                "                                            margin: 0;\n" +
                "                                            padding-right: 40px;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <img\n" +
                "                                            title=\"X\"\n" +
                "                                            src=\"https://fljlqug.stripocdn.email/content/assets/img/social-icons/logo-black/x-logo-black.png\"\n" +
                "                                            alt=\"X\"\n" +
                "                                            width=\"32\"\n" +
                "                                            height=\"32\"\n" +
                "                                            style=\"\n" +
                "                                              display: block;\n" +
                "                                              font-size: 14px;\n" +
                "                                              border: 0;\n" +
                "                                              outline: none;\n" +
                "                                              text-decoration: none;\n" +
                "                                            \"\n" +
                "                                          />\n" +
                "                                        </td>\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          style=\"\n" +
                "                                            padding: 0;\n" +
                "                                            margin: 0;\n" +
                "                                            padding-right: 40px;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <img\n" +
                "                                            title=\"Instagram\"\n" +
                "                                            src=\"https://fljlqug.stripocdn.email/content/assets/img/social-icons/logo-black/instagram-logo-black.png\"\n" +
                "                                            alt=\"Inst\"\n" +
                "                                            width=\"32\"\n" +
                "                                            height=\"32\"\n" +
                "                                            style=\"\n" +
                "                                              display: block;\n" +
                "                                              font-size: 14px;\n" +
                "                                              border: 0;\n" +
                "                                              outline: none;\n" +
                "                                              text-decoration: none;\n" +
                "                                            \"\n" +
                "                                          />\n" +
                "                                        </td>\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          style=\"padding: 0; margin: 0\"\n" +
                "                                        >\n" +
                "                                          <img\n" +
                "                                            title=\"Youtube\"\n" +
                "                                            src=\"https://fljlqug.stripocdn.email/content/assets/img/social-icons/logo-black/youtube-logo-black.png\"\n" +
                "                                            alt=\"Yt\"\n" +
                "                                            width=\"32\"\n" +
                "                                            height=\"32\"\n" +
                "                                            style=\"\n" +
                "                                              display: block;\n" +
                "                                              font-size: 14px;\n" +
                "                                              border: 0;\n" +
                "                                              outline: none;\n" +
                "                                              text-decoration: none;\n" +
                "                                            \"\n" +
                "                                          />\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                  <td\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"\n" +
                "                                      padding: 0;\n" +
                "                                      margin: 0;\n" +
                "                                      padding-bottom: 35px;\n" +
                "                                    \"\n" +
                "                                  >\n" +
                "                                    <p\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        line-height: 18px;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        color: #333333;\n" +
                "                                        font-size: 12px;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      StyleLoft © 2021 Style Casual, Inc. All\n" +
                "                                      Rights Reserved.\n" +
                "                                    </p>\n" +
                "                                    <p\n" +
                "                                      style=\"\n" +
                "                                        margin: 0;\n" +
                "                                        mso-line-height-rule: exactly;\n" +
                "                                        font-family: arial, 'helvetica neue',\n" +
                "                                          helvetica, sans-serif;\n" +
                "                                        line-height: 18px;\n" +
                "                                        letter-spacing: 0;\n" +
                "                                        color: #333333;\n" +
                "                                        font-size: 12px;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      4562 Hazy Panda Limits, Chair Crossing,\n" +
                "                                      Kentucky, US, 607898\n" +
                "                                    </p>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                  <td style=\"padding: 0; margin: 0\">\n" +
                "                                    <table\n" +
                "                                      cellpadding=\"0\"\n" +
                "                                      cellspacing=\"0\"\n" +
                "                                      width=\"100%\"\n" +
                "                                      class=\"es-menu\"\n" +
                "                                      role=\"presentation\"\n" +
                "                                      style=\"\n" +
                "                                        mso-table-lspace: 0pt;\n" +
                "                                        mso-table-rspace: 0pt;\n" +
                "                                        border-collapse: collapse;\n" +
                "                                        border-spacing: 0px;\n" +
                "                                      \"\n" +
                "                                    >\n" +
                "                                      <tr class=\"links\">\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          width=\"33.33%\"\n" +
                "                                          style=\"\n" +
                "                                            margin: 0;\n" +
                "                                            border: 0;\n" +
                "                                            padding-top: 5px;\n" +
                "                                            padding-bottom: 5px;\n" +
                "                                            padding-right: 5px;\n" +
                "                                            padding-left: 5px;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <a\n" +
                "                                            target=\"_blank\"\n" +
                "                                            href=\"\"\n" +
                "                                            style=\"\n" +
                "                                              mso-line-height-rule: exactly;\n" +
                "                                              text-decoration: none;\n" +
                "                                              font-family: arial,\n" +
                "                                                'helvetica neue', helvetica,\n" +
                "                                                sans-serif;\n" +
                "                                              display: block;\n" +
                "                                              color: #999999;\n" +
                "                                              font-size: 12px;\n" +
                "                                            \"\n" +
                "                                            >Visit Us\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          width=\"33.33%\"\n" +
                "                                          style=\"\n" +
                "                                            margin: 0;\n" +
                "                                            border: 0;\n" +
                "                                            padding-top: 5px;\n" +
                "                                            padding-bottom: 5px;\n" +
                "                                            padding-right: 5px;\n" +
                "                                            padding-left: 5px;\n" +
                "                                            border-left: 1px solid #cccccc;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <a\n" +
                "                                            target=\"_blank\"\n" +
                "                                            href=\"\"\n" +
                "                                            style=\"\n" +
                "                                              mso-line-height-rule: exactly;\n" +
                "                                              text-decoration: none;\n" +
                "                                              font-family: arial,\n" +
                "                                                'helvetica neue', helvetica,\n" +
                "                                                sans-serif;\n" +
                "                                              display: block;\n" +
                "                                              color: #999999;\n" +
                "                                              font-size: 12px;\n" +
                "                                            \"\n" +
                "                                            >Privacy Policy</a\n" +
                "                                          >\n" +
                "                                        </td>\n" +
                "                                        <td\n" +
                "                                          align=\"center\"\n" +
                "                                          valign=\"top\"\n" +
                "                                          width=\"33.33%\"\n" +
                "                                          style=\"\n" +
                "                                            margin: 0;\n" +
                "                                            border: 0;\n" +
                "                                            padding-top: 5px;\n" +
                "                                            padding-bottom: 5px;\n" +
                "                                            padding-right: 5px;\n" +
                "                                            padding-left: 5px;\n" +
                "                                            border-left: 1px solid #cccccc;\n" +
                "                                          \"\n" +
                "                                        >\n" +
                "                                          <a\n" +
                "                                            target=\"_blank\"\n" +
                "                                            href=\"\"\n" +
                "                                            style=\"\n" +
                "                                              mso-line-height-rule: exactly;\n" +
                "                                              text-decoration: none;\n" +
                "                                              font-family: arial,\n" +
                "                                                'helvetica neue', helvetica,\n" +
                "                                                sans-serif;\n" +
                "                                              display: block;\n" +
                "                                              color: #999999;\n" +
                "                                              font-size: 12px;\n" +
                "                                            \"\n" +
                "                                            >Terms of Use</a\n" +
                "                                          >\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </table>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";
        return content;
    }
    public String MailOrderStatus(Order oldOrder, User user)
    {


        String emailContent="";
        if(oldOrder.getStatus().equals(StatusOrder.COMPLETED)) {

            String randomCode ="P" +  user.getAccount().getId().substring(0, 4) +  RandomStringUtils.randomAlphanumeric(4);

            Voucher voucher = Voucher
                    .builder()
                    .code(randomCode)
                    .active(true)
                    .discountValue(10.0)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(30))
                    .build();
            voucherService.saveVoucher(voucher);
             emailContent = "<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                    "<h2 style='color: #ff6600;'>Đơn hàng của bạn đã hoàn tất</h2>" +
                    "<p>StyloftCloth</p>" +
                    "<h3 style='color: #28a745;'>Thông báo: Đơn hàng của bạn đã được hoàn thành!</h3>" +
                    "<p style='color: #333;'>Cảm ơn bạn đã tin tưởng và mua sắm tại StyloftCloth. Đơn hàng của bạn với mã <strong>" + oldOrder.getId().substring(0, 5) + "</strong> đã được hoàn thành.</p>" +
                    "<p style='color: #333;'>Chúng tôi hy vọng bạn hài lòng với sản phẩm của mình. Nếu có bất kỳ vấn đề gì, đừng ngần ngại liên hệ với chúng tôi qua email hoặc hotline để được hỗ trợ kịp thời.</p>" +
                    "<a href='"+NAME_HOST+"' style='color: #0066cc;'>Xem chi tiết đơn hàng</a>" +
                     " <p style='color: #333;'> Tặng bạn voucher cho đơn hàng tiếp theo: <strong>" + randomCode + "</strong> hạn tới ngày: <strong> " + voucher.getEndDate().toString() + "</strong> là <strong> 10% giá trị đơn hàng </strong> </p>"+
                    "<br><br><p>Best regards,<br>StyLoft</p></div></body></html>";
        } if (oldOrder.getStatus().equals(StatusOrder.RETURNED)) {
            emailContent = "<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                "<h2 style='color: #ff6600;'>Đơn hàng của bạn đã được xác nhận hoàn trả</h2>" +
                "<p>StyloftCloth</p>" +
                "<p style='color: #333;'>Xin chào <strong>" + user.getName() + "</strong>,</p>" +
                "<p style='color: #333;'>Đơn hàng của bạn với mã <strong>" + oldOrder.getId().substring(0, 5) + "</strong> đã được chúng tôi xác nhận hoàn trả.</p>" +
                "<p style='color: #333;'>Chúng tôi rất tiếc vì sản phẩm không đáp ứng được mong đợi của bạn. Bạn có thể gửi lại sản phẩm qua địa chỉ bên dưới:</p>" +
                "<p style='color: #333;'><strong>StyloftCloth Return Center</strong></p>" +
                "<p style='color: #333;'>123 Đường Hoàn Trả, Quận Hoàn Kiếm, Hà Nội, Việt Nam</p>" +
                "<p style='color: #333;'>Lưu ý: Vui lòng ghi rõ mã đơn hàng trên gói hàng để chúng tôi xử lý nhanh chóng.</p>" +
                "<h3 style='color: #28a745;'>Chúng tôi luôn sẵn sàng hỗ trợ bạn!</h3>" +
                "<p style='color: #333;'>Nếu bạn có bất kỳ câu hỏi nào hoặc cần hỗ trợ thêm, vui lòng liên hệ với chúng tôi qua email hoặc hotline để được trợ giúp.</p>" +
                "<a href=/orders' style='color: #0066cc;'>Kiểm tra trạng thái hoàn trả</a>" +
                "<br><br><p>Best regards,<br>StyLoft</p></div></body></html>";
        }
        return emailContent;
    }
    public String MailLogin(Account account,Voucher voucher)
    {
        String content="<html><body><div style='background-color: #f0f0f0; padding: 20px;'>" +
                "<h2 style='color: #ff6600;'>Welcome to our service!</h2>" +
                "<p>StyloftCloth</p>" +
                "<p style='color: #333;'>Bạn đã đăng kí thành công tài khoản <strong>" + account.getEmail() + "</strong> và giờ bạn có thể sử dụng dịch vụ bên chúng tôi.</p>" +
                "<p style='color: #333;'>Chúc <strong>bạn</strong> có thời gian mua sắm vui vẻ!!!</p>" +
                "<p style='color: #333;'>Nếu có thắc mắc gì mong <strong>bạn</strong> phản hồi lại sớm cho bên chúng tôi</p>" +
                "<a href='"+NAME_HOST+"' style='color: #0066cc;'>Visit our website</a>" +
                "<p style='color: #333;'>Mã voucher thành viên mới của bạn là: <strong>" + voucher.getCode() + "</strong> 10% cho sản phẩm tới ngày: " + voucher.getEndDate() + " </p>"+
                "<br><br><p>Best regards,<br>StyLoft</p></div></body></html>";
        return content;
    }
    public String MailFoget(String email,String pass){
        String content = "<html><body>" +
                "<div style='background-color: #f9f9f9; padding: 20px; font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #4CAF50; text-align: center;'>Thay đổi mật khẩu thành công!</h2>" +
                "<p style='color: #333;'>Xin chào <strong>" + email + "</strong>,</p>" +
                "<p style='color: #333;'>Mật khẩu của bạn đã được thay đổi thành công. Nếu bạn không thực hiện yêu cầu này, vui lòng liên hệ ngay với chúng tôi để được hỗ trợ.</p>" +
                "<p style='color: #333;'>Mật khẩu mới của bạn là: <strong>" + pass + "</strong></p>" +
                "<p style='color: #333;'>Bạn có thể đăng nhập tài khoản của mình tại liên kết bên dưới:</p>" +
                "<div style='text-align: center; margin: 20px 0;'>" +
                "<a href="+NAME_HOST+"/login style='background-color: #0066cc; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Đăng nhập ngay</a>" +
                "</div>" +
                "<p style='color: #333;'>Nếu có bất kỳ thắc mắc nào, hãy liên hệ với đội ngũ hỗ trợ của chúng tôi qua email hoặc hotline.</p>" +
                "<br><p>Trân trọng,<br>Đội ngũ hỗ trợ StyLoft</p>" +
                "</div>" +
                "</body></html>";
        return content;

    }
}

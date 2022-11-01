package com.ccrt.onlineshop.shared;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.ccrt.onlineshop.shared.dto.UserDto;

@Component
public class AmazonSES {
  private final String FROM = "rafi1017623150@gmail.com";
  private final String SUBJECT = "[CCRT Clinic] Email Verification Code";
  private final String PASSWORD_RESET_CODE_EMAIL_SUBJECT = "[CCRT Clinic] Password reset code.";

  public void sendVerificationEmail(String email, String code) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();

    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(getHTMLBody(code)))
                .withText(new Content().withCharset("UTF-8").withData(getTextBody(code))))
            .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
        .withSource(FROM);
    client.sendEmail(sendEmailRequest);

  }

  public void sendPasswordResetCode(UserDto userDto, String code) {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
        .build();
    String email = userDto.getEmail();
    String fullName = userDto.getFirstName() + " " + userDto.getLastName();
    SendEmailRequest sendEmailRequest = new SendEmailRequest()
        .withDestination(new Destination().withToAddresses(email))
        .withMessage(new Message()
            .withBody(new Body()
                .withHtml(new Content().withCharset("UTF-8").withData(getPrescriptionViewCodeEmailBody(fullName, code)))
                .withText(
                    new Content().withCharset("UTF-8").withData(getPrescriptionViewCodeEmailText(fullName, code))))
            .withSubject(new Content().withCharset("UTF-8").withData(PASSWORD_RESET_CODE_EMAIL_SUBJECT)))
        .withSource(FROM);
    try {
      client.sendEmail(sendEmailRequest);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private String getHTMLBody(String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Your email verification code is </p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<h1>");
    stringBuilder.append(code);
    stringBuilder.append("</h1>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getTextBody(String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Your email verification code is ");
    stringBuilder.append(code);
    return stringBuilder.toString();
  }

  private String getPrescriptionViewCodeEmailBody(String name, String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("<div>");
    stringBuilder.append("<p>Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",</p>");

    stringBuilder.append("</br>");
    stringBuilder.append("<p>");
    stringBuilder.append(
        "Your prescription view code is");
    stringBuilder.append("</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<h1>");
    stringBuilder.append(
        code);
    stringBuilder.append("</h1>");
    stringBuilder.append("</br>");

    stringBuilder.append("<p>Thanks,</p>");
    stringBuilder.append("</br>");
    stringBuilder.append("<p>CCRT Clinic</p>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  private String getPrescriptionViewCodeEmailText(String name, String code) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder.append("Dear ");
    stringBuilder.append(name);
    stringBuilder.append(",\n");
    stringBuilder.append(
        "Your password reset code is \n");
    stringBuilder.append(
        code);
    stringBuilder.append("Thanks\n");
    stringBuilder.append("CCRT Clinic");
    return stringBuilder.toString();
  }
}

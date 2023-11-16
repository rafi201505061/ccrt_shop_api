package com.ccrt.onlineshop.shared;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.ccrt.onlineshop.shared.dto.EmailTemplate;
import com.ccrt.onlineshop.shared.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AmazonSES {
  // private final String FROM = "rafi1017623150@gmail.com";
  private final String SUBJECT = "[CCRT Clinic] Email Verification Code";
  private final String PASSWORD_RESET_CODE_EMAIL_SUBJECT = "[CCRT Clinic] Password reset code.";
  private WebClient webClient = WebClient.create("http://178.128.101.29:8002");

  public void sendVerificationEmail(String email, String code) {
    // AmazonSimpleEmailService client =
    // AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
    // .build();

    // SendEmailRequest sendEmailRequest = new SendEmailRequest()
    // .withDestination(new Destination().withToAddresses(email))
    // .withMessage(new Message()
    // .withBody(new Body().withHtml(new
    // Content().withCharset("UTF-8").withData(getHTMLBody(code)))
    // .withText(new Content().withCharset("UTF-8").withData(getTextBody(code))))
    // .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
    // .withSource(FROM);
    // client.sendEmail(sendEmailRequest);

    try {
      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, SUBJECT, getTextBody(code), "html", getHTMLBody(code));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

  }

  public void sendPasswordResetCode(UserDto userDto, String code) {
    // AmazonSimpleEmailService client =
    // AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
    // .build();
    // String email = userDto.getEmail();
    // String fullName = userDto.getFirstName() + " " + userDto.getLastName();
    // SendEmailRequest sendEmailRequest = new SendEmailRequest()
    // .withDestination(new Destination().withToAddresses(email))
    // .withMessage(new Message()
    // .withBody(new Body()
    // .withHtml(new
    // Content().withCharset("UTF-8").withData(getPrescriptionViewCodeEmailBody(fullName,
    // code)))
    // .withText(
    // new
    // Content().withCharset("UTF-8").withData(getPrescriptionViewCodeEmailText(fullName,
    // code))))
    // .withSubject(new
    // Content().withCharset("UTF-8").withData(PASSWORD_RESET_CODE_EMAIL_SUBJECT)))
    // .withSource(FROM);
    // try {
    // client.sendEmail(sendEmailRequest);
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // }
    try {
      String endpoint = "/api/v1/send-email";
      HttpHeaders headers = new HttpHeaders();
      String email = userDto.getEmail();
      String fullName = userDto.getFirstName() + " " + userDto.getLastName();

      headers.set("Content-Type", "application/json");
      EmailTemplate emailTemplate = new EmailTemplate(email, PASSWORD_RESET_CODE_EMAIL_SUBJECT,
          getPrescriptionViewCodeEmailText(fullName, code), "html", getPrescriptionViewCodeEmailBody(fullName, code));
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString;
      jsonString = objectMapper.writeValueAsString(emailTemplate);
      webClient.post()
          .uri(endpoint)
          .headers(httpHeaders -> httpHeaders.addAll(headers))
          .body(BodyInserters.fromValue(jsonString))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
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

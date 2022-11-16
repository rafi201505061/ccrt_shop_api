package com.ccrt.onlineshop.shared;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {
  public static final String CATEGORY_UPLOAD_DIR = "C:\\rakibul\\Office\\ccrt-online-shop\\ccrt-online-shop\\public\\categories";
  public static final String SUB_CATEGORY_UPLOAD_DIR = "C:\\rakibul\\Office\\ccrt-online-shop\\ccrt-online-shop\\public\\sub-categories";
  public static final String COVER_UPLOAD_DIR = "C:\\rakibul\\Office\\ccrt-online-shop\\ccrt-online-shop\\public\\covers";
  public static final String PRODUCT_UPLOAD_DIR = "C:\\rakibul\\Office\\ccrt-online-shop\\ccrt-online-shop\\public\\products";

  // public static final String PROFILE_PICTURE_UPLOAD_DIR =
  // "C:\\rakibul\\Office\\CCRT Clinic\\ccrt-clinic\\public\\users";
  // public static final String APPOINTMENT_RESOURCES_UPLOAD_DIR =
  // "C:\\rakibul\\Office\\CCRT Clinic\\ccrt-clinic\\public\\appointments";
  // public static final String USER_REPORTS_UPLOAD_DIR =
  // "C:\\rakibul\\Office\\CCRT Clinic\\ccrt-clinic\\public\\uploads";

  public String saveFile(String uploadDir, String fileName,
      MultipartFile multipartFile) throws IOException {
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    InputStream inputStream = multipartFile.getInputStream();
    Path filePath = uploadPath.resolve(fileName);
    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    return filePath.toString();
  }
}

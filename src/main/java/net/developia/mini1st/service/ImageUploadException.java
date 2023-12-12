package net.developia.mini1st.service;
// ImageUploadException.java
public class ImageUploadException extends RuntimeException {
    public ImageUploadException() {
        super("Failed to upload image.");
    }

    public ImageUploadException(String message) {
        super(message);
    }

    // 추가적인 생성자 또는 메서드를 필요에 따라 정의할 수 있습니다.
}
package net.developia.mini1st.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.UserDTO;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service {
    private final AmazonS3 amazonS3;

    private final UserService userService;
    private final PetBoardService petBoardService;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름


    private String changedImageName(String originName) { //이미지 이름 중복 방지를 위해 랜덤으로 생성
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    private String uploadImageToS3(MultipartFile image) { //이미지를 S3에 업로드하고 이미지의 url을 반환
        String originName = image.getOriginalFilename(); //원본 이미지 이름
        String ext = originName.substring(originName.lastIndexOf(".")); //확장자
        String changedName = changedImageName(originName); //새로 생성된 이미지 이름
        ObjectMetadata metadata = new ObjectMetadata(); //메타데이터

        metadata.setContentType("png/" + ext);
        metadata.setContentLength(image.getSize());

        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucketName, changedName, image.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ImageUploadException(); //커스텀 예외 던짐.
        }
        return amazonS3.getUrl(bucketName, changedName).toString(); //데이터베이스에 저장할 이미지가 저장된 주소

    }


    // 프로필 수정 메서드
    public void uploadProfile(MultipartFile image, UserDTO userDTO) {
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImageToS3(image);
            userDTO.setImg_url(imageUrl);
            userDTO.setNickname(userDTO.getNickname());
            userService.updateUserProfile(userDTO);
        }
    }


    public String responseUrl(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            return uploadImageToS3(imageFile);
        }else {
            return "False";
        }
    }
    // 자랑 게시판 글 이미지 등록 메서드
    public void uploadbestPets(MultipartFile image, PetBoardDTO petBoardDTO) {
        if (image != null && !image.isEmpty()) {
            System.out.println("image = " + image);
            String imageUrl = uploadImageToS3(image);
            petBoardDTO.setPhoto(imageUrl);
            System.out.println(" === uploadbestPets === ");
            System.out.println("petBoardDTO = " + petBoardDTO);
            petBoardService.insertPetBoard(petBoardDTO);
        }
    }
    public String updatebestPets(MultipartFile image,PetBoardDTO petBoardDTO){
        if (image != null && !image.isEmpty()){
            String imageUrl = uploadImageToS3(image);
            petBoardDTO.setPhoto(imageUrl);
            petBoardService.updatePetBoard(petBoardDTO);
        }else {
            return "False";
        }
        return "True";
    }
}

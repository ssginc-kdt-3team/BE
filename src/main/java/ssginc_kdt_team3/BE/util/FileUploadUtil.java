package ssginc_kdt_team3.BE.util;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class FileUploadUtil {

    public static Optional<File> saveFile(MultipartFile mf) throws IOException {

        log.info("세이브 파일 진입");

        byte[] data;

        String imgName = mf.getOriginalFilename();

        File convertFile = new File(imgName);

        if (convertFile.createNewFile()) {
            data = mf.getBytes();
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(data);
                log.info("파일 변환 성공");
            }
            log.info("파일 변환 실패");
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
}

package ssginc_kdt_team3.BE.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class FileUploadUtil {

    public static Optional<File> saveFile(MultipartFile mf) throws IOException {

        byte[] data;
        String imgname = mf.getOriginalFilename();

        File convertFile = new File(imgname);

        if (convertFile.createNewFile()) {
            data = mf.getBytes();
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(data);
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}

package com.igorkunicyn.springfilestore.services;

import com.igorkunicyn.springfilestore.entities.FileMetaDTO;
import com.igorkunicyn.springfilestore.repositories.interfaces.IFileMetaProvider;
import com.igorkunicyn.springfilestore.repositories.interfaces.IFileSystemProvider;
import com.igorkunicyn.springfilestore.services.interfaces.IFileStoreService;
import com.igorkunicyn.springfilestore.utils.HashHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileStoreService implements IFileStoreService {

    @Autowired
    IFileSystemProvider systemProvider;

    @Autowired
    IFileMetaProvider fileMetaProvider;

    @Override
    public String storeFile(MultipartFile file, int subFileType) throws IOException, NoSuchAlgorithmException {
        final UUID md5 = HashHelper.getMd5Hash(file.getBytes());
        String data = new Date().toString();
        String filename = fileMetaProvider.checkFileExists(md5);
        if (filename == null) {
            fileMetaProvider.saveFileMeta(md5, file.getOriginalFilename(), subFileType, file.getSize(), data);
            filename = systemProvider.storeFile(file.getBytes(), md5, file.getOriginalFilename());
        }

        return filename;
    }

    @Override
    public byte[] getFile(UUID md5) throws IOException {
       String filename = fileMetaProvider.checkFileExists(md5);
       String ext = FilenameUtils.getExtension(filename);
       String fullFileName = md5.toString() + "." + ext;
       return systemProvider.getFile(fullFileName);
    }

    @Override
    public Collection<FileMetaDTO> getMetaFiles(int subtype) {
        return fileMetaProvider.getMetaFiles(subtype);
    }

    @Override
    public byte[] getPackageFiles(List<UUID> hashList) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(); вместо 68-69 строки
//        ZipOutputStream zip = new ZipOutputStream(baos);  вместо 70 строки
        File file = new File("C:\\java\\spring-file-store\\spring-" +
                "file-store\\Arhives\\output.zip");
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(file));
            for (UUID hash: hashList) {
                String filename = fileMetaProvider.checkFileExists(hash);
                String ext = FilenameUtils.getExtension(filename);
                String fullFileName = hash.toString() + "." + ext;
                ZipEntry entry = new ZipEntry(fullFileName);
                zip.putNextEntry(entry);
                zip.write(getFile(hash));
            }
        zip.closeEntry();
        zip.close();
//        return baos.toByteArray; вместо 82 - 85 строк
        byte[] bytes = new byte[(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        return bytes;
    }
}

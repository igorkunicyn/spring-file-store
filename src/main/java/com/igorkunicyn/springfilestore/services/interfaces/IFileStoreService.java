package com.igorkunicyn.springfilestore.services.interfaces;

import com.igorkunicyn.springfilestore.entities.FileMetaDTO;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IFileStoreService {

    String storeFile(MultipartFile file, int subType) throws IOException, NoSuchAlgorithmException;

    byte[] getFile(UUID md5) throws IOException;

    Collection<FileMetaDTO> getMetaFiles(int subtype);

    byte[] getPackageFiles(List<UUID> hashList) throws IOException;
}

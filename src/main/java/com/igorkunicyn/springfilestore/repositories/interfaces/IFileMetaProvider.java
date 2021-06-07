package com.igorkunicyn.springfilestore.repositories.interfaces;

import com.igorkunicyn.springfilestore.entities.FileMetaDTO;

import java.util.Collection;
import java.util.UUID;

public interface IFileMetaProvider {
    String checkFileExists(UUID fileHash);

    void saveFileMeta(UUID Hash, String fileName, int subType, long size, String data);

    Collection<FileMetaDTO> getMetaFiles(int subType);
}

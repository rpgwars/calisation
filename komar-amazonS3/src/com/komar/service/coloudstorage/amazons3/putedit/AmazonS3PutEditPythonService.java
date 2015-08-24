package com.komar.service.coloudstorage.amazons3.putedit;

import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.service.cloudstorage.put.PutEditService;
import com.komar.service.cloudstorage.put.PutException;
import com.komar.service.coloudstorage.amazons3.AmazonS3KeyGenerator;
import com.komar.service.coloudstorage.amazons3.put.AmazonS3PutResultTO;
import com.komar.domain.resource.transfer.Edit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

@Service
public class AmazonS3PutEditPythonService implements PutEditService {

    private String pythonSrcriptPath;
    private String tmpFilesFolderPath;
    private String profileName;
    private String bucketName;

    @Autowired
    private AmazonS3KeyGenerator keyGenerator;

    @Override
    public PutResultTO putEdit(UploadedFile file, Edit edit) throws PutException {
        String key = keyGenerator.getKey();
        saveFile(file, key);
        String link = executePythonScripts(file, edit, key);
        return new AmazonS3PutResultTO(bucketName, key, link, file.getFile().getContentType());
    }

    private void saveFile(UploadedFile file, String key) throws PutException {
        File ordinaryFile = new File(getTemporaryFilePath(file, key));
        try {
            ordinaryFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(ordinaryFile);
            fileOutputStream.write(file.getFile().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            throw new PutException();
        }

    }

    private String getTemporaryFilePath(UploadedFile file, String key) {
        return tmpFilesFolderPath + key + file.getExtension();
    }

    private String executePythonScripts(UploadedFile file, Edit edit, String key) throws PutException {
        try {

            String command;
            switch (file.getResourceType()) {
                case AUDIO:
                    command = String.format("python %s %s %f %f %s %s",
                            pythonSrcriptPath, getTemporaryFilePath(file, key), edit.getStart(), edit.getEnd(), bucketName, profileName);
                    break;
                case VIDEO:
                    command = String.format("python %s %s %f %f %b %s %s",
                            pythonSrcriptPath, getTemporaryFilePath(file, key), edit.getStart(), edit.getEnd(), edit.isWithAudio(), bucketName, profileName);
                    break;
                default:
                    throw new RuntimeException("Unknown media type");
            }

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            String line;
            String link = null;
            while ((line = stdInput.readLine()) != null) {
                System.out.println(line);
                link = line;
            }
            try {
                if(p.waitFor() != 0)
                    throw new PutException();
            } catch (InterruptedException e) {
                throw new PutException();
            }
            return link;
        } catch (IOException e) {
            throw new PutException();
        }
    }

    @Override
    public PutResultTO put(UploadedFile file) throws PutException {
        throw new NotImplementedException();
    }

    public void setPythonSrcriptPath(String pythonSrcriptPath) {
        this.pythonSrcriptPath = pythonSrcriptPath;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setKeyGenerator(AmazonS3KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setTmpFilesFolderPath(String tmpFilesFolderPath) {
        this.tmpFilesFolderPath = tmpFilesFolderPath;
    }
}

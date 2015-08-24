package com.komar.service.cloudstorage.put;

import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.domain.resource.transfer.Edit;

public interface PutEditService extends PutService{

    PutResultTO putEdit(UploadedFile file, Edit edit) throws PutException;
}

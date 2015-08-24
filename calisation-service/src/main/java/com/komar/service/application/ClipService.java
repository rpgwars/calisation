package com.komar.service.application;

import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.ClipTO;
import com.komar.service.application.exception.MaximalClipsNumberExceeded;

import java.util.List;

public interface ClipService {

    List<ClipTO> getClips(String login);
    List<ClipTO> saveClip(PutResultTO putResultTO, String login, String name, ResourceType resourceType, boolean withAudio)
            throws AccountNotFound, MaximalClipsNumberExceeded;
    void deleteClip(String login, String url);
}

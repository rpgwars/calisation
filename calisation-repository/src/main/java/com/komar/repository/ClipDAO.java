package com.komar.repository;

import com.komar.domain.account.Account;
import com.komar.domain.cloudstorage.resource.Clip;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;

import java.util.List;

public interface ClipDAO {

    Clip saveClip(PutResultTO putResultTO, Account account, String name, ResourceType resourceType, boolean withAudio);
    List<Clip> getClips(String login);
    void deleteClip(String login, String url);
}

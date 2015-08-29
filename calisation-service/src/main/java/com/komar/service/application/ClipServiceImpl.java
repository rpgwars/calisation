package com.komar.service.application;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.cloudstorage.resource.Clip;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.ClipTO;
import com.komar.repository.AccountDAO;
import com.komar.repository.ClipDAO;
import com.komar.service.application.exception.MaximalClipsNumberExceeded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClipServiceImpl implements ClipService{

    private static final int maxClipsNumberAllowed = 100;

    @Autowired
    private ClipDAO clipDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ClipTO> getClips(String login) {
        List<Clip> clips = clipDAO.getClips(login);
        return clips.stream().map(clip -> clip.toTO()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ClipTO> saveClip(PutResultTO putResultTO, String login, String name, ResourceType resourceType, boolean withAudio) throws AccountNotFound, MaximalClipsNumberExceeded {
        Account account = accountDAO.findAccount(login);
        List<Clip> clips = account.getClips();
        if(clips.size() >= maxClipsNumberAllowed)
            throw new MaximalClipsNumberExceeded();
        clipDAO.saveClip(putResultTO, account, name, resourceType, withAudio);
        List<ClipTO> clipsTOs = clips.stream().map(clip -> clip.toTO()).collect(Collectors.toList());
        return clipsTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAddingClipPossible(String login) throws AccountNotFound, MaximalClipsNumberExceeded {
        Account account = accountDAO.findAccount(login);
        List<Clip> clips = account.getClips();
        if(clips.size() >= maxClipsNumberAllowed)
            throw new MaximalClipsNumberExceeded();
        return true;
    }

    @Override
    @Transactional
    public void deleteClip(String login, String url){
        clipDAO.deleteClip(login, url);
    }

    public void setClipDAO(ClipDAO clipDAO) {
        this.clipDAO = clipDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}

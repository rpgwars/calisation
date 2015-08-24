package com.komar.service.application;


import com.komar.domain.resource.transfer.ResourceTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceRetrievalServiceImpl implements ResourceRetrievalService{
    @Override
    public List<ResourceTO> getResources(String login) {
        return null;
    }
}

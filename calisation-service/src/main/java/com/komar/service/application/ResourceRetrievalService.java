package com.komar.service.application;

import com.komar.domain.resource.transfer.ResourceTO;

import java.util.List;

public interface ResourceRetrievalService {
    List<ResourceTO> getResources(String login);
}

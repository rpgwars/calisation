package com.komar.domain.resource.transfer;

import java.time.LocalDateTime;
import java.util.List;

public class ResourceTO {

    private String name;
    private LocalDateTime localDateTime;
    private String retrievalLink;

    private List<ResourceLinkTO> resourceLinkTOList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getRetrievalLink() {
        return retrievalLink;
    }

    public void setRetrievalLink(String retrievalLink) {
        this.retrievalLink = retrievalLink;
    }
}

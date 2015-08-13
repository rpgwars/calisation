package com.komar.domain.cloudstorage.resource.transfer.put;

import java.util.List;

public abstract class PutResultTO
{
	protected String retrievalData;

	protected String retrievalLink;

	protected String mimeType;
	
	protected PutResultTO(String retrievalData, String retrievalLink, String mimeType)
	{
		this.retrievalData = retrievalData;
		this.retrievalLink = retrievalLink;
		this.mimeType = mimeType;
	}

	public String getRetrievalData() {
		return retrievalData;
	}

	public String getRetrievalLink() { return  retrievalLink; }

	public String getMimeType() {
		return mimeType;
	}

	public abstract List<String> getSplittedRetrievalData(String retrievalData);

	public abstract String getStorageProviderName();
	
	@Override
	public String toString(){
		return "PutResultTO " + retrievalData;
	}
}

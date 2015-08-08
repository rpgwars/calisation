package com.komar.domain.transfer.cloudstorage.put;

import java.util.List;

public abstract class PutResultTO
{
	protected String retrievalData;
	
	protected PutResultTO(String retrievalData)
	{
		this.retrievalData = retrievalData;
	}
	
	protected abstract List<String> getSplittedRetrievalData(String retrievalData);
	
	@Override
	public String toString(){
		return "PutResultTO " + retrievalData;
	}
}

package com.komar.domain.transfer.cloudstorage.put;

import java.util.List;

public abstract class PutResult 
{
	protected String retrievalData;
	
	protected PutResult(String retrievalData)
	{
		this.retrievalData = retrievalData;
	}
	
	protected abstract List<String> getSplittedRetrievalData(String retrievalData);
	
	@Override
	public String toString(){
		return "PutResult " + retrievalData; 
	}
}

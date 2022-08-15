package com.yindangu.plugin.demo;

import java.io.File;
import java.io.InputStream;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.file.api.model.IAppFileInfo;
import com.yindangu.v3.business.plugin.business.api.file.storage.IFileStorageService;

public class MyFileStorageService implements IFileStorageService {

	public static final String FileStorage_Code = "my_fss";
	
	@Override
	public IAppFileInfo upload(File file, IAppFileInfo appFileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAppFileInfo upload(InputStream inputStream, IAppFileInfo appFileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAppFileInfo getAppFileInfo(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteFiles(String[] fileIds) {
		// TODO Auto-generated method stub
		
	}

}

package com.shiyuanhui.util;

import java.util.ArrayList;
import java.util.Collections;

import android.text.TextUtils;

public class DirectorySplit {
	/**
	 * 拆分地址上/的方法，该方法不用关注，可废弃
	 * @param directory
	 * @return
	 */
	public synchronized static ArrayList<String> handleDirectory(String directory){
		ArrayList<String> directorylist = new ArrayList<String>(0);
		if(!TextUtils.isEmpty(directory)){
			directory = directory.replaceAll("~","/" );
			String[] a = directory.split(";;;");
			if(a!=null && a.length>0){
				Collections.addAll(directorylist, a);

			}
			return directorylist;
		}

		else return null;
	}
}

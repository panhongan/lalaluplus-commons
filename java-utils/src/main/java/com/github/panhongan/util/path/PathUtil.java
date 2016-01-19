package com.github.panhongan.util.path;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.panhongan.util.StringUtil;

public class PathUtil {
	
	public static String absolutePath(String path) {
		String abs_path = null;
		
		if (!StringUtil.isEmpty(path)) {
			File file = new File(path);
			abs_path = file.getAbsolutePath();
		}
		
		return abs_path;
	}
	
	public static List<String> recursivePathList(String path) {
		List<String> list = null;
		
		if (path != null) {
			String[] arr = path.split("/");
			if (arr != null && arr.length > 0) {
				String abs_path = "";
				list = new ArrayList<String>();
	
				for (int i = 0; i < arr.length; ++i) {
					if (!StringUtil.isEmpty(arr[i])) {
						abs_path += "/";
						abs_path += arr[i];
						list.add(abs_path);
					}
				}
				
				if (list.isEmpty()) {
					list.add("/");
				}
			}
		}
		
		return list;
	}

}

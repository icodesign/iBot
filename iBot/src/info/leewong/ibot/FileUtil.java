package info.leewong.ibot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

public class FileUtil{
	public static String sdPath = "/sdcard/fairybot/";
	
	public static File creatFileDir(String dirName){
		File file = new File(dirName);
		Boolean b = file.mkdirs();
		return file;
	}
	
	public static File creatSdFile(String path, String fileName) throws IOException {
		File file= new File(path + fileName);
		try{
			file.createNewFile();
		}catch (IOException e) {
			System.out.println(e.toString());
		}
		return file;
	}
	
	public static Boolean isFileExist(String fillName){
		File file = new File(fillName);
		return file.exists();
	}
	
	@SuppressWarnings("finally")
	public static File write2SdCard(String path,String name,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
			int length;
			creatFileDir(path);
			file = creatSdFile(path,name);
			output = new FileOutputStream(file);
			byte buffer [] = new byte [1024];
			while((length=input.read(buffer))!=-1){
				output.write(buffer,0,length); 
			}			
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			return file;
		}
}
}
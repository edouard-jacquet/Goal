package goal.model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import goal.model.bean.FileResult;

public class FileDAO {
	
	public static final String DIRECTORY = "storage/resource/text";
	private String folder;
	
	public FileDAO() {
		String path = FileDAO.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		this.folder = path + DIRECTORY;
	}
	
	public FileResult getFile(String name) {
		FileResult result = new FileResult();
		File file = new File(this.folder + "/" + name + ".txt");
		String content = "";
		try {
			InputStream inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				content = content + " " + line;
			}
			bufferedReader.close();
		} catch (Exception e) {
			System.out.println("File read error");
			e.printStackTrace();
		}
		result.setTitle(name);
		result.setSummarize(content.substring(0, 250) + "(...)");
		result.setContent(content);
		result.setLocation("resource?type=text&&name=" + name);
		return result;
	}

	public String getFolder() {
		return folder;
	}

}

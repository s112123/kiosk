import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConnectFile {
	/* variable */
	File file;
	FileWriter fw;
	PrintWriter pw;
	FileReader fr;
	BufferedReader br;
	String resultData = "";
	
	/* constructor */
	ConnectFile(String filePath) {
		file = new File(filePath);
	}
	
	/* method */
	void saveRowData(String rowData, boolean isAppend) {
		try {
			fw = new FileWriter(file, isAppend);
			pw = new PrintWriter(fw);
			
			//기존코드: pw.println(rowData);
			
			//수정코드
			if(!rowData.equals("")) {
				pw.println(rowData);
			}
			
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* method */
	String getResultData() {
		/* DB내 전체 데이터를 String으로 반환 */
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String line = "";
			while((line=br.readLine()) != null) {
				resultData += (line + "/n");
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultData;
	}
	
	ArrayList<String> loadRowData() {
		/* DB내 전체 데이터를 ArrayList로 반환 */
		
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String line = null;
			while((line=br.readLine()) != null) {
				lines.add(line);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return lines;
	}
	
	ArrayList<String> loadFindCategoryRowData(String categoryName) {
		/* 특정 카테고리의 데이터를 ArrayList로 반환 */
		
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String line = null;
			while((line=br.readLine()) != null) {
				String[] tempDatas = line.split("#");

				if(tempDatas[1].equals(categoryName)) {
					lines.add(line);
				}	
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return lines;
	}
	
}

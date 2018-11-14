package kdemo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.kdemo.model.BeanRequest;

public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "C:\\Users\\Emre\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\KDemo\\WEB-INF\\data\\veritabani.txt";
		removeLineFromFile(path, "2&Elmalıkent Mahallesi, Ümraniye Eğitim ve Araştırma Hastanesi, Ümraniye/İstanbul, Türkiye&Mecidiyeköy Mahallesi, Türkiye&01/04/2017 - 12:32&12947&Evrak&NOT");
//		BeanRequest beanRequest = new BeanRequest("ElmalıKent", "Mecidiyeköy1", "Bugun", "1000", "evrak");
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//		String url = "http://localhost:8080/KDemo/myproject/mapping";
//		boolean result = restTemplate.postForObject(url, beanRequest, boolean.class);
//		System.out.println(result);
	}
	
	public static void removeLineFromFile(String file, String lineToRemove) {
   	 	
        try {
            File inFile = new File(file);
            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }
            //Construct the new file that will later be renamed to the original filename. 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line ;
            //Read from the original file and write to the new 
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
 
            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

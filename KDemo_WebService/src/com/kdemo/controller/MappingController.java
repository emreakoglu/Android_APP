package com.kdemo.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdemo.model.BeanRequest;
import com.kdemo.services.BeanRequestService;

import kdemo.test.JSPBeanRequest;



@Controller
public class MappingController {
	
	//private static final String FILENAME = "‪C:\\Users\\Emre\\Dropbox\\filename.txt";
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	BeanRequestService beanRequestService;
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String save(@ModelAttribute("beanRequest") BeanRequest beanRequest, Model model) {
		beanRequestService.add(beanRequest);
		return "redirect:/list";
	}
	
	
	@RequestMapping(value="/add" , method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("beanRequest", beanRequestService.create());
		return "add";
		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		List<BeanRequest> beanRequests = beanRequestService.findAll();
		model.addAttribute("beanRequests", beanRequests);
		return "list";
	}
	
	@RequestMapping(value="/edit/{beanRequestId}", method = RequestMethod.GET)
	public String edit (@PathVariable Long beanRequestId, Model model) {
		BeanRequest beanRequest = beanRequestService.findById(beanRequestId);
		model.addAttribute("beanRequest", beanRequest);
		return "add";
	}
	
	@RequestMapping(value = "/delete/{beanRequestId}", method = RequestMethod.GET)
	public String delete (@PathVariable Long beanRequestId,  Model model) {
		BeanRequest beanRequest = beanRequestService.findById(beanRequestId);
		beanRequestService.delete(beanRequest);
		return "redirect:/list";
	}
	
	 @RequestMapping(value="/mapping", method = RequestMethod.POST)
    @ResponseBody
    public boolean add1(@RequestBody BeanRequest beanRequest) {
        beanRequest.toString();
        beanRequestService.add(beanRequest);
        return true; 
        
    }
	 
	 
//     String path = servletContext.getRealPath("/WEB-INF/data");
//		File gpxFile = new File(path,"veritabani.txt");
//		List<String> lines;
//		int id = 0;
//		try {
//			lines = FileUtils.readLines(gpxFile,"UTF-8");
//			id = lines.size()+1;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		JSPBeanRequest jspBeanRequest=new JSPBeanRequest(id, beanRequest.getNereden(), beanRequest.getNereye(), beanRequest.getTarih(), beanRequest.getDistance(), beanRequest.getGondericiTipi(), "NOT");
//     boolean fileStatus = fileWrite(path, jspBeanRequest.toString());

	
	
	
//	@SuppressWarnings("null")
//	@RequestMapping(value="/updateQuery",method=RequestMethod.GET)
//	public String printHello(Model model) {
//		String path = servletContext.getRealPath("/WEB-INF/data");
//		File gpxFile = new File(path,"veritabani.txt");
//        List<String> lines;
//        List<JSPBeanRequest> jspBeanReqeustList = new ArrayList<JSPBeanRequest>();
//		try {
//			lines = FileUtils.readLines(gpxFile,"UTF-8");
//			lines.size();
//			for (String string : lines) {
//				List<String> elephantList = Arrays.asList(string.split("&"));
//				if (elephantList.size() == 7) {
//					JSPBeanRequest jspBeanRequest = new JSPBeanRequest(Integer.parseInt(elephantList.get(0)), elephantList.get(1),elephantList.get(2), elephantList.get(3), elephantList.get(4),elephantList.get(5),elephantList.get(6));
//					jspBeanReqeustList.add(jspBeanRequest);
//				}
//				
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		model.addAttribute("message", jspBeanReqeustList);
//	    return "index";
//	}
//	
//	@RequestMapping(value="/editStatus",method = RequestMethod.GET)
//	public String edit(HttpServletRequest request,Model model) {
//		String [] editler = request.getParameterValues("editable");
//		String path = servletContext.getRealPath("/WEB-INF/data");
//		File gpxFile = new File(path,"veritabani.txt");
//		List<String> lines;
//		try {
//			lines = FileUtils.readLines(gpxFile,"UTF-8");
//			lines.size();
//			for (String string : lines) {
//				List<String> elephantList = Arrays.asList(string.split("&"));
//				for (String edit : editler) {
//					if (edit.equals(elephantList.get(0))){
//						removeLineFromFile(gpxFile, string);
//						JSPBeanRequest jspBeanRequest=new JSPBeanRequest(Integer.parseInt(elephantList.get(0)), elephantList.get(1), elephantList.get(2), elephantList.get(3), elephantList.get(4), elephantList.get(5), "OK");
//				        boolean fileStatus = fileWrite(path, jspBeanRequest.toString());
//					}
//				}
//				
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<JSPBeanRequest> jspBeanReqeustList = new ArrayList<JSPBeanRequest>();
//		try {
//			lines = FileUtils.readLines(gpxFile,"UTF-8");
//			lines.size();
//			for (String string : lines) {
//				List<String> elephantList = Arrays.asList(string.split("&"));
//				if (elephantList.size() == 7) {
//					JSPBeanRequest jspBeanRequest = new JSPBeanRequest(Integer.parseInt(elephantList.get(0)), elephantList.get(1),elephantList.get(2), elephantList.get(3), elephantList.get(4),elephantList.get(5),elephantList.get(6));
//					jspBeanReqeustList.add(jspBeanRequest);
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		model.addAttribute("message", jspBeanReqeustList);
//		return "index";
//	}
//	
//	
	
   
    
//    public boolean fileWrite (String path,String content) {
//    	File root = new File (path);
//    	if (!root.exists()) {
//    		root.mkdirs();
//    	}
//		File gpxFile = new File(path,"veritabani.txt");
//		
//        try {
//        	OutputStreamWriter writer = new OutputStreamWriter(
//                    new FileOutputStream(gpxFile, true), "UTF-8");
//              BufferedWriter fbw = new BufferedWriter(writer);
//              fbw.write(content);
//              fbw.newLine();
//              fbw.close();
//            return true;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	return false;
//    }
//    
//public static void removeLineFromFile(File file, String lineToRemove) {
//   	 	
//        try {
//            File inFile = file;
//            if (!inFile.isFile()) {
//                System.out.println("Parameter is not an existing file");
//                return;
//            }
//            //Construct the new file that will later be renamed to the original filename. 
//            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
//            List<String> lines = FileUtils.readLines(file,"UTF-8");
//            //Read from the original file and write to the new 
//            //unless content matches data to be removed.
//            OutputStreamWriter writer = new OutputStreamWriter(
//                    new FileOutputStream(tempFile, true), "UTF-8");
//              BufferedWriter fbw = new BufferedWriter(writer);
//            for (String string : lines) {
//            	if (!string.trim().equals(lineToRemove)) {
//            		fbw.write(string);
//            		fbw.newLine();
//                }
//			}
//            fbw.close();
// 
//            //Delete the original file
//            if (!inFile.delete()) {
//                System.out.println("Could not delete file");
//                return;
//            }
//            //Rename the new file to the filename the original file had.
//            if (!tempFile.renameTo(inFile))
//                System.out.println("Could not rename file");
// 
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    
}

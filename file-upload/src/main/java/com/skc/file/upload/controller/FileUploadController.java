package com.skc.file.upload.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skc.file.upload.model.Address;
import com.skc.file.upload.model.Employee;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class FileUploadController {

    @RequestMapping(path = "upload1", method = RequestMethod.POST)
    public String upload1(@RequestParam MultipartFile myFile){
        try {
            byte[] bytes = myFile.getBytes();
            FileOutputStream fout = new FileOutputStream("test1.png");
            fout.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File Upload success. Check in your project by refreshing";
    }

    @RequestMapping(path = "upload2", method = RequestMethod.POST)
    public String upload2(@RequestParam MultipartFile file) throws  Exception{
        FileOutputStream fout = new FileOutputStream("test2.txt");
        fout.write(file.getBytes());
        return "file upload successfully.";
    }

    @RequestMapping(path = "upload3", method = RequestMethod.POST)
    public String upload3(@RequestParam MultipartFile myFile) throws  Exception{
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/Test.java");
        fout.write(myFile.getBytes());
        return "file upload successfully.";
    }

    @RequestMapping(path = "upload4", method = RequestMethod.POST)
    public String upload4(@RequestParam MultipartFile myFile) throws  Exception{
        String originalFileName = myFile.getOriginalFilename();
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/" + originalFileName);
        fout.write(myFile.getBytes());
        return originalFileName + " uploaded successfully.";
    }

    @RequestMapping(path = "upload5", method = RequestMethod.POST)
    public String upload5(@RequestParam MultipartFile myFile) throws  Exception{
        String originalFileName = myFile.getOriginalFilename();
        String fileName = "hello";
        String fileType = originalFileName.substring(originalFileName.indexOf('.'));
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/" + fileName + fileType);
        fout.write(myFile.getBytes());
        return fileName + fileType + " uploaded successfully.";
    }

    int counter = 0;
    synchronized  int getCounter(){
        counter++;
        return counter;
    }

    @RequestMapping(path = "upload6", method = RequestMethod.POST)
    public String upload6(@RequestParam MultipartFile myFile) throws  Exception{
        String originalFileName = myFile.getOriginalFilename();
        String fileName = "hello" + getCounter();
        String fileType = originalFileName.substring(originalFileName.indexOf('.'));
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/" + fileName + fileType);
        fout.write(myFile.getBytes());
        return originalFileName + " uploaded as " + fileName + fileType + " successfully.";
    }

    @RequestMapping(path = "upload7", method = RequestMethod.POST)
    public String upload7(@RequestParam MultipartFile myFile, @RequestParam String jsonObject) throws  Exception{
        String originalFileName = myFile.getOriginalFilename();
        String fileType = originalFileName.substring(originalFileName.indexOf('.'));
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/new_file" + fileType);
        fout.write(myFile.getBytes());

        Employee emp = new ObjectMapper().readValue(jsonObject, Employee.class);
        System.out.println(emp.getId());
        System.out.println(emp.getFirstName());
        System.out.println(emp.getLastName());
        return "success";
    }

    @RequestMapping(path = "upload8", method = RequestMethod.POST)
    public String upload8(@RequestParam MultipartFile file1,
                          @RequestParam MultipartFile file2,
                          @RequestParam String jsonEmpObject,
                          @RequestParam String jsonAddressObject) throws  Exception{
        String originalFileNameOfFile1 = file1.getOriginalFilename();
        String originalFileNameOfFile2 = file2.getOriginalFilename();
        String fileTypeOfFile1 = originalFileNameOfFile1.substring(originalFileNameOfFile1.indexOf('.'));
        String fileTypeOfFile2 = originalFileNameOfFile2.substring(originalFileNameOfFile2.indexOf('.'));

        FileOutputStream fout1 = new FileOutputStream("src/main/resources/uploads/new_file" + fileTypeOfFile1);
        fout1.write(fileTypeOfFile1.getBytes());

        FileOutputStream fout2 = new FileOutputStream("src/main/resources/uploads/new_file" + fileTypeOfFile2);
        fout2.write(fileTypeOfFile2.getBytes());

        Employee emp = new ObjectMapper().readValue(jsonEmpObject, Employee.class);
        Address address = new ObjectMapper().readValue(jsonAddressObject, Address.class);
        System.out.println("---------------- Employee -----------------------------");
        System.out.println(emp.getId());
        System.out.println(emp.getFirstName());
        System.out.println(emp.getLastName());
        System.out.println("---------------- Address -----------------------------");
        System.out.println(address.getHouseNo());
        System.out.println(address.getStreetName());
        return "success";
    }

    @RequestMapping(path = "upload9", method = RequestMethod.POST)
    public String upload9(@RequestParam MultipartFile myFile) throws  Exception{
        String originalFileName = myFile.getOriginalFilename();
        String fileName = "hello" + getCounter();
        String fileType = originalFileName.substring(originalFileName.indexOf('.'));
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/" + fileName + fileType);
        fout.write(myFile.getBytes());

        /* Reading from excel sheet */
        XSSFWorkbook workbook = new XSSFWorkbook("src/main/resources/uploads/" + fileName + fileType);
        XSSFSheet workSheet = workbook.getSheetAt(0);

        for(int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++){
            XSSFRow row = workSheet.getRow(i);
            System.out.print(row.getCell(0).getNumericCellValue() + "\t");
            System.out.print(row.getCell(1).getStringCellValue() + "\t");
            System.out.print(row.getCell(2).getNumericCellValue() + "\t");
            System.out.print(row.getCell(3).getStringCellValue());
            System.out.println();
        }

        return originalFileName + " uploaded as " + fileName + fileType + " successfully.";
    }
}

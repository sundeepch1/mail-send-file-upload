package com.skc.file.upload.controller;

import com.skc.file.upload.entity.Employee;
import com.skc.file.upload.repository.EmployeeRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.ArrayList;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public String upload9(@RequestParam MultipartFile myFile) throws  Exception{
        String originalFileName = myFile.getOriginalFilename();
        String fileName = "data";
        String fileType = originalFileName.substring(originalFileName.indexOf('.'));
        FileOutputStream fout = new FileOutputStream("src/main/resources/uploads/" + fileName + fileType);
        fout.write(myFile.getBytes());

        /* Reading from excel sheet */
        XSSFWorkbook workbook = new XSSFWorkbook("src/main/resources/uploads/" + fileName + fileType);
        XSSFSheet workSheet = workbook.getSheetAt(0);
        XSSFRow row;
        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee;

        for(int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++){
            row = workSheet.getRow(i);
            employee = new Employee();
            employee.setId((int)row.getCell(0).getNumericCellValue());
            employee.setFirstName(row.getCell(1).getStringCellValue());
            employee.setAge((int)row.getCell(2).getNumericCellValue());
            employee.setEmail(row.getCell(3).getStringCellValue());
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
        return "success";
    }
}

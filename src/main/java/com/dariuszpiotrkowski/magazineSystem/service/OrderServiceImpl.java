package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.dao.OrderRepository;
import com.dariuszpiotrkowski.magazineSystem.entity.Element;
import com.dariuszpiotrkowski.magazineSystem.entity.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ElementService elementService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(int id) {
        Optional<Order> result = orderRepository.findById(id);

        Order order = null;

        if (result.isPresent()) {
            order = result.get();
        }
        else {
            throw new RuntimeException("Nie znaleziono zamowienia o id = " + id);
        }

        return order;
    }

    @Override
    public Order findByNumber(String number) {
        return orderRepository.findByNumber(number);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> searchByNumber(String number) {
        List<Order> results = null;

        if (number != null && (number.trim().length() > 0)) {
            results = orderRepository.findByNumberContainsAllIgnoreCase(number);
        }
        else {
            results = findAll();
        }

        return results;
    }

    @Override
    public void saveFile(Order order, MultipartFile multipartFile) {
        String path = "D:\\MagazineSystemPliki\\order\\" + StringUtils.cleanPath(multipartFile.getOriginalFilename());

        File file = new File(path);
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(multipartFile.getBytes());
            os.close();
        }
        catch (IOException e) {
            System.out.println("Blad pliku");
        }

        if (order.getBomPath() != null) {
            File oldFile = new File(order.getBomPath());
            oldFile.delete();
        }

        order.setBomPath(path);

        orderRepository.save(order);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String filePath) {

        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            String mimeType = Files.probeContentType(file.toPath());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        }
        catch (IOException e) {
            System.out.println("IOException");
        }

        return null;
    }

    @Override
    public void checkBom(int orderId) {
        Order order = this.findById(orderId);

        File file = new File(order.getBomPath());

        Map<String, Integer> inputMap = new HashMap<>();
        int pnCounter = 0;
        int amountCounter = 0;
        int counter = 1;

        try {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet myExcelSheet = myExcelBook.getSheet("BOM Report");
            XSSFRow row;
            String columnName = "";
            while(!(columnName.contains("PART NUMBER"))) {
                columnName = myExcelSheet.getRow(0).getCell(pnCounter).getStringCellValue().toUpperCase();
                pnCounter++;
            }
            columnName = "";
            while (!(columnName.contains("QUANT"))) {
                columnName = myExcelSheet.getRow(0).getCell(amountCounter).getStringCellValue().toUpperCase();
                amountCounter++;
            }
            pnCounter--;
            amountCounter--;
            try {
                while (true) {
                    row = myExcelSheet.getRow(counter);
                    String partNumber = row.getCell(pnCounter).getStringCellValue();
                    int amount = (int)(row.getCell(amountCounter).getNumericCellValue());
                    inputMap.put(partNumber, amount);
                    counter++;
                }
            }
            catch (NullPointerException e) {}
        }
        catch (IOException e) {}

        List<Element> elements = elementService.findAll();
        Map<String, Integer> resultMap = new HashMap<>();
        Set<Map.Entry<String, Integer>> entrySet = inputMap.entrySet();
        int amountToBuy = 0;
        boolean check;

        for(Map.Entry<String, Integer> entry : entrySet) {
            check = false;
            String partNumber = entry.getKey();
            int amount = entry.getValue();
            for (Element element : elements) {
                if(element.getPartNumber().equals(partNumber)) {
                    if (element.getNumber() >= amount){
                        check = true;
                    }
                    else {
                        amountToBuy = amount - element.getNumber();
                    }

                    if (check == false) {
                        resultMap.put(entry.getKey(), amountToBuy);
                        check = true;
                    }
                    break;
                }
            }
            if (check == false) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Arkusz1");
        Set<Map.Entry<String, Integer>> resultSet = resultMap.entrySet();
        int rowNumber = 0;

        String path = "D:\\MagazineSystemPliki\\order\\" + order.getNumber() + "Lista.xlsx";
        path = path.replace("/", "_");
        order.setToBuyPath(path);
        this.save(order);

        for(Map.Entry<String, Integer> entry : resultSet) {
            Row row = sheet.createRow(rowNumber);
            Cell firstCell = row.createCell(0);
            Cell secondCell = row.createCell(1);
            firstCell.setCellValue(entry.getKey());
            secondCell.setCellValue((Integer)entry.getValue());
            rowNumber++;
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
        }
        catch(IOException e) {}
    }

}

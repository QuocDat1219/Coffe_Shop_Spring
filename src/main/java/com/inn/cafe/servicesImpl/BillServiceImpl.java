package com.inn.cafe.servicesImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Bill;
import com.inn.cafe.constents.CafeConstans;
import com.inn.cafe.dao.BillDao;
import com.inn.cafe.services.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Phaser;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillDao billDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestmap) {
        log.info("Inside generateReport");
        try {
            String fileName;
            if(validateRequestMap(requestmap)){
                if(requestmap.containsKey("isGenerate") && !(Boolean) requestmap.get("isGenerate")){
                    fileName = (String) requestmap.get("uuid");
                }else{
                    fileName = CafeUtils.getUUID();
                    requestmap.put("uuid",fileName);
                    insertBill(requestmap);
                }

                String data = "Name: "+requestmap.get("name") + "\n" +
                        "Contact Number: "+requestmap.get("contactNumber") + "\n" +
                        "Email: "+requestmap.get("email")+"\n"+
                        "Payment Method: "+requestmap.get("paymentMethod");

                Document document = new Document();
                String home = System.getProperty("user.home");
                PdfWriter.getInstance(document,new FileOutputStream(home+CafeConstans.STORE_LOCATION+"\\"+fileName+".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph chunk = new Paragraph("Cafe Vipp", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data+"\n \n",getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String) requestmap.get("productDetails"));
                for (int i=0;i<jsonArray.length();i++){
                    addRows(table,CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total: "+requestmap.get("totalAmount")+"\n"+
                        "Thank you!!",getFont("Data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Required dat not found", HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double)data.get("price")));
        table.addCell(Double.toString((Double)data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Name","Category","Quantity","Price","Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.ORANGE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type){
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.RED);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case  "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
            return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.LIGHT_GRAY);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private void insertBill(Map<String, Object> requestmap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestmap.get("uuid"));
            bill.setName((String) requestmap.get("name"));
            bill.setEmail((String) requestmap.get("email"));
            bill.setContactNumber((String) requestmap.get("contactNumber"));
            bill.setPaymentMethod((String) requestmap.get("paymentMethod"));
            bill.setProductDetail((String) requestmap.get("productDetails"));
            bill.setTotal(Integer.parseInt((String) requestmap.get("totalAmount")));
            bill.setCreateBy(jwtFilter.getCurrentUser());

            billDao.save(bill);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestmap) {
        return requestmap.containsKey("name") &&
                requestmap.containsKey("contactNumber") &&
                requestmap.containsKey("email") &&
                requestmap.containsKey("paymentMethod") &&
                requestmap.containsKey("productDetails") &&
                requestmap.containsKey("totalAmount");
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
        if(jwtFilter.isAdmin()){
            list = billDao.getAllBills();
        }else {
            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf : requestMap {}",requestMap);
        try{
            byte[] byteArray = new byte[0];
            if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap)){
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
            }
            String home = System.getProperty("user.home");
            String filePath = home+CafeConstans.STORE_LOCATION+"\\"+(String) requestMap.get("uuid")+".pdf";
            if (CafeUtils.isFileExists(filePath)){
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }else {
                requestMap.put("isGenerate",false);
                generateReport(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String filePath) throws Exception{
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            Optional optional = billDao.findById(id);
            if(!optional.isEmpty()){
                billDao.deleteById(id);
                return CafeUtils.getResponseEntity("Bill Deleted Successfully",HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Bill dose not exists",HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


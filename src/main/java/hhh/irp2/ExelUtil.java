package hhh.irp2;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExelUtil {

    private static int numberRows = 38;
    private static XSSFWorkbook excelDocument;
    private static XSSFSheet sheet;
    //private static CellStyle cellStyle;

    public static void createSchedule(Map<String, Node> map, LocalDate date){

        String month = WordUtil.getMonth(date.getMonthValue());
        String year = date.getYear()+"";

        String monthRPLast = WordUtil.getMonthRP(date.minusMonths(1).getMonthValue());
        String yearLast = date.minusMonths(1).getYear()+"";

        try {
            // открытие шаблона
            //FileInputStream fis = new FileInputStream("templates"+File.separator+"schedule.xlsx");

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("schedule.xlsx");
            if (is != null) {
                excelDocument = new XSSFWorkbook(is);
                is.close();
                System.out.println("Закрываем schedule.xlsx");
            } else {
                System.out.println("не удалось загрузить файл расписания!");
            }

            // открытие докумета
            sheet = excelDocument.getSheetAt(0);

            // заполняем даты
            sheet.getRow(4).getCell(18).setCellValue("\"25\" "+monthRPLast+" "+yearLast+" г.");
            sheet.getRow(6).getCell(1).setCellValue(month+" "+year+" г.");

            int razn = 31 - date.lengthOfMonth();
            for (int i = 0; i < razn; i++) {
                sheet.getRow(9).getCell(33-i).setCellValue("");
                sheet.getRow(12).getCell(33-i).setCellValue("");
                sheet.getRow(15).getCell(33-i).setCellValue("");
                sheet.getRow(18).getCell(33-i).setCellValue("");
            }

            // работа с таблицей
            editTable(listForBlock(map,"Em"), 18);
            editTable(listForBlock(map,"Dm"), 15);
            editTable(listForBlock(map,"Ns"), 12);
            editTable(listForBlock(map,"Oby"), 9);

            // сохранение графика
            String currentDir = System.getProperty("user.dir");
            File file = new File(currentDir+File.separator+"output");
            if(!file.exists()) {
                Path dir = Files.createDirectories(Paths.get(currentDir + File.separator + "output"));
            }

            FileOutputStream fos = new FileOutputStream(currentDir + File.separator + "output" + File.separator + "schedule.xlsx");
            excelDocument.write(fos);
            fos.close();
        }catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static CellStyle getCellCtyle1(){
        CellStyle cellStyle = excelDocument.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        XSSFFont font = excelDocument.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Times New Roman");

        cellStyle.setFont(font);
        return cellStyle;
    }
    private static CellStyle getCellCtyle2(){
        CellStyle cellStyle = excelDocument.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        XSSFFont font = excelDocument.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Times New Roman");

        cellStyle.setFont(font);
        return cellStyle;
    }

    private static CellStyle getCellCtyle3(){
        CellStyle cellStyle = excelDocument.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = excelDocument.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Times New Roman");
        font.setBold(true);

        cellStyle.setFont(font);
        return cellStyle;
    }

    private static Row insertNewRow(int number){
        sheet.shiftRows(number+1, numberRows, 1);
        numberRows++;
        return addNewRow(number+1);
    }

    private static Row addNewRow(int number){
        Row row = sheet.createRow(number);
        row.setHeightInPoints(15);
        for (int i = 0; i < 34; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(getCellCtyle3());
        }
        for (int i = 1; i < 3; i++) {
            Cell cell = row.getCell(i);
            cell.setCellStyle(getCellCtyle2());
        }
        row.getCell(0).setCellStyle(getCellCtyle1());
        return row;
    }

    private static List<MyLine> listForBlock(Map<String, Node> map, String nick){
        VBox block = (VBox)map.get("block" + nick);

        List<MyLine> list = new ArrayList<>();

        for (int j = 3; j < block.getChildren().size(); j++) {

            MyLine line = new MyLine();

            GridPane gridPane = (GridPane) block.getChildren().get(j);
            TextField textField = (TextField) gridPane.getChildren().get(0);

            if(textField.getText().equals("")){
                break;
            }

            line.setName(textField.getText());

            DatePicker datePicker = (DatePicker) map.get("datePicker");
            LocalDate date = datePicker.getValue();

            for (int i = 1; i <= date.lengthOfMonth(); i++) {
                RadioButton radioButton = (RadioButton) gridPane.getChildren().get(i);
                line.setDuty(i-1, radioButton.isSelected());
            }

            list.add(line);
        }
        return list;
    }

    private static void editTable(List<MyLine> list, int numStr){
        for(int j = list.size()-1; j > -1; j--){
            MyLine line = list.get(j);
            String[] split = line.getName().split(" ");
            String vzv = "";
            String fio = "";
            switch (split.length){
                case 0: break;
                case 1:
                    vzv = "";
                    fio = split[0];
                    break;
                case 2:
                    vzv = "";
                    fio = line.getName();
                    break;
                case 3:
                    vzv = split[0];
                    fio = split[1]+" "+split[2];
                    break;
                case 4:
                    vzv = split[0]+" "+split[1];
                    fio = split[2]+" "+split[3];
                    break;
            }
            Row newRow = insertNewRow(numStr);
            newRow.getCell(0).setCellValue(j+1);
            newRow.getCell(1).setCellValue(vzv);
            newRow.getCell(2).setCellValue(fio);
            for (int i = 0; i < 31; i++) {
                if(line.isDuty(i)) {
                    newRow.getCell(i+3).setCellValue("Д");
                }
            }
        }
    }
}

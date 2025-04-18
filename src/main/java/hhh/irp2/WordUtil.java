package hhh.irp2;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WordUtil {

    public static void createStatement(ArrayList<MyArray> peopleInMonth, LocalDate date) {
        try {
//            File file = new File("templates"+File.separator+"statement.docx");
//            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream fis = classloader.getResourceAsStream("statement.docx");

            XWPFDocument document = new XWPFDocument(fis);
            fis.close();

            String year = date.getYear() + "";
            String lastDay = date.lengthOfMonth() + "";
            String monthNum = date.getMonthValue() + "";
            if (monthNum.length()<2) {
                monthNum = "0"+monthNum;
            }
            String monthRP = getMonthRP(date.getMonthValue());
            String month = getMonth(date.getMonthValue());

            replace(document, "$year", year);
            replace(document, "$monthRP", monthRP);
            replace(document, "$monthNum", monthNum);
            replace(document, "$month", month);
            replace(document, "$lastDay", lastDay);

            editTableForStatement(document, peopleInMonth, monthNum, year);

            String currentDir = System.getProperty("user.dir");

            FileOutputStream fos = new FileOutputStream(currentDir + File.separator + "output" + File.separator + "statement.docx");
            document.write(fos);
            fos.close();
        }
        catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    private static void editTableForStatement(
            XWPFDocument document,
            ArrayList<MyArray> peopleInMonth,
            String monthNum,
            String year)
            throws IOException{

        XWPFTable table = document.getTables().get(1);

        int total = 0;
        for (int i = 0; i < peopleInMonth.size(); i++) {

            MyArray peopleInDay = peopleInMonth.get(i);
            for (int j = 0; j < peopleInDay.getSize(); j++) {

                XWPFTableRow row = table.getRow(9+total);

                row.getTableCells().get(0).setText(total+1+"");
                row.getTableCells().get(1).setText(peopleInDay.getArr()[j]);
                row.getTableCells().get(16).setText((i+1)+"."+monthNum + "." + year);

                for (int k = 0; k < row.getTableCells().size(); k++) {
                    XWPFTableCell cell = row.getTableCells().get(k);

                    if( i%2 == 0){
                        cell.setColor("CCCCCC");
                    }
                    else {
                        cell.setColor("FFFFFF");
                    }
                }
                total++;
            }
        }
        // удаление пустых строк
        while(table.getRows().size()-3 > total+9){
            table.removeRow(total+9);
        }
        replace(document, "$total", total+"");
    }

    private static void replace(XWPFDocument doc, String target, String replacement) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(target)) {
                        text = text.replace(target, replacement);
                        r.setText(text, 0);
                    }
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains(target)) {
                                text = text.replace(target, replacement);
                                r.setText(text,0);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String getMonthRP(int munthNum){
        switch (munthNum){
            case 1: return "января";
            case 2: return "февраля";
            case 3: return "марта";
            case 4: return "апреля";
            case 5: return "мая";
            case 6: return "июня";
            case 7: return "июля";
            case 8: return "августа";
            case 9: return "сентября";
            case 10: return "октября";
            case 11: return "ноября";
            case 12: return "декабря";
        }
        return null;
    }

    public static String getMonth(int munthNum){
        switch (munthNum){
            case 1: return "январь";
            case 2: return "февраль";
            case 3: return "март";
            case 4: return "апрель";
            case 5: return "май";
            case 6: return "июнь";
            case 7: return "июль";
            case 8: return "август";
            case 9: return "сентябрь";
            case 10: return "октябрь";
            case 11: return "ноябрь";
            case 12: return "декабрь";
        }
        return null;
    }

    //-------------------------Редактирование рапорта------------------------------
    public static void createReport(LocalDate date){
        try {

            // открываем шаблон
//            File file = new File("templates" + File.separator + "report.docx");
//            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream fis = classloader.getResourceAsStream("report.docx");

            XWPFDocument document = new XWPFDocument(fis);
            fis.close();

            // создаем замены
            String yearNext = date.plusMonths(2).getYear() + "";
            String monthNext = getMonth(date.plusMonths(2).getMonthValue());
            String amount = "_____";
            String year = date.plusMonths(1).getYear() + "";
            String lastDay = (date.plusMonths(1).lengthOfMonth()-5) + "";
            String monthNum = date.plusMonths(1).getMonthValue() + "";
            if (monthNum.length()<2) {
                monthNum = "0"+monthNum;
            }

            // Заменяем
            replace(document, "$yearNext", yearNext);
            replace(document, "$year", year);
            replace(document, "$monthNum", monthNum);
            replace(document, "$monthNext", monthNext);
            replace(document, "$lastDay", lastDay);
            replace(document, "$amount", amount);

            // сохраняем новый документ в папку output
            String currentDir = System.getProperty("user.dir");

            FileOutputStream fos = new FileOutputStream(currentDir + File.separator + "output" + File.separator + "report.docx");
            document.write(fos);
            fos.close();
        }
        catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}

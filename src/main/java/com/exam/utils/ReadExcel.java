package com.exam.utils;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_ANSWER = 1;
    public static final int COLUMN_INDEX_CONTENT = 2;
    public static final int COLUMN_INDEX_IMAGE = 3;
    public static final int COLUMN_INDEX_OPTION1 = 4;
    public static final int COLUMN_INDEX_OPTION2 = 5;
    public static final int COLUMN_INDEX_OPTION3 = 6;
    public static final int COLUMN_INDEX_OPTION4 = 7;
    public static final int COLUMN_INDEX_QUIZ_Q_ID = 8;

    public static void main(String[] args) throws IOException {
        final String excelFilePath = "D:/Book1.xlsx";
        final List<Question> books = readExcel(excelFilePath);
        for (Question book : books) {
            System.out.println(book.getAnswer());
        }

//        String jdbcUrl = "jdbc:sqlserver://localhost;databaseName=examAssignment";
//        String userName = "sa";
//        String password = "123";
//        String excelFilePath = "D:\\Book2.csv";
//        int batchSize = 20;
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(jdbcUrl, userName, password);
//            connection.setAutoCommit(false);
//            String sql = "insert into question\n" +
//                    "(ques_id, answer, content, image, option1, option2, option3, option4, quiz_q_id)\n" +
//                    "values (?, ?, ?,?, ?, ?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            BufferedReader lineReader = new BufferedReader(new FileReader(excelFilePath));
//            String lineText = null;
//            int count = 0;
//            lineReader.readLine();
//            while ((lineText = lineReader.readLine()) != null) {
//                String[] data = lineText.split(",");
//                String id = data[0];
//                String answer = data[1];
//                String content = data[2];
//                String image = data[3];
//                String option1 = data[4];
//                String option2 = data[5];
//                String option3 = data[6];
//                String option4 = data[7];
//                String quiz_q_id = data[8];
//                statement.setLong(1, Long.parseLong(id));
//                statement.setString(2, answer);
//                statement.setString(3, content);
//                statement.setString(4, image);
//                statement.setString(5, option1);
//                statement.setString(6, option2);
//                statement.setString(7, option3);
//                statement.setString(8, option4);
//                statement.setLong(9, Long.parseLong(quiz_q_id));
//                statement.addBatch();
//                if (count % batchSize == 0) {
//                    statement.executeBatch();
//                }
//                lineReader.close();
//                statement.executeBatch();
//                connection.commit();
//                connection.close();
//                lineReader.close();
//                System.out.println("OK");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static List<Question> readExcel(String excelFilePath) throws IOException {
        List<Question> listBooks = new ArrayList<>();
        InputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();

            Iterator<Cell> cellIterator = nextRow.cellIterator();

            Question book = new Question();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_ID:
                        book.setQuesId((long) BigDecimal.valueOf((double) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_ANSWER:
                        book.setAnswer((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_CONTENT:
                        book.setContent((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_IMAGE:
                        book.setImage((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_OPTION1:
                        book.setOption1((String) getCellValue(cell));
                        break;

                    case COLUMN_INDEX_OPTION2:
                        book.setOption2((String) getCellValue(cell));
                        break;

                    case COLUMN_INDEX_OPTION3:
                        book.setOption3((String) getCellValue(cell));
                        break;

                    case COLUMN_INDEX_OPTION4:
                        book.setOption4((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_QUIZ_Q_ID:
                        Quiz q = new Quiz();
                        q.setQId((long) BigDecimal.valueOf((double) cellValue).intValue());
                        book.setQuiz(q);
                        break;
                    default:
                        break;
                }

            }
            listBooks.add(book);
        }

        workbook.close();
        inputStream.close();

        return listBooks;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}

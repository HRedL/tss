package com.eas.common.utils;

import com.eas.modules.exam.entity.Exam;
import com.eas.modules.grade.dao.GradeDao;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.room.entity.Room;
import com.eas.modules.teacher.dao.TeacherDao;
import com.eas.modules.teacher.entity.Teacher;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author huahonglei
 * @Version 2019/3/16
 */
public class
ReadLesson1 {

    private static GradeDao gradeDao=(GradeDao) SpringTool.getBean("gradeDao");

    private static TeacherDao teacherDao = (TeacherDao) SpringTool.getBean("teacherDao");



    public static File createExcelExam(String path, String filename, List<Exam> exams) throws IOException {

        File file = new File(path, filename);

        //创建输出流
        OutputStream outputStream = new FileOutputStream(file);
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个表
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        //创建一个行对象
        XSSFRow row = sheet.createRow(0);
        //创建一个单元格
        row.createCell(0).setCellValue("课程号");
        row.createCell(1).setCellValue("课程名");
        row.createCell(2).setCellValue("考试日期");
        row.createCell(3).setCellValue("开始时间");
        row.createCell(4).setCellValue("结束时间");
        row.createCell(5).setCellValue("考场");
        row.createCell(6).setCellValue("参加考试人数");
        row.createCell(7).setCellValue("教室考试容量");
        row.createCell(8).setCellValue("任课教师");
        row.createCell(9).setCellValue("行政班名称");
        row.createCell(10).setCellValue("取卷、送卷地点（公共课）");
        row.createCell(11).setCellValue("监考教师1");
        row.createCell(12).setCellValue("监考教师2");
        row.createCell(13).setCellValue("监考教师3");
        row.createCell(14).setCellValue("监考教师4");
        row.createCell(15).setCellValue("监考教师5");

        for(int i=1;i<exams.size()+1;i++){
            Exam exam=exams.get(i-1);
            XSSFRow row1=sheet.createRow(i);
            row1.createCell(0).setCellValue(exam.getLesson().getLnum());
            row1.createCell(1).setCellValue(exam.getLesson().getSubject());
            row1.createCell(2).setCellValue(getFormatDate(exam.getAvailableRoom().getWeek().getDate()));
            row1.createCell(3).setCellValue(exam.getAvailableRoom().getPeriod().getStarttime());
            row1.createCell(4).setCellValue(exam.getAvailableRoom().getPeriod().getEndtime());
            row1.createCell(5).setCellValue(getRoomName(exam.getAvailableRoom().getRoom()));
            //额，凑合先用着，算法那一块，分班的问题还没有解决
            row1.createCell(6).setCellValue(getExamNum(exam.getGids()));
            row1.createCell(7).setCellValue(exam.getAvailableRoom().getRoom().getCapacity());
            if(exam.getLesson().getTeacher()==null){
                row1.createCell(8).setCellValue("");
            }else{
                row1.createCell(8).setCellValue(exam.getLesson().getTeacher().getTname());
            }


            row1.createCell(9).setCellValue(getGradesName(exam.getGids()));
            //这一行是取卷。送卷地点，暂时没有
            row1.createCell(10).setCellValue("");
            //设置教师单元格
            for(int j=0;j<exam.getTeachers().size();j++){
                Teacher teacher=exam.getTeachers().get(j);
                row1.createCell(11+j).setCellValue(teacher.getTname());
            }
        }

        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();
        return file;
    }


    private static String getFormatDate(String date){
        StringBuffer formatDate=new StringBuffer();
        String[] dates=date.split("-");
        formatDate.append(dates[0]);
        formatDate.append("月");
        formatDate.append(dates[1]);
        formatDate.append("日");

        return new String(formatDate);
    }

    private static String getRoomName(Room room){
        StringBuffer roomName=new StringBuffer();
        roomName.append(room.getBuilding().getCampus());
        roomName.append(room.getBuilding().getBnum());
        roomName.append(room.getRnum());
        return new String(roomName);
    }

    /**
     * 获取考试人数
     * @param gids
     * @return
     */
    private static int getExamNum(String gids){
        int examNum=0;
        List<Grade> grades= getGradesByGName(gids);
        if(grades.size()>0){
            for(Grade grade:grades){
                examNum+=grade.getGtotalnum();
            }
        }
        return examNum;

    }


    /**
     * 获取行政班名称
     * @param gids
     * @return
     */
    private static String getGradesName(String gids){
        StringBuffer gradesName=new StringBuffer();
        List<Grade> grades= getGradeByGids(gids);
        if(grades.size()>0){
            for(Grade grade:grades){
                gradesName.append(grade.getGname());
                gradesName.append("，");
            }
        }
        return new String(gradesName);

    }



    public static List<Lesson> readLessonExcel(String path, String filename) throws IOException {

        List<Lesson> lessons = new ArrayList<>();

        File file = new File(path, filename);

        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        XSSFWorkbook workbook = new XSSFWorkbook(bufferedInputStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        //获取最后一行的下标
        int lastRowIndex = sheet.getLastRowNum();
        //System.out.println(lastRowIndex);
        for (int i = 2; i <= lastRowIndex; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            } else {
                Lesson lesson = new Lesson();
                String lnum = row.getCell(1).toString();
                String lname = row.getCell(2).toString();
                String ltotalnum = row.getCell(3).toString();
                String typeStr = row.getCell(6).toString();
                String gradesNames = row.getCell(7).toString();
                //String tnum = row.getCell(11).toString();
                String tname = row.getCell(12).toString();

                List<Grade> grades=getGradesByGName(gradesNames);
                Teacher teacher =getTeacher(tname);
                String type=getType(typeStr);
                lesson.setLnum(lnum);
                lesson.setSubject(lname);
                if(!StringUtils.isEmpty(ltotalnum)){
                    //走到宏观经济学那个90，就突然编程90.0了，这什么操作
                    //lesson.setLtotalnum(Integer.parseInt(ltotalnum));
                    lesson.setLtotalnum((int)Double.parseDouble(ltotalnum));
                }
                if(!StringUtils.isEmpty(type)){
                    lesson.setType(type);
                }
                lesson.setTeacher(teacher);
                lesson.setgIds(getGids(grades));
                lessons.add(lesson);
            }
        }
        bufferedInputStream.close();
        return lessons;
    }

    //这个就更难受了，因为数据库中的字典表里面是考试课和考查课三个字
    //我们是应该规定一下Excel表里面是写成考查课三个字的，还是说这个表就是天生就长这样不变的考试，考查呢
    //所以暂时就先写死了
    private static String getType(String typeStr) {
        String type=typeStr;
        if(!StringUtils.isEmpty(typeStr)){
            if(typeStr.equals("考试")){
                type="2";
            }else if(typeStr.equals("考查")){
                type="3";
            }
        }
        return type;
    }

    private static List<Grade> getGradesByGName(String gradeIdsStr){
        List<Grade> grades=new ArrayList<>();
        if(!StringUtils.isEmpty(gradeIdsStr)){
            String[] gradeIdArr=gradeIdsStr.split(" ");
            for (String gName : gradeIdArr) {
                if(!StringUtils.isEmpty(gName)){
                    Grade grade=new Grade();
                    grade.setGname(gName);
                    grade= gradeDao.getT(grade);
                    if(grade!=null){
                        grades.add(grade);
                    }

                }
            }
        }
        return grades;
    }

    private static List<Grade> getGradeByGids(String gradeIdsStr){
        List<Grade> grades=new ArrayList<>();
        if(!StringUtils.isEmpty(gradeIdsStr)){
            String[] gradeIdArr=gradeIdsStr.split(",");
            for (String gradeId : gradeIdArr) {
                if(!StringUtils.isEmpty(gradeId)){
                    Grade grade= gradeDao.findGradeById(Integer.parseInt(gradeId));
                    grades.add(grade);
                }
            }
        }
        return grades;
    }



    private static String getGids(List<Grade> grades){
        StringBuffer gidsStr=new StringBuffer();
        if(grades.size()>0){
            for(Grade grade:grades){
                gidsStr.append(grade.getId()).append(",");
            }
        }
        return gidsStr.toString();
    }

    //以后使用教师号来查，但是现在教师号是不准的，所以暂时使用教师名来查一下吧
    private static Teacher getTeacher(String tname){
        Teacher teacher=new Teacher() ;
        if(!StringUtils.isEmpty(tname)){
           teacher.setTname(tname);
           teacher= teacherDao.getT(teacher);
        }
        return teacher;
    }



    public static void main(String[] args) throws Exception {
        List<Lesson> lessons = new ArrayList<>();

        File file=new File("C:\\Users\\lenovo\\Desktop\\test.xlsx");

        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        XSSFWorkbook workbook = new XSSFWorkbook(bufferedInputStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        //获取最后一行的下标
        int lastRowIndex = sheet.getLastRowNum();
        //System.out.println(lastRowIndex);
        for (int i = 2; i <= lastRowIndex; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            } else {
                Lesson lesson = new Lesson();
                String lnum = row.getCell(1).toString();
                String lname = row.getCell(2).toString();
                String ltotalnum = row.getCell(3).toString();
                String type = row.getCell(6).toString();
                String grades = row.getCell(7).toString();
                String tnum = row.getCell(11).toString();
                String tname = row.getCell(12).toString();
                System.out.print("lnum="+lnum);
                System.out.print("--lname="+lname);
                System.out.print("--ltotalnum="+ltotalnum);
                System.out.print("--type="+type);
                System.out.print("--grades="+grades);
                System.out.print("--tnum="+tnum);
                System.out.print("--tname="+tname);
                System.out.println();
            }
        }
        bufferedInputStream.close();
    }



}

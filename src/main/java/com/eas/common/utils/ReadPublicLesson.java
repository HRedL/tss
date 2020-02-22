package com.eas.common.utils;

import com.eas.modules.availableRoom.dao.AvailableRoomDao;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.building.entity.Building;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.lesson.dao.LessonDao;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.period.dao.PeriodDao;
import com.eas.modules.publicSession.entity.PublicSession;
import com.eas.modules.publicLesson.entity.PublicLesson;
import com.eas.modules.room.dao.RoomDao;
import com.eas.modules.room.entity.Room;
import com.eas.modules.week.dao.WeekDao;
import com.eas.modules.week.entity.Week;
import com.eas.modules.period.entity.Period;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author huahonglei
 * @Version 2019/6/28
 */
public class ReadPublicLesson {

    private static WeekDao weekDao=(WeekDao) SpringTool.getBean("weekDao");
    private static PeriodDao periodDao=(PeriodDao) SpringTool.getBean("periodDao");
    private static BuildingDao buildingDao=(BuildingDao) SpringTool.getBean("buildingDao");
    private static RoomDao roomDao=(RoomDao) SpringTool.getBean("roomDao");
    private static AvailableRoomDao availableRoomDao=(AvailableRoomDao) SpringTool.getBean("availableRoomDao");
    private static DictDao dictDao=(DictDao) SpringTool.getBean("dictDao");
    private static LessonDao lessonDao=(LessonDao) SpringTool.getBean("lessonDao");



    public static List<PublicLesson> readPublicLessonExcel(String path, String filename) throws IOException {

        List<PublicLesson> publicLessons  = new ArrayList<>();

        File file = new File(path, filename);

        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        XSSFWorkbook workbook = new XSSFWorkbook(bufferedInputStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        //获取最后一行的下标
        int lastRowIndex = sheet.getLastRowNum();
        //System.out.println(lastRowIndex);
        for (int i = 1; i <=lastRowIndex; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            } else {
                PublicLesson publicLesson = new PublicLesson();
                String lnum = row.getCell(1).toString();
                String lname = row.getCell(2).toString();
                String totalnum = row.getCell(3).toString();
                Integer ltotalnum=Integer.valueOf(totalnum);
                String weekStr = row.getCell(4).toString();
                String timeStr = row.getCell(5).toString();
                String roomInf = row.getCell(6).toString();


                /*获取考试日期*/
                Week w=new Week();
                w.setDate(weekStr);
                Week week=weekDao.getT(w);

                /*获取考试时间*/
                Period period=getTime(timeStr);
                /*获取考试教室*/
                Room room= getRoom(roomInf);

                /*生成可用教室*/

                AvailableRoom availableRoom=new AvailableRoom();
                availableRoom.setRoom(room);
                availableRoom.setWeek(week);
                availableRoom.setPeriod(period);
                availableRoom=availableRoomDao.getT(availableRoom);

                /*获取LID*/
                Lesson lesson=new Lesson();
                lesson.setLnum(lnum);
                lesson.setLtotalnum(ltotalnum);
                lesson.setSubject(lname);
                lesson=lessonDao.getT(lesson);

                publicLesson.setAvailableRoom(availableRoom);
                publicLesson.setLesson(lesson);
                System.out.println(publicLesson.getAvailableRoom().getId()+"******************");
                publicLessons.add(publicLesson);
            }
        }
        bufferedInputStream.close();
        return publicLessons;
    }

    private static Room getRoom(String roomInf) {

        String campusStr=roomInf.substring(0,2);

        String bnumStr=roomInf.substring(2,4);

        String rnum=roomInf.substring(4,roomInf.length());

        String campus=dictDao.getDictByTypeAndLabel("CAMPUS",campusStr).getValue();
        String bnum=dictDao.getDictByTypeAndLabel("BNUM",bnumStr).getValue();


        Building building=new Building();
        building.setCampus(campus);
        building.setBnum(bnum);
        building=buildingDao.getT(building);

        Room room=new Room();
        room.setBuilding(building);
        room.setRnum(rnum);

        return roomDao.getT(room);
    }

    private static Period getTime(String timeStr) {
        String[] dates=timeStr.split("--");
        Period period=new Period();
        period.setStarttime(dates[0]);
        period.setEndtime(dates[1]);

        return periodDao.getT(period);

    }

}

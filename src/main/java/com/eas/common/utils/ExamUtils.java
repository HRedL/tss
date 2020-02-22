package com.eas.common.utils;

import com.eas.modules.availableRoom.dao.AvailableRoomDao;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.availableRoom.service.AvailableRoomService;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.building.entity.Building;
import com.eas.modules.lesson.dao.LessonDao;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.room.entity.Room;
import com.eas.modules.period.dao.PeriodDao;
import com.eas.modules.period.entity.Period;
import com.eas.modules.week.dao.WeekDao;
import com.eas.modules.week.entity.Week;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @Author: lilinzhen
 * @Version: 2019/6/3
 **/
public class ExamUtils {

    /**
     * 	在外部工具类中用这种方式声明一个dao
     * 	因为dao是mvc的一个组件，直接声明会报错private static DictDao dictDao = new DictDao();这种传统方式不可行
     * 	所以找了一个SpringTool工具类作为dao的注入
     */
    private static AvailableRoomDao availableRoomDao = (AvailableRoomDao) SpringTool.getBean("availableRoomDao");
    private static AvailableRoomService availableRoomService = (AvailableRoomService) SpringTool.getBean("availableRoomService");
    private static WeekDao weekDao = (WeekDao) SpringTool.getBean("weekDao");
    private static PeriodDao periodDao = (PeriodDao) SpringTool.getBean("timeDao");
    private static BuildingDao buildingDao = (BuildingDao) SpringTool.getBean("buildingDao");
    private static LessonDao lessonDao = (LessonDao) SpringTool.getBean("lessonDao");

    public Boolean checkExamWeek() {
        return null;
    }


    public Boolean checkExamTime() {
        return null;
    }


    public Boolean checkExamAvailableRoom() {
        return null;
    }

    /**
     * 获取考试日期、考试时间、教学楼相同的可用教室的集合
     * @param week 考试日期
     * @param period 考试时间
     * @param building 教学楼
     * @return 可用教室集合
     */
    public static List<AvailableRoom> getAvailableRoomByWTB(Week week, Period period, Building building) {
        AvailableRoom availableRoom = new AvailableRoom();
        availableRoom.setWeek(week);
        availableRoom.setPeriod(period);
        Room room = new Room();
        room.setBuilding(building);
        availableRoom.setRoom(room);
        return availableRoomService.findListByWTB(availableRoom);
    }

    /**
     * 获取考试日期、考试时间、教学楼相同的可用教室的集合的集合
     * @return 分类后的可用教室的集合
     */
    public static List<List<AvailableRoom>> getAvailableRoomLists() {
        List<List<AvailableRoom>> availableRoomLists = new ArrayList<>();
        List<Week> weekList = weekDao.findAllList();
        List<Period> periodList = periodDao.findAllList();
        List<Building> buildingList = buildingDao.findAllList();
        for (Week week : weekList) {
            for (Period period : periodList) {
                for (Building building : buildingList) {
                    List<AvailableRoom> availableRoomList = getAvailableRoomByWTB(week, period, building);
                    availableRoomLists.add(availableRoomList);
                }
            }
        }
        return availableRoomLists;
    }

    /**
     * 获取课程号相同的课程的集合
     * @param lessonNumber 课程号
     * @return 课程集合
     */
    public static List<Lesson> getLessonByLessonNumber(String lessonNumber) {
        Lesson lesson = new Lesson();
        lesson.setLnum(lessonNumber);
        List<Lesson> lessonList = lessonDao.findList(lesson);
        return lessonList;
    }

    /**
     * 获取课程号相同的课程的集合的集合
     * @return 分类后的课程的集合
     */
    public static List<List<Lesson>> getLessonLists() {
        List<List<Lesson>> lessonLists = new ArrayList<>();
        List<String> lessonNumberList = lessonDao.findLessonNumberList();
        for (String lessonNumber : lessonNumberList) {
            List<Lesson> lessonList = getLessonByLessonNumber(lessonNumber);
            lessonLists.add(lessonList);
        }
        return lessonLists;
    }


    public Boolean isRoomEnough(AvailableRoom availableRoom, Lesson lesson) {
        return null;
    }


    public Boolean isRoomListEnough(List<AvailableRoom> availableRoomList, Lesson lesson) {
        return null;
    }


    public Boolean isRoomBig(AvailableRoom availableRoom, Lesson lesson) {
        return null;
    }
}

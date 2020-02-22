package com.eas.modules.publicLesson.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.availableRoom.dao.AvailableRoomDao;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.availableRoom.service.AvailableRoomService;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.publicLesson.dao.PublicLessonDao;
import com.eas.modules.publicLesson.entity.PublicLesson;
import com.eas.modules.grade.dao.GradeDao;
import com.eas.modules.lesson.dao.LessonDao;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.lesson.service.LessonService;
import com.eas.modules.period.dao.PeriodDao;
import com.eas.modules.room.dao.RoomDao;
import com.eas.modules.week.dao.WeekDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PublicLessonService extends CrudService<PublicLessonDao,PublicLesson> {

    @Autowired
    PublicLessonDao publicLessonDao;

    @Autowired
    BuildingDao buildingDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    WeekDao weekDao;

    @Autowired
    PeriodDao periodDao;

    @Autowired
    LessonDao lessonDao;

    @Autowired
    AvailableRoomDao availableRoomDao;

    @Autowired
    LessonService lessonService;

    @Autowired
    AvailableRoomService availableRoomService;

    @Autowired
    GradeDao gradeDao;

    @Override
    public PublicLesson get(int id) {
        PublicLesson publicLesson = publicLessonDao.get(id);
        //把可用教室实体查出来放进去
        publicLesson.setAvailableRoom(availableRoomService.get(publicLesson.getAvailableRoom().getId()));
        //把课程实体查出来放进去
        publicLesson.setLesson(lessonService.get(publicLesson.getLesson().getId()));
        return publicLesson;
    }

    @Override
    public List<PublicLesson> findAllList() {
        List<PublicLesson> publicLessons = publicLessonDao.findAllList();
        for (PublicLesson publicLesson : publicLessons) {
            publicLesson.setLesson(lessonService.get(publicLesson.getLesson().getId()));
            publicLesson.setAvailableRoom(availableRoomService.get(publicLesson.getAvailableRoom().getId()));
        }
        return publicLessons;
    }

    @Override
    public int delete(int id) {
        return super.dao.delete(id);
    }


    @Override
    public int insert(PublicLesson publicLesson) {
        return publicLessonDao.insert(publicLesson);
    }

    public void deletePublicLessons(Integer[] ids) {
        publicLessonDao.deletePublicLessons(ids);
    }

    public void updateRoom(){
        AvailableRoom availableRoom=new AvailableRoom();
        List<PublicLesson> publicLessons = publicLessonDao.findAllList();
        List<PublicLesson> publicLessons1=publicLessonDao.getRoom(publicLessons);
        for(PublicLesson publicLesson:publicLessons1){
            availableRoom=availableRoomService.get(publicLesson.getAvailableRoom().getId());
            availableRoomDao.updateRoom(availableRoom);
        }
    }

    //将公共课表数据插入到考次表中
    public void insertToExam(PublicLesson publicLesson) {
                publicLessonDao.insertToExam(publicLesson);
        }

    public List<PublicLesson> findPublicLessonByCondition(Map<String,Object> map) {
        Integer weekId=(Integer) map.get("weekId");
        Integer timeId=(Integer)map.get("timeId");
        Integer roomId=(Integer)map.get("roomId");
        Integer lessonId=(Integer)map.get("lessonId");

        List<AvailableRoom> availableRooms=null;
        Lesson lesson=null;
        Map<String,Object> map1=new HashMap<>();
        boolean flag=false;
        if(weekId!=-1){
            map1.put("weekId",weekId);
            flag=true;
        }
        if(timeId!=-1){
            map1.put("timeId",timeId);
            flag=true;
        }
        if(roomId!=-1){
            map1.put("roomId",roomId);
            flag=true;
        }
        if(flag){
            availableRooms= availableRoomDao.findAvailableRoomsCriteria(map1);
        }
        if(lessonId!=-1){
            lesson=lessonDao.get(lessonId);
        }

        Map<String,Object> map2=new HashMap<>();
        map2.put("availableRooms",availableRooms);
        map2.put("lesson",lesson);
        map2.put("start",map.get("start"));
        map2.put("pageSize",map.get("pageSize"));

        List<PublicLesson> publicLessons=publicLessonDao.findPublicLessonsByCondition(map2);

        //根据表中的外键id补全publicLesson对象的信息。
        publicLessons =getEntirePublicLessons(publicLessons);
        return publicLessons;
    }
    private List<PublicLesson> getEntirePublicLessons(List<PublicLesson> publicLessons){
        List<Integer> availableRoomIds=new ArrayList<>();
        List<Integer> lessonIds=new ArrayList<>();
        if(publicLessons.size()>0){

            for(PublicLesson publicLesson:publicLessons){
                availableRoomIds.add(publicLesson.getAvailableRoom().getId());
                lessonIds.add(publicLesson.getLesson().getId());
            }

            List<AvailableRoom> availableRoomList= availableRoomService.findAvailableRoomsByIds(availableRoomIds);
            List<Lesson> lessonList=lessonDao.findLessonsByIds(lessonIds);
            Map<Integer,AvailableRoom> availableRoomMap=new HashMap<>();
            Map<Integer,Lesson> lessonMap=new HashMap<>();

            for(AvailableRoom availableRoom:availableRoomList){
                availableRoomMap.put(availableRoom.getId(),availableRoom);
            }
            for(Lesson lesson:lessonList){
                lessonMap.put(lesson.getId(),lesson);
            }


            for(PublicLesson publicLesson:publicLessons){
                publicLesson.setAvailableRoom(availableRoomMap.get(publicLesson.getAvailableRoom().getId()));
                publicLesson.setLesson(lessonMap.get(publicLesson.getLesson().getId()));
            }

        }
        return publicLessons;
    }

    @Override
    public PublicLesson getT(PublicLesson entity) {
        return publicLessonDao.getT(entity);
    }


    public List<List<Lesson>> test() {
        List<Lesson> lessons = lessonService.findAllList();
        List<List<Lesson>> lessonsList=new ArrayList<>();

        for(int i=0;i<lessons.size();i++){
            Lesson lesson=lessons.get(i);
            if(lesson.getFlag()==1){
                continue;
            }
            String lnum=lesson.getLnum();
            List<Lesson> lessons1=new ArrayList<>();
            lesson.setFlag(1);
            lessons1.add(lesson);
            for(int j=i+1;j<lessons.size();j++){
                Lesson currentLesson=lessons.get(j);
                if(currentLesson.getFlag()==0){
                    String currentLnum=currentLesson.getLnum();
                    if(lnum.equals(currentLnum)){
                        currentLesson.setFlag(1);
                        lessons1.add(currentLesson);
                    }
                }
            }
            lessonsList.add(lessons1);
        }
        return lessonsList;
    }

    public int getCount(Map<String, Object> map) {
        Integer weekId=(Integer) map.get("weekId");
        Integer timeId=(Integer)map.get("timeId");
        Integer roomId=(Integer)map.get("roomId");
        Integer lessonId=(Integer)map.get("lessonId");

        List<AvailableRoom> availableRooms=null;
        Lesson lesson=null;
        Map<String,Object> map1=new HashMap<>();
        boolean flag=false;
        if(weekId!=-1){
            map1.put("weekId",weekId);
            flag=true;
        }
        if(timeId!=-1){
            map1.put("timeId",timeId);
            flag=true;
        }
        if(roomId!=-1){
            map1.put("roomId",roomId);
            flag=true;
        }
        if(flag){
            availableRooms= availableRoomDao.findAvailableRoomsCriteria(map1);
        }
        if(lessonId!=-1){
            lesson=lessonDao.get(lessonId);
        }
        Map<String,Object> map2=new HashMap<>();
        map2.put("availableRooms",availableRooms);
        map2.put("lesson",lesson);

        return publicLessonDao.getCount(map2);
    }

    public int deleteAllPublicLessons() {
        return publicLessonDao.deleteAllPublicLessons();
    }

    public boolean vailidateExisitPublicLesson() {
        int count= publicLessonDao.getCount(new HashMap<>());
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
}


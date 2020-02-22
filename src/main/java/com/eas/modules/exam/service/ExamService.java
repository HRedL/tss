package com.eas.modules.exam.service;
import com.eas.common.persistence.CrudService;
import com.eas.modules.availableRoom.dao.AvailableRoomDao;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.availableRoom.service.AvailableRoomService;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.exam.dao.ExamDao;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.lesson.dao.LessonDao;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.lesson.service.LessonService;
import com.eas.modules.room.dao.RoomDao;
import com.eas.modules.teacher.dao.TeacherDao;
import com.eas.modules.teacher.entity.Teacher;
import com.eas.modules.teacher.service.TeacherService;
import com.eas.modules.period.dao.PeriodDao;
import com.eas.modules.week.dao.WeekDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author hhl
 * @Version 2018/10/20
 */
@Service
public class ExamService extends CrudService<ExamDao,Exam> {

    @Autowired
    ExamDao examDao;

    @Autowired
    TeacherDao teacherDao;

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
    TeacherService teacherService;


    /**
     * 通过教室的字符串id，例如：“1,2,3”查出每个教师并且封装list返回
     *
     * @param tidsStr
     * @return
     */
    private List<Teacher> getTeachers(String tidsStr) {
        List<Teacher> teachers = new ArrayList<>();
        if (!StringUtils.isEmpty(tidsStr)) {
            //分隔开每个教师 id
            String[] tidsArr = tidsStr.split(",");
            //遍历教师id，查出教师信息并且list
            for (String tid : tidsArr) {
                if (!StringUtils.isEmpty(tid)) {
                    Teacher teacher = teacherDao.get(Integer.parseInt(tid));
                    teachers.add(teacher);
                }
            }
        }

        return teachers;
    }

    /*
    得到考次信息
     */
    @Override
    public Exam get(int id) {
        Exam exam = examDao.get(id);
        //把教师实体查出来放进去
        exam.setTeachers(getTeachers(exam.gettIds()));
        //把可用教室实体查出来放进去
        exam.setAvailableRoom(availableRoomService.get(exam.getAvailableRoom().getId()));
        //把课程实体查出来放进去
        exam.setLesson(lessonService.get(exam.getLesson().getId()));
        return exam;
    }


    @Override
    public List<Exam> findAllList() {

        List<Exam> exams = examDao.findAllList();
        for (Exam exam : exams) {
            exam.setLesson(lessonService.get(exam.getLesson().getId()));
            exam.setAvailableRoom(availableRoomService.get(exam.getAvailableRoom().getId()));
            exam.setTeachers(getTeachers(exam.gettIds()));
        }
        return exams;


//        return super.dao.findAllList();
    }

    @Override
    public int delete(int id) {
        return super.dao.delete(id);
    }


    @Override
    public int insert(Exam exam) {
        return examDao.insert(exam);
    }

    // @Override
//    public int insert(Exam exam){
//
//        AvailableRoom availableRoom=exam.getAvailableRoom();
//
//        Room room=availableRoom.getRoom();
//        room.setBuilding(buildingDao.getT(room.getBuilding()));
//
//        availableRoom.setRoom(roomDao.getT(room));
//        availableRoom.setWeek(weekDao.getT(availableRoom.getWeek()));
//        availableRoom.setPeriod(timeDao.getT(availableRoom.getPeriod()));
//        exam.setAvailableRoom(availableRoomDao.getT(availableRoom));
//
//        StringBuffer tids=new StringBuffer();
//        List<Teacher> teachers=exam.getTeachers();
//        for(Teacher teacher:teachers){
//            Teacher teacher1= teacherDao.getT(teacher);
//            tids.append(teacher1.getId()+",");
//        }
//        exam.settIds(new String(tids));
//
//        Lesson lesson=exam.getLesson();
//        exam.setLesson( lessonDao.getT(lesson));
//
//        return examDao.insert(exam);
//    }

    @Override
    public int update(Exam exam) {
        return examDao.update(exam);
    }


//    @Override
//    public int update(Exam exam){
//
//        AvailableRoom availableRoom=exam.getAvailableRoom();
//
//        Room room=availableRoom.getRoom();
//        room.setBuilding(buildingDao.getT(room.getBuilding()));
//
//        availableRoom.setRoom(roomDao.getT(room));
//        availableRoom.setWeek(weekDao.getT(availableRoom.getWeek()));
//        availableRoom.setPeriod(timeDao.getT(availableRoom.getPeriod()));
//        exam.setAvailableRoom(availableRoomDao.getT(availableRoom));
//
//        StringBuffer tids=new StringBuffer();
//        List<Teacher> teachers=exam.getTeachers();
//        for(Teacher teacher:teachers){
//            Teacher teacher1= teacherDao.getT(teacher);
//            tids.append(teacher1.getId()+",");
//        }
//        exam.settIds(new String(tids));
//
//        Lesson lesson=exam.getLesson();
//        exam.setLesson( lessonDao.getT(lesson));
//
//        return examDao.update(exam);
//    }


    public void deleteExams(Integer[] ids) {
        examDao.deleteExams(ids);

    }

    public List<Exam> findExamByCondition(String queryText) {
        List<Exam> exams = null;

        //如果有查询条件就行条件查询
        if (queryText != null) {
            //模糊查询出exam
            List<AvailableRoom> availableRooms = availableRoomService.findAvailableRoomsByCondition1(queryText);
            List<Lesson> lessons = lessonDao.findLessonsByCondition(queryText);
            List<Teacher> teachers = teacherDao.findTeacherByCondition(queryText);
            //根据availableRooms/lessons/teachers查出exam的集合
            List<Exam> exams1 = getExamsByAvailableRooms(availableRooms);
            List<Exam> exams2 = getExamsByLessons(lessons);
            List<Exam> exams3 = getExamsByTeachers(teachers);
            //取三集合的并集
            exams = unionAvailableRooms(exams3, unionAvailableRooms(exams1, exams2));

        } else {
            exams = examDao.findAllList();
        }
        List<Exam> examList = new ArrayList<>();


        //根据表中的外键id补全exam对象的信息。
        for (Exam exam : exams) {
            exam.setAvailableRoom(availableRoomService.get(exam.getAvailableRoom().getId()));
            exam.setLesson(lessonService.get(exam.getLesson().getId()));
            exam.setTeachers(getTeachers(exam.gettIds()));
            examList.add(exam);
        }


        return examList;

    }

    private List<Exam> getExamsByAvailableRooms(List<AvailableRoom> availableRooms) {
        List<Exam> exams = new ArrayList<>();
        for (AvailableRoom availableRoom : availableRooms) {
            Exam exam = new Exam();
            exam.setAvailableRoom(availableRoom);
            List<Exam> exams1 = examDao.getExams(exam);
            if (exams1.size() != 0) {
                exams.addAll(exams1);
            }
        }
        return exams;
    }

    private List<Exam> getExamsByLessons(List<Lesson> lessons) {
        List<Exam> exams = new ArrayList<>();
        for (Lesson lesson : lessons) {
            Exam exam = new Exam();
            exam.setLesson(lesson);
            List<Exam> exams1 = examDao.getExams(exam);
            if (exams1.size() != 0) {
                exams.addAll(exams1);
            }
        }
        return exams;
    }

    private List<Exam> getExamsByTeachers(List<Teacher> teachers) {
        List<Exam> exams = new ArrayList<>();
        for (Teacher teacher : teachers) {
            Exam exam = new Exam();
            exam.settIds(teacher.getId().toString());
            List<Exam> exams1 = examDao.getExams(exam);
            if (exams1.size() != 0) {
                exams.addAll(exams1);
            }
        }
        return exams;
    }

    private List<Exam> unionAvailableRooms(List<Exam> exams1, List<Exam> exams2) {

        if (exams1.size() == 0) {
            //如果集合1,2都为空，那么直接返回一个空的集合对象
            if (exams2.size() == 0) {
                return new ArrayList<>();
                //如果集合1为空，集合2不为空，那么直接返回集合2
            } else {
                return exams2;
            }
        }
        //如果集合1不为空，集合2为空，那么直接返回集合1
        if (exams2.size() == 0) {
            return exams1;
        }
        //如果集合1,2都不为空
        List<Exam> exams = exams1;
        for (Exam exam2 : exams2) {
            boolean equal = false;
            for (Exam exam1 : exams1) {
                //如果集合里里已经有，则设置标志位为true，结束当前循环
                if (exam2.getId().equals(exam1.getId())) {
                    equal = true;
                    break;
                }
            }
            //如果集合里没有，那么保存下来该元素
            if (!equal) {
                exams.add(exam2);
            }
        }
        return exams;
    }


    @Override
    public Exam getT(Exam entity) {
        return examDao.getT(entity);
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




    @Transactional
    public List<Exam> generateExam() {
        //根据weekid升序，timeid升序查出所有可用教室（前提：week表的时间是升序的，time表里的时间也是升序的）
        List<AvailableRoom> availableRooms = availableRoomService.findAllList();
        //根据课程号升序查出所有课程
        List<Lesson> lessons = lessonService.findAllList();


        //按照同一时间同一教学楼。。。将可用教室分为各个list
        List<List<AvailableRoom>> availableRoomsLists=new ArrayList<>();
        for(int i=0;i<availableRooms.size();i++){

            AvailableRoom availableRoom=availableRooms.get(i);
            if(availableRoom.getSortFlag()==1){
                continue;
            }
            int bid=availableRoom.getRoom().getBuilding().getId();
            int weekid=availableRoom.getWeek().getId();
            int timeid=availableRoom.getPeriod().getId();
            List<AvailableRoom> availableRooms1=new ArrayList<>();
            availableRooms1.add(availableRoom);
            availableRoom.setSortFlag(1);
            for(int j=i+1;j<availableRooms.size();j++){
                AvailableRoom currentAvailableRoom=availableRooms.get(j);
                if(currentAvailableRoom.getFlag()==0){
                    int currentBid=currentAvailableRoom.getRoom().getBuilding().getId();
                    int currentWeekid=currentAvailableRoom.getWeek().getId();
                    int currentTimeid=currentAvailableRoom.getPeriod().getId();
                    if ( bid==currentBid && weekid == currentWeekid && timeid == currentTimeid) {
                        currentAvailableRoom.setSortFlag(1);
                        availableRooms1.add(currentAvailableRoom);
                    }
                }

            }
            availableRoomsLists.add(availableRooms1);

        }


        //按照课程号相同。。。将课程分为各个list
        List<List<Lesson>> lessonsLists=new ArrayList<>();

        for(int i=0;i<lessons.size();i++){
            Lesson lesson=lessons.get(i);
            if(lesson.getSortFlag()==1){
                continue;
            }
            String lnum=lesson.getLnum();
            List<Lesson> lessons1=new ArrayList<>();
            lesson.setSortFlag(1);
            lessons1.add(lesson);
            for(int j=i+1;j<lessons.size();j++){
                Lesson currentLesson=lessons.get(j);
                if(currentLesson.getFlag()==0){
                    String currentLnum=currentLesson.getLnum();
                    if(lnum.equals(currentLnum)){
                        currentLesson.setSortFlag(1);
                        lessons1.add(currentLesson);
                    }
                }
            }
            lessonsLists.add(lessons1);
        }

        //最终的考次信息存放在这里面
        List<Exam> exams = new ArrayList<>();

        Map<Integer,List<List<Lesson>>> lessonMap=new HashMap<>();
        int max=100;
        int min=30;
        int emptyRoomCount=0;

        for(List<AvailableRoom> availableRoomList:availableRoomsLists){
            //取出一个教室来一个教室只放一个课程号的，先放着，放着放着课程慢慢放到课程的人数比教室容量大
            for(AvailableRoom availableRoom:availableRoomList){
                int capacity=availableRoom.getRoom().getCapacity();
                Exam exam=new Exam();
                //教室容量<min，那么这个教室就暂时废了，不用他
                if(capacity<min){
                    emptyRoomCount++;
                    //如果教室容量大于课程的最大值，那么说明，这个教室随便装，就装最大的就好了
                }else if(capacity>max){
                    exam.setAvailableRoom(availableRoom);
                    List<Lesson> lessons1= lessonMap.get(max).get(0);
                    if(lessonMap.get(max).size()>0){
                        lessonMap.remove(max);
                    }else{
                        lessonMap.get(max).remove(0);
                    }
                    //放进exam
                }else{
                    for(int i=30;i<=max;i=i+10){
                        if(capacity<i){
                            exam.setAvailableRoom(availableRoom);
                            List<Lesson> lessons1= lessonMap.get(max).get(0);
                            if(lessonMap.get(i).size()>0){
                                lessonMap.remove(i);
                            }else{
                                lessonMap.get(i).remove(0);
                            }
                            //放进exam
                            break;
                        }
                    }
                }
                //每分配完一个可用教室，就更新lesson的max和min
                //如果说此时可用教室的



            }
        }







        int count=0;
        int size=0;
        boolean flag=true;
        for(List<Lesson> lessonList:lessonsLists){
            List<AvailableRoom> availableRoomList=availableRoomsLists.get(count);
            if(flag){
                size=availableRoomList.size();
                flag=false;
            }
            for(Lesson lesson:lessonList){
                if(lesson.getFlag()==0){

                    //定义一些东西
                    int notMatchNum=0;
                    int bigMatchNum=0;
                    List<AvailableRoom> notMatchRoom=new ArrayList<>();
                    List<AvailableRoom> bigMatchRoom=new ArrayList<>();

                    //遍历所有可用教室
                    for(AvailableRoom availableRoom:availableRoomList){
                        //就是如果是当前可用教室还未分配那么就进行分配
                        if (availableRoom.getFlag()==0){
                            //可用教室的容量-课程的人数（<0即为太小，需要分为多个；0-30即为正好；>30为太大，应该找个更合适的）
                            int enough = availableRoom.getRoom().getCapacity() - lesson.getLtotalnum();
                            //如果说这么数小于零，那么就应该找更合适的教室或者说分配在多个教室中
                            if (enough < 0) {
                                //使得不匹配次数加1，并且保留不匹配可用教室
                                notMatchNum++;
                                notMatchRoom.add(availableRoom);
                                //如果不匹配次数大于5（也就是说找了五个可用教室了还不满足，那么就把这个课程分到多个教室，从刚不满足的那几个可用教室里面找几个教室给这个分）
                                if(notMatchNum>5){
                                    //记录一下够不够
                                    enough=-lesson.getLtotalnum();
                                    for(AvailableRoom availableRoom1:notMatchRoom){
                                        Exam exam = new Exam();
                                        lesson.setFlag(1);
                                        exam.setLesson(lesson);
                                        availableRoom1.setFlag(1);
                                        exam.setAvailableRoom(availableRoom1);
                                        size--;
                                        exams.add(exam);
                                        enough+=availableRoom1.getRoom().getCapacity();
                                        //如果够了
                                        if(enough>0){
                                            break;
                                        }
                                    }
                                    //分配结束，结束本次循环
                                    break;
                                }
                                //如果说这个数大于0小于30，说明这个教室的容量正好，所以说分配给这个课程该可用教室
                            } else if (enough <= 30) {
                                Exam exam = new Exam();
                                lesson.setFlag(1);
                                exam.setLesson(lesson);
                                exam.setAvailableRoom(availableRoom);
                                availableRoom.setFlag(1);
                                size--;
                                exams.add(exam);
                                break;
                                //如果说这个数大于30，那么说明这个教室容量过大，暂时不分配给当前教室
                            }else{
                                bigMatchNum++;
                                //如果比较了三个教室，容量都比当前课程大的超过30，那么找一个最小的给这个课程
                                bigMatchRoom.add(availableRoom);
                                if(bigMatchNum>3){
                                    AvailableRoom smallBigRoom=new AvailableRoom();
                                    smallBigRoom=bigMatchRoom.get(0);
                                    for(int i=1;i<bigMatchRoom.size();i++){
                                        AvailableRoom availableRoom1=bigMatchRoom.get(i);
                                        if(smallBigRoom.getRoom().getCapacity()>availableRoom1.getRoom().getCapacity()){
                                            smallBigRoom=availableRoom1;
                                        }
                                    }
                                    Exam exam = new Exam();
                                    exam.setLesson(lesson);
                                    exam.setAvailableRoom(smallBigRoom);
                                    smallBigRoom.setFlag(1);
                                    size--;
                                    exams.add(exam);
                                    break;
                                }
                            }
                            //如果当前可用教室已经被分配了，那么就去看下一个可用教室
                        }else{

                        }
                    }

                }
            }
            if(size<3||lessonList.get(lessonList.size()-1).getFlag()==0){
                count++;
                flag=true;
            }
        }

        List<Teacher> teachers=teacherDao.findAllOrderByTINVTIMES();
        int i=0;
        for(Exam exam:exams){
            StringBuffer tIds=new StringBuffer();
            for(int j=0;j<exam.getAvailableRoom().getRoom().getTtotalnum();j++){
               tIds.append(teachers.get(i).getId());
               tIds.append(",");
               i++;
               if(i==exams.size()-1){
                   i=0;
               }
            }
            exam.settIds(new String(tIds));
        }
        examDao.insertExams(exams);

        return exams;
    }


    @Transactional
    public List<Exam> generateExam1() {

        //根据weekid升序，timeid升序查出所有可用教室（前提：week表的时间是升序的，time表里的时间也是升序的）
        List<AvailableRoom> availableRooms = availableRoomService.findAllList();
        //根据课程号升序查出所有课程
        List<Lesson> lessons = lessonService.findAllList();


        //按照同一时间同一教学楼。。。将可用教室分为各个list
        List<List<AvailableRoom>> availableRoomsLists=new ArrayList<>();
        for(int i=0;i<availableRooms.size();i++){

            AvailableRoom availableRoom=availableRooms.get(i);
            if(availableRoom.getSortFlag()==1){
                continue;
            }
            int bid=availableRoom.getRoom().getBuilding().getId();
            int weekid=availableRoom.getWeek().getId();
            int timeid=availableRoom.getPeriod().getId();
            List<AvailableRoom> availableRooms1=new ArrayList<>();
            availableRooms1.add(availableRoom);
            availableRoom.setSortFlag(1);
            for(int j=i+1;j<availableRooms.size();j++){
                AvailableRoom currentAvailableRoom=availableRooms.get(j);
                if(currentAvailableRoom.getFlag()==0){
                    int currentBid=currentAvailableRoom.getRoom().getBuilding().getId();
                    int currentWeekid=currentAvailableRoom.getWeek().getId();
                    int currentTimeid=currentAvailableRoom.getPeriod().getId();
                    if ( bid==currentBid && weekid == currentWeekid && timeid == currentTimeid) {
                        currentAvailableRoom.setSortFlag(1);
                        availableRooms1.add(currentAvailableRoom);
                    }
                }

            }
            availableRoomsLists.add(availableRooms1);

        }


        //按照课程号相同。。。将课程分为各个list
        List<List<Lesson>> lessonsLists=new ArrayList<>();

        for(int i=0;i<lessons.size();i++){
            Lesson lesson=lessons.get(i);
            if(lesson.getSortFlag()==1){
                continue;
            }
            String lnum=lesson.getLnum();
            List<Lesson> lessons1=new ArrayList<>();
            lesson.setSortFlag(1);
            lessons1.add(lesson);
            for(int j=i+1;j<lessons.size();j++){
                Lesson currentLesson=lessons.get(j);
                if(currentLesson.getFlag()==0){
                    String currentLnum=currentLesson.getLnum();
                    if(lnum.equals(currentLnum)){
                        currentLesson.setSortFlag(1);
                        lessons1.add(currentLesson);
                    }
                }
            }
            lessonsLists.add(lessons1);
        }

        //最终的考次信息存放在这里面
        List<Exam> exams=new ArrayList<>();
        //分配出去的教室数
      // List<Integer> usedRoomCount=new ArrayList<>();

       //记录第几个可用教室
       int avaliableRoomListCount=0;

       for(int i=0;i<lessonsLists.size();i++){
           //记录这一轮形成exam
           List<Exam> signedExam=new ArrayList<>();
           //记录一下被分配的可用教室
           List<AvailableRoom> signedAvailableRoom=new ArrayList<>();
           //取出一组可用教室
           List<AvailableRoom> availableRoomList=availableRoomsLists.get(avaliableRoomListCount);
           //在这一块，，，取出课程的班级，然后对应着有个考次的时间，，反正瞎弄出来，，
           //然后跟当前可用教室的时间比较一下，如果不行，那么就
           //avaianleRoomList++;
           //i--;
           //continue;
           //反正就是怎么瞎弄出来，还没想好。。。。。


           //记录当前可用教室里面还剩下多少教室
          // Integer restCurrentRoomCount=usedRoomCount.get(avaliableRoomListCount);
           //获取一组课程
           List<Lesson> lessons1=lessonsLists.get(i);

           //记录当前课程分配了几个
           int signLessonCount=0;
           //遍历课程
           for(Lesson lesson:lessons1){

               List<AvailableRoom> notMatchRoom=new ArrayList<>();
               List<AvailableRoom> bigMatchRoom=new ArrayList<>();
               //遍历可用教室
               for(int k=0;k<availableRoomList.size();k++){
                   AvailableRoom availableRoom=availableRoomList.get(k);
//               }
//               for(AvailableRoom availableRoom:availableRoomList){
                   //就是如果是当前可用教室还未分配那么就进行分配
                   if (availableRoom.getFlag()==0){
                       //可用教室的容量-课程的人数（<0即为太小，需要分为多个；0-30即为正好；>30为太大，应该找个更合适的）
                       int enough = availableRoom.getRoom().getCapacity() - lesson.getLtotalnum();
                       //如果说数小于0，说明当前教室不够
                       if (enough < 0) {
                           //把小于的教室记录下来
                           notMatchRoom.add(availableRoom);
                           //如果有了五个教室都不够这个课程使，或者说到了最后了
                           if (notMatchRoom.size() >= 5 || (notMatchRoom.size() > 0 && k == availableRoomList.size() - 1)) {
                               //记录一下够不够
                               enough = -lesson.getLtotalnum();
                               //遍历记录下来的小于课程人数的教室
                               for (AvailableRoom availableRoom1 : notMatchRoom) {

                                   //新建一条exam
                                   Exam exam=new Exam();
                                   //设置课程
                                   exam.setLesson(lesson);
                                   //设置可用教室
                                   exam.setAvailableRoom(availableRoom1);
                                   //设置可用教室已经被分配
                                   availableRoom1.setFlag(1);
                                   //把当前exam加入exams
                                   signedExam.add(exam);
                                   //分配出去了一个教室，剩余教室-1
                                  // restCurrentRoomCount--;
                                   //并记录下被分配的教师
                                   signedAvailableRoom.add(availableRoom1);
                                   //enough=enough+可用教室的容量
                                   enough += availableRoom1.getRoom().getCapacity();
                                   //如果够了
                                   if (enough > 0) {
                                       //设置课程分配数量+1
                                       signLessonCount++;
                                       break;
                                       //如果说没够，那么课程分配数量永远不会+1，那么最后会回复
                                   }
                               }
                                break;

                           }
                       //说明该教室分配给当前课程后，空座位数小于30，则说明正好，就把这个教室给他
                       }else if(enough<30){
                           //新建一条exam
                           Exam exam=new Exam();
                           //设置课程
                           exam.setLesson(lesson);
                           //设置可用教室
                           exam.setAvailableRoom(availableRoom);
                           //设置可用教室已经被分配
                           availableRoom.setFlag(1);
                           //把当前exam加入exams
                           signedExam.add(exam);
                           //分配出去了一个教室，剩余教室-1
                           //restCurrentRoomCount--;
                           //并记录下被分配的教师
                           signedAvailableRoom.add(availableRoom);
                           //分配出去了一个课程
                           signLessonCount++;
                           break;
                       //就是太大了，说明了这个教室不合适。
                       }else{
                           //如果比较了三个教室，容量都比当前课程大的超过30，那么找一个最小的给这个课程
                           bigMatchRoom.add(availableRoom);
                           if(bigMatchRoom.size()>3||(bigMatchRoom.size()>0&&k==availableRoomList.size()-1)) {
                               //求出可用可用教室容量最小的可用教室
                               AvailableRoom smallBigRoom = new AvailableRoom();
                               smallBigRoom = bigMatchRoom.get(0);
                               for (int j = 1; j < bigMatchRoom.size(); j++) {
                                   AvailableRoom availableRoom1 = bigMatchRoom.get(j);
                                   if (smallBigRoom.getRoom().getCapacity() > availableRoom1.getRoom().getCapacity()) {
                                       smallBigRoom = availableRoom1;
                                   }
                               }

                               //新建一条exam
                               Exam exam=new Exam();
                               //设置课程
                               exam.setLesson(lesson);
                               //设置可用教室
                               exam.setAvailableRoom(smallBigRoom);
                               //设置可用教室已经被分配
                               smallBigRoom.setFlag(1);
                               //把当前exam加入exams
                               signedExam.add(exam);
                               //分配出去了一个教室，剩余教室-1
                               // restCurrentRoomCount--;
                               //并记录下被分配的教师
                               signedAvailableRoom.add(smallBigRoom);
                               signLessonCount++;
                               break;
                           }

                       }
                   }
               }
           }
           //如果说当前一组课程全被分配了
           if(signLessonCount==lessons1.size()) {
               exams.addAll(signedExam);
               //如果说当前并没有被分配，那么就回到原来的一组课程再改一组可用教室,并且使得被分配的可用教室回到原样
           }else{
               //回复原来的课程
               i--;
               //把分配的可用教室收回来
               for(AvailableRoom availableRoom:signedAvailableRoom){
                   availableRoom.setFlag(0);
               }
               //换一个可用教室分配
               avaliableRoomListCount++;
               if(avaliableRoomListCount==availableRoomList.size()-1){
                   avaliableRoomListCount=0;

               }
           }

       }

        examDao.insertExams(exams);

        return exams;

    }


}






//    public List<Exam> generateExam() {
//        List<AvailableRoom> availableRooms = availableRoomService.findAllList();
//        List<Lesson> lessons = lessonService.findAllList();
//        //排除已经被占用的可用教室
//        //分配课程，取出来课程分配教室
//        List<Exam> exams = new ArrayList<>();
//        List<AvailableRoom> availableRooms1 = new ArrayList<>();
//        for (int i = 0; i < lessons.size(); i++) {
//            Exam exam = new Exam();
//            Lesson lesson = lessons.get(i);
//            exam.setLesson(lesson);
//            int flag = 0;
//            for (int j = 0; j < availableRooms.size(); j++) {
//                //取出一个availableRoom
//                AvailableRoom availableRoom = availableRooms.get(j);
//                int enough = availableRoom.getRoom().getCapacity() - lesson.getLtotalnum();
//                //如果说这么数小于零，那么就应该找更合适的教室或者说分配在多个教室中
//                if (enough < 0) {
//                    //就是记录如果匹配了5个教室，都比这个课程的容量小，那么就把这个课程分配到不同的教室里面
//                    flag++;
//                    //这个用来存放比较的最近的五个教室
//                    availableRooms1.add(availableRoom);
//                    if (flag > 5) {
//                        enough = -lesson.getLtotalnum();
//                        for (int k = 0; k < 5; i++) {
//
//                        }
//                        for (AvailableRoom availableRoom2 : availableRooms1) {
//                            //教室的容量减去课程的人数
//                            //把这个五个可用教室挑选几个分配给这个课程（五个教室一定够了吧）
//                            //如果还不够，就接着分配
//                            if (enough < 0) {
//                                exam.setAvailableRoom(availableRoom2);
//                                exams.add(exam);
//                                enough = enough + availableRoom2.getRoom().getCapacity();
//                                //够了，就break出循环
//                            } else {
//                                break;
//                            }
//                        }
//                        break;
//                    }
//                    //如果说这个数大于0小于30，说明这个教室的容量正好，所以说分配给这个课程该可用教室
//                } else if (enough < 30) {
//                    exam.setAvailableRoom(availableRoom);
//                    availableRooms.remove(j);
//                    exams.add(exam);
//                    break;
//                    //如果说这个数大于30，那么说明这个教室容量过大，暂时不分配给当前教室
//                } else {
//
//                }
//            }
//        }
//
//        examDao.insertExams(exams);
//
//        return exams;
//    }



//    public int generateExam(int weekId,int timeId) {
//
//        List<Exam> exams=new ArrayList<>();
//        List<Lesson> lessons=new ArrayList<>();
//        //查出指定Week和Time的的可用教室
//        List<AvailableRoom> availableRooms=new ArrayList<>();
//        //假设就是上面这些数据了
//
//        //把课程分一下类
//        List<Lesson> lessons_30=new ArrayList<>();
//        List<Lesson> lessons_60=new ArrayList<>();
//        List<Lesson> lessons_90=new ArrayList<>();
//        List<Lesson> lessons_120=new ArrayList<>();
//        List<Lesson> lessons_big=new ArrayList<>();
//        for(Lesson lesson:lessons){
//            if(lesson.getLtotalnum()<=30){
//                lessons_30.add(lesson);
//            }else if(lesson.getLtotalnum()<=60){
//                lessons_60.add(lesson);
//            }else if(lesson.getLtotalnum()<=90){
//                lessons_90.add(lesson);
//            }else if(lesson.getLtotalnum()<=120){
//                lessons_120.add(lesson);
//            }else{
//                lessons_big.add(lesson);
//            }
//        }
//        //availableRoom是按照教学楼升序，教室号降序排列
//        int count_30,count_60,count_90,count_120;
//        count_30=count_60=count_90=count_120=0;
//        List<AvailableRoom> weifenpeiRoom=new ArrayList<>();
//        for(AvailableRoom availableRoom:availableRooms) {
//            int capacity = availableRoom.getRoom().getCapacity();
//            if (capacity < 30) {
//                weifenpeiRoom.add(availableRoom);
//            } else if (capacity <= 60) {
//                if (count_30 > lessons_30.size()) {
//                    Lesson lesson = lessons_30.get(count_30);
//                    Exam exam = new Exam();
//                    exam.setAvailableRoom(availableRoom);
//                    exam.setLesson(lesson);
//                    exams.add(exam);
//                    count_30++;
//                } else {
//                    weifenpeiRoom.add(availableRoom);
//                }
//
//            } else if (capacity <= 90) {
//                if (count_60 > lessons_60.size()) {
//                    Lesson lesson = lessons_60.get(count_60);
//                    Exam exam = new Exam();
//                    exam.setAvailableRoom(availableRoom);
//                    exam.setLesson(lesson);
//                    exams.add(exam);
//                    count_60++;
//                } else {
//                    weifenpeiRoom.add(availableRoom);
//                }
//            } else if (capacity <= 120) {
//                if (count_90 > lessons_90.size()) {
//                    Lesson lesson = lessons_90.get(count_90);
//                    Exam exam = new Exam();
//                    exam.setAvailableRoom(availableRoom);
//                    exam.setLesson(lesson);
//                    exams.add(exam);
//                    count_90++;
//                } else {
//                    weifenpeiRoom.add(availableRoom);
//                }
//            } else {
//                if (count_120 > lessons_120.size()) {
//                    Lesson lesson = lessons_120.get(count_120);
//                    Exam exam = new Exam();
//                    exam.setAvailableRoom(availableRoom);
//                    exam.setLesson(lesson);
//                    exams.add(exam);
//                    count_120++;
//                } else {
//                    weifenpeiRoom.add(availableRoom);
//                }
//            }
//
//        }
//        for(Lesson lesson:lessons_big){
//            int ltotalnum=lesson.getLtotalnum();
//            for(AvailableRoom availableRoom1:weifenpeiRoom){
//                Exam exam=new Exam();
//                exam.setLesson(lesson);
//                exam.setAvailableRoom(availableRoom1);
//                exams.add(exam);
//            }
//
//
//        }
//
//
//
//    }

//    public int deleteAllExam() {
//        return examDao.deleteAllExam();
//    }


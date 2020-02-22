package com.eas.modules.lesson.service;

import com.eas.common.persistence.CrudService;
import com.eas.common.utils.DictUtil;
import com.eas.common.utils.GradeUtils;
import com.eas.common.utils.TeacherUtils;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import com.eas.modules.grade.dao.GradeDao;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.lesson.dao.LessonDao;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.teacher.dao.TeacherDao;
import com.eas.modules.teacher.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author hhl
 * @Version 2018/10/20
 */
@Service
public class LessonService extends CrudService<LessonDao,Lesson> {


    @Autowired
    LessonDao lessonDao;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    DictDao dictDao;

    private List<Grade> getGrades(String gradeIdsStr){
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
    /*
    查询课程信息
     */
    @Override
    public Lesson get(int id) {
        Lesson lesson=lessonDao.get(id);

        lesson.setGrades(getGrades(lesson.getgIds()));
        lesson.setTeacher(teacherDao.get(lesson.getTeacher().getId()));
        Dict dict=dictDao.getDictByCondition("LTYPE",lesson.getType());
        lesson.setType(dict.getLabel());
        return lesson;
    }

    public Lesson getWithoutDict(int id){
        Lesson lesson=lessonDao.get(id);
        lesson.setGrades(getGrades(lesson.getgIds()));
        lesson.setTeacher(teacherDao.get(lesson.getTeacher().getId()));
        return lesson;
    }

    @Override
    public List<Lesson> findAllList() {
        /*
        List<Lesson> lessons=lessonDao.findAllList();

        for (Lesson lesson : lessons) {
            lesson.setTeacher(teacherDao.get(lesson.getTeacher().getId()));
            lesson.setGrades(getGrades(lesson.getgIds()));
        }

        return lessons;
        */

        return super.dao.findAllList();
    }

    public List<Lesson> findAllLessonWithAllInfo(){
       List<Lesson> lessons= lessonDao.findAllLessonWithAllInfo();

       //花里胡哨的把grades放进去
       List<Grade> grades=gradeDao.findAllGrade();
       Map<Integer,Grade> gradeMap= GradeUtils.getMapWithKeyId(grades);

        List<Teacher> teachers=teacherDao.findAllList();
        Map<Integer,Teacher> teacherMap= TeacherUtils.getMapWithKeyId(teachers);
       for(Lesson lesson:lessons){
           List<Grade> grades2=new ArrayList<>();
           List<Grade> grades1= GradeUtils.getGradeIdByGids(lesson.getgIds());
           for(Grade grade:grades1){
               grades2.add(gradeMap.get(grade.getId()));
           }
           lesson.setGrades(grades2);

           lesson.setTeacher(teacherMap.get(lesson.getTeacher().getId()));
       }
        return lessons;
    }



    @Override
    @Transactional
    public int delete(int id) {
        return super.delete(id);
    }


   // @Override
//    public int insert(Lesson lesson) {
//
//        Teacher teacher= teacherDao.getT(lesson.getTeacher());
//        lesson.setTeacher(teacher);
//
//        StringBuffer stringBuffer=new StringBuffer();
//        List<Grade> grades=lesson.getGrades();
//        for(Grade grade:grades){
//            Grade grade1 =gradeDao.getT(grade);
//            stringBuffer.append(grade1.getId()).append(",");
//        }
//        lesson.setgIds(new String(stringBuffer));
//        return lessonDao.insert(lesson);
//    }

    @Transactional
    @Override
    public int insert(Lesson lesson) {

        return lessonDao.insert(lesson);
    }


//    @Override
//    public int update(Lesson lesson) {
//        Teacher teacher= teacherDao.getT(lesson.getTeacher());
//        lesson.setTeacher(teacher);
//
//        StringBuffer stringBuffer=new StringBuffer();
//        List<Grade> grades=lesson.getGrades();
//        for(Grade grade:grades){
//            Grade grade1 =gradeDao.getT(grade);
//            stringBuffer.append(grade1.getId()).append(",");
//        }
//        lesson.setgIds(new String(stringBuffer));
//        return lessonDao.update(lesson);
//    }

    @Transactional
    @Override
    public int update(Lesson lesson) {
        //可以的话这里尽量先查一下数据，判断一下是否被修改了再进行修改。

        return lessonDao.update(lesson);
    }


    public List<Lesson> findLessonsByCondition(String queryText){
        return lessonDao.findLessonsByCondition(queryText);
    }

    public void deleteLessons(Integer[] ids) {
        lessonDao.deleteLessons(ids);
    }

    /**
     * 根据条件查找所有课程
     * @param queryText
     * @return
     */
    public List<Lesson> findListByText(String queryText) {
        List<Lesson> lessons= lessonDao.findListByText(queryText);
        if(lessons.size()>0) {
            List<Dict> dicts=dictDao.getDictsByCondition("LTYPE");
            Map<String,String> dictMap= DictUtil.getMap(dicts);
            for (Lesson lesson : lessons) {
                 lesson.setType(dictMap.get(lesson.getType()));
            }
        }
        return lessons;
    }

    public Lesson getLessonByLnum(String lnum) {
        return lessonDao.getLessonByLnum(lnum);
    }

}

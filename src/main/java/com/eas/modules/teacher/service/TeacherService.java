package com.eas.modules.teacher.service;
import com.eas.common.persistence.CrudService;
import com.eas.common.utils.DictUtil;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import com.eas.modules.exam.dao.ExamDao;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.student.entity.Student;
import com.eas.modules.teacher.dao.TeacherDao;
import com.eas.modules.teacher.entity.Teacher;
import com.eas.modules.teacher.service.ex.TeaExamInfNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author llp
 * @Version 2018/10/23
 */
@Service
public class TeacherService extends CrudService<TeacherDao,Teacher> {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    ExamDao examDao;

    @Autowired
    DictDao dictDao;

    public List<Exam> findExamsByTid(Integer tid){
        String campusType = "CAMPUS";
        String bnumType = "BNUM";
        String ltype = "LTYPE";
        List<Exam> exams = teacherDao.findExamsByTid(tid);
        if(exams != null){
            System.out.println(exams.get(0));
            System.out.println("service中的findExamsByGid():" + exams.get(0).getLesson().getSubject());
            for(int i = 0; i < exams.size(); i++){
                String campusValue = exams.get(i).getAvailableRoom()
                        .getRoom().getBuilding().getCampus();
                System.out.println("campusValue:" + campusValue);
                String bnumValue = exams.get(i).getAvailableRoom()
                        .getRoom().getBuilding().getBnum();
                System.out.println("bnumValue:" + bnumValue);
                String ltypeValue = exams.get(i).getLesson().getType();
                System.out.println("ltypeValue:" + ltypeValue);

                Dict dict = teacherDao.
                        findLabelByValueAndType(campusValue, campusType);
                System.out.println("dict:" + dict);
                String campusLabel = dict.getLabel();
                System.out.println("campusLabel:" + campusLabel);
                String bnumLabel = teacherDao.
                        findLabelByValueAndType(bnumValue, bnumType).getLabel();
                String ltypeLabel = teacherDao.
                        findLabelByValueAndType(ltypeValue, ltype).getLabel();

                exams.get(i).getAvailableRoom()
                        .getRoom().getBuilding().setCampus(campusLabel);
                exams.get(i).getAvailableRoom()
                        .getRoom().getBuilding().setBnum(bnumLabel);
                exams.get(i).getLesson().setType(ltypeLabel);
            }

            return exams;
        } else {
            throw new TeaExamInfNotFoundException("监考信息查询不到！");
        }
    }

    public String findAcaAndDeptByTnum(String tnum){
        String academyType = "ACADEMY";
        String deptType = "DEPT";
        Teacher teacher = teacherDao.findTeacherByTnum(tnum);
        String academyLabel = teacherDao.
                findLabelByValueAndType(teacher.getAcademy(), academyType).getLabel();
        String deptLabel = teacherDao.
                findLabelByValueAndType(teacher.getDept(), deptType).getLabel();
        return academyLabel + deptLabel;
    }

    public List<Exam> getTeaExamInfByLogname(String tnum){
        Exam exam1=new Exam();
        Teacher teacher=new Teacher();
        teacher.setTnum(tnum);
        Teacher teacher1= new Teacher();
        teacher1=teacherDao.getT(teacher);
        exam1.settIds(teacher1.getId().toString());
        List<Exam> exam = new ArrayList<>();
        exam=examDao.getTInf(exam1);
        if(exam != null){
            return exam;
        } else {
            throw new TeaExamInfNotFoundException("考试信息查询不到！");
        }
    }

    public String findSexByType(Teacher teacher){
        String sexType = "SEX";
        String sex = teacherDao.
                findLabelByValueAndType(teacher.getSex(), sexType).getLabel();
        return sex;
    }

    public Teacher findTeacherByTnum(String tnum){
        System.out.println("tnum:" + tnum);
        return teacherDao.findTeacherByTnum(tnum);
    }

    @Override
    public Teacher get(int id) {
        return super.get(id);
    }

    @Override
    public List<Teacher> findAllList() {
        return super.findAllList();
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int insert(Teacher teacher) {
        return super.insert(teacher);
    }

    @Override
    public int update(Teacher teacher) {
        return super.update(teacher);
    }

    public List<Teacher> findTeacherByCondition(String queryText){
        return teacherDao.findTeacherByCondition(queryText);
    }

    public List<Teacher> findListByText(String queryText) {

        List<Teacher> teachers= teacherDao.findListByText(queryText);
        if(teachers.size()>0) {
            List<Dict> dicts1=dictDao.getDictsByCondition("SEX");
            List<Dict> dicts2=dictDao.getDictsByCondition("ACADEMY");
            List<Dict> dicts3=dictDao.getDictsByCondition("DEPT");
            Map<String,String> dictMap1= DictUtil.getMap(dicts1);
            Map<String,String> dictMap2= DictUtil.getMap(dicts2);
            Map<String,String> dictMap3= DictUtil.getMap(dicts3);
            for (Teacher teacher : teachers) {
                teacher.setSex(dictMap1.get(teacher.getSex()));
                teacher.setAcademy(dictMap2.get(teacher.getAcademy()));
                teacher.setDept(dictMap3.get(teacher.getDept()));
            }
        }
        return teachers;
    }

    public void deleteTeachers(Integer[] id) {
        teacherDao.deleteTeachers(id);
    }
}

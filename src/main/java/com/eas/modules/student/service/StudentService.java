package com.eas.modules.student.service;

import com.eas.common.persistence.CrudService;
import com.eas.common.utils.DictUtil;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import com.eas.modules.exam.dao.ExamDao;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.grade.dao.GradeDao;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.student.dao.StudentDao;
import com.eas.modules.student.entity.Student;
import com.eas.modules.student.service.ex.StuExamInfNotFoundException;
import com.eas.modules.teacher.entity.Teacher;
import com.eas.modules.user.entity.User;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author llp
 * @Version 2018/10/22
 */
@Service
public class StudentService extends CrudService<StudentDao,Student> {

    @Autowired
    StudentDao studentDao;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    DictDao dictDao;

    public String findAcaAndDeptByGid(Integer gid){
        String academyType = "ACADEMY";
        String deptType = "DEPT";
        Grade grade = studentDao.findGradeByGid(gid);
        String academyLabel = studentDao.
                findLabelByValueAndType(grade.getAcademy(), academyType).getLabel();
        String deptLabel = studentDao.
                findLabelByValueAndType(grade.getDept(), deptType).getLabel();
        return academyLabel + deptLabel;
    }

    public Grade findGradeByGid(Integer gid){
        Grade grade = studentDao.findGradeByGid(gid);
        return grade;
    }

    public String findSexByType(Student student){
        String sexType = "SEX";
        String sex = studentDao.
                findLabelByValueAndType(student.getSex(), sexType).getLabel();
        return sex;
    }

    public Student findStudentBySnum(String snum){
        return studentDao.findStudentBySnum(snum);
    }

    public List<Exam> findExamsByGid(Integer gid){
        String campusType = "CAMPUS";
        String bnumType = "BNUM";
        String ltype = "LTYPE";
        List<Exam> exams = studentDao.findExamsByGid(gid);
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

            Dict dict = studentDao.
                    findLabelByValueAndType(campusValue, campusType);
            System.out.println("dict:" + dict);
            String campusLabel = dict.getLabel();
            System.out.println("campusLabel:" + campusLabel);
            String bnumLabel = studentDao.
                    findLabelByValueAndType(bnumValue, bnumType).getLabel();
            String ltypeLabel = studentDao.
                    findLabelByValueAndType(ltypeValue, ltype).getLabel();

            exams.get(i).getAvailableRoom()
                    .getRoom().getBuilding().setCampus(campusLabel);
            exams.get(i).getAvailableRoom()
                    .getRoom().getBuilding().setBnum(bnumLabel);
            exams.get(i).getLesson().setType(ltypeLabel);
        }

        return exams;
    }

    /*public Grade getInfo(String snum){
        return studentDao.getInfo(snum);
    }*/

    /**
     * 根据学号查询学生考试信息
     * @param snum 学生学号
     * @return 返回Exam
     */
    @Autowired
    ExamDao examDao;
    public List<Exam> getStuExamInfByLogname(String snum){
        Exam exam1=new Exam();
        Student student=new Student();
        student.setSnum(snum);
        Student student1= new Student();
        student1=studentDao.getT(student);
        exam1.setGids(student1.getGid().toString());
        List<Exam> exam = new ArrayList<>();
        exam=examDao.getSInf(exam1);
        if(exam != null){
            return exam;
        } else {
            throw new StuExamInfNotFoundException("考试信息查询不到！");
        }
    }

    @Override
    public Student get(int id) {
        Student student=studentDao.get(id);
        if(student.getGrade()==null){
            student.setGrade(gradeDao.findGradeById(1));
        }
        else {
            student.setGrade(gradeDao.findGradeById(student.getGrade().getId()));
        }
        return student;
    }

    @Override
    public List<Student> findAllList() {
        return super.findAllList();
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int insert(Student student) {
        return super.insert(student);
    }

    @Override
    public int update(Student student) {
        return super.update(student);
    }

    public List<Student> findStudentByCondition(String queryText){
        return studentDao.findStudentByCondition(queryText);
    }

    public void deleteStudents(Integer[] id) {
        studentDao.deleteStudents(id);
    }

    public List<Student> findListByText(String queryText) {
        List<Student> students= studentDao.findListByText(queryText);
        if(students.size()>0) {
            List<Dict> dicts=dictDao.getDictsByCondition("SEX");
            Map<String,String> dictMap= DictUtil.getMap(dicts);
            for (Student student : students) {
                student.setSex(dictMap.get(student.getSex()));
            }
        }
        return students;
    }
}

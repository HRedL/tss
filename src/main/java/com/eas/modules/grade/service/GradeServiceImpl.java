package com.eas.modules.grade.service;

import java.util.List;
import java.util.Map;

import com.eas.common.utils.DictUtil;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eas.modules.grade.dao.GradeDao;
import com.eas.modules.grade.entity.Grade;

@Service("gradeService")
public class GradeServiceImpl implements IGradeService{
	
	@Autowired
	private GradeDao gradeDao;

	@Autowired
	private DictDao dictDao;
	
	@Override
	public List<Grade> findAllGrade() {
		return gradeDao.findAllGrade();
	}

	@Override
	public Integer delete(Integer id) {
		return gradeDao.delete(id);
	}

	@Override
	public Grade findGradeById(Integer id) {
		Grade grade= gradeDao.findGradeById(id);
		return grade;
	}

	@Override
	public Integer update(Grade grade) {
		return gradeDao.update(grade);
	}

	@Override
	public Integer createGrade(Grade grade) {
		return gradeDao.createGrade(grade);
	}


	public List<Grade> findLessonsByCondition(String queryText){
		return gradeDao.findGradesByCondition(queryText);
	}

    public List<Grade> getAllGrades() {
		//此处调用已有的grade方法
		return gradeDao.findAllGrade();
    }


    public List<Grade> findListByText(String queryText) {
		List<Grade> grades= gradeDao.findListByText(queryText);
		if(grades.size()>0) {
			List<Dict> dicts1=dictDao.getDictsByCondition("ACADEMY");
			List<Dict> dicts2=dictDao.getDictsByCondition("DEPT");
			Map<String,String> dictMap1= DictUtil.getMap(dicts1);
			Map<String,String> dictMap2= DictUtil.getMap(dicts2);
			for (Grade grade : grades) {
				grade.setAcademy(dictMap1.get(grade.getAcademy()));
				grade.setDept(dictMap2.get(grade.getDept()));
			}
		}
		return grades;

    }

	public void deleteGrades(Integer[] ids) {
		gradeDao.deleteGrades(ids);
	}

    public int add(Grade grade) {
		return gradeDao.insert(grade);
    }

    public List<Grade> getGradesByAcademyAndDept(Grade grade) {

		return gradeDao.getGradesByAcademyAndDept(grade);
    }
	public Grade getT(Grade grade){
		return  gradeDao.getT(grade);
	}
}
















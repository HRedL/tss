package com.eas.common.utils;


import com.eas.modules.grade.dao.GradeDao;
import com.eas.modules.grade.entity.Grade;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/4
 */
public class GradeUtils {

    private static GradeDao gradeDao = (GradeDao) SpringTool.getBean("gradeDao");


    /**
     * 分隔gids并且查出grades
     * @param gradeIdsStr
     * @return
     */
    public static List<Grade> getGradesByGids(String gradeIdsStr) {
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


    /**
     * 分隔gids，并且放入List<Grade>
     * @param gradeIdsStr
     * @return
     */
    public static List<Grade> getGradeIdByGids(String gradeIdsStr){

        List<Grade> grades=new ArrayList<>();
        if(!StringUtils.isEmpty(gradeIdsStr)){
            String[] gradeIdArr=gradeIdsStr.split(",");
            for (String gradeId : gradeIdArr) {
                if(!StringUtils.isEmpty(gradeId)){
                    Grade grade= new Grade();
                    grade.setId(Integer.parseInt(gradeId));
                    grades.add(grade);
                }
            }
        }
        return grades;
    }

    public static Map<Integer,Grade> getMapWithKeyId(List<Grade> grades){
        Map<Integer,Grade> map=new HashMap<>();
        for(Grade grade:grades){
            map.put(grade.getId(),grade);
        }
        return map;
    }


}

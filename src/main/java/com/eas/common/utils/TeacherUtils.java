package com.eas.common.utils;

import com.eas.modules.teacher.entity.Teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/5
 */
public class TeacherUtils {

    public static Map<Integer,Teacher> getMapWithKeyId(List<Teacher> teachers){
        Map<Integer,Teacher> map=new HashMap<>();
        for(Teacher teacher:teachers){
            map.put(teacher.getId(),teacher);
        }
        return map;
    }
}

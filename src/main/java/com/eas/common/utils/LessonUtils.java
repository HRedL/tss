package com.eas.common.utils;

import com.eas.modules.lesson.entity.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/4
 */
public class LessonUtils {

    /**
     * 用key为id，lesson为value形成map
     * @param lessons
     * @return
     */
    public static Map<Integer,Lesson> getMapWithKeyId(List<Lesson> lessons){
        Map<Integer,Lesson> map=new HashMap<>();
        for(Lesson lesson:lessons){
            map.put(lesson.getId(),lesson);
        }
        return map;
    }


    public static List<List<Lesson>> getLessonLists(List<Lesson> lessons){

        //按照课程号相同。。。将课程分为各个list
        List<List<Lesson>> lessonsLists=new ArrayList<>();
        List<Lesson> lessonList=new ArrayList<>();
        String lnum="";
        for(Lesson lesson:lessons){
            if(!lesson.getLnum().equals(lnum)){
                lessonList=new ArrayList<>();
                lessonsLists.add(lessonList);
                lessonList.add(lesson);
                lnum=lesson.getLnum();
            }else{
                lessonList.add(lesson);
            }
        }

        return lessonsLists;

    }




}

package com.eas.modules.exam.service;

import com.eas.common.persistence.CrudService;
import com.eas.common.utils.ExamUtils;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.exam.dao.ExamDao;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.lesson.entity.Lesson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TODO
 *
 * @Author: lilinzhen
 * @Version: 2019/6/3
 **/
@Service
public class ExamService_llz extends CrudService<ExamDao, Exam> {

    @Override
    public Exam get(int id) {
        return super.get(id);
    }

    @Override
    public Exam getT(Exam entity) {
        return super.getT(entity);
    }

    @Override
    public List<Exam> findList(Exam entity) {
        return super.findList(entity);
    }

    @Override
    public List<Exam> findAllList() {
        return super.findAllList();
    }

    @Override
    public int insert(Exam entity) {
        return super.insert(entity);
    }

    @Override
    public int update(Exam entity) {
        return super.update(entity);
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int deleteAll(int[] ids) {
        return super.deleteAll(ids);
    }


    Comparator comparator = new Comparator<AvailableRoom>() {
        @Override
        public int compare(AvailableRoom o1, AvailableRoom o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public List<Exam> generateExam() {
        List<Exam> examList = new ArrayList<>();
        List<List<AvailableRoom>> availableRoomLists = ExamUtils.getAvailableRoomLists();
        List<List<Lesson>> lessonLists = ExamUtils.getLessonLists();
        System.out.println("课程组总数"+ lessonLists.size());

        // 取出一组课程号相同的课程，要求同一时间同一教学楼
        for (List<Lesson> lessonList : lessonLists) {
            // 取出一个可用教室集，对这一组lesson进行排考
            for (List<AvailableRoom> availableRoomList : availableRoomLists) {
                // 如果可用教室集为空，跳过，遍历下一个可用教室集
                if (availableRoomList.size() <= 0) {
                    continue;
                }

                // 已排课程数，如果在遍历完一个lessonList时，这个数字大于0而小于lessonList.size()，该组课程排考失败，取下一个availableRoomList
                int lesson_arranged = 0;

                // 取出一个课程
                for (Lesson lesson : lessonList) {
                    // 用来标志availableRoomList遍历结束是true（排考成功break出来）还是false（排考未成功，正常结束遍历）
                    boolean break_flag = false;
                    // 遍历可用教室集，来安排课程
                    for (AvailableRoom availableRoom : availableRoomList) {
                        // 检查可用教室的状态，null或0表示未排、可排，1表示排成功但未全部排完，2表示排考中，3表示课程组全部安排成功
                        // 如果状态非0非null，跳过
                        if (availableRoom.getSortFlag() != null && availableRoom.getSortFlag() != 0) {
                            continue;
                        }
                        // 如果教室容量大于等于考试人数
                        if (availableRoom.getRoom().getCapacity() >= lesson.getLtotalnum()) {
                            // 已排课程数+1，把这个可用教室状态置为1，插入一条考次，结束本次对可用教室的遍历，进行下一课程的安排
                            lesson_arranged ++;
                            System.out.println("one is enough ! sortFlag will change to 1 from "+ availableRoom.getSortFlag());
                            availableRoom.setSortFlag(1);
                            Exam exam = new Exam();
                            exam.setLesson(lesson);
                            exam.setAvailableRoom(availableRoom);
                            examList.add(exam);
                            break_flag = true;
                            break;
                        }
                    }

                    // 检查是什么原因结束了availableRoomList的遍历
                    // 如果是true，说明排考成功break出来了，跳过，遍历下一课程
                    if (break_flag) {
                        continue;
                    }
                    // 如果是false，说明正常结束遍历，排考失败，需要多教室
                    // 寻找最大的教室
                    int biggest_index = -1;
                    for (int i = availableRoomList.size() - 1; i >= 0; i--) {
                        // 检查可用教室的状态
                        if (availableRoomList.get(i).getSortFlag() == null || availableRoomList.get(i).getSortFlag() == 0) {
                            // 如果教室未排，则最大教室找到，退出遍历
                            biggest_index = i;
                            break;
                        }
                    }
                    // 如果最大教室是唯一的未排教室，之前肯定遍历过，不符合多教室的需求，退出遍历
                    if (biggest_index < 1) {
                        break;
                    }
                    // 教室总容量
                    int room_capacity = availableRoomList.get(biggest_index).getRoom().getCapacity();
                    // 将该最大教室置为排考中
                    System.out.println("this is the biggest one ! sortFlag will change to 2 from " + availableRoomList.get(biggest_index).getSortFlag());
                    availableRoomList.get(biggest_index).setSortFlag(2);

                    for (int i = 0; i < biggest_index; i++) {
                        // 此时用最大教室+最小教室
                        // 检查可用教室的状态，如果状态非0非null，跳过
                        if (availableRoomList.get(i).getSortFlag() != null && availableRoomList.get(i).getSortFlag() != 0) {
                            // 如果教室状态不对，且遍历教室到倒数第二个了（倒数第一是最大的教室），说明不符合多教室的需求
                            if (i == biggest_index - 1) {
                                // 释放所有排考中的教室
                                for (AvailableRoom availableRoom : availableRoomList) {
                                    if (availableRoom.getSortFlag() == 2) {
                                        System.out.println("this is the biggest two but has used ! sortFlag will change to 0 from " + availableRoom.getSortFlag());
                                        availableRoom.setSortFlag(0);
                                    }
                                }
                            }
                            continue;
                        }
                        // 如果教室为排考，则置为排考中
                        room_capacity += availableRoomList.get(i).getRoom().getCapacity();
                        System.out.println("this is the small one ! sortFlag will change to 2 from " + availableRoomList.get(i).getSortFlag());
                        availableRoomList.get(i).setSortFlag(2);
                        // 判断总容量与课程人数，如果总容量够，则已排课程数+1，将所有排考中教室置为1，并对应添加多条考次，退出遍历，进行下一课程的安排
                        if (room_capacity >= lesson.getLtotalnum()) {
                            lesson_arranged++;
                            for (AvailableRoom availableRoom : availableRoomList) {
                                if (availableRoom.getSortFlag() == 2) {
                                    System.out.println("these are enough ! sortFlag will change to 1 from " + availableRoom.getSortFlag());
                                    availableRoom.setSortFlag(1);
                                    Exam exam = new Exam();
                                    exam.setLesson(lesson);
                                    exam.setAvailableRoom(availableRoom);
                                    examList.add(exam);
                                }
                            }
                            break;
                        }
                        // 如果没退出遍历，说明本次教室容量还是不够，判断一下该教室是否是倒数第二个，如果是，说明多教室也不够了，释放所有排考中的教室
                        if (i == biggest_index - 1) {
                            for (AvailableRoom availableRoom : availableRoomList) {
                                if (availableRoom.getSortFlag() == 2) {
                                    System.out.println("these arenot enough ! sortFlag will change to 0 from " + availableRoom.getSortFlag());
                                    availableRoom.setSortFlag(0);
                                }
                            }
                        }
                    }
                }
                // 对一组课程遍历完毕，检查已排课程数与课程组数量
                // 如果已排课程数不足，则存在跨时间或跨教学楼的情况
                // 释放所有已排教室，并将生成的考次全部删除
                if (lesson_arranged < lessonList.size()) {
                    for (AvailableRoom availableRoom : availableRoomList) {
                        System.out.println("lesson_arranged < lessonList.size() ! But lesson_arranged is" + lesson_arranged);
                        if (availableRoom.getSortFlag() == 1) {
                            for (int i = 0; i < examList.size(); i++) {
                                Exam exam = examList.get(i);
                                if (exam.getAvailableRoom().getId().equals(availableRoom.getId())) {
                                    examList.remove(exam);
                                    i=0;
                                }
                            }
                            System.out.println("lesson_arranged < lessonList.size() ! sortFlag will change to 0 from "+ availableRoom.getSortFlag());
                            availableRoom.setSortFlag(0);
                        }
                    }
                } else {
                    // 如果已排课程数满足，则将所有已排教室置为3
                    for (AvailableRoom availableRoom : availableRoomList) {
                        if (availableRoom.getSortFlag() == 1) {
                            System.out.println("GOOD ! sortFlag will change to 3 from "+ availableRoom.getSortFlag());
                            availableRoom.setSortFlag(3);
                        }
                    }
                    System.out.println(lessonList.size());
                    break;
                }
            }
        }
        return examList;
    }
}

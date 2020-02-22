package com.eas.modules.sys;

import com.eas.common.json.AjaxJson;
import com.eas.common.utils.ReadLesson1;
import com.eas.common.utils.ReadPublicLesson;
import com.eas.modules.exam.dao.ExamDao;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.exam.service.ExamService;
import com.eas.modules.lesson.dao.LessonDao;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.publicLesson.dao.PublicLessonDao;
import com.eas.modules.publicLesson.entity.PublicLesson;
import com.eas.modules.publicSession.dao.PublicSessionDao;
import com.eas.modules.publicSession.entity.PublicSession;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author huahonglei
 * @Version 2019/3/28
 */

@Controller
@RequestMapping("/uploadFile")
public class FileUploadController {


    @Autowired
    LessonDao lessonDao;

    @Autowired
    ExamService examService;

    @Autowired
    PublicLessonDao publicLessonDao;

    @Autowired
    ExamDao examDao;

    @ResponseBody
    @PostMapping("/importLesson")
    public AjaxJson upload(MultipartFile file, HttpServletRequest request){
        AjaxJson ajaxJson=new AjaxJson();

        try {
            if (file.getSize() > 0) {
                String filename = file.getOriginalFilename();
                if (filename.endsWith("xls") || filename.endsWith("xlsx")) {
                    String path = request.getServletContext().getRealPath("");
                    File file1 = new File(path, filename);
                    file.transferTo(file1);
                    List<Lesson> lessons= ReadLesson1.readLessonExcel(path,filename);
                    lessonDao.bacthInset(lessons);
                }
            }
            ajaxJson.setSuccess(true);
        }catch (IOException e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }

        return ajaxJson;
    }

    @ResponseBody
    @PostMapping("/importPublicLesson")
    public AjaxJson uploadPublicLesson(MultipartFile file, HttpServletRequest request){
        AjaxJson ajaxJson=new AjaxJson();

        try {
            if (file.getSize() > 0) {
                String filename = file.getOriginalFilename();
                if (filename.endsWith("xls") || filename.endsWith("xlsx")) {
                    String path = request.getServletContext().getRealPath("");
                    File file1 = new File(path, filename);
                    file.transferTo(file1);
                    /*publicLessons 从文件中获取的公共课信息*/
                    List<PublicLesson> publicLessons= ReadPublicLesson.readPublicLessonExcel(path,filename);
                    publicLessonDao.batchInsert(publicLessons);
                }
            }
            ajaxJson.setSuccess(true);
        }catch (IOException e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }

        return ajaxJson;
    }



    @PostMapping("/exportExam")
    public void exportExam (String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {


        List<Exam> exams = examService.findAllList();

        String path = request.getServletContext().getRealPath("");

        File file = ReadLesson1.createExcelExam(path, filename, exams);


        response.setHeader("Content-Disposition",

                "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(filename, "UTF-8"));

        InputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }

        in.close();
        out.close();

    }





}

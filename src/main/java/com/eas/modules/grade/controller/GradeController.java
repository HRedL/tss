package com.eas.modules.grade.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.modules.grade.service.GradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.eas.modules.grade.entity.Grade;

@Controller
@RequestMapping("/grade")
public class GradeController {

	@Autowired
	GradeServiceImpl gradeService;


//	@RequestMapping("/get/{id}")
//	public ModelAndView getGrade(@PathVariable("id")Integer id)
//	{
//		Grade grade=gradeService.findGradeById(id);
//		//分开指定视图与模型
//		//ModelAndView modelAndView=new ModelAndView();
//		//指定视图名称
//		//modelAndView.setViewName("/teacher/lookTeacher");
//		//添加模型中的对象
//		//modelAndView.getModel().put("teacher",teacher);
//		//return modelAndView;
//		return new ModelAndView("grade/seeGrade", "grade", grade);
//	}

	@ResponseBody
	@GetMapping("/list")
	public AjaxJson list(Integer pageNumber,Integer pageSize,String queryText){
		if(pageNumber==null){
			pageNumber=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		AjaxJson ajaxJson=new AjaxJson();
		try{
			PageHelper.startPage(pageNumber,pageSize);
			List<Grade> grades=gradeService.findListByText(queryText);

			PageInfo pageInfo=new PageInfo(grades,5);
			ajaxJson.put("pageInfo",pageInfo);
			ajaxJson.setSuccess(true);
			ajaxJson.setMsg("查询成功");
		}catch(Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("查询失败");
		}
		return ajaxJson;
	}


//	@RequestMapping(value = "/list")
//	public String listAllGrades(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber, Map<String,Object> map){
//		PageHelper.startPage(pageNumber,5);
//		List<Grade> grades=gradeService.findAllGrade();
//
//		PageInfo pageInfo=new PageInfo(grades,5);
//		map.put("pageInfo",pageInfo);
//		return "manager/grade/manageGrade";
//	}

//	@RequestMapping(value = "/delete/{id}")
//	public String deleteGrade(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
//		try {
//			gradeService.delete(id);/*delete方法删除数据*/
//			redirectAttributes.addAttribute("info", "删除成功");/*信息为“删除成功”或“删除失败”，在jsp页面上使用${param.info}引用*/
//		} catch (Exception e) {
//			e.printStackTrace();
//			redirectAttributes.addAttribute("info", "删除失败");
//		}
//		return "redirect:/grade/list";/*重定向*/
//	}

	@ResponseBody
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public AjaxJson delete(Integer id){

		AjaxJson ajaxJson=new AjaxJson();
		try{
			gradeService.delete(id);
			ajaxJson.setMsg("删除成功");
			ajaxJson.setSuccess(true);
		}catch (Exception e){
			ajaxJson.setMsg("删除失败");
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}

	@ResponseBody
	@RequestMapping("/deleteGrades")
	public AjaxJson deleteGrades(Integer[] id){
		AjaxJson ajaxJson=new AjaxJson();

		try{
			gradeService.deleteGrades(id);
			ajaxJson.setSuccess(true);
			ajaxJson.setMsg("成功");
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("删除失败");
		}

		return ajaxJson;
	}

//	@RequestMapping("/toAddPage")
//	public String toAddPage(){
//		return "manager/grade/addGrade";
//	}

//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	public String add(Grade grade) {
//		gradeService.createGrade(grade);
//		return "redirect:/grade/list";
//	}

//	@RequestMapping(value = "/toUpdatePage", method = RequestMethod.GET)
//	public String toUpdatePage(Model model, @RequestParam("id")Integer id){
//
//		model.addAttribute("grade",gradeService.findGradeById(id));
//		return "/manager/grade/updateGrade";
//	}

	@ResponseBody
	@GetMapping("/initUpdateGrade")
	public AjaxJson initUpdateGrade(Integer id){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			Grade grade=gradeService.findGradeById(id);
			ajaxJson.getBody().put("grade",grade);
			ajaxJson.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}


	@ResponseBody
	@GetMapping("/getGradesByAcademyAndDept")
	public AjaxJson getGradesByAcademyAndDept(Grade grade){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			List<Grade> grades=gradeService.getGradesByAcademyAndDept(grade);
			ajaxJson.getBody().put("grades",grades);
			ajaxJson.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}




//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String update(Grade grade) {
//		System.out.println(grade);
//		gradeService.update(grade);
//		return "redirect:/grade/list";
//	}

	@ResponseBody
	@PostMapping(value = "/update")
	public AjaxJson update(Grade grade) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			gradeService.update(grade);
			ajaxJson.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("更新班级失败");
		}

		return ajaxJson;
	}

	@ResponseBody
	@PostMapping(value = "/add")
	public AjaxJson add(Grade grade) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			if(gradeService.getT(grade)==null){
				gradeService.add(grade);
				ajaxJson.setSuccess(true);}
			else {
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("该班级已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("更新班级失败");
		}

		return ajaxJson;
	}

//	@RequestMapping("/detail")
//	public String toDetailPage(@RequestParam("id")Integer id,Map<String,Object> map){
//		Grade grade=gradeService.findGradeById(id);
//		map.put("grade",grade);
//		return "/manager/grade/detailGrade";
//	}


//	@RequestMapping("/getGradeByCondition")
//	public String queryLessonByCondition(@RequestParam(value = "queryText")String queryText,Map<String,Object> map){
//		PageHelper.startPage(1,5);
//		List<Grade> grades=gradeService.findLessonsByCondition(queryText);
//
//		PageInfo pageInfo=new PageInfo(grades,5);
//		map.put("pageInfo",pageInfo);
//		return "manager/grade/manageGrade";
//	}


	@ResponseBody
	@GetMapping("/getAllGrades")
	public AjaxJson getAllGrades(){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			List<Grade> grades=gradeService.getAllGrades();
			ajaxJson.getBody().put("grades",grades);
			ajaxJson.setSuccess(true);
		}catch (Exception e){
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("获取班级信息失败");
		}
		return ajaxJson;
	}
}

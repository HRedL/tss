package com.eas.modules.week.controller;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;

import com.eas.modules.week.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import com.eas.modules.week.entity.Week;

@Controller
@RequestMapping("/week")
public class WeekController {
	
	@Autowired
	private WeekService weekService;


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
			List<Week> weeks=weekService.findListByText(queryText);

			PageInfo pageInfo=new PageInfo(weeks,5);
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

	@ResponseBody
	@PostMapping("/delete")
	public AjaxJson delete(Integer id){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			weekService.delete(id);
			ajaxJson.setMsg("删除成功");
			ajaxJson.setSuccess(true);
		}catch (Exception e){
			ajaxJson.setMsg("删除失败");
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;

	}

	@ResponseBody
	@PostMapping(value = "/update")
	public AjaxJson update(Week week) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			weekService.update(week);
			ajaxJson.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("修改教师失败");
		}

		return ajaxJson;
	}

	@ResponseBody
	@PostMapping(value = "/add")
	public AjaxJson add(Week week){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			if(weekService.getT(week)==null){
				weekService.insert(week);
				ajaxJson.setSuccess(true);}
			else {
				ajaxJson.setMsg("该日期已存在");
				ajaxJson.setSuccess(false);
			}
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("添加日期失败");
		}

		return ajaxJson;
	}


	@ResponseBody
	@GetMapping("/initUpdateWeek")
	public AjaxJson initUpdateWeek(Integer id){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			Week week=weekService.get(id);
			ajaxJson.getBody().put("week",week);
			ajaxJson.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}



	@ResponseBody
	@RequestMapping("/deleteWeeks")
	public AjaxJson deleteWeeks(Integer[] id){
		AjaxJson ajaxJson=new AjaxJson();

		try{
			weekService.deleteWeeks(id);
			ajaxJson.setSuccess(true);
			ajaxJson.setMsg("成功");
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("删除失败");
		}

		return ajaxJson;
	}


//	@RequestMapping("/list")
//	public String listAllTeachers(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber, Map<String,Object> map){
//		PageHelper.startPage(pageNumber,5);
//		List<Week> times=weekService.findAllWeek();
//
//		PageInfo pageInfo=new PageInfo(times,5);
//		map.put("pageInfo",pageInfo);
//		return "manager/week/manageWeek";
//	}
//
//	@RequestMapping("toAddPage")
//	public String toAddPage(){
//		return "manager/week/addWeek";
//	}
//
//
//	@RequestMapping(method=RequestMethod.POST,
//			value="/add")
//	public String addWeekInfo(HttpServletRequest request){
//		String term = request.getParameter("term");
//		String date = request.getParameter("date");
//		Week week = new Week();
//		week.setTerm(term);
//		week.setDate(date);
//		weekService.createWeek(week);
//		return "redirect:/week/list";
//	}
//
//	@RequestMapping("/del")
//	public String delete(Integer id,
//			HttpServletRequest request){
//		id = Integer.parseInt(request.getParameter("id"));
//		weekService.delete(id);
//		return "redirect:/week/list";
//	}
//
//	@RequestMapping("/checkWeek")
//	public String showWeekInfoById(HttpServletRequest request,
//			ModelMap modelMap){
//		Integer id = Integer.parseInt(request.getParameter("id"));
//		Week week = weekService.findWeekById(id);
//		modelMap.addAttribute("week",week);
//		return "week/showWeek";
//	}
//
//	@RequestMapping("/updateWeek")
//	public String toUpdateWeekPage(HttpServletRequest request,
//			ModelMap modelMap){
//		Integer id = Integer.parseInt(
//				request.getParameter("id"));
//		modelMap.addAttribute("id",id);
//		return "week/updateWeek";
//	}
//
//	@RequestMapping(method=RequestMethod.POST,
//			value="/revise")
//	public String reviseWeekInfo(HttpServletRequest request){
//		Integer id = Integer.parseInt(
//				request.getParameter("id"));
//		String term = request.getParameter("term");
//		String date = request.getParameter("date");
//		Week week = new Week();
//		week.setId(id);
//		week.setTerm(term);
//		week.setDate(date);
//		weekService.update(week);
//		return "redirect:/week/list";
//	}


	@ResponseBody
	@GetMapping("/getAllWeeks")
	public AjaxJson getAllWeeks(){
		AjaxJson ajaxJson=new AjaxJson();
		try{
			List<Week> weeks=weekService.findAllList();
			ajaxJson.getBody().put("weeks",weeks);
			ajaxJson.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);

		}
		return ajaxJson;
	}
}















/**
 * Copyright &copy; 2015-2020 <a href="#">HuagaoSoft</a> All rights reserved.
 */
package com.eas.common.utils;

import java.util.List;

import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * 字典工具类
 * @author lilinzhen
 * @version 2018年1月6日
 */

public class DictUtils {

	/**
	 * 	在外部工具类中用这种方式声明一个dao
	 * 	因为dao是mvc的一个组件，直接声明会报错private static DictDao dictDao = new DictDao();这种传统方式不可行
	 * 	所以找了一个SpringTool工具类作为dao的注入
	 */
	private static DictDao dictDao = (DictDao) SpringTool.getBean("dictDao");


	public static String getDictId(String value, String type){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equalsIgnoreCase(dict.getType()) && value.equals(dict.getValue())){
					return String.valueOf(dict.getId());
				}
			}
		}
		return "";
	}






	/**
	 * 根据value和type值返回label值，如果匹配不到则返回一个默认值
	 * @param value
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equalsIgnoreCase(dict.getType()) && value.equals(dict.getValue())){
					System.out.println(dict.getLabel());
					return dict.getLabel();
				}
			}
		}
		return defaultLabel;
	}
	
	/**
	 * 根据value和type值返回label值，如果匹配不到则返回空值
	 * @param value
	 * @param type
	 * @return
	 */
	public static String getDictLabel(String value, String type){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equalsIgnoreCase(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return "";
	}

	/**
	 * 根据value和type值返回label值，如果匹配不到则返回空值
	 * @param value
	 * @param type
	 * @return
	 */
	public static String getDictLabel(String value, String parentId, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equalsIgnoreCase(dict.getType()) && value.equals(dict.getValue()) && parentId.equals(String.valueOf(dict.getParentId()))){
					return dict.getLabel();
				}
			}
		}
		return defaultLabel;
	}

	/**
	 * 根据一堆value（拼成的一个字符串）和type值返回一个label的List对象，如果匹配不到则返回一个默认值
	 * @param values
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public static String getDictLabels(String values, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultLabel));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultLabel;
	}

	/**
	 * 根据label和type值返回value值，如果匹配不到则返回一个默认值
	 * @param label
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictValue(String label, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equalsIgnoreCase(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 根据label和type值返回value值，如果匹配不到则返回空值
	 * @param label
	 * @param type
	 * @return
	 */
	public static String getDictValue(String label, String type){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equalsIgnoreCase(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return "";
	}


	/**
	 * 获取dict全部
	 * @param type
	 * @return
	 */
	public static List<Dict> getDictList(String type){
		Dict dict = new Dict();
		dict.setType(type);
		return dictDao.findList(dict);
	}

	/*
	 * 反射根据对象和属性名获取属性值
	 */
/*	public static Object getValue(Object obj, String filed) {
		try {
			Class clazz = obj.getClass();
			String[] temp = filed.split("\\.");
			if(temp.length > 0)
			{
				PropertyDescriptor pd = new PropertyDescriptor(temp[0], clazz);
				Method getMethod = pd.getReadMethod();//获得get方法
	
				if (pd != null) {
					
					Object o = getMethod.invoke(obj);//执行get方法返回一个Object
					if(temp.length > 1 && o != null){
						pd = new PropertyDescriptor(temp[1], o.getClass());
						getMethod = pd.getReadMethod();//获得get方法
			
						if (pd != null) {
							return getMethod.invoke(o);//执行get方法返回一个Object
						}
					}else {
						if(o instanceof java.util.Date ){//日期格式化
							return DateUtils.formatDate((java.util.Date)o,"yyyy-MM-dd HH:mm:ss");
						}
						return o;
					}
				}
			}else{
				PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
				Method getMethod = pd.getReadMethod();//获得get方法
	
				if (pd != null) {
					Object o = getMethod.invoke(obj);//执行get方法返回一个Object
					return o;
	
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
*/
}

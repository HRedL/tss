package com.eas.common.utils;

import com.eas.modules.dict.entity.Dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/2/15
 */
public class DictUtil {

    //把dicts集合转换成Value和Label的Map
     public static Map<String,String> getMap(List<Dict> dicts){
        Map<String,String> map=new HashMap<>();
        for(Dict dict:dicts){
            map.put(dict.getValue(),dict.getLabel());
        }
        return map;
    }


}

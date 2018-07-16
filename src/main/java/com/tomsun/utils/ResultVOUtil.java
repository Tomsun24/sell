package com.tomsun.utils;

import com.tomsun.vo.ResultVO;

/**
 * Created by Administrator on 2018/4/18.
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO vo = new ResultVO();
        vo.setCode(0);
        vo.setMessage("成功");
        vo.setData(object);
        return vo;
    }

    public static ResultVO success(){
        return success(null);
    }

    public  static ResultVO error(Integer code,String meg){
        ResultVO vo = new ResultVO();
        vo.setCode(code);
        vo.setMessage(meg);
        return vo;
    }
}

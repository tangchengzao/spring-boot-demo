package com.tangcz.springboot.common.data.support;

import com.tangcz.springboot.common.data.beans.JsonPojo;

import java.io.Serializable;

/**
 * ClassName:Tuple
 * Package:com.tangcz.springboot.common.data.support
 * Description:
 *
 * @date:2020/6/6 14:35
 * @author:tangchengzao
 */
public class Tuple extends JsonPojo implements Serializable {

    protected Object[]        arr;

    public Tuple(int num) {
        arr = new Object[num];
    }

}

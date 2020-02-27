package com.xinghua.elasticsearchservice.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 处理数据工具类
 * @Author 姚广星
 * @Date 2020/2/27 23:09
 **/
public class DataUtils {

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len  长度
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<T>> result = new ArrayList<List<T>>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}


package com.example.demo.model.book;

import lombok.Data;


@Data
public class Publish {
    private int id;//编号
    private String name;//出版社姓名
    private boolean showPublish;//出版社是否显示
    private Integer grade;//排序值
    private Integer num;//该出版社中有多少书

}

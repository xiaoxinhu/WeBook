package com.example.demo.model.book;

import lombok.Data;

@Data
public class BookSort {
    private int id;
    private String sortName;
    private String upperName;//上一级的标签名
    private String level;
    private int grade;
}

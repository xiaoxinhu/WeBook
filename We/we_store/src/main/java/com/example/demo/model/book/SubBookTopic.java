package com.example.demo.model.book;


import lombok.Data;

@Data
public class SubBookTopic {
    private int topicId;//书单id
    private int bookId;
    private String recommendReason;//推荐理由
}

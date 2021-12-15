package com.example.demo.model.supply;

public interface Subject {
    // 增加
    public int attach(Observer observer);

    // 删除
    public int detach(Observer observer);

    // 通知
    public int notifyObservers();
}

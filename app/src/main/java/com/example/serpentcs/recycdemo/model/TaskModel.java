package com.example.serpentcs.recycdemo.model;

/**
 * Created by serpentcs on 25/11/16.
 */

public class TaskModel {

    private String Name;
    private String Email;
    private String Add;
    private String FriendOrder;
    private String MinOrder;

    public TaskModel() {
    }

    TaskModel(String Name, String Email, String Add, String FriendOrder, String MinOrder) {
        this.Name = Name;
        this.Email = Email;
        this.Add = Name;
        this.FriendOrder = FriendOrder;
        this.Name = Name;

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdd() {
        return Add;
    }

    public void setAdd(String add) {
        Add = add;
    }

    public String getFriendOrder() {
        return FriendOrder;
    }

    public void setFriendOrder(String friendOrder) {
        FriendOrder = friendOrder;
    }

    public String getMinOrder() {
        return MinOrder;
    }

    public void setMinOrder(String minOrder) {
        MinOrder = minOrder;
    }
}

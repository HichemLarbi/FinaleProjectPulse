package com.example.hlarbi.app3.MainClasses.FragOne;
/* In this package : we find setters and getters that are used to display data in FragmentOne GridView
* In fact, each lines from our RunningTable SQLite Database are extract with those getters
* */
public class Running {
    private int id;
    private String name;
    private String numbers;
    public Running(String name,
                   String numbers,
                   int id) {
        this.name = name;
        this.numbers = numbers;

        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumbers() {
        return numbers;
    }
    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

}

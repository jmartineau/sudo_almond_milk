package edu.csumb.ma6317.myapplication.model;

import edu.csumb.ma6317.myapplication.contract.MainActivityContract;

public class MainActivityModel implements MainActivityContract.Model {

    @Override
    public String getData() {return "Hello World!";}
}

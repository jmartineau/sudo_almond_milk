package edu.csumb.ma6317.myapplication.model;

import edu.csumb.ma6317.myapplication.contract.MainActivityContract;

/**
 * Created by sinistro on 10/31/17.
 */

public class MainActivityModel implements MainActivityContract.Model {

    @Override
    public String getData() {return "Hello World!";}
}

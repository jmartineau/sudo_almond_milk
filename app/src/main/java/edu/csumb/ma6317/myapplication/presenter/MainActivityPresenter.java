package edu.csumb.ma6317.myapplication.presenter;

import android.view.View;

import edu.csumb.ma6317.myapplication.contract.MainActivityContract;
import edu.csumb.ma6317.myapplication.model.MainActivityModel;
import edu.csumb.ma6317.myapplication.view.MainActivity;

/**
 * Created by sinistro on 10/31/17.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private MainActivityContract.Model mModel;

    public MainActivityPresenter(MainActivityContract.View view) {
        mView = view;
        initPresenter();
    }

    private void initPresenter() {
        mModel = new MainActivityModel();
        mView.initView();
    }

    public void onClick(android.view.View view) {
        String data = mModel.getData();
        mView.setViewData(data);
    }
}


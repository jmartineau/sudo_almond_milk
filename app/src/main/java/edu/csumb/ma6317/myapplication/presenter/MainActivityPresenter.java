package edu.csumb.ma6317.myapplication.presenter;


import edu.csumb.ma6317.myapplication.contract.MainActivityContract;
import edu.csumb.ma6317.myapplication.model.MainActivityModel;

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


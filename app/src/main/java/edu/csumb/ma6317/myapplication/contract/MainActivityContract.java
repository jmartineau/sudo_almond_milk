package edu.csumb.ma6317.myapplication.contract;

/**
 * Created by sinistro on 10/31/17.
 */

public interface MainActivityContract {

    interface View {

        void initView();

        void setViewData(String data);
    }

    interface Model {

        String getData();
    }

    interface Presenter {

        void onClick(android.view.View view);
    }
}

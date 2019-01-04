package com.zmj.mvp.testsocket.widget;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * @author Zmj
 * @date 2019/1/3
 */
public class JumpDialog {
    private Context context;

    public JumpDialog(Context context) {
        this.context = context;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(context);

}

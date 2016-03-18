package cn.jlb.oknet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import java.lang.ref.WeakReference;


/**
 * 进度观察者的实现，使用 ProgressDialog 展示进度
 * 防止在多线程中被调用，我们使用了 activity.runOnMainThread方法来确保。
 * Created by zhangyunfei on 15/12/25.
 */
public class DialogProgressIndicator implements ProgressIndicator {
    private int delay = 0;
    private ProgressDialog progress;
    private WeakReference<Activity> activityWeakReference;

    public DialogProgressIndicator(Activity context) {
        this.activityWeakReference = new WeakReference<Activity>(context);

    }

//    /**
//     * 指定一个启动前 延迟，该延迟 为了防止 diaglog闪现
//     *
//     * @param activityWeakReference
//     * @param delay
//     */
//    public DialogProgressIndicator(Context activityWeakReference, int delay) {
//        this.activityWeakReference = activityWeakReference;
//        this.delay = delay;
//        handler = new MyHandler();
//    }

    @Override
    public void onProgressStart() {
        if (progress == null)
            createProgress();
        if (progress != null) {
            if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing())
                return;
            activityWeakReference.get().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    getProgresDialog().show();
                }
            });
        }
    }

    private void createProgress() {
        if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing())
            return;
        activityWeakReference.get().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progress = new ProgressDialog(activityWeakReference.get());
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.setOnKeyListener(new DialogInterface.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {

                        if (null != arg2 && arg2.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                            arg0.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                DialogProgressIndicator.this.progress = progress;
            }
        });

    }

    private ProgressDialog getProgresDialog() {
        return progress;
    }

    @Override
    public void onProgressEnd() {
        dismissDialog();
    }

    private void dismissDialog() {

        if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing())
            return;
        activityWeakReference.get().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (progress != null) {
                        progress.dismiss();
                        progress = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        if (progress != null) {
                            progress.dismiss();
                            progress = null;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

}

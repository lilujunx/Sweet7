package library.base;

import android.app.Application;

import xutils.x;


/**
 * Created by Administrator on 2016/10/21.
 */
public abstract class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        initTitleBar();
        initOthers();

    }
    
    public abstract void initOthers();
    
    public abstract void initTitleBar();
    
    public  abstract  boolean isDebugMode();
    
    
    private void initXutils() {
    
        x.Ext.init(this);
        x.Ext.setDebug(isDebugMode());
    }



    
}

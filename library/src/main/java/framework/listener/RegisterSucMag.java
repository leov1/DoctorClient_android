package framework.listener;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/3/31.
 */
public class RegisterSucMag {

    public ArrayList<RegisterSucListener> downloadListeners = new ArrayList<RegisterSucListener>();
    private static RegisterSucMag instance;
    public static RegisterSucMag getInstance(){
        if (instance == null){
            instance = new RegisterSucMag();
        }
        return instance;
    }

    public void addRegisterSucListeners(RegisterSucListener listener) {
        this.downloadListeners.add(listener);
    }

    public void removeRegisterSucListeners(RegisterSucListener listener) {
        this.downloadListeners.remove(listener);
    }
}

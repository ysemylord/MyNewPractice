package com.example.maidlclient;

import android.os.RemoteException;
import android.util.Log;

import com.ryg.sayhi.aidl.ICallBack;

/**
 * binder双向通信
 * 客户端将CallbackService发送给服务端，服务端用次IBinder对象，给客户端发送消息
 */
public class CallbackService extends ICallBack.Stub{
    @Override
    public int changeData(int data) throws RemoteException {
        Log.i("CallbackService","client changeData");
        return data+1;
    }
}

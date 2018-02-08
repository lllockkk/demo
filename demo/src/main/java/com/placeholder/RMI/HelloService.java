package com.placeholder.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by L on 2017/8/21.
 */
public interface HelloService extends Remote {
    String service(String data) throws RemoteException;
}

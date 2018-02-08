package com.placeholder.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by L on 2017/8/21.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    private String name;

    protected HelloServiceImpl(String name) throws RemoteException {
        super();
        this.name = name;
        // todo export
    }

    @Override
    public String service(String data) throws RemoteException {
        return data + name;
    }
}

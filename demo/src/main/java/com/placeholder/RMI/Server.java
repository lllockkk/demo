package com.placeholder.RMI;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by L on 2017/8/21.
 */
public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            HelloService service1 = new HelloServiceImpl("service1");
            Context namingContext = new InitialContext();
            namingContext.rebind("rmi://localhost:1099/HelloService", service1);
        } catch (RemoteException | NamingException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully register a remote object.");
    }
}

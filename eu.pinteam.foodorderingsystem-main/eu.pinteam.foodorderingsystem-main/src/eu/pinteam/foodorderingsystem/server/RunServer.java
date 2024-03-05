package eu.pinteam.foodorderingsystem.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import eu.pinteam.foodorderingsystem.admin.AdminApp;
import eu.pinteam.foodorderingsystem.service.IAdminService;
import eu.pinteam.foodorderingsystem.service.IClientService;

public class RunServer {
	private Registry registry;
	private boolean serverIsOn;

	public Registry getRegistry() {
		return registry;
	}

	public void startServer() throws RemoteException, AlreadyBoundException, NotBoundException {
		IClientService server = new ServerImplClient();
		IAdminService aserver = new ServerImplAdmin();
		registry = LocateRegistry.createRegistry(7777);
		registry.bind("clientServer", server);
		registry.bind("adminServer", aserver);
		System.out.println("Server started");
		serverIsOn = true;

		AdminApp adminApp = new AdminApp();
		System.out.println();
		System.out.println();
		adminApp.run();
		stopServer();
		serverIsOn = false;
	}

	public void stopServer() throws AccessException, RemoteException, NotBoundException {
		if (registry != null) {
			registry.unbind("clientServer");
			registry.unbind("adminServer");
			registry = null;
			System.exit(0);
		}
	}

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException, NotBoundException {
		RunServer instance = new RunServer();
		instance.startServer();
	}
	
	public boolean checkIfServerIsOn() {
		return serverIsOn;
	}

}

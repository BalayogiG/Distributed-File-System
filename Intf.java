package dfs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Intf extends Remote{
	public String uploadFileToServer(byte[] mybyte, String serverpath, int length) throws RemoteException;
	public byte[] downloadFileFromServer(String servername) throws RemoteException;
	public StringBuilder FileListFromFolder(String folder_name) throws Exception;
	public String DeleteFile(String filename) throws Exception;
}

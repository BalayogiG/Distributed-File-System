package dfs;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class Impl extends UnicastRemoteObject implements Intf
{
protected Impl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
private static final String SRC_FOLDER = "C:/DistributedStorage";

Scanner sc=new Scanner(System.in);

public String uploadFileToServer(byte[] mydata, String serverpath, int length) throws RemoteException {
			String result="File uploaded";
    	try {
    		File serverpathfile = new File(serverpath);
    		FileOutputStream out=new FileOutputStream(serverpathfile);
    		byte [] data=mydata;
			
    		out.write(data);
			out.flush();
	    	out.close();
	 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
    	return result;
		
}

public byte[] downloadFileFromServer(String serverpath) throws RemoteException {
					
		byte [] mydata;	
		
			File serverpathfile = new File(serverpath);			
			mydata=new byte[(int) serverpathfile.length()];
			FileInputStream in;
			try {
				in = new FileInputStream(serverpathfile);
				try {
					in.read(mydata, 0, mydata.length);
				} catch (IOException e) {
					
					e.printStackTrace();
				}						
				try {
					in.close();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}		
			return mydata;				 
}

public StringBuilder FileListFromFolder(String folder_name)throws RemoteException{
    File file = new File(SRC_FOLDER);
    StringBuilder names= new StringBuilder();
    int i;
    File[] files = file.listFiles();
    for(i=0;i<1;i++){
        for(File f: files){        
                names.append(f.getName());
                names.append("\n");                
        }        
    }
    return names;
}

public String DeleteFile(String filename) throws RemoteException{
    String filePath = SRC_FOLDER+"/"+filename;
    String result="File deleted...";
    File f = new File(filePath);
    if(f.exists()){
    f.delete(); 
    }
    return result;
}

public String checkHosts(String subnet) throws UnknownHostException, Exception{
	   int timeout=5000;
	   String reachable = null;
	   for (int i=1;i<255;i++){
	       String host=subnet + "." + i;
	       if (InetAddress.getByName(host).isReachable(timeout)){
	          reachable="accessible";
	       }
	   }
	   return reachable;
	}
}


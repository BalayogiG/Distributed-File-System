package dfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

public class Download {

	static int portnumber = 802;
	private static final String SRC_FOLDER = "C:/DistributedStorage";
	public static void main(String[] args) throws IOException {
		Download down=new Download();
		String fname;
		fname = JOptionPane.showInputDialog("Enter File name:");
		down.DownloadFile(fname);
	}
	public void DownloadFile(String fname) {
		String hostname = "127.0.0.1";
		Registry myreg;
		Intf fileserverintf;
		boolean loop=false;
		int c = 0; 
		String serverpath = SRC_FOLDER+ "/" +fname;
		String cpath = "D:/DSDownloads";
		String clientpath= cpath +"/"+ fname;
		do
		{
		if (c==0)
			{
				hostname = "168.0.0.1";
			}
		else if (c==1)
			{					
				hostname = "127.0.0.1";
			}
		try {
			myreg = LocateRegistry.getRegistry(hostname, portnumber);				
			fileserverintf = (Intf)myreg.lookup("Server");
			byte[] mydata = fileserverintf.downloadFileFromServer(serverpath);
			JOptionPane.showMessageDialog(null, "File downloaded" , "Information", JOptionPane.INFORMATION_MESSAGE);
			File clientpathfile = new File(clientpath);
			FileOutputStream out=new FileOutputStream(clientpathfile);				
				out.write(mydata);
				out.flush();
		    	out.close();
			loop = false;
			}catch (Exception e)
		   		{
	   				//restart main method
	    			loop = true;
					c = 1;
		   		}
		}while(loop);
		}
	}
package dfs;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

public class Delete{
static int portnumber = 802;
private static final String SRC_FOLDER = "C:/DistributedStorage";
public static void main(String[] args) throws IOException {
	Delete del=new Delete();
	String fname;
	fname = JOptionPane.showInputDialog("Enter File name:");
	del.DeleteFile(fname);
}

public void DeleteFile(String filename) {
	String hostname = "127.0.0.1";
	Registry myreg;
	Intf fileserverintf;
	boolean loop=false;
	int c = 0;
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
	
	try
	{
		myreg = LocateRegistry.getRegistry(hostname, portnumber);
		fileserverintf = (Intf)myreg.lookup("Server");
		StringBuilder str;
		str = fileserverintf.FileListFromFolder(SRC_FOLDER);
		if(str.toString().contains(filename)){
			//server side deletion
			String output= fileserverintf.DeleteFile(filename);
			JOptionPane.showMessageDialog(null, output , "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
			//client side deletion
			String filePath = SRC_FOLDER+"/"+filename;
			File f = new File(filePath);
			if(f.exists()){
			f.delete(); 
			JOptionPane.showMessageDialog(null, "File deleted" , "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "File not deleted" , "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			}
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

	

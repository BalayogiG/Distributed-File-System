package dfs;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Upload {

	static int portnumber = 802;
	private static final String SRC_FOLDER = "C:/DistributedStorage";
	public static void main(String[] args) {
		Upload up=new Upload();
		up.UploadFile();
	}
	
	public void UploadFile() {
		String hostname = "127.0.0.1";
		String clientpath;
		String serverpath;
		double storage = 10; // Megabytes
		double length = 0;
		double bytes = 0;
		Registry myreg;
		Intf fileserverintf;
		boolean loop=false;
		int c = 0;
		File dir;
		File[] fList; 
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
		  		  dir = new File(SRC_FOLDER);

				fList = dir.listFiles();
				if (fList == null || fList.length == 0)
					 {
					System.out.println(length);
					}
				for (File file : fList)
				{
				if(file.isFile())
					{
					length +=file.length(); 
					}
	
				}
			length = length/1048576;
			
			JFileChooser fileChooser = new JFileChooser(".");
			int status = fileChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION)
				 {
 				 	  File selectedFile = fileChooser.getSelectedFile();
					String path1 = 	selectedFile.getParent();
			  		String path2 = selectedFile.getName();
			   	  	clientpath=  path1 + "/" + path2; 
					File file = new File(clientpath);
					bytes = file.length();
					bytes = bytes/1048576;
				if (storage > length + bytes)
					{
					serverpath = SRC_FOLDER+"/"+path2;
					File clientpathfile = new File(clientpath);
					byte [] mydata=new byte[(int) clientpathfile.length()];
					FileInputStream in=new FileInputStream(clientpathfile);	
					 in.read(mydata, 0, mydata.length);					 
					 fileserverintf.uploadFileToServer(mydata, serverpath, (int) clientpathfile.length());		 
					 in.close();
					 JOptionPane.showMessageDialog(null, "File uploaded" , "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				else
					{
					JOptionPane.showMessageDialog(null, "File size exceeds" , "Information", JOptionPane.INFORMATION_MESSAGE);
					length = 0;
					bytes = 0;
					}
				}
			else if (status == JFileChooser.CANCEL_OPTION)
				 {
				JOptionPane.showMessageDialog(null, "Cancelled" , "Information", JOptionPane.INFORMATION_MESSAGE);
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
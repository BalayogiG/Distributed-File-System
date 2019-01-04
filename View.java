package dfs;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class View {
	static int portnumber = 802;
	private static final String SRC_FOLDER = "C:/DistributedStorage";
	public static void main(String[] args) throws Exception {
		View v=new View();
		v.ViewFiles();		
	}
	
	public StringBuilder ViewFiles() throws Exception {
		String hostname = "127.0.0.1";
		StringBuilder names1 = new StringBuilder();
		StringBuilder result = new StringBuilder();
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
		try {
		myreg = LocateRegistry.getRegistry(hostname, portnumber);				
		fileserverintf = (Intf)myreg.lookup("Server");
		File file1 = new File(SRC_FOLDER);
		StringBuilder filenames=fileserverintf.FileListFromFolder(SRC_FOLDER);
	    File[] files1 = file1.listFiles();
	    for(int i=0;i<1;i++){
	        for(File f: files1){        
	                names1.append(f.getName());
	                names1.append("\n");                
	        }        
	    }
	    result=filenames.append(names1);
	   // JOptionPane.showMessageDialog(null, result , "Information", JOptionPane.INFORMATION_MESSAGE);
	    loop = false;
		}catch (Exception e)
		   {
	   		//restart main method
	    	loop = true;
			c = 1;
			}
		}while(loop);
		return result;
		}
}

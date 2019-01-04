package dfs;

import java.rmi.*;
class DistributedFileServer
{
public static void main(String []args)
{
try
{
Impl fileserverimpl=new Impl();
Naming.rebind("Server",fileserverimpl);
System.out.println("server listening....");
}
catch(Exception e)
{
System.out.println("Exception :" +e);
}
}
}

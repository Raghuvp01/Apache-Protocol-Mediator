/*
* CS 656 / Spring 21 / Apache Mediator / Stub V3.00
* Group: N1 / Leader: Andy (abc123)
* Group Members: Bella (bcd123), Chandni (cv123), Deng (dy123)
*
*   ADC - add your code here
*   NOC - do not change this
*   ??  - you may change these vars/parms etc
*   Your own methods must be after run() ONLY!
*/

import java.net.InetAddress;
// other imports go here
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.System;
import java.io.File;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
/*--------------- end imports --------------*/

class Apache {

  // NOC these 3 fields
  private byte [] HOST;      /* should be initialized to 1024 bytes in the constructor */
  private int PORT;      /* port this Apache should listen on, from cmdline        */
  private InetAddress PREFERRED ; /* must set this in dns() */
  // ADC additional fields here
  private File file;      /* name of the file in URL, if you like */
  private int offset,offset1,length,flag;
  private byte[] header;

public static void main(String [] a){ // NOC - do not change main()
  Apache apache = new Apache(Integer.parseInt(a[0]));

  apache.run(2);
}
Apache(int port) {
  PORT = port;
  HOST = new byte[1024];
  PREFERRED = null;
  file = null;
  // other init stuff ADC here
  header = new byte[65535];
}

// Note: parse() must set HOST correctly
int parse(byte [] buffer) throws Exception
{ 
 if((buffer[0]=='G') && (buffer[1]=='E') && (buffer[2]=='T') && (buffer[3]==' '))
 {
 	if((buffer[4]=='h') && (buffer[5]=='t') && (buffer[6]=='t') && (buffer[7]=='p'))
	{ if((buffer[8]==':') && (buffer[9]=='/') && (buffer[10]=='/'))
	  {  	int i=11;
		while(buffer[i++]!=' '){ }
	        if((buffer[i]=='H') && (buffer[i+1]=='T') && (buffer[i+2]=='T') && (buffer[i+3]=='P'))
		{ if((buffer[i+4]=='/') && (buffer[i+5]=='1') && (buffer[i+6]=='.') && ((buffer[i+7]=='1')||(buffer[i+7]=='0')))
		  {if(buffer[i+8]==13 && buffer[i+9]==10)
			{if(buffer[i+10]=='H' && buffer[i+11]=='o' && buffer[i+12]=='s' && buffer[i+13]=='t' && buffer[i+14]==':')
				{
					int j=i+16;
					offset=j;
					while(buffer[j]!=13 && buffer[j+1]!=10)
						j++;
					length=j-1;
					dns(1);
				}}}}}}
 }
 
	
 return 0; }

// Note: dns() must set PREFERRED
int dns(int X)  // NOC - do not change this signature; X is whatever you want
{
     long startClock,stopClock;
     try {
            InetAddress[] ss = InetAddress.getAllByName(byte2str(header,offset,length));
	    long list[]= new long[ss.length];
            for (int i=0;i< ss.length;i++)
            {
                startClock = System.currentTimeMillis();
		if(Ping(ss[i].getHostAddress(),1000))
                { stopClock = System.currentTimeMillis();
                  list[i]=stopClock-startClock;
              	}
                else
                {list[i]=999;}
            }
            PREFERRED = ss[getminim(list)];
	    HOST = PREFERRED.getAddress();
	    flag=0;
	  }
        catch (UnknownHostException e){
           // System.out.println("Unknown Host");
	   flag=1;
        } 

return 0; 
}

int http_fetch(Socket client) // NOC - don't change signature
{
  Socket p; // peer, connection to HOST

  // get file from peer (HOST) and send back to c
  //
  // return bytes transferred
  return 0;
}

int  ftp_fetch(Socket client) // NOC - don't change signature
{
  Socket p; // peer, connection to HOST

  // do FTP transaction with peer, get file, send back to c
  // Note: do not 'store' the file locally; it must be sent
  // back as it arrives
  //
  // return bytes transferred
  return 1;
}

int  echo_req(Socket client) // NOC - don't change signature
{
try{	OutputStream out = client.getOutputStream();
   // used in Part 1 only; echo the HTTP req with added info
   // from PREFERRED
        if(flag<1){
	System.out.println("REQ: " + byte2str(header,offset,length) + "/ RESP: " + 
	PREFERRED.getHostAddress());}
	else{
	System.out.println("REQ: " + byte2str(header,offset,length) + "/ RESP: ERROR ");}
	
	out.write("HTTP/1.1 200 OK\r\n".getBytes());
	out.write("Content-Type: text/html; charset=iso-8859-1\r\n\r\n".getBytes());
	out.write("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\r\n".getBytes());
        out.write("<html>\r\n<head>\r\n</head>\r\n<body>\r\n".getBytes());
    	out.write(header,0,offset1-4);
	if(flag<1){
    	out.write("\nDNS LOOKUP: ".getBytes());
	out.write(PREFERRED.getHostName().getBytes());
	out.write("\r\nPreferred IP: ".getBytes());
//	out.write(HOST,0,HOST.length);
	out.write(PREFERRED.getHostAddress().getBytes());}
	else
	{
    	out.write("\nDNS LOOKUP: ".getBytes());
	out.write(byte2str(header,offset,length).getBytes());
	out.write("\r\nPreferred IP: ERROR ".getBytes());
	}
	out.write("\r\n</body>\r\n</html>\r\n\r\n".getBytes());
	out.flush();
    	out.close();
   	}
	catch(IOException e){}

return 0;
}
 
int run(int X)  // NOC - do not change the signature for run()
{
  ServerSocket s0 = null; // NOC - this is the listening socket
  Socket s1 = null; // NOC - this is the accept-ed socket i.e. client
  byte[] b0 = new byte[1024];  // ADC - general purpose buffer
 
  InputStream in = null;
  InetAddress local = null;
  
  int reqcount = 1;

  try
  {
    s0 = new ServerSocket();
    s0.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),PORT),X);
    System.out.println("Apache Listening on socket " + PORT + "\n");
  }
  catch(Exception e)
  {	
    e.printStackTrace();	
  }
  // ADC here
Term:
  while ( true ) {        // NOC - main loop, do not change this!
    // ADC from here to LOOPEND : add or change code
try
{
    s1 = s0.accept();
    System.out.print("(" + reqcount++ + ") Incoming client connection from ["); 
    printIP(s1.getInetAddress().getAddress());  System.out.print(s1.getPort() + "] to me " + "["); 
    printIP(s0.getInetAddress().getAddress());  System.out.println(s0.getLocalPort() + "]");
    int rbytes=0;  
    offset1=0;
    in = s1.getInputStream();
     Term1:
    do
    { 		
       	rbytes = in.read(header,offset1,65535-offset1);
	offset1 = offset1 + rbytes;
	if((offset1==65535)||(rbytes==-1)||(rbytes<4))
		break Term1;
    }while(!((header[offset1-4]==13)&&(header[offset1-3]==10)&&(header[offset1-2]==13)&&(header[offset1-1]==10)));
    if(rbytes>0)
    {
      	parse(header);
	echo_req(s1);
	in.close();
	s1.close();
    }
}
catch(Exception e)
{
  e.printStackTrace();
  break Term;
}


   // s1.InputStream??.read(b0 ) ?? ; // example: req is "GET http://site.com/dir1/dir2/file2.html"

   // parse(b0 ?? or some other buffer ?? ); // set HOST as 's' 'i' 't' 'e' '.' 'c' 'o' 'm'

   // dns(0);  // sets PREFERRED

   // echo_req( s1 );  // used in Part 1 only

    /* Part 2 - hints
    is it http_fetch or ftp_fetch ??
    nbytes = http_fetch(s1) or ftp_fetch(s1);
    LOG "REQ http://site.com/dir1/dir2/file2.html transferred nbytes"
    */

    // LOOPEND
   // NOC - main loop
}return 1;
}

/* ------------- your own methods below this line ONLY ----- */
String byte2str(byte []b, int i, int j)
{

 String s = "";
 for(int k=i;k<=j;k++)
 {
  	char c=(char)b[k];
  	s=s+c;
  
 }
 return s;
}
int getminim(long[] arr){
        long minVal = arr[0];
        int minValind = 0;
        for(int i = 1;i < arr.length;i++){
            if(arr[i] < minVal )
            {
                minVal = arr[i];
                minValind = i;
            }

        }

        return minValind;
    }

void printIP(byte[] ip)
{
	byte[] special = {46,58};
	for(int i=0;i<ip.length;i++)
	{
		if(ip[i]<0)
			System.out.print((ip[i]+256));
		else
			System.out.print(ip[i]);
		if(i<ip.length-1)
			System.out.print(byte2str(special,0,0));
	}
	System.out.print(byte2str(special,1,1));
}

boolean Ping(String host,int timeout)
{try{	Socket temp = new Socket();
	temp.connect(new InetSocketAddress(host,80),timeout);
	temp.close();
	return true;
}catch(IOException e)
{return false;}

}
} // class Apache

package testThread;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.*;


public class CountDownLatchExample {
	static String str1 = "";
	static String str2 = "";
	static String str3 = "";
	
	static byte[] buff1 = null;//new byte[16];
	static byte[] buff2 = null;//new byte[16];
	static byte[] buff3 = null;//new byte[16];

public static class ProcessThread implements Runnable {

  CountDownLatch latch;
  long workDuration;
  int name;

  public ProcessThread(int name, CountDownLatch latch, long duration){
      this.name= name;
      this.latch = latch;
      this.workDuration = duration;
  }


  public void run() {
      try {	  
    	  
    	  if(name==1) {
    		  str1 = str1 + name+""+workDuration;
    	  }else if(name==2) {
    		  str2 = str2 + name+""+workDuration;
    	  }else {
    		  str3 = str3 + name+""+workDuration;
    	  }
    	  
          System.out.println(name +" Processing Something for "+ workDuration/1000 + " Seconds");
          Thread.sleep(workDuration);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
      
      
      
      System.out.println(name+ "completed its works");
      //when task finished.. count down the latch count...

      // basically this is same as calling lock object notify(), and object here is latch
      latch.countDown();
  }
}
	

public static void main(String[] args) {
	
	long l1 = System.currentTimeMillis();
	
	FileInputStream fin = null;
	FileOutputStream fou = null;
	File flin = null;
	File flou = null;
	
	int thread = 4;
	
	int ln =0;
	byte[] lbuff = new byte[16*thread];
	
	int[] Atmp = null;//new byte[0];
	
	byte[][] b = new byte[thread][0];
	
	
	
	
	
	try {
		flin = new File("datain.txt");
		flou = new File("dataout.txt");
		fin = new FileInputStream(flin);
		fou = new FileOutputStream(flou);
		
		while((ln=fin.read(lbuff))!=-1) {
			
			Atmp = splitNum(ln,16);
			
			b[0]=Arrays.copyOfRange(lbuff,0, Atmp[0]);
			for(int i=0;i<Atmp.length-1;i++) {
				b[i+1]=Arrays.copyOfRange(lbuff, Atmp[i], Atmp[i+1]);
				//System.out.println(Arrays.toString( b[i]));
			}
			
			for(int i=0;i<Atmp.length;i++) {
				fou.write(b[i],0,b[i].length);
			}
						
		}
		
		
		
	}catch(Exception e) {e.printStackTrace();}
	
	long l2 = System.currentTimeMillis();
	
	System.out.println((l2-l1));
	
	
  // Parent thread creating a latch object	
	
	long start = System.currentTimeMillis();
	
	/*CountDownLatch latch = new CountDownLatch(3);

			  new Thread(new ProcessThread(1,latch, 100)).start(); // time in millis.. 2 secs
			  new Thread(new ProcessThread(2,latch, 300)).start();//6 secs
			  new Thread(new ProcessThread(3,latch, 200)).start();//4 secs


			  System.out.println("waiting for Children processes to complete....");
			  try {
			      //current thread will get notified if all chidren's are done 
			      // and thread will resume from wait() mode.
			      latch.await();
			  } catch (InterruptedException e) {
			      e.printStackTrace();
			  }

			  System.out.println("All Process Completed....");

			  System.out.println("Parent Thread Resuming work....");*/
			  long end = System.currentTimeMillis();
			  //System.out.println("strAll : "+str1+" "+str2+" "+str3+"\n"+(end-start));//+++++++++++++++++++++++++++++++++++++++++++

			
		
	int[] n = null;
	for (int i=0;i<=64;i++) {
		n = splitNum(i,16);
		//System.out.println(i+" "+Arrays.toString(n));
	}
	//System.out.println(Arrays.toString(splitNum(64,16,4)));
	
  


   }

public static int[] splitNum(int num,int nBlock) {
	int nSizeArr = (int)( ((double)num-0.1)/nBlock )+1;
	int[] na = new int[nSizeArr];
	
	int nMod = num%nBlock;
	int nNew = num - nMod;
	int nQtnt = nNew/nBlock;
	
	
	na[nQtnt%nSizeArr] = nMod;
	
	int nsum=0;
	
	for(int i=0;i<nQtnt;i++) {
		nsum = nsum+nBlock;
		na[i] = nsum;
	}
	na[nSizeArr-1]=num;
	return na;
}


}
package testThread;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class TestThread {
	 
    private long c1 = 0;
    private long c2 = 0;
    int nt1 = 0;
    int nt2 = 0;
    static String nsum = "";
 
    private Object lock1 = new Object();
    private Object lock2 = new Object();
 
    private void inc1(String str) {
        synchronized (lock1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //---------------------------------------
            if(c1>=1000) {
            	Thread.currentThread().destroy();
            }
            //------------------------------------------
            
            c1++;
          //  System.out.println(str+"   c1 : "+c1);//*********
            nsum = nsum+""+c1+" ";System.out.println(str+"   c1 : "+c1+"   c2   : "+c2);//*********
            
            
            
            
        }
    }
 
    private void inc2(String str) {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // c2++;
            //nsum = nsum+""+c1+" ";
           
        }
    }
 
    private void process(String str) {
        for (int i = 0; i < 1000; i++) {
            inc1(str);
            //inc2(str);
            
        }
    }
 
    public void doWork() {
        System.out.println("Starting ...");
 
        long start = System.currentTimeMillis();
 
        Thread t1 = new Thread(new Runnable() {
 
            @Override
            public void run() {
                process("t1");
            }
 
        });
 
        t1.start();
 
        Thread t2 = new Thread(new Runnable() {
 
            @Override
            public void run() {
                process("t2");
            }
 
        });
 
        t2.start();
        
        //*****************
        Thread t3 = new Thread(new Runnable() {
        	 
            @Override
            public void run() {
                process("t3");
            }
 
        });
 
        t3.start();
        
        Thread t4 = new Thread(new Runnable() {
       	 
            @Override
            public void run() {
                process("t4");
            }
 
        });
 
        t4.start();
        //---------------
 
        try {
            t1.join();
            t2.join();
            t3.join();//--------------
            t4.join();//--------------
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        long end = System.currentTimeMillis();
 
        System.out.println("Time take: " + (end - start));
        System.out.println("c1: " + c1 + "; c2: " + c2);
        System.out.println("t1: " + nt1 + "; t2: " + nt2);
        //System.out.println(nsum);
    }
 
    public static void main(String[] args) {
    	TestThread app = new TestThread();
        app.doWork();
        
        /*try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream("filename.txt"), "utf-8"));
			
			try {
				writer.write(nsum);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        //+++++++++++++++++++++++++++++++++++++++
        try {
			FileOutputStream fou = new FileOutputStream("data.txt");
			fou.write(nsum.getBytes());
			fou.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
        
    }
 
}

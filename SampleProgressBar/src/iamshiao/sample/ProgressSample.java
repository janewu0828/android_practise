package iamshiao.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressSample extends Activity {
	
	private final int step1 = 1, step2 = 2, step3 = 3, finish = 4;
	private Thread thread = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui);
        
        //建構執行緒
        thread = new Thread(){ 
        	@Override
        	public void run(){ 
        		try{
        			doStep1();
        			doStep2();
        			doStep3();
        			Thread.sleep(3000);
        			Message msg = new Message();
        	        msg.what = finish;
        	        uiMessageHandler.sendMessage(msg);
        		}catch (Exception e){
        			e.printStackTrace();
        		}finally{
                }
        	}
        };
        //開始執行執行緒
        thread.start();
        
    }
    
    private void doStep1() throws InterruptedException{
    	Thread.sleep(3000);
		Message msg = new Message();
        msg.what = step1;
        uiMessageHandler.sendMessage(msg);
    }
    
    private void doStep2() throws InterruptedException{
    	Thread.sleep(3000);
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putString("data", "data");
		msg.setData(data);
        msg.what = step2;
        uiMessageHandler.sendMessage(msg);
    }
    
    private void doStep3() throws InterruptedException{
    	Thread.sleep(3000);
		Message msg = new Message();
        msg.what = step3;
        uiMessageHandler.sendMessage(msg);
    }
    
    //宣告Handler並同時建構隱含類別實體
    Handler uiMessageHandler = new Handler(){
    	@Override
		public void handleMessage(Message msg){
    		//讀出ui.xml中的進度光棒
    		final ProgressBar pBar = 
    			(ProgressBar)findViewById(R.id.ProgressDialogSample_ProgressBar_pBar);
			pBar.setVisibility(View.VISIBLE); //開始後設為可見
			//讀出ui.xml中用以表示未開始的圖案
			final ImageView pImg = 
				(ImageView)findViewById(R.id.ProgressDialogSample_ImageView_pImg);
			pImg.setVisibility(View.INVISIBLE); //開始後設為不可見
			//讀出ui.xml中的描述用TextView
			TextView tv = 
				(TextView)findViewById(R.id.ProgressDialogSample_TextView_desc);
			
			switch (msg.what){
				case step1:
					tv.setText(R.string.processing_step1);
					break;
				case step2:
					tv.setText(msg.getData().getString("data"));
					break;
				case step3:
					tv.setText(R.string.processing_step3);
					break;
				case finish:
					tv.setText(R.string.finish);
					pBar.setVisibility(View.INVISIBLE);
    		        pImg.setVisibility(View.VISIBLE);
	        		tv.setText("完成。");
	        		thread.interrupt();
					break;
			}
			
    		super.handleMessage(msg);
    	}
    };
}
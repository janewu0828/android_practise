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
        
        //�غc�����
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
        //�}�l��������
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
    
    //�ŧiHandler�æP�ɫغc���t���O����
    Handler uiMessageHandler = new Handler(){
    	@Override
		public void handleMessage(Message msg){
    		//Ū�Xui.xml�����i�ץ���
    		final ProgressBar pBar = 
    			(ProgressBar)findViewById(R.id.ProgressDialogSample_ProgressBar_pBar);
			pBar.setVisibility(View.VISIBLE); //�}�l��]���i��
			//Ū�Xui.xml���ΥH��ܥ��}�l���Ϯ�
			final ImageView pImg = 
				(ImageView)findViewById(R.id.ProgressDialogSample_ImageView_pImg);
			pImg.setVisibility(View.INVISIBLE); //�}�l��]�����i��
			//Ū�Xui.xml�����y�z��TextView
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
	        		tv.setText("�����C");
	        		thread.interrupt();
					break;
			}
			
    		super.handleMessage(msg);
    	}
    };
}
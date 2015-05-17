package com.androidhive.pushnotifications;

import android.content.Context;
import android.os.CountDownTimer;

public class TimerManager {
	CountDownTimer gameTime;
	public static CountDownTimer taskTime;
	CountDownTimer hunterTime;

	/** control game timer **/
	public  void gameTimer(final Context context, long timesup) {
		// TODO Auto-generated method stub
		gameTime = new CountDownTimer(timesup, 1000) {
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				/** if statusGame is false, gameOver **/
				PolylinesPolygonsActivity.statusGame=false;
				PolylinesPolygonsActivity.lblTimer.setText(context.getString(R.string.txt_timesup));		
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				PolylinesPolygonsActivity.lblTimer.setText("" + millisUntilFinished / 1000 / 60 + ":"
							+ millisUntilFinished / 1000 % 60);
			}
		}.start();
	}

	/** control isMyLoc()'s timer, if (arrived isMyLoc()) stop taskTimer **/
	public void taskTimer(final Context context, long timesup) {
		// TODO Auto-generated method stub
		/**statusTask is true, start timer**/
		if (PolylinesPolygonsActivity.statusTask) {
			taskTime = new CountDownTimer(timesup, 1000) {
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					if (PolylinesPolygonsActivity.player.equals("0")) {
						/** if arrivednewMessage is false, Survivor is not completed task**/
						if (!PolylinesPolygonsActivity.arrivednewMessage) {
							/** if statusGame is false, gameOver **/
							PolylinesPolygonsActivity.statusGame = false;
							PolylinesPolygonsActivity.taskTimer.setText(context.getString(R.string.txt_timesupIsMyLoc));
							/** Pic msg **/
							AlertDialogManager.ShowPicDialog(context,
									context.getString(R.string.txt_timesupIsMyLoc),"",PolylinesPolygonsActivity.addViewPicError);
						} else {
							/** if arrivednewMessage is true, Survivor is completed task**/
							PolylinesPolygonsActivity.taskTimer.setText("");
							/** if stausGame is true, gameStart, continue display newMessage **/
							PolylinesPolygonsActivity.statusGame = true;
						}
					} else {
						/** Hunter: display times up**/
						PolylinesPolygonsActivity.taskTimer.setText(context.getString(R.string.txt_timesup2));
					}
					/**statusTask is false, clear old task**/
					PolylinesPolygonsActivity.statusTask = false;
					/**arrivednewMessage is true, clear old arrivednewMessage**/
					PolylinesPolygonsActivity.arrivednewMessage=true;
				}

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					if (!PolylinesPolygonsActivity.arrivednewMessage) {
						PolylinesPolygonsActivity.taskTimer.setText(""
								+ millisUntilFinished / 1000 / 60 + ":"
								+ millisUntilFinished / 1000 % 60);
					} else {
						PolylinesPolygonsActivity.taskTimer.setText("");
					}
				}
			}.start();
		}
	}

	/** control hunter timer **/
	public void hunterTimer(final Context context, long timesup) {
		// TODO Auto-generated method stub
		hunterTime = new CountDownTimer(timesup, 1000) {
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				/** show warning msg of Hunter **/
				/** Pic msg **/
				AlertDialogManager.ShowPicDialog(context,
						context.getString(R.string.txt_timesupHunter),"",PolylinesPolygonsActivity.addViewPicHunter);
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}
		}.start();
	}
}

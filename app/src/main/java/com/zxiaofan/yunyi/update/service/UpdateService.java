package com.zxiaofan.yunyi.update.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.update.Config;

import util.Constants;

public class UpdateService extends Service {
	private NotificationManager nm;
	private Notification notification;
	private File tempFile = null;
	private boolean cancelUpdate = false;
	private MyHandler myHandler;
	private int download_precent = 0;
	private RemoteViews views;
	private int notificationId = 1234;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(Constants.TAG,"update  service");
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.mipmap.desk;
		notification.tickerText = Config.APP_NAME + "更新";
		notification.when = System.currentTimeMillis();
		notification.defaults = Notification.DEFAULT_LIGHTS;
		views = new RemoteViews(getPackageName(),
				R.layout.alert_dialog_progress);
		notification.contentView = views;
		Intent upIntent = new Intent(UpdateService.this, UpdateService.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				upIntent, 0);
		notification.contentIntent = contentIntent;

		nm.notify(notificationId, notification);

		myHandler = new MyHandler(Looper.myLooper(), this);

		Message message = myHandler.obtainMessage(3, 0);
		myHandler.sendMessage(message);

		// 下载
		downFile(Config.UPDATE_SERVER
				+Config.SERVER_APP_NAME);
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		nm.cancel(notificationId);
		//停止掉当前的服务
		stopSelf();
	}

	private long totalLength;

	//下载更新文件
	private void downFile(final String url) {
		new Thread() {
			public void run() {
				try {
					HttpClient client = new DefaultHttpClient();
				    // params[0]代表连接的url     
					HttpGet get = new HttpGet(url);
					HttpResponse response = client.execute(get);
					HttpEntity entity = response.getEntity();
					totalLength = entity.getContentLength();
					InputStream is = entity.getContent();
					if (is != null) {
						File rootFile = new File(
								Environment.getExternalStorageDirectory(), "/"
										+ Config.SD_FILE_NAME);
						if (!rootFile.exists() && !rootFile.isDirectory())
							rootFile.mkdir();

						tempFile = new File(
								Environment.getExternalStorageDirectory(),
								"/"
										+ Config.SD_FILE_NAME
										+ "/"
										+ Config.SERVER_APP_NAME);
						if (tempFile.exists())
							tempFile.delete();
						tempFile.createNewFile();

						  //已读出流作为参数创建一个带有缓冲的输出流
						BufferedInputStream bis = new BufferedInputStream(is);

						 //创建一个新的写入流，讲读取到的图像数据写入到文件中
						FileOutputStream fos = new FileOutputStream(tempFile);
						 //已写入流作为参数创建一个带有缓冲的写入流
						BufferedOutputStream bos = new BufferedOutputStream(fos);

						int read;
						long count = 0;
						int precent = 0;
						byte[] buffer = new byte[1024];
						while ((read = bis.read(buffer)) != -1 && !cancelUpdate) {
							bos.write(buffer, 0, read);
							count += read;
							precent = (int) (((double) count / totalLength) * 100);

							//每下载完成5%就通知任务栏进行修改下载进度
							if (precent - download_precent >= 5) {
								download_precent = precent;
								Message message = myHandler.obtainMessage(3,
										precent);
								myHandler.sendMessage(message);
							}
						}
						bos.flush();
						bos.close();
						fos.flush();
						fos.close();
						is.close();
						bis.close();
					}

					if (!cancelUpdate) {
						Message message = myHandler.obtainMessage(2, tempFile);
						myHandler.sendMessage(message);
					} else {
						tempFile.delete();
					}
				} catch (ClientProtocolException e) {
					Message message = myHandler.obtainMessage(4, "下载更新文件失败");
					myHandler.sendMessage(message);
				} catch (IOException e) {
					Message message = myHandler.obtainMessage(4, "下载更新文件失败");
					myHandler.sendMessage(message);
				} catch (Exception e) {
					Message message = myHandler.obtainMessage(4, "下载更新文件失败");
					myHandler.sendMessage(message);
				}
			}
		}.start();
	}

	private void Instanll(File file, Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");

		context.startActivity(intent);
	}

	//安装下载后的apk文件
	class MyHandler extends Handler {
		private Context context;

		public MyHandler(Looper looper, Context c) {
			super(looper);
			this.context = c;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg != null) {
				switch (msg.what) {
				case 0:
					Toast.makeText(context, msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					break;
				case 2:

					//下载完成后清除所有下载信息，执行安装提示
					download_precent = 0;
					nm.cancel(notificationId);
					Instanll((File) msg.obj, context);

					  //停止掉当前的服务
					stopSelf();
					break;
				case 3:

					 //更新状态栏上的下载进度信息
					views.setTextViewText(R.id.progress_percent, "已下载"
							+ download_precent + "%");
					views.setTextViewText(R.id.progress_number, totalLength
							* download_precent / 100 / 1024 / 1024 + "/"
							+ totalLength / 1024 / 1024 + "M");
					views.setProgressBar(R.id.progress, 100, download_precent,
							false);
					notification.contentView = views;
					nm.notify(notificationId, notification);
					break;
				case 4:
					nm.cancel(notificationId);
					break;
				}
			}
		}
	}

}

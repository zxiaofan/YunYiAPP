package com.zxiaofan.yunyi.update;

import java.io.File;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

/**
* Describe:     下载安装工具类
* User:         xiaofan
* Date:         2016/4/12 14:30
*/
public class UpdateVersion {

	/**
	* Describe:     判断是否已下载，若已下载，侧检测是否最新版本
	* User:         xiaofan
	* Date:         2016/4/12 14:32
	*/
	public static boolean getUninstallAPKInfo(Context context,
			int newVerCode) {
		String archiveFilePath = Environment.getExternalStorageDirectory()
				+ "/" + Config.SD_FILE_NAME + "/" + Config.SERVER_APP_NAME;

		int verCode = 0;
		PackageManager pm = context.getPackageManager();
		PackageInfo pakinfo = pm.getPackageArchiveInfo(archiveFilePath,
				PackageManager.GET_ACTIVITIES);
		if (pakinfo != null) {
			verCode = pakinfo.versionCode;
		}
		if (newVerCode == verCode) {
			return true;
		}
		return false;
	}

	/**
	* Describe:    	已经下载过安装包的安装提示框
	* User:         xiaofan
	* Date:         2016/4/12 14:34
	*/
	public static void showInstallDialog(final Context currentContext) {
		
		final AlertDialog.Builder nickbuilder = new AlertDialog.Builder(currentContext);
		nickbuilder.setMessage("已下载过最新版本，是否立即安装？");
		nickbuilder.setTitle("软件更新");
		nickbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		nickbuilder.setPositiveButton("安装", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/"+Config.SD_FILE_NAME+"/"+Config.SERVER_APP_NAME)),
						"application/vnd.android.package-archive");
				Bundle bundle = new Bundle();
			    intent.putExtras(bundle);
			    currentContext.startActivity(intent);
			    dialog.dismiss();
			}
		});
		nickbuilder.create().show();
	}
}

package com.example.openfireapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

public class FileToStringUtils {
	
	private static FileToStringUtils fileToStringUtils = new FileToStringUtils();
	
	private FileToStringUtils() {
		
	}

	public static FileToStringUtils getInstance(){
		return fileToStringUtils;
	}
	
	/*public static String addImageTag(byte[] data) {
		// byte[]转成字符串
		// 字符编码Base64是iphone,android都支持
		String string = Base64.encodeToString(data, Base64.DEFAULT);
		return string;
	}*/
	
	/*public ByteArrayInputStream addImageTag(byte[] data) {
		return new ByteArrayInputStream(data);
	}*/
	
	public String imageBitmapToString(Bitmap bitmap){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 60,outputStream);
		String body = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
		return body;
	}
	
	public Bitmap imageStringToBitmap(String string){
		Bitmap bitmap = null;  
        try {  
            byte[] bitmapArray;  
            bitmapArray = Base64.decode(string, Base64.DEFAULT);  
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);  
            return bitmap;  
        }  
        catch (Exception e) {  
        	LogUtils.log("imageStringToBitmap", e.getMessage());
            return null;  
        }  
	}
	
	public String getSoundString(Bitmap bitmap){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 60,outputStream);
		String body = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
		return body;
	}
	
	/*public void putToServer(Bitmap bitmap){
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 60,outputStream);
		
		//String body = addImageTag(outputStream.toByteArray());
		ByteArrayInputStream body = addImageTag(outputStream.toByteArray());
		
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		
		params.addHeader("Charset", "utf-8");
		params.addHeader("connection", "keep-alive");
		params.addHeader("Content-Type", "multipart/form-data");
		params.addQueryStringParameter("name", body);
		//params.addBodyParameter("parameter", body);
		params.addBodyParameter("imgFile", body, body.available(), System.currentTimeMillis()+".jpg", "image/jpg");
		
		String url = ConUtils.serverIp+"/icon/upLoadIcon" ;
		//访问后台时将下面注释去掉
		ConUtils.showProgressDialog(context, "图片上传中");
		
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				ConUtils.closeProgressDialog();
				Toast.makeText(context, "连接服务器失败", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				ConUtils.closeProgressDialog();
				Toast.makeText(context, "上传成功", 0).show();
				Intent intent = new Intent(context,SeachResultActivity.class);
				intent.putExtra("json", arg0.result);
				intent.putExtra("upload", 1);
				context.startActivity(intent);
			}
		});
		
		Intent intent = new Intent(context,SeachResultActivity.class);
		context.startActivity(intent);
		
	}*/
	
}

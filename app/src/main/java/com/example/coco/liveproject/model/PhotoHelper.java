package com.example.coco.liveproject.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.coco.liveproject.app.LiveApplication;
import com.example.coco.liveproject.bean.UserProfile;

import java.io.File;
import java.io.IOException;

/**
 * Created by coco on 2018/1/6.
 */

public class PhotoHelper {
    private static final int REQUEST_SELECT_PHOTO = 101;
    private static final int REQUEST_TAKE_CAMERA = 102;
    private static final int REQUEST_CROP = 103;

    static PhotoHelper helper;
    Activity activity;
    private Uri createAlbumUri;
    private Uri outUri;

    public enum CropType{
        HeadImg,Cover;
    }
    public interface onEditHeadImgListener {
        void onReady(Uri outUri);
    }

    public PhotoHelper(Activity activity) {
        this.activity = activity;
    }

    public static PhotoHelper getInstance(Activity activity) {
        if (helper == null) {
            helper = new PhotoHelper(activity);
        }
        return helper;
    }

    //转换content：//uri
    public Uri getImgContentUri(Uri uri) {
        String filePath = uri.getPath();
        Cursor cursor = LiveApplication.getApp().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=?",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            return LiveApplication.getApp().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    //创建拍照后的的输出路径
    public Uri createAlbumUri(UserProfile profile) {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + LiveApplication.getApp().getApplicationInfo().packageName;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String id = "";
        if (profile != null) {
            //获取id号
            id = profile.getProfile().getIdentifier();
        }
        //将用户的id号当作文件名，便于每次拍照后将之前的图片覆盖
        String fileName = "liveproject_" + id + ".jpg";
        File imgFile = new File(dirPath, fileName);
        if (imgFile.exists()) {
            imgFile.delete();
        }
        createAlbumUri= Uri.fromFile(imgFile);
        return createAlbumUri;

    }

    //通过id和包名生成裁剪输出文件
    public Uri createCropUri(UserProfile profile) {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + LiveApplication.getApp().getApplicationInfo().packageName;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String id = "";
        if (profile != null) {
            //获取id号
            id = profile.getProfile().getIdentifier();
        }
        //删除之前的图片
        String finalId = id;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.getName().startsWith(finalId)) {
                f.delete();
            }

        }
        //拿到新的图片
        String newImg = id + System.currentTimeMillis() + "_crop.jpg";
        File newImgFile = new File(dirPath, newImg);
        if (newImgFile.exists()) {
            newImgFile.delete();
        }
        try {
            newImgFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(newImgFile);
    }

    public Uri formatUri(Uri uri) {
        Uri rightUri = uri;
        int sdkInt = Build.VERSION.SDK_INT;//拿到当前的手机版本
        if (sdkInt < 24) {
            return rightUri;
        } else {
            String scheme = uri.getScheme();//拿到uri的开头信息（格式）
            if ("content".equals(scheme)) {
                rightUri = uri;
            } else {
                rightUri = getImgContentUri(uri);
            }

        }
        return rightUri;
    }

    public Uri getImgUri() {
        return createAlbumUri;
    }

    //开启跳转相机的方法
    public void startCameraIntent() {
        Uri cameraFileUri = createAlbumUri(LiveApplication.getApp().getUserProfile());
        int sdkInt = Build.VERSION.SDK_INT;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (sdkInt < 24) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
        } else {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.DATA, cameraFileUri.getPath());
            Uri uri = getImgContentUri(cameraFileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        activity.startActivityForResult(intent, REQUEST_TAKE_CAMERA);
    }

    public void startPhotoIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }

    private void startCrop(Intent data,CropType cropType) {
        Uri uri;
        if (data == null) {
            uri = getImgUri();
        } else {
            uri = data.getData();
        }
        //创建裁剪之后那个uri
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        if (cropType==CropType.HeadImg){
        intent.putExtra("aspectX", 800);
        intent.putExtra("aspectY", 800);
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        }else if (cropType==CropType.Cover){
            intent.putExtra("aspectX", 800);
            intent.putExtra("aspectY", 500);
            intent.putExtra("outputX", 800);
            intent.putExtra("outputY", 500);
        }
        // 设置为true直接返回bitmap,这里不做输出，只需要指定我们自己定义uri即可
        intent.putExtra("return-data", false);
        //设置输出图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //传入要裁剪的图像uri,但是有的android7.0手机的uri首地址不是content:这样是不识别的，所以转化
        Uri rightUri = formatUri(uri);
        //传入源uri
        intent.setDataAndType(rightUri, "image/*");
        //设置输出uri
        outUri = createCropUri(LiveApplication.getApp().getUserProfile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        //开启
        activity.startActivityForResult(intent, REQUEST_CROP);

    }

    public void onActivityResult(int requestcode, int resultcode, Intent data,CropType cropType ,onEditHeadImgListener listener) {
        if (requestcode == REQUEST_SELECT_PHOTO) {
            if (resultcode == Activity.RESULT_OK) {
                startCrop(data,cropType);
            }
        } else if (requestcode == REQUEST_TAKE_CAMERA) {
            if (resultcode == Activity.RESULT_OK) {
                startCrop(data,cropType);
            }
        } else if (requestcode == REQUEST_CROP) {
            if (resultcode == Activity.RESULT_OK) {
                if (listener != null) {
                    listener.onReady(outUri);
                }
            }
        }
    }


}

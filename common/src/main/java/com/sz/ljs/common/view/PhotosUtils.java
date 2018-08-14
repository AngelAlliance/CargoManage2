package com.sz.ljs.common.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.base.BaseApplication;
import com.sz.ljs.common.R;
import com.sz.ljs.common.utils.FileUtils;
import com.sz.ljs.common.utils.MiPictureHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 侯晓戬 on 2017/8/11.
 * 用户选择照片的组件
 * 支持照相
 * 支持相册选择
 */

public class PhotosUtils extends BaseActivity implements View.OnClickListener {

    public interface IUserPhotosCallBack {
        void onResult(Bitmap bitmap);
    }

    private static IUserPhotosCallBack UserCallBack = null;

    //选项图像
    public static void selectUserPhotos(IUserPhotosCallBack callBack) {
        UserCallBack = callBack;
        BaseApplication.startActivity(PhotosUtils.class);
    }

    //图像转字符串
    public static String bitmapToHexString(Bitmap bitmap) {
        StringBuilder sb = new StringBuilder();
        String stmp;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);// 100)压缩文件
        byte[] b = stream.toByteArray();
        for (byte bte : b) {
            stmp = Integer.toHexString(bte & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0");
                sb.append(stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    //Drawable转字符串
    public static String drawableToHexString(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bitmapToHexString(bd.getBitmap());
    }

    public static String fileBitmapToHexString(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            return bitmapToHexString(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private final static String TAG = "PhotosUtils";

    private final static int SELECT_FORM_PHOTOS = 0x2011;//从本地相册选择
    private final static int SELECT_FORM_CAMERA = 0x2012;//选择拍照
    private final static int CAPTURE_IMAGE_FROM_ALL = 0x2013; //将用户选择或者拍照的相片进行剪裁
    private final static String UP_TEMP_DIR_PATH = "TempPhotos";//剪裁临时目录
    //保存剪裁图片本地的路径
    private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";
    private static final String TEMP_IMAGE_CAMERA_FILE_NAME = "QL_camera_temp.jpg";

    private final String[] permission = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private String IMG_ROOT_PATH = null;
    private TextView mtvPhotos;
    private TextView mtvCamera;
    //转进制
    private String IMAGE_BYTE = "";//图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanSystemBarTransparent(false);
        setContentView(R.layout.activity_user_photos);
        setFinishOnTouchOutside(false);
        initWindows();
        initView();
    }

    //初始化窗口
    private void initWindows() {
        Window window = getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    //初始化UI界面
    private void initView() {
        findViewById(R.id.text_view_cancel).setOnClickListener(this);
        mtvPhotos = (TextView) findViewById(R.id.text_view_photos);
        mtvPhotos.setOnClickListener(this);
        mtvCamera = (TextView) findViewById(R.id.text_view_camera);
        mtvCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        if (R.id.text_view_cancel == viewID) {
            finish();
            return;
        }
        if (R.id.text_view_photos == viewID) {
            if (authorizeRuntimePermission()) {
                handlerPhotos();
            } else {
                showApplyDialog();
            }
            return;
        }
        if (R.id.text_view_camera == viewID) {
            if (authorizeRuntimePermission()) {
                handlerCamera();
            } else {
                showApplyDialog();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_FORM_PHOTOS: {
                    if ("vivo".equals(Build.MANUFACTURER)) {
                        handlerVivoCameraResult(data);
                    }else {
                        Uri uri = data.getData();
//                        startPhotoZoom(uri);
                        handlerVivoCameraResult(data);
                        handlerPhotosPath(uri);
                    }

                }
                break;
                case SELECT_FORM_CAMERA: {
                    try {
                        Bundle bundle = data.getExtras();
                        if (null != bundle) {
                            Bitmap bitmap = (Bitmap) bundle.get("data");
                            String path = getPhotosTempPath() + TEMP_IMAGE_CAMERA_FILE_NAME;
                            Uri uri = FileUtils.getUriForFile(this, saveBitmap(bitmap, path));
                            VivoImage(bitmap);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
                case CAPTURE_IMAGE_FROM_ALL: {
                    //剪裁完的图片，就是我们需要的
                    handlerCaptureImage(data);
                }
                break;
                default: {
                    break;
                }
            }
        }
    }

    @Override
    protected String[] getRuntimePermissions() {
        return permission;
    }

    //TODO 处理相册的选择
    private void handlerPhotos() {
        if (authorizeRuntimePermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, SELECT_FORM_PHOTOS);
        }
    }

    //TODO 获取用户选择图片的路径
    private void handlerPhotosPath(Uri url) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(url, proj, null,
                null, null);
        String path = null;
        if (cursor != null && cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                cursor.moveToFirst();
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(column_index);// 图片在的路径
            }
            cursor.close();
        } else {
            path = MiPictureHelper.getPath(this, url);
        }
        Logger.d("handlerPhotosPath() path=" + path);
    }


    //TODO 处理相机
    private void handlerCamera() {
        if (authorizeRuntimePermission()) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, SELECT_FORM_CAMERA);
            } else {
                Toast.makeText(getApplicationContext(), R.string.str_sel_photo_no_sd,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //TODO 处理Vivo相机返回数据
    private void handlerVivoCameraResult(Intent data) {
        Uri url = data.getData();
        VivoImage(getVivoBitmap(data));
    }
    //TODO 处理相机返回数据
    private void handlerCameraResult(Intent data) {
        try {
            Bundle bundle = data.getExtras();
            if (null != bundle) {
                Bitmap bitmap = (Bitmap) bundle.get("data");
                String path = getPhotosTempPath() + TEMP_IMAGE_CAMERA_FILE_NAME;
                Uri uri = FileUtils.getUriForFile(this, saveBitmap(bitmap, path));
                Logger.i("handlerCameraResult() " + uri);
//                if ("vivo".equals(Build.MANUFACTURER)) {

//                    Bitmap bitmap1=getVivoBitmap(uri1);
//                    VivoImage(bitmap1);

//                } else {
                if(null!=bitmap){
                    VivoImage(bitmap);
                }
//                    startPhotoZoom(uri);
//                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO 2018-04-03 因为适配vivo手机拍照回来裁剪出问题
    private void VivoImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0
        // 100)压缩文件
        byte[] bt = stream.toByteArray();
        IMAGE_BYTE = byte2HexStr(bt);
        Log.i("vivo相册选择返回", "bitmap=" + bitmap);
        if (null != bitmap&&!"".equals(bitmap)) {
            if (null != UserCallBack) {
                Log.i("vivo相册选择返回", "UserCallBack=" + bitmap);
                UserCallBack.onResult(Bitmap.createBitmap(bitmap));
            }
        } else {
            //提示获取失败
            Toast.makeText(getContext(),
                    R.string.str_pszpsb, Toast.LENGTH_LONG).show();
        }
//        bitmap.recycle();
        deleteTempFile();
        finish();
    }

    /**
     * Vivo手机单独适配，直接转换Intent为Bitmap
     *
     * @param data
     * @return
     */
    private Bitmap getVivoBitmap(Intent data) {
        // TODO: 2018/5/24 添加转换时等待进度条
        WaitingDialog TipDialog = new WaitingDialog(this);
        TipDialog.setWaitText("");
        TipDialog.show();
        try {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            Bitmap pickBitmap = null;
            ContentResolver resolver = getContentResolver();
            bitmap = BitmapFactory.decodeStream(resolver
                    .openInputStream(uri));
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scal = 300 / (float) width;
            if (width >= 300) {
                Matrix matrix = new Matrix();
                matrix.postScale(scal, scal);
                pickBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            } else {
                pickBitmap = Bitmap.createBitmap(bitmap);
            }
            // 回收Bitmap的空间
            bitmap.recycle();
            // 回收Bitmap的空间
            if (TipDialog != null && TipDialog.isShowing()) {
                TipDialog.dismiss();
            }
            Log.i("vivo相册选择处理成功", "pickBitmap=" + pickBitmap);
            return pickBitmap;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.i("vivo相册选择处理失败", "exception=" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    //TODO 图片剪裁处理 圆形
    private void startPhotoZoom(Uri uri) {
        Logger.i("startPhotoZoom() " + uri);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Uri out = Uri.fromFile(new File(getPhotosTempPath(), TMP_IMAGE_FILE_NAME));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, out);
        }
        intent.putExtra("return-data", true);//设置为返回数据
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        startActivityForResult(intent, CAPTURE_IMAGE_FROM_ALL);
    }

    //TODO 处理剪裁完的图片资源
    private void handlerCaptureImage(Intent data) {
        if (data != null) {
            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Uri imageUri = data.getData();
                if (null != imageUri) {//非原生系统也是直接传数据的
                    bitmap = decodeUriAsBitmap(imageUri);
                }
            }
            if (null == bitmap) {
                Bundle bundle = data.getExtras();
                if (null != bundle) {
                    bitmap = bundle.getParcelable("data");
                }
            }
            if (null == bitmap) {
                bitmap = decodeUriAsBitmap(null);
            }
            if (null != bitmap) {
                if (null != UserCallBack) {
                    UserCallBack.onResult(bitmap);
                }
            } else {
                //提示获取失败
                Toast.makeText(getContext(),
                        R.string.str_pszpsb, Toast.LENGTH_LONG).show();
            }
        }
        deleteTempFile();
        finish();
    }

    //TODO 删除临时文件
    private void deleteTempFile() {
        String tempPath = getPhotosTempPath() + TMP_IMAGE_FILE_NAME;
        File file = new File(tempPath);
        if (file.exists()) {
            file.deleteOnExit();
        }
        tempPath = getPhotosTempPath() + TEMP_IMAGE_CAMERA_FILE_NAME;
        file = new File(tempPath);
        if (file.exists() && file.isFile()) {
            file.deleteOnExit();
        }
    }

    //TODO 解码数据
    public Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap;
        try {
            if (null == uri) {
                Uri out = FileUtils.getUriForFile(this, new File(getPhotosTempPath(),
                        TMP_IMAGE_FILE_NAME));
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(out));
            } else {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    //TODO 保存bitmap到文件
    private File saveBitmap(Bitmap bitmap, String path) {
        File f = new File(path);
        if (f.exists()) {
            f.deleteOnExit();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    //TODO 获取相片临时目录
    private String getPhotosTempPath() {
        if (null == IMG_ROOT_PATH) {
            IMG_ROOT_PATH = getExternalCacheDir().getAbsolutePath() + "/" + UP_TEMP_DIR_PATH + "/";
        }
        File file = new File(IMG_ROOT_PATH);
        if (!file.exists()) {
            boolean bret = file.mkdirs();
        }
        return IMG_ROOT_PATH;
    }

    /*
     *  实现字节数组向十六进制的转换
     */
    public static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        String stmp;
        for (byte bte : b) {
            stmp = Integer.toHexString(bte & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0");
                sb.append(stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

}

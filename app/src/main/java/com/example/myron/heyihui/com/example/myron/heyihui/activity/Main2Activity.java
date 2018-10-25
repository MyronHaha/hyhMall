package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.common.design.MaterialDialog;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;

import com.example.myron.heyihui.com.example.myron.heyihui.Data.AppInfo;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.SearchActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.HomeFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.MyFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.NeedFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.ProductFragment2;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1.MallHomeFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.StatusBarUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CommonProgressDialog;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.AndroidWorkaround;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.response.GsonResHandler;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore.API_TOKEN;
import static com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore.APPID;

//请求account；
public class Main2Activity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FrameLayout fl_content;
    private FragmentManager fragmentManager;
    //    FragmentTransaction transaction;  一个transaction只能commit一次，多次commit要重新建对象；
    private MallHomeFragment homeFragment;
    private NeedFragment needFragment;
    private ProductFragment2 productFragment;
    private MyFragment myFragment;

    private RadioGroup rbGroup;
    private RadioButton rbHome, rbNeed, rbProduct, rbMy;
    private Fragment currentFragment;
    private boolean isExit = false;
    private long lastTime = 0;
    private CommonProgressDialog pBar;

    private String packageName;
    private String appName;
    private String downLoadPath;
    private String apkName;

    public static boolean isclick = false;
    public static String newVerName = "";

    //----
    public DrawerLayout drawer;
    public Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    public NavigationView navigationView;
    ImageView iv_nav;
    //搜索
    @BindView(R.id.iv_right)
    View search;

    public Main2Activity(){
        super(R.layout.activity_main22);
    }
    @Override
    public void initView() {
        setUpdateInfo();
        bindView();
        initHomeFragment();
        initFragment();
        checkPermission();
        initDrawLayout();
    }

    private void setUpdateInfo() {
        packageName = BaseApp.getMyApplicationContext().getPackageName();
        appName = common.getAppName();
        downLoadPath = Environment.getExternalStorageDirectory() + "/" + packageName;
        apkName = appName + ".apk";
    }

    private void initDrawLayout() {
        iv_nav = (ImageView) findViewById(R.id.iv_back);
        ViewGroup.LayoutParams params = iv_nav.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        iv_nav.setLayoutParams(params);
        iv_nav.setImageResource(R.mipmap.nav_icon);
        iv_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        toolbar.setNavigationIcon(R.mipmap.nav_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView iv  = (ImageView) navigationView.findViewById(R.id.user_icon);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                common.launchActivity(Main2Activity.this,LoginActivity.class);
//            }
//        });
    }

    //获取读写权限
    public void checkPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(listener)
                .start();

    }

    //权限监听
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            if (requestCode == 200) {
                checkVersion();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                MyToast.makeText(Main2Activity.this, "用户拒绝授权", 2000).show();
            }
        }
    };

    private void checkVersion() {
        int currentVerCode = common.getCurrentVerCode();
        getVersion(currentVerCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initHomeFragment() {
        homeFragment = new MallHomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, homeFragment);
        transaction.commit();
    }

    private void initFragment() {

//        homeFragment = new HomeFragment();
        needFragment = new NeedFragment();
        productFragment = new ProductFragment2();
        myFragment = new MyFragment();
        currentFragment = homeFragment;
    }

    private void bindView() {
//        common.hideBackIv(this);
        //radio button
        rbGroup = (RadioGroup) findViewById(R.id.tab_menu);
        rbGroup.setOnCheckedChangeListener(this);
        rbHome = (RadioButton) findViewById(R.id.tab_home);
        rbNeed = (RadioButton) findViewById(R.id.tab_need);
        rbProduct = (RadioButton) findViewById(R.id.tab_product);
        rbMy = (RadioButton) findViewById(R.id.tab_my);
        //
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        //
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              common.launchActivity(Main2Activity.this, SearchActivity.class);
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Log.e("onCheckedChanged", "true");
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        hideAllFragment(transaction);
        // 因为我的toolbar隐藏
        switch (i) {
            case R.id.tab_home:
                handler.sendEmptyMessage(0);
                switchFragment(homeFragment);
                break;
            case R.id.tab_need:
                handler.sendEmptyMessage(1);
                switchFragment(needFragment);
                break;
            case R.id.tab_product:
                handler.sendEmptyMessage(2);
                switchFragment(productFragment);
                break;
            case R.id.tab_my:
                handler.sendEmptyMessage(3);
                switchFragment(myFragment);
                break;
        }

    }

//    public void hideAllFragment(FragmentTransaction transaction) {
//        if (homeFragment != null) {
//            transaction.hide(homeFragment);
//        }
//        if (needFragment != null) {
//            transaction.hide(needFragment);
//        }
//        if (productFragment != null) {
//            transaction.hide(productFragment);
//        }
//        if (myFragment != null) {
//            transaction.hide(myFragment);
//        }
//    }

    public void tabChangedUI(String title, boolean visible) {
        if (!visible) {
            common.SetToolbarVisibility(visible, Main2Activity.this);
        } else {
            common.SetToolbarVisibility(visible, Main2Activity.this);
            common.changeTitle(Main2Activity.this, title);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //首页
                case 0:
                    tabChangedUI("ALL IN ONE", true);
                    search.setVisibility(View.VISIBLE);
                    break;
                //需求
                case 1:
                    tabChangedUI("消息", true);
                    search.setVisibility(View.GONE);
                    break;
                //产品
                case 2:
                    tabChangedUI("购物车", true);
                    search.setVisibility(View.GONE);
                    break;
                //我的
                case 3:
                    tabChangedUI("我的", false);
                    search.setVisibility(View.GONE);
                    break;
            }
        }
    };

    public void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Log.e("switchFragment", "");
        if (!targetFragment.isAdded()) {
            Log.e("add", "");

            transaction.hide(currentFragment)
                    .add(R.id.fl_content, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                if ((time - lastTime) > 2000) {
                    lastTime = time;
                    isExit = true;
                    MyToast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();

                } else {
                    isExit = false;
                    BaseApp.getInstance().exit(Main2Activity.this);
                }
            }
        });

    }

    // 获取更新版本号
    public void getVersion(final int vision) {

        HttpCore.get("http://api.fir.im/apps/latest/" + APPID + "?api_token=" + API_TOKEN, new GsonResHandler<AppInfo>() {
            @Override
            public void onFailed(int i, String s) {
                Log.e("获取信息失败", s);
            }

            @Override
            public void onSuccess(int i, AppInfo appInfo) {
                String newversion = appInfo.getVersion();
                String content = appInfo.getChangelog() == null ? "" : appInfo.getChangelog();
                String url = appInfo.getInstallUrl();
                newVerName = appInfo.getVersionShort();
                double newversioncode = Double
                        .parseDouble(newversion);
//        int cc = (int) (newversioncode);
                double cc = newversioncode;
                System.out.println(newversion + "v" + vision + ",,"
                        + cc);
                if (cc != vision) {
                    if (vision < cc) {
                        System.out.println(newversion + "v"
                                + vision);
                        // 版本号不同
                        if (content.contains("[F]")) {
                            content = content.replace("[F]", "");
                            ShowDialog(vision, newversion, content, url, true);
                        } else {
                            if (isclick) {
                                ShowDialog(vision, newversion, content, url, false);
                            } else {
                                if (!common.SP_Read(Main2Activity.this, "updateClose").equals("true")) {
                                    ShowDialog(vision, newversion, content, url, false);
                                }
                            }

                        }

                    }
                }
            }
        });
//        String data = "";

//        = "更新内容"+"\n" +
//                "1.xxxxxxxxxx\n" +
//                "2.xxxxxxxxxxxx-\n";//更新内容
//        String url = "https://download.fir.im/apps/5a56bd60548b7a544d00005b/install?download_token=1e844cfc226568f2b3916f6a1bf91c89&release_id=5a9e02a4548b7a649947a6c8";//安装包下载地址

    }


    /**
     * 升级系统
     *
     * @param content
     * @param url
     */
    private void ShowDialog(int vision, String newversion, String content,
                            final String url, boolean isForceToSure) {
//        new android.app.AlertDialog.Builder(this)
//                .setTitle("版本更新")
//                .setMessage(content)
//                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        pBar = new CommonProgressDialog(Main2Activity.this);
//                        pBar.setCanceledOnTouchOutside(false);
//                        pBar.setTitle("正在下载");
//                        pBar.setCustomTitle(LayoutInflater.from(
//                                Main2Activity.this).inflate(
//                                R.layout.title_dialog, null));
//                        pBar.setMessage("正在下载");
//                        pBar.setIndeterminate(true);
//                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                        pBar.setCancelable(true);
//                        // downFile(URLData.DOWNLOAD_URL);
//                        final DownloadTask downloadTask = new DownloadTask(
//                                Main2Activity.this);
//                        downloadTask.execute(url);
//                        pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                downloadTask.cancel(true);
//                            }
//                        });
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();

        //        final MaterialDialog dialog = new MaterialDialog(this);//自定义的对话框，可以呀alertdialog
//        dialog.content(content).btnText("取消", "更新").title("版本更新 ")
//                .titleTextSize(15f).show();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnBtnClickL(new OnBtnClickL() {// left btn click listener
//            @Override
//            public void onBtnClick() {
//                dialog.dismiss();
//            }
//        }, new OnBtnClickL() {// right btn click listener
//
//            @Override
//            public void onBtnClick() {
//                dialog.dismiss();
//                // pBar = new ProgressDialog(MainActivity.this,
//                // R.style.dialog);
//                pBar = new CommonProgressDialog(MainActivity.this);
//                pBar.setCanceledOnTouchOutside(false);
//                pBar.setTitle("正在下载");
//                pBar.setCustomTitle(LayoutInflater.from(
//                        MainActivity.this).inflate(
//                        R.layout.title_dialog, null));
//                pBar.setMessage("正在下载");
//                pBar.setIndeterminate(true);
//                pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                pBar.setCancelable(true);
//                // downFile(URLData.DOWNLOAD_URL);
//                final DownloadTask downloadTask = new DownloadTask(
//                        MainActivity.this);
//                downloadTask.execute(url);
//                pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        downloadTask.cancel(true);
//                    }
//                });
//            }
//        });
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.setTitle("版本更新");
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("更新", new MaterialDialog.OnClickListener() {
            @Override
            public boolean onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                pBar = new CommonProgressDialog(Main2Activity.this);
                pBar.setCanceledOnTouchOutside(false);
                pBar.setTitle("正在下载");
                pBar.setCustomTitle(LayoutInflater.from(
                        Main2Activity.this).inflate(
                        R.layout.title_dialog, null));
                pBar.setMessage("正在下载");
                pBar.setIndeterminate(true);
                pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pBar.setCancelable(true);
                // downFile(URLData.DOWNLOAD_URL);
                final DownloadTask downloadTask = new DownloadTask(
                        Main2Activity.this);
                downloadTask.execute(url);
                pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
                return false;
            }
        });
        //
        if (!isForceToSure) {
            builder.setNegativeButton("稍后再试", new MaterialDialog.OnClickListener() {
                @Override
                public boolean onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    HashMap map = new HashMap();
                    map.put("updateClose", "true");
                    common.SP_Write(Main2Activity.this, map);
                    return false;
                }
            });
        }
        builder.show();
    }


    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error
                // report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(downLoadPath,
                            apkName);

                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }

                } else {
                    Toast.makeText(Main2Activity.this, "sd卡未挂载",
                            Toast.LENGTH_LONG).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);

                }
            } catch (Exception e) {
                System.out.println(e.toString());
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            pBar.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            pBar.dismiss();
            if (result != null) {

                AndPermission.with(Main2Activity.this)
                        .requestCode(REQUEST_CODE_PERMISSION_SD)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(rationaleListener
                        )
                        .send();


                Toast.makeText(context, "您未打开SD卡权限" + result, Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(context, "File downloaded",
                // Toast.LENGTH_SHORT)
                // .show();
                update();
            }

        }
    }

    private static final int REQUEST_CODE_PERMISSION_SD = 101;

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            AndPermission.rationaleDialog(Main2Activity.this, rationale).show();
        }
    };


    private void update() {
        //安装应用
        File apkFile = new File(downLoadPath + "/" + apkName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(Main2Activity.this, packageName + ".fileprovider", apkFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

//    public void toLogin(View view){
//        common.launchActivity(this,LoginActivity.class);
//   }
}


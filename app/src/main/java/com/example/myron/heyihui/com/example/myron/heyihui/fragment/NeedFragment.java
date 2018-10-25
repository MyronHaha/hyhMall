package com.example.myron.heyihui.com.example.myron.heyihui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Myron on 2017/10/23.
 */

public class NeedFragment extends Fragment {
    EditText et_device,et_needDetails,et_contractName,et_contractPNum;
    Button bt_submit;
    View view;
    CustomDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_needfragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BindView();
    }
    public void BindView(){
        et_device = (EditText) view.findViewById(R.id.et_deviceName);
        et_needDetails = (EditText) view.findViewById(R.id.et_needDetails);
        et_contractName = (EditText) view.findViewById(R.id.et_contractName);
        et_contractPNum = (EditText) view.findViewById(R.id.et_contractPNum);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        dialog = new CustomDialog(getActivity(),R.style.CustomDialog,"提交中...");
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //

                submitRequest();
            }
        });
    }
    // 提交需求 接口
    public void submitRequest(){
        if(et_device.getText().toString().equals("")){
            MyToast.makeText(getContext(),"终端名字不能为空", Toast.LENGTH_SHORT).show();
            et_device.requestFocus();
            return;
        }
        if(et_needDetails.getText().toString().equals("")){
            MyToast.makeText(getContext(),"需求不能为空", Toast.LENGTH_SHORT).show();
            et_needDetails.requestFocus();
            return;
        }
        if(et_contractName.getText().toString().equals("")){
            MyToast.makeText(getContext(),"联系人不能为空", Toast.LENGTH_SHORT).show();
            et_contractName.requestFocus();
            return;
        }
        if(et_contractPNum.getText().toString().equals("")){
            MyToast.makeText(getContext(),"联系人电话不能为空", Toast.LENGTH_SHORT).show();
            et_contractPNum.requestFocus();
            return;
        }
        dialog.show();
        String deviceName,details,contractName,phoneNum;
        deviceName = et_device.getText().toString();
        details = et_needDetails.getText().toString();
        contractName = et_contractName.getText().toString();
        phoneNum = et_contractPNum.getText().toString();
        Log.e("submit_params",deviceName+"\n"+details+"\n"+contractName+"\n"+phoneNum);
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
//        {"companyName":"myronhaha","content":"dhsafdflsdkfjlskfdflskf","linkman":"myron","linkmanTel":"13189677342"}
        map.put("companyName",deviceName);
        map.put("content",details);
        map.put("linkman",contractName);
        map.put("linkmanTel",phoneNum);
        map2.put("bean",new JSONObject(map).toString());
        HttpCore.post(URL.POSTREQUEST, map2, new JsonResHandler() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                try {
                    int status  = response.getInt("status");
                    String message = response.getString("message");
                    dialog.dismiss();
                    if(status == 1){
                        MyToast.makeText(getActivity(),"提交成功!",3000).show();
                    }else{
                        MyToast.makeText(getActivity(),message+",请稍后重试!",3000).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

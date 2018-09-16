package com.shiyuanhui.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.shiyuanhui.bean.Feedback;
import com.shiyuanhui.neuip.R;


public class ActSendFeedbackFragment extends Fragment implements OnClickListener {

	EditText et_userconnectway;
	EditText et_content;
	Button btn_feedback;
	static String msg;
	private View parentView;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.activity_sendfeedback, container, false);
		setUpViews();
		return parentView;
	}



	private void setUpViews() {
		et_userconnectway = (EditText)parentView.findViewById(R.id.et_userconnetway);
		et_content = (EditText) parentView.findViewById(R.id.et_content);
		btn_feedback = (Button)parentView.findViewById(R.id.btn_feedback);
		btn_feedback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btn_feedback){
			String content = et_content.getText().toString();
			String user_connect = et_userconnectway.getText().toString();
			if(!TextUtils.isEmpty(content)){
				if(content.equals(msg)){
					Toast.makeText(getActivity(), "请勿重复提交反馈", Toast.LENGTH_SHORT).show();
				}else {
					msg = content;
					// 发送反馈信息
					saveFeedbackMsg(user_connect,content);
					Toast.makeText(getActivity(), "您的反馈信息已发送", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(getActivity(), "请填写反馈内容", Toast.LENGTH_SHORT).show();
			}
		}
	}



	/**
	 * 保存反馈信息到服务器
	 * @param msg 反馈信息
	 */
	private void saveFeedbackMsg(String contacts,String msg){
		Feedback feedback = new Feedback();
		feedback.setContacts(contacts);
		feedback.setContent(msg);
		feedback.save(new SaveListener<String>() {
			@Override
			public void done(String s, BmobException e) {
				if (e == null)
				{
					Log.i("bmob", "反馈信息已保存到服务器");
				}
				else
				{
					Log.e("bmob", "保存反馈信息失败："+s);
				}
			}
		});
	}

}

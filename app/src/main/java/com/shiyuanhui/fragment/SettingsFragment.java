package com.shiyuanhui.fragment;


import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.shiyuanhui.neuip.R;
import com.shiyuanhui.util.SharedPreferencesUtil;


/**
 * SettingsFragment
 * @author shiyuanhui
 *
 */
public class SettingsFragment extends Fragment implements OnClickListener{
	 private View parentView;
	 private EditText et_college_username;
	 private EditText et_college_password;
	 private Button bind_collegenet;
	 private Button unbind_collegnet;
	 private SharedPreferencesUtil preferencesUtil;
	 private CheckBox checkBox;
	 private boolean isAuto = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	 parentView = inflater.inflate(R.layout.settings, container, false);
    	 setUpViews();
    	 return parentView;
    }

    private void setUpViews() {
       checkBox = (CheckBox)parentView.findViewById(R.id.auto_login);
       et_college_username = (EditText)parentView.findViewById(R.id.et_college_username);
       et_college_password = (EditText)parentView.findViewById(R.id.et_college_password);
       bind_collegenet = (Button)parentView.findViewById(R.id.bind_collegenet);
       unbind_collegnet = (Button)parentView.findViewById(R.id.unbind_collegnet);
       preferencesUtil = SharedPreferencesUtil.getSpUtil(getActivity());
       bind_collegenet.setOnClickListener(this);
       unbind_collegnet.setOnClickListener(this);
       isAuto = preferencesUtil.getIsAutoIn();
       checkBox.setChecked(isAuto);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bind_collegenet:
		{   String username = et_college_username.getText().toString();
		    String password = et_college_password.getText().toString();
			preferencesUtil.saveUserCount(username, password);
			preferencesUtil.setAutoIn(checkBox.isChecked());
			Toast.makeText(getActivity(), "已绑定校园网账号", Toast.LENGTH_SHORT).show();
			hideSoft();
		}
			break;
        case R.id.unbind_collegnet:
        {
        	 preferencesUtil.saveUserCount("", "");
			 Toast.makeText(getActivity(), "已解绑校园网账号", Toast.LENGTH_SHORT).show();
			 hideSoft();
        }
			break;
		default:
			break;
		}
		
	}
	
	private  void hideSoft(){
		((InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE))
		.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

}

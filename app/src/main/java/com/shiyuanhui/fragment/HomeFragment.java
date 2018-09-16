package com.shiyuanhui.fragment;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shiyuanhui.model.User;
import com.shiyuanhui.neucse.MenuActivity;
import com.shiyuanhui.neuip.R;
import com.shiyuanhui.presenter.NetworkPresenter;
import com.shiyuanhui.util.SharedPreferencesUtil;
import com.shiyuanhui.view.INetworkView;
import com.special.ResideMenu.ResideMenu;


/**
 * Homefragment
 * @author shiyuanhui
 *
 */
public class HomeFragment extends Fragment implements OnClickListener,INetworkView{
    private View parentView;
    private ResideMenu resideMenu;
    private TextView tv_connect_information;
    private Button btn_moblie_link;
    private Button btn_moblie_unlink;
    private Button btn_moblie_unalllink;
    private ProgressBar progressBar;
    private SharedPreferencesUtil preferencesUtil;
    private NetworkPresenter networkPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {


        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        progressBar = (ProgressBar)parentView.findViewById(R.id.pb_1);
        progressBar.setVisibility(View.GONE);
        tv_connect_information = (TextView)parentView.findViewById(R.id.tv_connectnet_info);
        btn_moblie_link = (Button)parentView.findViewById(R.id.btn_moblie_link);
        btn_moblie_unlink = (Button)parentView.findViewById(R.id.btn_moblie_unlink);
        btn_moblie_unalllink = (Button)parentView.findViewById(R.id.btn_moblie_unalllink);
        btn_moblie_link.setOnClickListener(this);
        btn_moblie_unalllink.setOnClickListener(this);
        btn_moblie_unlink.setOnClickListener(this);

        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);

        InitSharedPreference();
        if(preferencesUtil.getIsAutoIn()){
        	btn_moblie_link.performClick();
        }


        networkPresenter = new NetworkPresenter(User.getInstance(),preferencesUtil);
        networkPresenter.attachView(this);
    }

	@Override
	public void onClick(View v) {
		tv_connect_information.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		switch (v.getId()) {
		case R.id.btn_moblie_link:
		{
			networkPresenter.connctNetwork();
		}
			break;
        case R.id.btn_moblie_unlink:
        {
        	networkPresenter.disConnectMobile();
        }
			break;
        case R.id.btn_moblie_unalllink:
        {
        	networkPresenter.disConnectAll();
        }
			break;
		default:
			break;
		}
		
	}

    @Override
    public void showMessage(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_connect_information.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tv_connect_information.setText(message);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        networkPresenter.detachView();
    }


    private void InitSharedPreference()
    {
        preferencesUtil = SharedPreferencesUtil.getSpUtil(getActivity());

        //如果是第一次进入，需要初始化下，避免异常
        boolean isFirst = preferencesUtil.getIsFirstIn();
        if(isFirst){
            preferencesUtil.saveUserCount("", "");
            preferencesUtil.setNotFirstIn();
        }
    }
}

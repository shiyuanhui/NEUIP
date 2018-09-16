package com.shiyuanhui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiyuanhui.neuip.R;


/**
 * Profileragment
 * @author shiyuanhui
 *
 */
public class ProfileFragment extends Fragment {
	private View parentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parentView = inflater.inflate(R.layout.home, container, false);
    	Intent intent = new Intent();
		intent.setData(Uri.parse("http://ipgw.neu.edu.cn:8800/"));
	    intent.setAction(Intent.ACTION_VIEW);
		startActivity(intent);
        return parentView;
    }
}

package com.shiyuanhui.bean;

import cn.bmob.v3.BmobObject;
/**
 * 创建用户反馈类
 * @ClassName: Feedback
 * @Description: TODO
 * @author shiyuanhui
 * @date 2016-7-28 下午9:50:20
 */
@SuppressWarnings("serial")
public class Feedback extends BmobObject {
	// 反馈内容
	private String content;
	// 联系方式
	private String contacts;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}


}

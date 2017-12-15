package com.thinkgem.jeesite.common.persistence;

import java.util.List;

public class JQResultModel extends BaseModel {

	private static final long serialVersionUID = -6172730525568642287L;

	private int result = 1;

	private String msg = "";

	private List<?> rows;

	private long total;

	private int page;

	private long records;

	private Object userdata = new Object();

	public JQResultModel() {
	}

	public JQResultModel(List<?> rows, Page<?> page) {
		this.rows = rows;
		this.total = page.getTotalPage();
		this.page = page.getPageNo();
		this.records = page.getCount();
		this.result = 1;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public Object getUserdata() {
		return userdata;
	}

	public void setUserdata(Object userdata) {
		this.userdata = userdata;
	}

	public class UserData {

		private int result = 1;
		private String msg = "";

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}

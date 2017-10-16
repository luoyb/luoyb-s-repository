package cc.cnfc.dto;

import cc.cnfc.core.orm.Page;

public class PageResult extends Result {

	public PageResult(String code, Page page) {
		super(code);
		this.page = page;
	}

	public PageResult(String code, String message) {
		super(code, message);
	}

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}

package cc.cnfc.core.orm;

/*
 * Copyright 2007 by RENWOYOU Corporation.
 * 
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of RENWOYOU Corporation ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with RENWOYOU.
 */
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 查询分页管理抽象管理类，子类有HibernatePage(Hibernate查询方式)及IbatisPage(iBatis查询方式)。<br>
 * 在进行分页查询时，可使用此类为参数，并返回相应查询结果及总计记录数(如果需要)
 *
 * @author hongdj
 * @version 1.0
 */
public class Page implements Serializable {

	private static final long serialVersionUID = -493155896647861350L;

	// 查询结果的起始位置
	private int offset;

	// 返回页大小
	private int pageSize;

	// 当前页码
	private int pageNo;

	// 总页数
	private int pageCount;

	// 总记录数
	private int recordCount;

	// 是否要统计总记录数,默认要
	private boolean countTotalSize;

	// 查询结果列表
	private List<?> queryList;

	/******** 用于jqGrid ***********/
	// // 查询结果列表
	// private List<?> rows;
	// // 总记录页数
	// private int total;
	// // 当前页
	// private int page;
	// // 总记录数
	// private int records;

	/********************************/

	/**
	 * 根据HttpServletRequest创建hql对象
	 *
	 * @param request
	 *            得到当前页
	 */
	public Page(HttpServletRequest request) {
		this.pageSize = 0;
		this.pageNo = 0;
		this.pageCount = 0;
		this.recordCount = 0;
		this.offset = -1;
		this.queryList = null;
		this.countTotalSize = true;
		// 以下根据前台传的参数赋值
		if (request != null) {
			String gtJson = request.getParameter("_gt_json");
			if (StringUtils.isNotEmpty(gtJson)) {
				JSONObject jo = JSONObject.fromObject(gtJson);
				JSONObject pageInfoJO = jo.getJSONObject("pageInfo");
				Integer pageNo = pageInfoJO.getInt("pageNum");
				Integer pageSize = pageInfoJO.getInt("pageSize");
				this.pageNo = pageNo;
				this.pageSize = pageSize;
				this.offset = pageSize * (pageNo - 1);
			} else {
				if (StringUtils.isNotEmpty(request.getParameter("page"))) {
					this.pageNo = Integer.parseInt(request.getParameter("page"));
				}
				if (StringUtils.isNotEmpty(request.getParameter("rows"))) {
					this.pageSize = Integer.parseInt(request.getParameter("rows"));
				}
				this.offset = pageSize * (pageNo - 1);
			}
		}
	}

	public Page(int pageNo, int pageSize, int recordCount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
	}

	public Page() {
		this.pageSize = 0;
		this.pageNo = 0;
		this.pageCount = 0;
		this.recordCount = 0;
		this.offset = -1;
		this.queryList = null;
		this.countTotalSize = true;
	}

	/**
	 * 获取页大小
	 *
	 * @return int
	 */
	public int getPageSize() {
		return pageSize < 0 ? 0 : pageSize;
	}

	/**
	 * 设置页大小
	 *
	 * @param pageSize
	 *            int
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.offset = this.pageSize * (this.pageNo - 1);
	}

	/**
	 * 获取当前页号
	 *
	 * @return int
	 */
	public int getPageNo() {
		return pageNo < 0 ? 0 : pageNo;
	}

	/**
	 * 设置当前页号
	 *
	 * @param pageNo
	 *            页号
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		this.offset = this.pageSize * (this.pageNo - 1);
	}

	/**
	 * 查询结果的起始位置
	 *
	 * @return int
	 */
	public int getOffset() {
		return offset < 0 ? 0 : offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * 总页数
	 *
	 * @return int
	 */
	public int getPageCount() {
		if (pageSize <= 0) {
			this.pageCount = 0;
		} else {
			this.pageCount = (recordCount - 1) / pageSize + 1;
		}
		return pageCount;
	}

	/**
	 * 总记录数
	 *
	 * @return int
	 */
	public int getRecordCount() {
		return recordCount < 0 ? 0 : recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * 查询结果列表
	 *
	 * @return List
	 */
	public List<?> getQueryList() {
		return queryList;
	}

	public void setQueryList(List<?> queryList) {
		this.queryList = queryList;
	}

	/**
	 * 是否要统计总记录数
	 *
	 * @return boolean
	 */
	public boolean isCountTotalSize() {
		return countTotalSize;
	}

	public void setCountTotalSize(boolean countTotalSize) {
		this.countTotalSize = countTotalSize;
	}

	/**
	 * 取得JQGrid查询结果
	 * 
	 * @return
	 */
	public String getJQGridJsonString() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", getPageNo());
		map.put("total", getPageCount());
		map.put("records", getRecordCount());
		map.put("rows", getQueryList());
		return JSON.toJSONString(map);
	}

}

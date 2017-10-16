package cc.cnfc.controller.wx;

import java.util.List;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMaterialNews.WxMpMaterialNewsArticle;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.cnfc.controller.pub.ImageUtilController;
import cc.cnfc.core.orm.Page;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.wx.WxService;

@Controller
@RequestMapping(value = "/wxMaterial")
public class WxMaterialController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxService wxSvc;
	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private ImageUtilController ImageUtilCtl;

	@RequestMapping(value = "/toWxNewsMaterialList.do")
	private ModelAndView toWxNewsMaterialList(Integer pageNo) throws WxErrorException {

		pageNo = (pageNo == null ? 1 : pageNo);

		ModelAndView mv = new ModelAndView();
		Customer customer = customerSvc.findLoginCustomer();

		WxMpMaterialNewsBatchGetResult result = wxSvc.getWxMpService(customer.getmSysId()).materialNewsBatchGet(
				(pageNo - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		List<WxMaterialNewsBatchGetNewsItem> itemLs = result.getItems();

		// 保存图片到本地
		for (WxMaterialNewsBatchGetNewsItem item : itemLs) {
			for (WxMpMaterialNewsArticle article : item.getContent().getArticles()) {
				ImageUtilCtl.saveByUrl(article.getThumbUrl(), article.getThumbMediaId());
			}
		}

		Page page = new Page(pageNo, Const.PAGE_SIZE, result.getTotalCount());

		mv.addObject("itemLs", itemLs);
		mv.addObject("page", page);
		mv.setViewName("wx/material/news-material/wrap_wx_news_material");
		return mv;
	}

	@RequestMapping(value = "/toWxImageMaterialList.do")
	public ModelAndView toWxImageMaterialList(Integer pageNo) throws Exception {
		String mediaType = WxConsts.MEDIA_IMAGE;
		String viewName = "wx/material/image-material/wrap_wx_image_material";
		return this.toWxNotNewsMaterialList(mediaType, pageNo, viewName);
	}

	@RequestMapping(value = "/toWxVoiceMaterialList.do")
	public ModelAndView toWxVoiceMaterialList(Integer pageNo) throws Exception {
		String mediaType = WxConsts.MEDIA_VOICE;
		String viewName = "wx/material/voice-material/wrap_wx_voice_material";
		return this.toWxNotNewsMaterialList(mediaType, pageNo, viewName);
	}

	@RequestMapping(value = "/toWxVideoMaterialList.do")
	public ModelAndView toWxVideoMaterialList(Integer pageNo) throws Exception {
		String mediaType = WxConsts.MEDIA_VIDEO;
		String viewName = "wx/material/video-material/wrap_wx_video_material";
		return this.toWxNotNewsMaterialList(mediaType, pageNo, viewName);
	}

	private ModelAndView toWxNotNewsMaterialList(String mediaType, Integer pageNo, String viewName) throws Exception {

		pageNo = (pageNo == null ? 1 : pageNo);

		ModelAndView mv = new ModelAndView();
		Customer customer = customerSvc.findLoginCustomer();

		WxMpMaterialFileBatchGetResult result = wxSvc.getWxMpService(customer.getmSysId()).materialFileBatchGet(
				mediaType, (pageNo - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		List<WxMaterialFileBatchGetNewsItem> itemLs = result.getItems();

		// 图片类型的话，需要要图片保存在本地以便显示在页面上
		if (WxConsts.MATERIAL_IMAGE.equals(mediaType)) {
			for (WxMaterialFileBatchGetNewsItem item : itemLs) {
				ImageUtilCtl.saveByUrl(item.getUrl(), item.getMediaId());
			}
		}

		Page page = new Page(pageNo, Const.PAGE_SIZE, result.getTotalCount());

		mv.addObject("itemLs", itemLs);
		mv.addObject("page", page);
		mv.setViewName(viewName);
		return mv;
	}

}

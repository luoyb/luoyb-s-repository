package cc.cnfc.controller.wx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.dto.MqDto;
import cc.cnfc.dto.Result;
import cc.cnfc.dto.wx.WxNewsDto;
import cc.cnfc.dto.wx.WxTextDto;
import cc.cnfc.entity.WxReplyMsg;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.MqMsgType;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MService;
import cc.cnfc.service.MqService;
import cc.cnfc.service.wx.WxMenuService;
import cc.cnfc.service.wx.WxReplyMsgService;
import cc.cnfc.service.wx.WxService;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;

/**
 * @author luoyb
 * @date Jun 8, 2016
 * @version $Revision$
 */
@WebService(serviceName = "wx4ws")
@Controller
@RequestMapping(value = "/wx4tp")
public class Wx4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxService wxSvc;
	@Autowired
	private WxMenuService wxMenuSvc;
	@Autowired
	private CustomerService customerInfoSvc;
	@Autowired
	private MService mSvc;
	@Autowired
	private MqService mqSvc;
	@Autowired
	private WxReplyMsgService wxReplyMsgSvc;

	@RequestMapping(value = "/xx.do")
	public void xx(String mSysId) throws WxErrorException {

		WxMpMaterialNews news = wxSvc.getWxMpService(mSysId).materialNewsInfo(
				"rVx2D_5Y6QY1WTUUqawCSzZrDQSwMGQZllYLbXMz_Hs");

		System.out.println(news.toJson());

		// WxMpMaterialFileBatchGetResult result = wxSvc.getWxMpService(mSysId).materialFileBatchGet("image", 0, 20);
		// List<WxMaterialFileBatchGetNewsItem> itemLs = result.getItems();
		// for (WxMaterialFileBatchGetNewsItem item : itemLs) {
		// System.out.println(item.toString());
		// }

		// WxMpMaterialNewsBatchGetResult result = wxSvc.getWxMpService(mSysId).materialNewsBatchGet(0, 20);
		// List<WxMaterialNewsBatchGetNewsItem> itemLs = result.getItems();
		// for (WxMaterialNewsBatchGetNewsItem item : itemLs) {
		// System.out.println(item.toString());
		// }

		// WxMpUserList userList = wxSvc.getWxMpService(mSysId).userList(null);
		// for (String openId : userList.getOpenIds()) {
		// System.out.println(openId);
		// }
		// List<WxMpGroup> ls = wxSvc.getWxMpService(mSysId).groupGet();
		// for (WxMpGroup group : ls) {
		// System.out.println(group.toJson());
		// }

		// String ss = "";
		// ss += "oEH_KwIvh6aZUvWWX_VEn7tsrnxs	";
		// ss += ",	oFk3osj1z-0ZJhJo7hIY515rK-F4	";
		// ss += ",	oFk3osriinTOlq9eQvT_1U7D26ro	";
		// ss += ",	oFk3osqOGpJmJ4JQ-C9ur0hDarq4	";
		// ss += ",	oFk3osr9i0S5OM-arKnFbc5HRqec	";
		// ss += ",	oFk3ospI5a0qVP86hYsKKjYKU6Fk	";
		// ss += ",	oFk3osgkXeGUyqdEJOyJD7poQtSs	";
		// ss += ",	oFk3ossqWdeBnshTDFbeCW8SIroo	";
		// ss += ",	oFk3oslpMrkDt0sWEAnmB_t3qLeE	";
		// ss += ",	oEH_KwHYisTuoK2wlbAWZ56kErD8	";
		// ss += ",	oEH_KwB7DKlGK4aF6jNn62Tt6gUE	";
		// ss += ",	oFk3osjnb57hwiVty2i70kGgxgRY	";
		// ss += ",	oEH_KwOm74Lx0VJWvocjbNBROtwQ	";
		// ss += ",	oFk3oskYh8_aqdox1T3sSA9I--zI	";
		// ss += ",	oFk3ossdAVn5gevA-fJKodRvsYWc	";
		// ss += ",	oEH_KwFOBeowSzHdM6bcIpLIQBmo	";
		// ss += ",	oEH_KwGSboFkhY2XwTBJQToAT0uE	";
		// ss += ",	oEH_KwIyKCt3KCzTqsRjWnFTMZ80	";
		// ss += ",	oFk3osts1kGmsC2jfTl4LCl9WHQk	";
		// ss += ",	oEH_KwFNLnQLLhhL0e5-5TbGcEjk	";
		// ss += ",	oFk3osmbcsl9Bmd3EwlhwW9JZvHQ	";
		// ss += ",	oEH_KwLUacuXc9S854YlPmqc6VYQ	";
		// ss += ",	oEH_KwI_p-uTQIHuk-tU_BVeQgaE	";
		// ss += ",	oFk3osrEcMEC6SuY5OhfmMnw7IHM	";
		// ss += ",	oEH_KwOD5yOYHDRtlxHeRfks6L8U	";
		// ss += ",	oFk3osqRbXqMfP1U21YALEMdpVVg	";
		// ss += ",	oFk3osmQ5hfc0gq5vQjTFOy9hkTg	";
		// ss += ",	oFk3osoQPvk_UQW1HxWc734Tv23Q	";
		// ss += ",	oFk3ossPtqvS9BA68hXYLon5hyt4	";
		// ss += ",	oEH_KwKO7ripm9xsOVnTHrwN5EhE	";
		// ss += ",	oEH_KwGEJ1yzeNWrJXwY2jyVTsgI	";
		// ss += ",	oEH_KwIZqxvlRos19ONnACaZdues	";
		// ss += ",	oEH_KwBa70ZkcEa4RIDGK4yoyulU	";
		// ss += ",	oEH_KwJsYPyfZ4KyJhZ8vSv18_1I	";
		// ss += ",	oFk3osqGkkXYd_fpx3wRrC1f4Asw	";
		// ss += ",	oFk3oshFRC0P83MzHMI5350RvN2w	";
		// ss += ",	oEH_KwJysHXofnSQ2XU78_VFcSnM	";
		// ss += ",	oEH_KwGQoo-0UenLEpZ_0QIoLAhk	";
		// ss += ",	oEH_KwNzVxagCPr8kPw-P_k5CrLI	";
		// ss += ",	oFk3osmx7P9a1M5eJUZHOVxTb9HE	";
		// ss += ",	oEH_KwH3kh5RrwkGbF8D0Gz3XbCw	";
		// ss += ",	oFk3ost-nZ-h20AkOvaOYE3DCVnk	";
		// ss += ",	oEH_KwEeMzupxks6prAuHQGHndTE	";
		// ss += ",	oEH_KwIR3mghFaSHVUziSF0j12Jk	";
		// ss += ",	oFk3ossaGkmn-cLgaUpiCz_ISd5Y	";
		// ss += ",	oFk3oshGOeH-0Jt1Mu_mUSrN7hUI	";
		// ss += ",	oEH_KwJrRx7_kuT6Alh81AMVhwk0	";
		// ss += ",	oEH_KwB2CAjaeIsLIicItTRRBW5A	";
		// ss += ",	oEH_KwK0D7G8OeLmhtFT-c-HK0J4	";
		// ss += ",	oEH_KwJszBZjigKIEkKBdhXm9pJ4	";
		// ss += ",	oFk3oslBBgrModLrgRUDkh_yFH_Y	";
		// ss += ",	oFk3osi-Lu26T6tq8o96tsR-IZpI	";
		// ss += ",	oFk3osi17KoI0AquiWC_4R_1TyEM	";
		// ss += ",	oFk3osnGAIXESezSV3H-jI7T7rVE	";
		// ss += ",	oFk3osvtAo_7nd1MFqY-7ajJRcDg	";
		// ss += ",	oFk3osgejmFHnZ5JcWLoBoK7x_1I	";
		// ss += ",	oFk3oshwmo1XChSyRVf7vAbVfMU4	";
		// ss += ",	oEH_KwA_iLICzk8PoDXqWL-P6Ut8	";
		// ss += ",	oFk3osk9Y44kpz6hKaTIRQLQKy6w	";
		// ss += ",	oFk3osm32uvptvBschL_ch2SwesA	";
		// ss += ",	oFk3osldLG1c5EckXvbNtlr2-R8A	";
		// ss += ",	oFk3oshhOQf4TylcxVWWbnK5WtCI	";
		// ss += ",	oFk3oshfcyFSIw6fPNvVY7tG8eq0	";
		// ss += ",	oFk3osj8LXEqHbuhxZb_CeSRPNj0	";
		// ss += ",	oEH_KwF3tejAY82jVvxs8sS9qep0	";
		// ss += ",	oFk3osph6RixAs5PPY3sbKTc1Bk8	";
		// ss += ",	oFk3osuUuYCPgwJFZxAOxJFDcL-k	";
		// ss += ",	oFk3oslSFT1QOYTwcnxPOq4JB3eA	";
		// ss += ",	oFk3oslIVt--ZSORH8kHf0BjPp2E	";
		// ss += ",	oFk3oshtE3t8zTzp_IN3b-6QQm-U	";
		// ss += ",	oFk3oskFx4AY1CQH4fouKedfO-CE	";
		// ss += ",	oFk3osq9JXTo6UI5f12J0Qq8FkO8	";
		// ss += ",	oFk3osg2AwCXb0FtYra_4RNo1URs	";
		// ss += ",	oFk3osiZBEs3d_712qRKZkHTCxrg	";
		// ss += ",	oFk3osj2SeERkKkP1sazTwrltb0c	";
		// ss += ",	oFk3osr67ElO1krr0sgTk23jR_kk	";
		// ss += ",	oEH_KwKgGhvER5bVfGm0Kyuht-lY	";
		// ss += ",	oFk3osmQDJXp0q7R0_eFe8iLo_hU	";
		// ss += ",	oFk3osvyuas-7oDmPaGaM-F6mRCg	";
		// ss += ",	oFk3oss-JM5cG1aaSNmtd1JSNGxc	";
		// ss += ",	oFk3oshCSh2RPOxRyM2aLPjZ2054	";
		// ss += ",	oFk3osvq8YG3PKOgO2AqOrGR1lBk	";
		// ss += ",	oFk3osqBgW_Cw03onuWCZ-2qvE3Y	";
		// ss += ",	oFk3ossCuRrvUtC1LnkaxFArOKMU	";
		// ss += ",	oFk3ossb9Sqc7A4XgYXa_7tDYaVI	";
		// ss += ",	oFk3osjWCHxlhIL2dEBGgfXKwvJ8	";
		// ss += ",	oFk3oslLaJo9LPVv7JFS8JelhObg	";
		// ss += ",	oFk3osrpxf-i4AjKEQNlFTmt7L98	";
		// ss += ",	oFk3osvCKAQviIvh4-4WSCEQwpmU	";
		// ss += ",	oFk3ossbu__rrnUVyMYekDM6e3Mo	";
		// ss += ",	oEH_KwI_4WckIwpbOvSHOasfrMQo	";
		// ss += ",	oFk3osv9uCX0-ZA23GI4QssFDHIk	";
		// ss += ",	oEH_KwJh93C2x-8m2Q9ZgBXRPJc8	";
		// ss += ",	oEH_KwEyQKECPZaaNxSyCeksNZVQ	";
		// ss += ",	oFk3ost0VID6qGEpj0lWSRTZhOrg	";
		// ss += ",	oFk3ost1DSuZp9Dv5kWX8OVQEUNA	";
		// ss += ",	oFk3ostDYMYp5DKDGeOZhzTuzHBM	";
		// ss += ",	oFk3osgT5_uNRlu4Bg4EQFcCFCfo	";
		// ss += ",	oFk3osnVE4xizChdEgSnQbX33NoQ	";
		// ss += ",	oFk3osuJniDR4pwL8r_wpasKYN54	";
		// ss += ",	oFk3osqfc4ZATSXBomX-Svcr0ds8	";
		// ss += ",	oEH_KwF46NWtZtB0-OLrcH3FIgOY	";
		// ss += ",	oEH_KwDiqr3YZabe86foMxhTiHeQ	";
		// ss += ",	oEH_KwKtnSDYhXj5fqQxhSCJx-yE	";
		// ss += ",	oFk3osj_fdRkcne9Yvs5mmTlsG34	";
		// ss += ",	oEH_KwBTZLUn6QEWoPlYjDaEHzkw	";
		// ss += ",	oFk3ostMlEM6c1dmapoz9ffSShBA	";
		// ss += ",	oEH_KwDKtpEsHDlaz4kLPdvRAF_w	";
		// ss += ",	oEH_KwHMC96f7-Y8GhT92NLFo5M0	";
		// ss += ",	oEH_KwGAENoDQZIZK3fRXKXHdLek	";
		// ss += ",	oFk3ost7ICXabkcg90EXTlKiT9aw	";
		// ss += ",	oFk3osm-LhtZS-Gwpsbrp42lUIIM	";
		// ss += ",	oEH_KwHQcnfStZQQwm_52K-s3R38	";
		// ss += ",	oFk3osieufr7WdBg3WjIzm1U0xhU	";
		// ss += ",	oFk3oslTbJsIfY82ZCm4XaKy4vAI	";
		// ss += ",	oEH_KwBf2SsTLXy8xf4BEv4lZSxg	";
		// ss += ",	oFk3osj2dJkpYQPRqBimrVsKeVzY	";
		// ss += ",	oFk3ost4sGRHmJVknkZmqIZX7bgs	";
		// ss += ",	oFk3ospsP2_INi4pP3R1XZaUQO-M	";
		// ss += ",	oEH_KwLe4BS2ZRhlEAB89qJJZX9w	";
		// ss += ",	oEH_KwH3CyFU9SR_sZcmnDnz4tww	";
		// ss += ",	oEH_KwGRYM4FWs9KfC-1S3sPDjiE	";
		// ss += ",	oEH_KwK413YYQ03UBOV062WRAxc8	";
		// ss += ",	oEH_KwFB5dm_XH1KhNZ2Wx38tBTY	";
		// ss += ",	oEH_KwBOo_vT5efS2bJCTb-Y3BRU	";
		// ss += ",	oEH_KwPyKYGPD7jan-2s_5CnN69M	";
		// ss += ",	oEH_KwPMHzbC9ghJDnhibeXN6_Ew	";
		// ss += ",	oEH_KwKq6K9TFp1_CpwR28zS1BU0	";
		// ss += ",	oEH_KwNG5eXpi7I1dOcvd9iEpHw0	";
		// ss += ",	oEH_KwJBLNInIFDb84REdD0jnj68	";
		// ss += ",	oFk3osgjo0SkuP9LCDIv9_Bnyxa8	";
		// ss += ",	oEH_KwEEP2G48bvaplGHY5tk1RRY	";
		// ss += ",	oEH_KwHoHqjc2d1II8pCNQw_Vq0E	";
		// ss += ",	oEH_KwJ8io8LCsWqFNLJ-iRvgF00	";
		// ss += ",	oEH_KwBNl7Sy2LAmH2hg6BhbNDYM	";
		// ss += ",	oFk3osjL0fJfr1AE5EPT9lPLaBBI	";
		// ss += ",	oFk3ospTf_eIaVZ5BU-gLOk1DLEU	";
		// ss += ",	oEH_KwAG5FRvqq8bYZE51QDoE-mI	";
		// ss += ",	oEH_KwLtf_BilORca7_5N8Gkz8hA	";
		// ss += ",	oEH_KwKvgsQtmHqaLEH-vYcc4oYA	";
		// ss += ",	oEH_KwPfR9N-JlV82qaeyQt0B9jg	";
		// ss += ",	oFk3ospYuZMaJ9Q1w3UCNf4xSDGY	";
		// ss += ",	oFk3ossG4LukM1vHPw0Y0vW46ao0	";
		// ss += ",	oFk3osnO-tXpcmrjtUFAlHG-pMac	";
		// ss += ",	oEH_KwJWpez51zKnPqaz2-S2AaHE	";
		// ss += ",	oEH_KwHbQlOngjZOSNsfph3lDGC8	";
		// ss += ",	oEH_KwMj_YIUUtR6f8lwKggWZL-8	";
		// ss += ",	oEH_KwHR87hHZjRP-4SN2ZRzqxMM	";
		// ss += ",	oFk3osqyTb6jwR-CrWzEZBFyk3xQ	";
		// ss += ",	oEH_KwMRRHuX53LCosKvqExNLv5A	";
		// ss += ",	oEH_KwEjhIw_jJqhnfVtHlwyUTvg	";
		// ss += ",	oFk3oslZfCaQgx8FynitPoxzQgw4	";
		// ss += ",	oFk3osq3qxjIAJBpz2K_FuN8W0SE	";
		// ss += ",	oEH_KwCwUqAa4FW5J_SIXootRT5s	";
		// ss += ",	oEH_KwI8I4Tp-sNEwsiFxNKEDzw0	";
		// ss += ",	oFk3oss3KHUVOOVN08JCEfClUMM4	";
		// ss += ",	oFk3oslTLvCuOO8Vb0S-WZBcmSKM	";
		// ss += ",	oEH_KwH3jj-9e25twqNi4Ml4Uc-c	";
		// ss += ",	oEH_KwNkZ8vvDYOOxnzivYobB9Gk	";
		// ss += ",	oEH_KwCntbc_9GTk95FJ6bWPxGyU	";
		// ss += ",	oEH_KwA20rcJqqEaRMkdxdTel9fY	";
		// ss += ",	oFk3osnD7IW5wokzF9wTWmUEViXg	";
		// ss += ",	oFk3oso8eCRaVxeqDnsRL0sQ5On4	";
		// ss += ",	oEH_KwMWDMcIItSp-ZRNEZ7wZAhg	";
		// ss += ",	oFk3osuLJEf30xabM20TPu7ZP778	";
		// ss += ",	oFk3ossMORHd7OUZCTMvyXj3Tars	";
		// ss += ",	oFk3osrsTV95IgbLsUP0mFK6Uq_Y	";
		// ss += ",	oEH_KwIK5lt6XT2p9ODBXL2W4TQ4	";
		// ss += ",	oEH_KwI0QaKW1YZinEfU178N18NQ	";
		// ss += ",	oEH_KwBujAaJIk2JtyqZfh7wAxyA	";
		// ss += ",	oEH_KwCzbie97quaguCMHMOv9j2M	";
		// ss += ",	oEH_KwNQvvJImtF9OXrXJJDBSU_U	";
		// ss += ",	oEH_KwHCdNQGel-Sa3ltK-HptJ1s	";
		// ss += ",	oEH_KwJyBfymQ7JNAx4oobN2aOg8	";
		// ss += ",	oEH_KwK6G9uYF6eGCSo4EN5MhpNY	";
		// ss += ",	oFk3osi7ASZEFALwnW8L8K4j8T9E	";
		// ss += ",	oEH_KwKcqQSA55J2tKv0rBiB29jY	";
		// ss += ",	oFk3osn9atzAVJXa9ogCkY2jIEpM	";
		// ss += ",	oEH_KwOWHUzrlUWrCUHoyEDro36I	";
		// ss += ",	oEH_KwNQNL39GFsY8OAJzjep0-pU	";
		// ss += ",	oEH_KwFlzzC1XsMl1fjcx9cm9PQ0	";
		// ss += ",	oEH_KwFtDgN4iNbx2kT6A4evONL4	";
		// ss += ",	oEH_KwLFksJ9Ug57rKg2kcFGS9LY	";
		// ss += ",	oEH_KwGtwgVKp7M2OEySw3MHnxd0	";
		// ss += ",	oEH_KwJ2cFKUwqfv2fwH9MdS2nJU	";
		// ss += ",	oEH_KwNQ1iPT0Xlya23b_Ry5ZiVU	";
		// ss += ",	oEH_KwJbkmIUZBqdA64EWRyrp_EU	";
		// ss += ",	oEH_KwFvoMIxfweUeDYKowRPWAQ0	";
		// ss += ",	oEH_KwBYCmDH6ZE0ER9lsxJh3w5o	";
		// ss += ",	oEH_KwBIwOzdjLgiflhDOof3yCMk	";
		// ss += ",	oFk3oshs5s5vu7MLd9PxYK5YoG8c	";
		// ss += ",	oEH_KwEyt_TlnwsX60rAFuhp6MGs	";
		// ss += ",	oEH_KwOoFTjl9d_7de7ZgeiVrV8o	";
		// ss += ",	oFk3osgHBaIUms7rz4D8IUmoItxY	";
		// ss += ",	oEH_KwLHi0j8GHaGNnQ41wsP-Dts	";
		// ss += ",	oEH_KwDJXT-pM_Ndh3V0-sFJeojY	";
		// ss += ",	oEH_KwFCsSXAmzDc6xjg9mvcZ4d4	";
		// ss += ",	oFk3ospKLqEu6ofU1_0EReoonMkA	";
		// ss += ",	oFk3ostNlq1siD0JNBN2HrdnrjVQ	";
		// ss += ",	oEH_KwMXUMIe7YIND713LRSirvRU	";
		// ss += ",	oEH_KwJkTvYJKmh3-92XissN7fI4	";
		// ss += ",	oFk3osi3A5rwg9u1efwbFmTp9VEY	";
		// ss += ",	oEH_KwA3V3cL9Da_ohYnGv96MHy8	";
		// ss += ",	oEH_KwLZrMvQg273RqoF1XPc5KFs	";
		// ss += ",	oEH_KwD2sAd1-Wly_LnjxvsnCJ_k	";
		// ss += ",	oFk3osjArbca4uwquIFtjjb_AMIk	";
		// ss += ",	oFk3ossR5N8gD8ukO3gUIOqziBng	";
		// ss += ",	oEH_KwCfYZzt25GMnr8yNESQUoDs	";
		// ss += ",	oEH_KwFrLt73bpfpODrQ12IVmydM	";
		// ss += ",	oEH_KwE39-Hn5izJlBAGMKs_hnfY	";
		// ss += ",	oEH_KwOIIqodDEJu-mxmQeKHTI8Q	";
		// ss += ",	oEH_KwN1zjhyrQuYDmJOwpwLsp3U	";
		// ss += ",	oEH_KwGqJqP9YdaaAkAJ6N_oxUTg	";
		// ss += ",	oEH_KwNtCwLGSaFdDOznU8LADsmI	";
		// ss += ",	oEH_KwABWzkdNAcoV79fD2DpVkio	";
		// ss += ",	oEH_KwIrZVik8ZtR7965yY3gxOdQ	";
		// ss += ",	oEH_KwHN4de2ZTwMZB_SWo9w51FE	";
		// ss += ",	oEH_KwFRIFnAwXydlMoVN6l-D_Qs	";
		// ss += ",	oEH_KwKyIu6S7k2JR5I5RZnG1x9U	";
		// ss += ",	oEH_KwLZHiXgCOJ5k7Pf4uq2d2xw	";
		// ss += ",	oFk3oshMuAlR9lL3Ol90xhhJqdrY	";
		// ss += ",	oEH_KwJwujdkcVfpvAVrduPKvoeY	";
		// ss += ",	oFk3osv0fUHpg6TPiTkSZvtaY_c4	";
		// ss += ",	oEH_KwIK5lt6XT2p9ODBXL2W4TQ4	";
		// ss += ",	oFk3osrfRRziN2OXl9nM9ZoQC9Mk	";
		// ss += ",	oFk3osj-G8jBmyDREgom2kvnOIFs	";
		// ss += ",	oFk3ospZa5Sv_nfuH1as-ysjssq8	";
		// ss += ",	oFk3osts4ztqG9fq-66MNLFX5rB4	";
		// ss += ",	oFk3oslE78lgR0z7PqIhhe66aXmQ	";
		// ss += ",	oFk3osve7W-IR7-flAGU5Cvn6JGI	";
		// ss += ",	oFk3ostDXgP-_2ZKtXRXke6v38Fk	";
		// ss += ",	oFk3osqyH82fFHkXHTZq8ptNoboE	";
		// ss += ",	oEH_KwBResIpvNqrj7QHdTeNYorA	";
		// ss += ",	oEH_KwPgn0U_88Gw23VvSSynVfXg	";
		// ss += ",	oEH_KwMdI1YVrBAAi4Gx9wgz_dw8	";
		// ss += ",	oEH_KwEf7sBbpboSgeLkhqv_yK2E	";
		// ss += ",	oFk3ostY7La61i9JFW6TuD3lRi9g	";
		// ss += ",	oFk3osppbttCA6PVuPlDV63s2vfc	";
		// ss += ",	oFk3osu3z6r18TEk5USSPgxo-ybs	";
		// ss += ",	oEH_KwEtw1MkO2CXUjfUoQWGeVLA	";
		// ss += ",	oFk3oshy3FsSTEi2roUx7OSgyjKo	";
		// ss += ",	oEH_KwCEd1XDentD72AvRWgaKdB8	";
		// ss += ",	oFk3osugxTjbxeARrCYCtMdT1tiE	";
		// ss += ",	oEH_KwJmyfI5PFeMkMDV1CEXuBOw	";
		// ss += ",	oEH_KwDeSmDtzXVEoiVsQmUmdyA4	";
		// ss += ",	oFk3oso6Y0tQQE2uZ6c1JP6s6GB4	";
		// ss += ",	oEH_KwIgmGpDh4by9YUmeOE7RshQ	";
		// ss += ",	oEH_KwGGvcCmZuV_xsVJ35jdNyeM	";
		// ss += ",	oFk3osrf7svpFdlmwdBcoX094QsI	";
		// ss += ",	oFk3osrf7svpFdlmwdBcoX094QsI	";
		// ss += ",	oFk3osrf7svpFdlmwdBcoX094QsI	";
		// ss += ",	oEH_KwFXou23vUhlDXEXJVASoI7I	";
		// ss += ",	oFk3osm1LbCyNh5xaeE1EOGbD1Ck	";
		// ss += ",	oEH_KwP23dcpwvTIjfEMNb_hvGP8	";
		// ss += ",	oEH_KwM6aboYSrBUOTqm8P4AulVI	";
		// ss += ",	oEH_KwLC4Lwa_FHJNo_4MRpZhqso	";
		// ss += ",	oFk3osim6r4Bg0vQlUHHi_BHeIa0	";
		// ss += ",	oFk3osqna0Np9EyndRZxLfCHhbug	";
		// ss += ",	oFk3osszbZimTj0BnXPI7q86KnTI	";
		// ss += ",	oEH_KwCbUjYr3FicQbGI8UG4dNgk	";
		// ss += ",	oFk3osvduDH9Y01h5Jq5ztxuFp-s	";
		// ss += ",	oFk3osiA7RvbhXA8ru1-9MzcKDbI	";
		// ss += ",	oEH_KwIvfyBcpNAyB7fYrQRPVFls	";
		// ss += ",	oEH_KwCmmB61boD3CYwbeMVJvFDU	";
		// ss += ",	oFk3osp-L1bpJqf9iIVZ412ICX88	";
		// ss += ",	oFk3osl44z97L4bFJwVdYKMdVQ50	";
		// ss += ",	oEH_KwGBw8QapNkoGQ-ppYenI8KM	";
		// ss += ",	oEH_KwGE67EcNjH0CKwytFIS3LLk	";
		// ss += ",	oEH_KwLEEwYUPRTuLh5aYDPlpByw	";
		// ss += ",	oEH_KwOdlUlBq01RFmVJD6vwzC8I	";
		// ss += ",	oEH_KwErcueeYodKsBkmHkU2IwAA	";
		// ss += ",	oEH_KwCSLp4y2vjwP3OF4h7z2stI	";
		// ss += ",	oEH_KwGbVRAiOJXQO86qaXzf3i1Y	";
		// ss += ",	oEH_KwOWNnlYHvlJ7OuEWK9DU3wM	";
		// ss += ",	oEH_KwE6-WrrvK8ab5ukjXf1c02k	";
		// ss += ",	oFk3osmRCvWXDmeEGm3bhZqEeSC8	";
		// ss += ",	oFk3osuocG3MxMxWQYgVxJgSDBXE	";
		// ss += ",	oFk3osl4JuTU6CRPQEmbwmeWO89Q	";
		// ss += ",	oFk3osnCIKz-XABU9ZI9KpClZht0	";
		// ss += ",	oFk3osgkNueQx4rANVc1PvuN4gb0	";
		// ss += ",	oFk3osor3vij6bI_3bKi9wFN6p-I	";
		// ss += ",	oFk3osqlv63_ZveP7pRCdSVsF92M	";
		// ss += ",	oFk3ossnAtbwE5exwOsde5xDRAjI	";
		// ss += ",	oEH_KwKofx0DdLGNJnd9K2FbzFMU	";
		// ss += ",	oFk3oshcmgRL1NdYRjaV8cNxKfCc	";
		// ss += ",	oFk3oslCROV_unbg8iiQSLJH17O8	";
		// ss += ",	oEH_KwFyWV5H2ciUJ1Ze2fR84k7E	";
		// ss += ",	oFk3osocsnOA7UO_QLjRYnmjbfrg	";
		// ss += ",	oFk3ossjletTZ3wHHWDQXxtbFBlE	";
		// ss += ",	oEH_KwMefSnsg-aqgmzZMLTp-7rE	";
		// ss += ",	oEH_KwHT7YpHAr5p9cJpr-DPB7IU	";
		// ss += ",	oEH_KwMcqYImYfsXJrX1MQzXbsBk	";
		// ss += ",	oFk3ospZB4A4cNtwHF42QBgliqho	";
		// ss += ",	oFk3osiniq89JbiSJmOHrKDPljkk	";
		// ss += ",	oEH_KwJpU_nQ3HxefNXPegpKvnCI	";
		// ss += ",	oFk3ostCc1MYy63DbWJDqt_ff7aQ	";
		// ss += ",	oFk3osqc4jMYtWgsmAba1fzu3D_g	";
		// ss += ",	oFk3osjfmeh9T56Z2mMay1bypRg8	";
		// ss += ",	oEH_KwKBXKnkPxxwiFev2YBS1cXQ	";
		// ss += ",	oFk3oshy3FsSTEi2roUx7OSgyjKo	";
		// ss += ",	oFk3osrFrzl3fRxXDHySkH4bklYw	";
		// ss += ",	oFk3osi1-4KhJvC_xuQmIQ_Wz6Cs	";
		// ss += ",	oFk3osk9bA1lY5gKRn8LkDSPTsr0	";
		// ss += ",	oFk3ospAYOmG60QaymV7hHJWBH9g	";
		// ss += ",	oFk3osuxq-IQpWe6IB7Z3ME12Zgc	";
		// ss += ",	oEH_KwMgMA2CBMW3ulKBovrj80bE	";
		// ss += ",	oFk3ospCfJWugaX_G45T3gMwsA8Q	";
		// ss += ",	oFk3oslom2RZATSKyI8VtyQTT32w	";
		// ss += ",	oFk3ossTy63RwxiI0lC8RsU57Cdw	";
		// ss += ",	oEH_KwCupvp7sLYwghoZA-V70XWM	";
		// ss += ",	oEH_KwNWOvSXt_vpaDg3PIZ5Z0bQ	";
		// ss += ",	oEH_KwPCBUjBy2LCZ9IL5T0afrxs	";
		// ss += ",	oFk3osmISEZZCbBKLrm63u_X_dmM	";
		// ss += ",	oFk3osoDrMNB1A_hNputjCQAUlIg	";
		// ss += ",	oFk3ossISHg6U3by3s35oFLwozpc	";
		// ss += ",	oFk3osknVDhZoMEDG1lk-agJDsSc	";
		// ss += ",	oFk3oshFQ-MGL4q0mXa_deaTSAGE	";
		// ss += ",	oFk3osmHRolAObJ6W4WO460OHoK8	";
		// ss += ",	oFk3oslRW8ZVi_TbF7GNgcHlgH0Q	";
		// ss += ",	oFk3osk3aGHdYjiL-_y-P5v5dY9A	";
		// ss += ",	oFk3oslkMhnRbs1c82QZLVwb9Hwg	";
		// ss += ",	oEH_KwPzanffPJrSNZF63SAxQ4Do	";
		// ss += ",	oFk3ospHT-nTdc5BW9gQFmZo5Yi8	";
		// ss += ",	oFk3osjgKdZI2RAaz1YU-58vM63U	";
		// ss += ",	oFk3osn1MQez3g4usq1vnyeToAG8	";
		// ss += ",	oEH_KwFFLyNf1DzjnliXMoxm199Y	";
		// ss += ",	oFk3osvjDy2MUXfYt5_3-tBgc67o	";
		// ss += ",	oEH_KwHBn99J2I91uxRPVPlnfmrM	";
		// ss += ",	oFk3osp2w7Wl5gkqrsDkLjJMt8sY	";
		// ss += ",	oEH_KwIoC28jUOGMlLNi2bYmvUrk	";
		// ss += ",	oEH_KwG3fL8otNfEdoQ3ltz4cN5Y	";
		// ss += ",	oFk3osic_Zn7DpCtXp8S-x7r1Zt4	";
		// ss += ",	oEH_KwNz9gMwRZIgTrplTJs8zepk	";
		// ss += ",	oEH_KwKAtAigY6zb83cX8WV8MyKU	";
		// ss += ",	oFk3ostsnQpDmHIYg9LsBDGubOU8	";
		// ss += ",	oEH_KwFbpyttrJjypg_olDs_0pck	";
		// ss += ",	oEH_KwFNjK1QVdZYX3BbsradhByw	";
		// ss += ",	oFk3osoFCGfr4_u3t7Pd9szRJA5I	";
		// ss += ",	oFk3osgNtXg_-4v_Ucw-tDbwhMuM	";
		// ss += ",	oEH_KwIdeZAPZSK9aoy0beKFZUMA	";
		// ss += ",	oFk3osmkAyFmmPx9_5ryFzZzIk5c	";
		// ss += ",	oFk3osvzOjQmzey1SQqskq3hmr8E	";
		// ss += ",	oEH_KwPdiugg57_qzGXVMxHCQbmA	";
		// ss += ",	oFk3osrKn_bw2o05h22vJlIcdj38	";
		// ss += ",	oEH_KwIVPMSBLrVnY7aE7nVLwh3k	";
		// ss += ",	oFk3ostnL8Y03F5aGKi4mALn2ZD8	";
		// ss += ",	oEH_KwO7mEzEjTds-1SdTyCJagJo	";
		// ss += ",	oEH_KwPxR7k0T6HIUUN7LftenFkM	";
		// ss += ",	oFk3osgqwsca31-VgHU0_ReekBaw	";
		// ss += ",	oEH_KwKK27NjQmPapKqNIB5NRgwI	";
		// ss += ",	oEH_KwNNnNJYNAeQpVrmHPlvNJXI	";
		// ss += ",	oEH_KwNNnNJYNAeQpVrmHPlvNJXI	";
		// ss += ",	oEH_KwBJuEldjPyYYl7LnT57cqRI	";
		// ss += ",	oFk3osl85B5hyuZo5BlcVp1LgfCo	";
		// ss += ",	oFk3osn1Wn9pnuDw9g5jFtJgIuoI	";
		// ss += ",	oFk3osr4uV40sg6acaX0gqsxHG2Q	";
		// ss += ",	oEH_KwMXjxGA5tPdayuXb7-lDhkc	";
		// ss += ",	oFk3ost6U9oBH6W7_wOvsScuDgZw	";
		// ss += ",	oFk3oshDPAYZPnwVrvYVrIOrcaQg	";
		// ss += ",	oEH_KwLnq7LclmH0Ak-i5lY_erYQ	";
		// ss += ",	oFk3ospPzGo4bKz4vfy-tg-om7Ec	";
		// ss += ",	oFk3osm_mBM3glgmsS9SDWXhm7XE	";
		// ss += ",	oFk3osg2dnc1aOSd780VnMlNAoyM	";
		// ss += ",	oFk3osqFq1LundaOn8nhxOKfY7Zs	";
		// ss += ",	oEH_KwKvt96R0UP-9ypt__tSU6As	";
		// ss += ",	oFk3osn-kFoha0ONlAbVsrcx_VQI	";
		// ss += ",	oEH_KwAQbSLaaueoKNrcfSOVJcK8	";
		// ss += ",	oFk3osn_F97U3oaTWH8d5mTdkp5Y	";
		// ss += ",	oEH_KwKIu1Q9SolY5ZWHKjkUfkfc	";
		// ss += ",	oFk3ospi7QwKborOXHNElvggnIJo	";
		// ss += ",	oFk3osllGtm6PZXDG6KAAz5Hg59c	";
		// ss += ",	oEH_KwLkbzfCW_vy4-54HY_96wOY	";
		// ss += ",	oEH_KwPZU9zV4lkHCUFD_bC00G0k	";
		// ss += ",	oEH_KwCLzug3lIvTFjw5NfElk0vs	";
		// ss += ",	oEH_KwPipJZ7hW0yeX-ufgP7MzeY	";
		// ss += ",	oFk3oss6E9bNrmAXTUKI0pYYlozw	";
		// ss += ",	oFk3osoHxnSNHHlbnR-KwZyx-pQg	";
		// ss += ",	oFk3osmBPovtDJhcrWJYd9Pb1JF8	";
		// ss += ",	oFk3osjL4nEvqyps6QWRCn-H6Nbo	";
		// ss += ",	oFk3osijYSHbxtX88db-Pb_pQgcM	";
		// ss += ",	oFk3osmwavbNL6fxZzDgjlFBO-xY	";
		// ss += ",	oFk3osstHIHEnI21GxJ-6W5EfP4I	";
		// ss += ",	oFk3osunLWWaqpEBfisb_mUA94r0	";
		// ss += ",	oFk3osjgrhPLRUnNKA5d8dZZ5rMs	";
		// ss += ",	oFk3ostTKAxjp6QYZdmwp4ssWZzc	";
		// ss += ",	oFk3osshFTCVipXNch2YDVxQZ7X0	";
		// ss += ",	oFk3oslOAunqkvuUCtkoAzkyIGeA	";
		// ss += ",	oFk3ospYjX4VelTQBycurJPwrcmw	";
		// ss += ",	oFk3osiAOJtQUjsyBgDhN7OY2_JA	";
		// ss += ",	oFk3osogVzLGc6ZuUHVIZnR8vELE	";
		// ss += ",	oFk3oshmgZ2r84lnj_gxzhvORQ_k	";
		// ss += ",	oEH_KwNmn-nbIfBjPScBsiPj8B_s	";
		// ss += ",	oFk3ostK7vhaygnl5RpEIKdynsDw	";
		// ss += ",	oEH_KwFFOb6f6Zpi1Ob5TxV_g4EU	";
		// ss += ",	oEH_KwJDJ9FG_Z4u7tgv49O29nwU	";
		// ss += ",	oFk3osvtmePGk6Gj8k3BJR-Ji7T4	";
		// ss += ",	oEH_KwHPZyWthVm5t3ddAcqA4SvY	";
		// ss += ",	oFk3osrRrUIfyVnEmxEbjzeNbRe8	";
		// ss += ",	oFk3osj34GDZI1PTuoAar2pkAWa8	";
		// ss += ",	oEH_KwHd6L5RQGt2ujkKucNS8hC0	";
		// ss += ",	oEH_KwJJ5wwIPxRNaZ1Lw8BX4jeI	";
		// ss += ",	oFk3osnzWdrpEXP8MqLN4GBl7WKk	";
		// ss += ",	oFk3osgwdPOKszeef3hkb0HFeQj4	";
		// ss += ",	oFk3oslm1ccN1ZMTu1UKOeHI8P9g	";
		// ss += ",	oEH_KwFrKavRLk__-sCC624-fP48	";
		// ss += ",	oFk3osnggxIxlkacfS68-KwUW6qA	";
		// ss += ",	oEH_KwCX6EtozBhqJ3BU23UMhEcQ	";
		// ss += ",	oFk3osghKnA0IwmqQP0M_6zixwa8	";
		// ss += ",	oFk3ospZxLicjqW05KhqJauY63Pc	";
		// ss += ",	oFk3osgqRs2MJo42aqVDrMfPQgAw	";
		// ss += ",	oFk3osubRI75NmDVQ_75Ga2DlmRs	";
		// ss += ",	oEH_KwGa1chIjUpDH-kYIstMhDqc	";
		// ss += ",	oFk3osvMCf6_TpRt__0otx_Zp-lw	";
		// ss += ",	oFk3osujk-5VRRmTuZ8e1xRXXb58	";
		// ss += ",	oEH_KwK8tO8_-THbvnmTLYJ9pZU0	";
		// ss += ",	oFk3osgEkWg3M53ktMP88C-32mIo	";
		// ss += ",	oFk3osim6lP3Z6q9CepJgf3zeO9Q	";
		// ss += ",	oFk3ossvtW55LjX96FgIfwYqL6-w	";
		// ss += ",	oFk3oskeAWE_uyNUPFW7IysLcHZY	";
		// ss += ",	oEH_KwFduWy7QO64l704cyfBEEFM	";
		// ss += ",	oFk3osmFiRvHPRSouQgccj49dDxc	";
		// ss += ",	oFk3osn4SQi-dhlQLLHvxOIQkNAg	";
		// ss += ",	oEH_KwFBGku3aieBHBG7dEcHTH7M	";
		// ss += ",	oEH_KwMJTX6k7HO44Xk9EPLBrF1w	";
		// ss += ",	oEH_KwA1jIpJ-YVfQHP4YRZ4rbDE	";
		// ss += ",	oEH_KwG5TRxHGmExdaMrW7yLUzPw	";
		// ss += ",	oEH_KwF2kl1CR6RNGiwG9gVH-vBs	";
		// ss += ",	oFk3osndVdoie7yfrAXexnNMA-wo	";
		// ss += ",	oEH_KwLhOHFLdL_GWp1VeQNMRIYQ	";
		// ss += ",	oEH_KwIq6pv8B7ARE5wy1oDDgJ2U	";
		// ss += ",	oEH_KwFyukKozxmZqTBfWkbGDEas	";
		// ss += ",	oEH_KwL6p-ExMpHQNYQ1oeFTx2eI	";
		// ss += ",	oFk3osiCVfK3nOjW9uviqKGBzzhQ	";
		// ss += ",	oEH_KwBoi6aQwzG1rbMTJdqbTBqU	";
		// ss += ",	oEH_KwBr9fBMRQ05yG81kG8W86mk	";
		// ss += ",	oEH_KwA-MX4znsA0trrcQnEOsOVY	";
		// ss += ",	oEH_KwPFQCZmHXs215DfB4knbR0g	";
		// ss += ",	oEH_KwDPQNHi-L-4pkUdLPnHTAqM	";
		// ss += ",	oEH_KwIaW5v4L_POk5mGyiGrFeMQ	";
		// ss += ",	oEH_KwE1A4-t3Xo9HCRySHTkvOhU	";
		// ss += ",	oFk3oskwkyBdST-2QENWkvTeHa5o	";
		// ss += ",	oEH_KwO2QfuiYSFRrN327CIwKTqk	";
		// ss += ",	oEH_KwAkft5wSpXPqVS60AdZViTo	";
		// ss += ",	oEH_KwIGqp63Ddt0XXx_AmPBkmeg	";
		// ss += ",	oEH_KwPlfy5Vnt7vX8TONa9h_3LI	";
		// ss += ",	oEH_KwDvYsQnSTrYxZBLgE1aL1Bg	";
		// ss += ",	oEH_KwNNvjhRZM7zc8wrsWEPcXjg	";
		// ss += ",	oEH_KwBvh_cfngCpGPVqK7po93bs	";
		// ss += ",	oEH_KwEgqNN1HHpFQT8-dLyjB_9M	";
		// ss += ",	oEH_KwPZRYeAWJ-7y4bV7TOxszLM	";
		// ss += ",	oFk3osvN6OKL3k0bJhErYty7IHu8	";
		// ss += ",	oFk3ost5EMhbScTBbvv6IEvJmhtY	";
		// ss += ",	oEH_KwEarw3iKdJ5G94BMn1pq1aM	";
		// ss += ",	oFk3osoPGfxLP5mX__Uj2892T2wY	";
		// ss += ",	oEH_KwLLir-nhoR7zJkjvSwMOI7U	";
		// ss += ",	oFk3oslv4RJ633iSO7IJIrhxRsQ4	";
		// ss += ",	oEH_KwAq3Oju7haHq38D4XfU_bzc	";
		// ss += ",	oEH_KwCfMdM3772FmytG1tPL7OL8	";
		// ss += ",	oEH_KwMBacFZTSthxYUMjiaUelGs	";
		// ss += ",	oEH_KwC3LX84IDhpH8lPE2AZmMXs	";
		// ss += ",	oFk3osoQOeGEYGqNHpmr0_Ac6GsA	";
		// ss += ",	oEH_KwIVWXci4aeP7GBw1VsAtOLg	";
		// ss += ",	oEH_KwARVBSHKkx7x7ERt_iydrQE	";
		// ss += ",	oFk3osubKi4EmVNuTeK7b_HWHWI0	";
		// ss += ",	oEH_KwNPQX5azWEshLBr5sun6Ulo	";
		// ss += ",	oEH_KwH1nWpyoOqkGePZ08ZTlP7Y	";
		// ss += ",	oEH_KwMyjNTpU4QCfmvfIL9w1CHE	";
		// ss += ",	oFk3ospZsbZHTuZXqNZKARsfpOPY	";
		// ss += ",	oFk3osmtQVrWWFevAQ0s0m-dHP2M	";
		// ss += ",	oFk3oshwh1Wn7cfjq443owwF8z7Q	";
		// ss += ",	oFk3osgDuK5qvv32Qds271dET6Dg	";
		// ss += ",	oFk3osgEEulYKouYFunVGKgdmyq4	";
		// ss += ",	oEH_KwK_hGJIBPXu0IeilKFdAyeE	";
		// ss += ",	oEH_KwPKQO_UlptsqpzBQmXuBckg	";
		// ss += ",	oFk3ostbtrzde_ncrixQTAZ-3PbI	";
		// ss += ",	oEH_KwPAkzICZXfz7Us0w8Fd2D8k	";
		// ss += ",	oFk3osqf1UHitaeS-NZoMTxGp96g	";
		// ss += ",	oEH_KwErjPQ2UA00NzWJwHO4FCHY	";
		// ss += ",	oFk3oso2VHFeRhui4cEnJD-cuZNw	";
		// ss += ",	oFk3osqe7AGWrtu6j_xEceQzCHAo	";
		// ss += ",	oEH_KwPeD3rLqd-qPDjkdDkuYP8s	";
		// ss += ",	oEH_KwKsyPA_VPFcik2z50y6M4ZE	";
		// ss += ",	oFk3osp6VkaF6cwJkEr4lOy247yE	";
		// ss += ",	oEH_KwKEYxIYB_UkLoUT3mIe0HRc	";
		// ss += ",	oFk3osvqpF4XLdYXG04rnIhp3eLo	";
		// ss += ",	oEH_KwGIs8fSqsdT6j3NLnJgmy1g	";
		// ss += ",	oEH_KwOUZVOjMF1hWcN9pxLfXHVU	";
		// ss += ",	oEH_KwAyJ0YP6XNorZ24v3h0JgeE	";
		// ss += ",	oEH_KwGhRk28Fz3cxcFM_leuiSac	";
		// ss += ",	oEH_KwIvsSO3Q7-BpstGbG03MafA	";
		// ss += ",	oEH_KwMO6SUZ-MplrSR8u-eD4rXc	";
		// ss += ",	oEH_KwBZN3Xkl4sMeYnq9I5xiEUU	";
		// ss += ",	oEH_KwEH39ku71ly5CnwkfkEBZnA	";
		// ss += ",	oEH_KwH-CTS37hffx7Vjuh94yk2o	";
		// ss += ",	oFk3osptjqLRh03GA64wsujqslAw	";
		// ss += ",	oEH_KwFTJTQTk8iE2Sj7clDYRrPg	";
		// ss += ",	oEH_KwGrRbQGWBbue9z1JqrWySOw	";
		// ss += ",	oEH_KwB-RjnslymX83FfW3T0T-3M	";
		// ss += ",	oEH_KwEEbW5H5xWYbmwMvxg5Xs6w	";
		// ss += ",	oEH_KwB6FioyNKR5GzuYeiTpW1jM	";
		// ss += ",	oEH_KwFsv0n38tRrxynKGu8XihaM	";
		// ss += ",	oFk3osgVc7Jt71mr1njtQ-OK2TKs	";
		// ss += ",	oFk3osrK2mElF0WTlpD0st7GooLs	";
		// ss += ",	oFk3osq2Vuv1UTnLrVqxpaBVWjuo	";
		// ss += ",	oEH_KwO0Hinyu45CDqzoFH8phnrg	";
		// ss += ",	oFk3ostYlkOqtbAIO1NuEx7UmpME	";
		// ss += ",	oEH_KwGE0t-RRtne__B3vXKo5LgY	";
		// ss += ",	oEH_KwPAJL8HA0KmxiKKS3XBPZY0	";
		// ss += ",	oEH_KwKy8IE7m8A15h4eP94HXQRA	";
		// ss += ",	oEH_KwJAp-KnTdlbwR9GT9iCBZmI	";
		// ss += ",	oFk3osgvGdpAQa3Zp7R-2BYRNbtM	";
		// ss += ",	oEH_KwBgpmJwxwmjea7zYFY6BboE	";
		// ss += ",	oFk3osgkxXJAwDEGy2ujPQTdblqo	";
		// ss += ",	oEH_KwCGV1tDWg3jwlIv727rVZsU	";
		// ss += ",	oEH_KwFb2hQtwp5TTzvCdcg8Mf5E	";
		// ss += ",	oEH_KwNUjbKHKSluFZ3CiIQ2H5uw	";
		// ss += ",	oEH_KwIpqYTIQ_klParDUmPLA8pE	";
		// ss += ",	oFk3osm5rlqlsLCLBVMk4bkAbcUs	";
		// ss += ",	oEH_KwMx5v7HRivdr3CLaocv84VI	";
		// ss += ",	oEH_KwCLx3SBX_0T4Jyl4pJxeuls	";
		// ss += ",	oEH_KwOcP6yMm5bZ8RVuqhwTgWxQ	";
		// ss += ",	oEH_KwOhyTTkuSeX-6pwi_ABbVJA	";
		// ss += ",	oEH_KwLglGKRX5IFAEBomlppDzu0	";
		// ss += ",	oFk3osoUqjseoYnOFCHSRN8rD7D4	";
		// ss += ",	oFk3osjKCb8NRJWbzTFxlAXvxPIo	";
		// ss += ",	oFk3osuVO8eaSGD_wSzSPsl8xzNs	";
		// ss += ",	oFk3osuPayuDQbXqmIJcUucgYQ4M	";
		// ss += ",	oFk3osrym-GQ8Vvhf32641rfJK6Y	";
		// ss += ",	oFk3osoU_G2jphOWfw5-5bCdUdUc	";
		// ss += ",	oFk3oshIVB7MGL6AE5atRsJoseN4	";
		// ss += ",	oFk3osn605o_xXDnfYcB9pV5mJIw	";
		// ss += ",	oEH_KwIdPmi-OSQ8Qc6klKGOXeSY	";
		// ss += ",	oFk3osoe0ybpo9GH3kveN5gPSqlg	";
		// ss += ",	oEH_KwKZ1h738g9TAK8ILIeeYUjo	";
		// ss += ",	oEH_KwD4-8NPYYB9VFDRLp6P3lJU	";
		// ss += ",	oFk3osmFjHslwZBjDTR4BHFqSnMY	";
		// ss += ",	oFk3osn2tABhaYpb-12RSPg8hmjY	";
		// ss += ",	oEH_KwPtLbp_gysgc_34Tzd5H31I	";
		// ss += ",	oFk3osoyCliF9AL30Pg8DgLerID0	";
		// ss += ",	oEH_KwMTmDOZ7yihBu2dUsFzZ4rc	";
		// ss += ",	oEH_KwO3P1-S55fyjJH67PNbkt3o	";
		// ss += ",	oFk3oshhBc5AbqZtat86yhStZ9mI	";
		// ss += ",	oEH_KwJdp2xfBuifBIm3sCfgKbyg	";
		// ss += ",	oEH_KwE8g0m0wfT5Ju15dl92x9vs	";
		// ss += ",	oEH_KwGe4GvMyrA1Fod5UnT71J7U	";
		// ss += ",	oEH_KwA-kJhRcpT6nVBcc6jkB9Rg	";
		// ss += ",	oEH_KwI14C6KzzBkHpmrM3qjd-Oc	";
		// ss += ",	oEH_KwHHl6VruP3EO9l1qa_ZRvzE	";
		// ss += ",	oEH_KwIKmOojjejxPekrqbDdj93A	";
		// ss += ",	oEH_KwNNmBSIOWE-DPiYEyg2V1Q4	";
		// ss += ",	oEH_KwI65valJA0aLbbaX87Vs5Rw	";
		// ss += ",	oEH_KwN3PbaDuCVjqCnSswhxjyew	";
		// ss += ",	oEH_KwIOvmgxiPrnSDmqvvA2DaGI	";
		// ss += ",	oEH_KwOl8LMbAqUy_1s422rY4xHc	";
		// ss += ",	oEH_KwF_-WI4H9Wr7kHmcEW0t-IA	";
		// ss += ",	oEH_KwBP2u3gbbHbMF54VuEWrk8c	";
		// ss += ",	oEH_KwPJ7jS71r_PnH0n0hy_7-HA	";
		// ss += ",	oEH_KwKUOJfOMu5qcJmeQTz7lnBc	";
		// ss += ",	oEH_KwFONO59NNvHsJ97ZMWPKVBY	";
		// ss += ",	oEH_KwMXhrrPUWYDN8AivgSJHIzc	";
		// ss += ",	oFk3ospJmbhMe0LNbkPu7FR-39Zo	";
		// ss += ",	oEH_KwNmubfJVYb3aZNzCSYt-vQ4	";
		// ss += ",	oFk3ospnBf4ND0D2kuH-2Wgp0wOI	";
		// ss += ",	oEH_KwGbJgWQ1b1PVB73lpj26BUc	";
		// ss += ",	oEH_KwGwv3obXnZt3Q__ks4u3byA	";
		// ss += ",	oEH_KwBIQ7AupRtZQmlrNscL4BtQ	";
		// ss += ",	oEH_KwM2PO0cwLe9LHS0C7HKnnVc	";
		// ss += ",	oEH_KwBHolG410gpNTBtn8_J8k7U	";
		// ss += ",	oEH_KwPmY5a9hgYwdUCJ-ItfN_BY	";
		// ss += ",	oEH_KwI8Wwsr4tuq4jBa-DDNC098	";
		// ss += ",	oEH_KwE2jhhAoLvezxES5zzVxHsk	";
		// ss += ",	oEH_KwB1AMgGB3e78nTuvh_X0OVk	";
		// ss += ",	oEH_KwKvcF7RtaMUK6XYVGR9mSOE	";
		// ss += ",	oEH_KwLtGvzC8Wt-4gNKlkZAt9a8	";
		// ss += ",	oEH_KwLGPeJN1-7eYt8aLsKrgm9M	";
		// ss += ",	oEH_KwL_RKa5K1Hx_MV9FzJ03PuE	";
		// ss += ",	oEH_KwJPU1uP9wbOzJ_19DqobPUs	";
		// ss += ",	oEH_KwBgyxz8FxuC-z40q-z3BbTc	";
		// ss += ",	oEH_KwBqMRmgQEvrMfkogbazjBdk	";
		// ss += ",	oEH_KwMvlxwVCm_OR8YHIq-eQx5I	";
		// ss += ",	oEH_KwCrlFTYgo3B7MmFXxFz-FZg	";
		// ss += ",	oEH_KwL4I4TopKYLFnYj8TBXC4co	";
		// ss += ",	oEH_KwHxTq1ixkCqznEXzkhN_TZY	";
		// ss += ",	oEH_KwByHyB_0MhlJHqrckBgU40U	";
		// ss += ",	oEH_KwE3Vnoq3aySSnv3NBcqTSUU	";
		// ss += ",	oEH_KwCsvJqKrZa7Yk52p4Ev4uTY	";
		// ss += ",	oEH_KwJ_4ez0GEoFCIaI7oPgI-hU	";
		// ss += ",	oFk3oskksYYSBHhYNAtmUdA-jH0E	";
		// ss += ",	oEH_KwFIkv43hTscdV_LiGS7Bgto	";
		// ss += ",	oEH_KwO19tVgo18iK7nNwWi_cymQ	";
		// ss += ",	oFk3osjXCu-bA_VfJYkPw3n98Yds	";
		// ss += ",	oEH_KwAUMV8fAgHIyATHwqa11uLc	";
		// ss += ",	oEH_KwC6Xau_kmxhMoNkmkVxCftU	";
		// ss += ",	oEH_KwAESPYHw1qEkQ2Vd92umm04	";
		// ss += ",	oEH_KwBH-o2y3MZJxZLetMPXShIA	";
		// ss += ",	oEH_KwPAVt_I2IJx8khsrKCDNcPE	";
		// ss += ",	oEH_KwAlq4KnMahYA1h_1NLA-ivg	";
		// ss += ",	oEH_KwI6Pm9u2Lugr0jrc11BM7ao	";
		// ss += ",	oEH_KwDJLZxt1Ur7hv-fOjSN5GDk	";
		// ss += ",	oEH_KwDKqerSYDQvLIdt0pD4Rmx0	";
		// ss += ",	oEH_KwEMJz6bX40G53RjgWp0AMYI	";
		// ss += ",	oEH_KwIMpFljWPW8Ire-TGmEoErA	";
		// ss += ",	oEH_KwAGL6HX0rjvdSvEWWxyGz98	";
		// ss += ",	oEH_KwDR6OQBUPOzdimJ1mqIPrZU	";
		// ss += ",	oFk3ostwawrXFrgX6ojfNi6eMU44	";
		// ss += ",	oEH_KwM1Xrp5qxaWfYQl8uyieQL8	";
		// ss += ",	oEH_KwDlEwFEXJJt8GefvVQiJAug	";
		// ss += ",	oEH_KwPVCu1sKCZoCstIwbrlVyWI	";
		// ss += ",	oEH_KwB3DYiMYuLQW2_ceoIoPmHw	";
		// ss += ",	oEH_KwES716sa6ypBNVBGgzTVsOw	";
		// ss += ",	oEH_KwOCs7zcQ6V_CcSn8C2QpyTU	";
		// ss += ",	oEH_KwC7k3g6kKy33UM3qP-rXcE4	";
		// ss += ",	oEH_KwNYsA4UBlAoXeN_3dVoNuVY	";
		// ss += ",	oFk3oslztOUQ3ewmeILRSCi8UmQg	";
		// ss += ",	oEH_KwMuhku_8CJoa7Q510ZKgmOU	";
		// ss += ",	oEH_KwO0rgJMHf2j20iNJCmbqGIQ	";
		// ss += ",	oEH_KwP8n8gBWc1cwOBQCWkhT-ME	";
		// ss += ",	oEH_KwNfhasfaGaL6lwBV2k_B8kE	";
		// ss += ",	oEH_KwHgKl3XJNYwBbSx990xCDm4	";
		// ss += ",	oFk3osp8QMiNMikpsOPVYhjUnjFA	";
		// ss += ",	oEH_KwFNRa6ueRIePGxwznARxhwE	";
		// ss += ",	oEH_KwBEBDTUADHBHSgAbYx0MpAg	";
		// ss += ",	oEH_KwBEBDTUADHBHSgAbYx0MpAg	";
		// ss += ",	oEH_KwPLzscqoenKVRl_4wcBraCA	";
		// ss += ",	oEH_KwMZO4vrIPzXKT6CPz5S7Lb4	";
		// ss += ",	oEH_KwMZO4vrIPzXKT6CPz5S7Lb4	";
		// ss += ",	oEH_KwN8hAHhSJeONcHjutJV2htE	";
		// ss += ",	oEH_KwFsEJtzlBr1LOUAXF_5sB7E	";
		// ss += ",	oEH_KwNYgrqaA5M-eMcjK5iYIiOk	";
		// ss += ",	oEH_KwJ1C_5VAIEiciHvWLxO6AAY	";
		// ss += ",	oEH_KwG2B7ntJK5aKQijlPzG0TtM	";
		// ss += ",	oEH_KwEQJjuT1yEYFj-HN39Qek5Q	";
		// ss += ",	oEH_KwDID08U_lzbrpFjnFShtZkk	";
		// ss += ",	oFk3osvPRiOdSdkStQrJRhUFVSoY	";
		// ss += ",	oEH_KwDL_0OZsJwZF5HiC32WiGwE	";
		// ss += ",	oEH_KwFpbnIboXComIlcDHIAuqJc	";
		// ss += ",	oEH_KwHYtFvVmHq8GiegTcGnTbqU	";
		// ss += ",	oEH_KwAx0if2ZY_KvnpyVYuhxICg	";
		// ss += ",	oEH_KwMw44tBGCXuyNt3jjsyPTXc	";
		// ss += ",	oFk3oshfgZ-CzoqGikj50gruAX5E	";
		// ss += ",	oEH_KwC64DlycVF2YXt6VDR1_C4M	";
		// ss += ",	oEH_KwIB8ZRD8oXkbidbuWlaniOM	";
		// ss += ",	oFk3osgmLjQsEBVgyA6GEMbkKv3Q	";
		// ss += ",	oEH_KwN6T-xuV0eY2HBbQQJNRN88	";
		// ss += ",	oEH_KwFZ6MtC-QW_yA5IAam87xiQ	";
		// ss += ",	oEH_KwD8TstBBF5g_D9gtKjITmBA	";
		// ss += ",	oEH_KwOGxCpvsurYPOX9U8zSKI5Y	";
		// ss += ",	oEH_KwJs7Alrs5mghi0qy_W3rXQE	";
		// ss += ",	oEH_KwNF-cnDfh4Q1UYPwCk-d86s	";
		// ss += ",	oEH_KwA7Ec2Wae253TD-83LANYBA	";
		// ss += ",	oEH_KwEy2Q7Eo-1KSaqdTzHwD-E0	";
		// ss += ",	oEH_KwLXDvpwHw891-nVV2u6fPBo	";
		// ss += ",	oEH_KwAPKZO9kWCohxgNpIkn8ssI	";
		// ss += ",	oEH_KwKncEpfxCzUTL4OFXt5fM44	";
		// ss += ",	oEH_KwIAMFWlWz_6DInUhk3aQFzQ	";
		// ss += ",	oEH_KwN4A24VkGjP1eG5BaDdunmo	";
		// ss += ",	oEH_KwFBHOpVgiaug6w548mIJXmY	";
		// ss += ",	oEH_KwBdbQEZ6pJlNEKg9AkSS5sM	";
		// ss += ",	oEH_KwP2QhimoQrTQtfVND4yQkfY	";
		// ss += ",	oEH_KwLyi9Mf1w-W8I8oiRcJOWmI	";
		// ss += ",	oEH_KwGcnoOZ1lMKQ_EFHQCojhnc	";
		// ss += ",	oEH_KwE-3hIbsu3wmRv72W1CzSg8	";
		// ss += ",	oEH_KwOt5ENSliIbta5bbVujuo7Q	";
		// ss += ",	oEH_KwGCsnFvjmenHD5XDpuJVA0Q	";
		// ss += ",	oEH_KwH0putlQjeGPR3izDKq-Wm4	";
		// ss += ",	oEH_KwPimxJ1JyVAm2g7tJHMe4lc	";
		// ss += ",	oEH_KwMbVOWC87LnQxqLR3hFbwrE	";
		// ss += ",	oEH_KwCPw9fAAopSPbpgLxCuQ2wk	";
		// ss += ",	oEH_KwK8Vg9ojK4SomlWHCteo0Zs	";
		// ss += ",	oEH_KwOJom4OUDhFNNHAUo2TeWmI	";
		// ss += ",	oEH_KwC6oc1lX8mG8BN83lX1sVsE	";
		// ss += ",	oEH_KwDXQHDnDZkmVrLt9GJ6jcIU	";
		// ss += ",	oEH_KwICLbxEmwG1gXZe6XLRRdTU	";
		// ss += ",	oEH_KwKFsSf_kNGJRpmqGO8kSTeQ	";
		// ss += ",	oEH_KwAyOlHzTLWfwNXRp9hHynE0	";
		// ss += ",	oEH_KwAqaTz5mIIh4-955ig0Nuzs	";
		// ss += ",	oEH_KwJr2sF8qb9QlmzG76XU3WtE	";
		// ss += ",	oEH_KwMioFk1hF60gAl6btrFgfTo	";
		// ss += ",	oEH_KwJVCLzSi0U_zbQIA7KYFhf0	";
		// ss += ",	oEH_KwPff-V4uTOwoDBBsKW_nauw	";
		// ss += ",	oEH_KwLJzm_ReOkU59JOoQzYOWow	";
		// ss += ",	oFk3osulLeYaO_5G6GM_vlqxduxo	";
		// ss += ",	oFk3osnyGacZ33Cc3a3GlhgejTAI	";
		// ss += ",	oEH_KwG2iTmD3NJMSi6WwdVPztkQ	";
		// ss += ",	oEH_KwK7u1pY9cQ_STMLId9oOIsk	";
		// ss += ",	oEH_KwI4EVoyFd4YWjUGM1_JgzuE	";
		// ss += ",	oEH_KwDbwqXwIGTv-BnSUK_9tZNk	";
		// ss += ",	oEH_KwETjFVSSQKtb70OtXqgQcHc	";
		// ss += ",	oEH_KwDqBx5tfGgeyXfa5X0pVfE0	";
		// ss += ",	oEH_KwIIPxy9E4tgXj8Q4QdX7y2Q	";
		// ss += ",	oEH_KwI5tGkf1tBsWheDsxFdtzps	";
		// ss += ",	oEH_KwHGkf49Kl5eXkzV0EpDrAGU	";
		// ss += ",	oEH_KwNGxlywm7AAD2KoW43AlvCA	";
		// ss += ",	oEH_KwOqlE4DIgfx7NIFYP321Jyc	";
		// ss += ",	oEH_KwKROOrbVUem7XHGhk_G1yRI	";
		// ss += ",	oEH_KwEC_DqJf2p7_dU6nGybWJmY	";
		// ss += ",	oEH_KwA6WyFi0HrlxtBKO9cI38DQ	";
		// ss += ",	oFk3ost-Yl-99VuasEaOglY8b5QM	";
		// ss += ",	oEH_KwC-ZgXkIsMUp2iXmirFS-lY	";
		// ss += ",	oEH_KwCMdQBLC52w0t6kNbJcrT-o	";
		// ss += ",	oEH_KwDJH4c88dBemyA_5v1k6y-M	";
		// ss += ",	oEH_KwM0XptYc2BrHil8ds7UkxYY	";
		// ss += ",	oEH_KwMlD6RJCqXfsCg3jM6siIrc	";
		// ss += ",	oEH_KwL0DmuNDGwC3rtVrU2r-9Rw	";
		// ss += ",	oFk3ostLsp2aWKDj7hbCLZXiVSrA	";
		// ss += ",	oEH_KwIIymuOHkeFWb5HqUprREUs	";
		// ss += ",	oEH_KwAup3j6yiKbvrwl5hOByj5c	";
		// ss += ",	oEH_KwGa1A4jZIOrM52NhSqqWxk4	";
		// ss += ",	oEH_KwCD1K5mCCURX6c02NbZ7f5o	";
		// ss += ",	oEH_KwFkvFE2_AdlvDW-Cg9FHeOE	";
		// ss += ",	oEH_KwHTI72yI7TdxHfe-TmufI5M	";
		// ss += ",	oEH_KwHZ4iVXtYihbpZ9DJ6EVqvE	";
		// ss += ",	oEH_KwA8XyAIcHldgD5zSo26UTI0	";
		// ss += ",	oEH_KwP7CjSqaG9mnUG3mr-zpAAE	";
		// ss += ",	oEH_KwKC_JEJvb8sSrM2Q8KvHIJM	";
		// ss += ",	oEH_KwBGRQNOGRjxssTwtcuEmDh0	";
		// ss += ",	oEH_KwCK-ghej7sVlnCWV8t-hKtk	";
		// ss += ",	oEH_KwEPJOqNtCKLKUato8MLlRvw	";
		// ss += ",	oEH_KwPGgCq5ksL3y2HHSMNU8hNM	";
		// ss += ",	oEH_KwAxXS3G_1_3byt7zf7WJfMU	";
		// ss += ",	oEH_KwIDqZBe5xC9fz36-cnwu2S0	";
		// ss += ",	oEH_KwNh3M06WP3sU6k4xDKrDD1U	";
		// ss += ",	oEH_KwDzj_ImkAEItz_jIFy6bUQ4	";
		// ss += ",	oEH_KwJJncjfEXs4dVaUs4nm7tbs	";
		// ss += ",	oEH_KwAne4YBz8f7MRA2KodXswQE	";
		// ss += ",	oEH_KwIIutO3yeT59qX1pVSWiT00	";
		// ss += ",	oEH_KwBRYfiSmDS6oCu4L2ObgLKM	";
		// ss += ",	oEH_KwCAiG6g-lBS7Xy_n-6TtytU	";
		// ss += ",	oEH_KwBXkykXfWNJs9B2JAxy5JxE	";
		// ss += ",	oEH_KwJ6I4m7KCHqSBWx4k0N5ARk	";
		// ss += ",	oEH_KwG-pFO7Nur89qKpjHtG-_pk	";
		// ss += ",	oEH_KwEY1xonvDZoG0mGJb3SAmQU	";
		// ss += ",	oEH_KwO28C2DXtmHcyUL6Gqg7oZA	";
		// ss += ",	oEH_KwLrr55JLMcL4CAISShv0zzg	";
		// ss += ",	oEH_KwBDUIOPl_HkRKBHOtQuf9NY	";
		// ss += ",	oEH_KwMIxEsSP7FQnVYNt3pr9Ysk	";
		// ss += ",	oEH_KwAlo-6JzhH6uq3SHoNHA-ds	";
		// ss += ",	oEH_KwCCWPc0iVM_MW1fer4oN4SY	";
		// ss += ",	oEH_KwC3z4UMPslxpryI2faZ1w38	";
		// ss += ",	oEH_KwCZW9r3zseaSomnqfZ544ZI	";
		// ss += ",	oEH_KwD4WUCGDAp79XYBVe3KYhvw	";
		// ss += ",	oEH_KwHVIlATEIbQTR5-GmAM3eC4	";
		// ss += ",	oEH_KwDHDbuM3SLxOWKErLf5RoKs	";
		// ss += ",	oEH_KwL28ct7XAahHC11HRR7VmwY	";
		// ss += ",	oEH_KwKA4SR1fAzLifTh50WX0MXw	";
		// ss += ",	oEH_KwAIFqhVMuofJS7uRMBOoDhU	";
		// ss += ",	oEH_KwKn9fNdC8OcV383P4UVAm0Y	";
		// ss += ",	oEH_KwA220FPqb3UC-Ir2iWEjj7Y	";
		// ss += ",	oEH_KwPabaAatp1ltnQUsNZiXNQ0	";
		// ss += ",	oEH_KwL9FkbOOc4O6n2HGpYsl2Eo	";
		// ss += ",	oEH_KwGEiiGpZj36Go68alDasMRk	";
		// ss += ",	oEH_KwDTQj_ZH6zVh8wbhVZ-_X3k	";
		// ss += ",	oEH_KwLu3ah2u77NT_Tas6T3jQdg	";
		// ss += ",	oEH_KwOCSAf7sY2HFPy0iaTjCfmg	";
		// ss += ",	oEH_KwC6XRqvU38-EF88-BdHcOt0	";
		// ss += ",	oFk3osrLDk9U5DJX-ioa1udGflV4	";
		// ss += ",	oFk3osqdOgEWn7lXHdm9HEm8Jhak	";
		// ss += ",	oEH_KwAvgR6bT1-0rXlCBGvUUdKM	";
		// ss += ",	oEH_KwLXvSGmZBde2BMXMgx7OOTs	";
		// ss += ",	oEH_KwN3Bxw9j47gpUEO3d1TPUxQ	";
		// ss += ",	oEH_KwOpbS1QMu7r03nd5t4UU3-U	";
		// ss += ",	oEH_KwLylDEIeA6owj6kK55diDcE	";
		// ss += ",	oEH_KwG8-zk_qt-k6bVmJI0wcOXk	";
		// ss += ",	oEH_KwNF1bYOTb-H5Q8c-hB6J1I4	";
		// ss += ",	oEH_KwLotvpAqxTmh3JrMnJHuhGI	";
		// ss += ",	oEH_KwHLhx6tHbCu6GLz3aAZwUbM	";
		// ss += ",	oEH_KwJztqTiWFjtpUIX0xidOw4w	";
		// ss += ",	oEH_KwMGAzM3cwLZykVLEaU6KFeM	";
		// ss += ",	oEH_KwO3Ui98jir90h-7LnlxgNwQ	";
		// ss += ",	oEH_KwKwKVKO_t8q3hEErkRUTTVs	";
		// ss += ",	oEH_KwHNcdOjynyRRpUe-MGO0GKI	";
		// ss += ",	oEH_KwDpJ56Zz1WqjyW3ArVvT8bM	";
		// ss += ",	oEH_KwHZazWgba3L6CnCsYmNXJ88	";
		// ss += ",	oEH_KwF4ICf1e0W4XBo25nwHXjmc	";
		// ss += ",	oEH_KwBfhnWv-j0gZpLEvd4j3T7I	";
		// ss += ",	oEH_KwIRr1FSaAgdIjEzQoNIj-KQ	";
		// ss += ",	oEH_KwNrPVFNstSL9HDPoVZaPLPg	";
		// ss += ",	oEH_KwAyhXOlEI5eXFjeNM1MNr-c	";
		// ss += ",	oEH_KwFHxffqg-4zwbCFTumSMDuE	";
		// ss += ",	oEH_KwOtLZ13tRCKli68k2CZpU20	";
		// ss += ",	oFk3ospLeyocvYR7uadix9XbYiBM	";
		// ss += ",	oEH_KwBUTVzIaSbQE2G5y9r07S1w	";
		// ss += ",	oEH_KwHRX3gGhWkVtQEzKHcXGeEA	";
		// ss += ",	oFk3oshPGmgqL8rkyQ0tb5czIlvg	";
		// ss += ",	oFk3osjZnANbuHNExG87AIOT06ic	";
		// ss += ",	oEH_KwETdbhzKBWW0393yapSdREE	";
		// ss += ",	oFk3osvLELdTckv8zrYtvOcXY8fc	";
		// ss += ",	oEH_KwArqCWjeT0QfdccUIus6fwo	";
		// ss += ",	oEH_KwHcK_5KlTpbTT0d5fdUL9-Q	";
		// ss += ",	oEH_KwJHQBwFZFLkvBfOGPh0DQw8	";
		// ss += ",	oFk3osgOKsWqSP5M3i_HxGmU9ksM	";
		// ss += ",	oEH_KwB4QggKiSXCzZsI3jV6RwnU	";
		// ss += ",	oEH_KwPLlSGYZx2RyQgCWWNFnmTk	";
		// ss += ",	oEH_KwF_VtgZpqC5MU8w5rRXDhrg	";
		// ss += ",	oEH_KwJDqIWxwGOyeOdYM9AfYqhs	";
		// ss += ",	oFk3oss7LmboHJpwUMQqqt-FjX5g	";
		// ss += ",	oEH_KwOfv284H2d_UBOmQP_aObpc	";
		// ss += ",	oEH_KwD9NxhypqVCQxhQzSYaQKeo	";
		// ss += ",	oEH_KwJkGkvIrT_E3N6Kj47w_YjY	";
		// ss += ",	oEH_KwKmVTWeDkHXhvSmTxwvLWFM	";
		// ss += ",	oEH_KwP1LFXyZPQy-ARRFum98awc	";
		// ss += ",	oEH_KwLO9Mgq2dcZCiMDavmlIudU	";
		// ss += ",	oEH_KwN2n8rVE-44Yq4MVjvgECv0	";
		// ss += ",	oEH_KwLccBC9eku--AUprUgWYX5k	";
		// ss += ",	oEH_KwHwnPTTA9bp3tYiyTFz_sts	";
		// ss += ",	oFk3osg5FP-J5VU1hHZtbgs2e_fQ	";
		// ss += ",	oEH_KwDtA0YlaP8VagC-aV8U-6uw	";
		// ss += ",	oEH_KwClSNAgaPj7GF1vKDFzkZsA	";
		// ss += ",	oEH_KwManqA2HjKSvB2BtAzeuSUo	";
		// ss += ",	oEH_KwL-owX_vhnt9fCiJcnmp_3w	";
		// ss += ",	oEH_KwF7jkoq4_3h99mynPXs6nfA	";
		// ss += ",	oFk3osqe4PJRneiCFFvGEyC2e370	";
		// ss += ",	oEH_KwJOO8z9p4DkYA7mXXxJ2k14	";
		// ss += ",	oEH_KwJ6DnsmleJWsSVB2VuvUnCQ	";
		// ss += ",	oEH_KwHv8EAgNIDGvR3bVLJd3_6I	";
		// ss += ",	oEH_KwIXnBh-vh-56eE7zRDwuBac	";
		// ss += ",	oEH_KwBAomW4wXGmMpxErDAjrKqg	";
		// ss += ",	oEH_KwHyXY0EcKG5mEk4A6xgKQUI	";
		// ss += ",	oFk3ossyJJfQLllqRdD4EsN9PBfM	";
		// ss += ",	oEH_KwIKG0ZCFhtLK8PaVJp7rJyc	";
		// ss += ",	oEH_KwG400OeZBsjBU-Olm_Y2jD4	";
		// ss += ",	oEH_KwDQOaApmfoD3DIdm1nCMImY	";
		// ss += ",	oEH_KwKL_YPgZF-UuWQY7w_FFnQY	";
		// ss += ",	oFk3osr_7UxCYotcYLir7RyprYow	";
		// ss += ",	oEH_KwPODxNJXp_6xIjOz7y_WM0U	";
		// ss += ",	oEH_KwCJWV_l3Vkqn-Gm9TFPBNuU	";
		// ss += ",	oEH_KwBEx9JNITAPPNtaxM_4RODA	";
		// ss += ",	oEH_KwCG4nYagGL4V8pvYD0L7quY	";
		// ss += ",	oEH_KwCSeU7JVelWNqm2h-s2TdqY	";
		// ss += ",	oEH_KwLg5r35mHtEob9bzK4AOzF0	";
		// ss += ",	oFk3ossPqUtLtLm0Ujt6bj9V3NFo	";
		// ss += ",	oEH_KwK1RaCNGxmyBR3LLA3tmmdg	";
		// ss += ",	oEH_KwCaZyX6ONXZHyQmXgT4vfdM	";
		// ss += ",	oEH_KwGWjsFM3FV3YU3ojb4Nke9k	";
		// ss += ",	oEH_KwEqOvcxW0QRL5F4y8YW8FxI	";
		// ss += ",	oEH_KwEjwn0kfvjBbGZHMXSnKKT0	";
		// ss += ",	oEH_KwO4-ZzX5ti5QXFaeXngJgP4	";
		// ss += ",	oEH_KwCaipcywt3HErTWhav8mbvU	";
		// ss += ",	oEH_KwLipws7XYyeaIEUglSiQkyY	";
		// ss += ",	oEH_KwAFkFctxN8J_LZumlPGJN-s	";
		// ss += ",	oEH_KwHTT53wXLwbX3shnA-Vx2Rk	";
		// ss += ",	oEH_KwIkFjrqA5qZ9--sP48ZKhKM	";
		// ss += ",	oEH_KwBgvQitMlPGNXhcNSm1kkQY	";
		// ss += ",	oFk3osoZS6h4VF3i3hB0hnKVrUzM	";
		// ss += ",	oEH_KwMHxRfWqEX3ydzt-fLi2tqM	";
		// ss += ",	oFk3osor3jGartyjqJIMKRl8SHwM	";
		// ss += ",	oEH_KwEmYuLTHpB75-EvvpSLGYAs	";
		// ss += ",	oFk3osgRHu5xg4eHTK_lBKgdCmzM	";
		// ss += ",	oEH_KwCkirj4eSMrZD9r5fs4tj7I	";
		// ss += ",	oEH_KwMwqvQT5xjZwJeW9XYVNmrQ	";
		// ss += ",	oFk3osvby1ZLHcGzrEHFDS4vkeN4	";
		// ss += ",	oEH_KwKohWPeqnkSzhm6J8859t5w	";
		// ss += ",	oEH_KwAlVK-1qt_idkw8AqBoSrzQ	";
		// ss += ",	oEH_KwIbLJg6DbbMBtPjGn5xEaTw	";
		// ss += ",	oEH_KwHO_eW0YXGMAKtHbEGbAeQU	";
		// ss += ",	oEH_KwN9xxwUtcCQl5g1IyM49S0s	";
		// ss += ",	oEH_KwOPk5HAyPawiOfJKs5RFUl0	";
		// ss += ",	oEH_KwJvkZ4r94Okum3VKa5IH3j8	";
		// ss += ",	oEH_KwAW_vHixZIEqyHIu7P__nCM	";
		// ss += ",	oEH_KwEU896gO53TuWeGSlOXaMwQ	";
		// ss += ",	oEH_KwOCv8tVbamkgDmdB-xRWk74	";
		// ss += ",	oEH_KwAy_gqIgtdO7x6ihHsFtZio	";
		// ss += ",	oEH_KwP8TTGzSJsa86k3_3jTPgBg	";
		// ss += ",	oEH_KwEhCJGoBgKhue-Yinj7gUJU	";
		// ss += ",	oEH_KwNJxL3E4mYU2VTSXwSoj-OI	";
		// ss += ",	oEH_KwFKXCY1eZAKJswHm-I59qzM	";
		// ss += ",	oEH_KwNXOvMAWGws63LCDv8jWDEo	";
		// ss += ",	oEH_KwKYj5FW0mtaBXfCaPMv9vtk	";
		// ss += ",	oEH_KwM1VFPfbzWKiafkvaL4s_9g	";
		// ss += ",	oEH_KwN8yupgVj8yVHuH-Iy0J_e8	";
		// ss += ",	oEH_KwEwP_FDBxkzZyvacVb-sbRo	";
		// ss += ",	oFk3oslhfLR2U3YeBvwce9nBmjj4	";
		// ss += ",	oFk3osiOudevGw0D4ulJhGJqbOVk	";
		// ss += ",	oEH_KwJGDZ7h8WBbZrPS-Ve6n4RA	";
		// ss += ",	oEH_KwFKtWJ6wJYjlhcVXWj8dgKs	";
		// ss += ",	oEH_KwJx64-UTE7xXrJf8tYWRQn8	";
		// ss += ",	oEH_KwLHVcr_aFSfoYnXNhTrmC9A	";
		// ss += ",	oEH_KwNh4Xj-FlXkC8w_pYhM6MWE	";
		// ss += ",	oEH_KwAuoF5GShRWww3Sd0AksD_M	";
		// ss += ",	oEH_KwEVs_b2fKEC9FwjOoNaq6NI	";
		// ss += ",	oEH_KwII3XqjoIGxSTF-NqzFR0Pg	";
		// ss += ",	oEH_KwOl2xpubqT2Dtz_I2sIaj6Q	";
		// ss += ",	oEH_KwHhbe2y0KLS_KeJcQhCVCvs	";
		// ss += ",	oEH_KwMoWgO5RpA0T1POr2jQU-4g	";
		// ss += ",	oEH_KwFhKxmmEzWBW5zQQ_l09i5w	";
		// ss += ",	oEH_KwHcT2IbTqtOp-m9NfoprH5w	";
		// ss += ",	oFk3osoSn9Rm15Nf167Dg0CX6R4M	";
		// ss += ",	oEH_KwKAkU3rlfllEg0gv1B9ZHNI	";
		// ss += ",	oEH_KwPzBAMq1nfnyiGGdILdgUR0	";
		// ss += ",	oEH_KwAB_ek7a6tC-0zss1Zc8xHw	";
		// ss += ",	oEH_KwOTTq2-b60zi2LRzOCRtE9Q	";
		// ss += ",	oEH_KwIvu5lv6swh--7HUb8bXRCk	";
		// ss += ",	oEH_KwDkhlO49uyffgFlMS58p4n0	";
		// ss += ",	oEH_KwEgHsLR3y5TNbolAFvgwy9M	";
		// ss += ",	oEH_KwP5Q-EHt8-5xFtBs1UvFaHM	";
		// ss += ",	oEH_KwNC9A0VT2BO-x5vKL9qoOAo	";
		// ss += ",	oEH_KwFE7qGskZB-SpsC38iSYezc	";
		// ss += ",	oEH_KwNnuOjcau1AWUlQXnQ3Agj4	";
		// ss += ",	oEH_KwCb8v6FDaZu6KEzXAZ43DBY	";
		// ss += ",	oEH_KwNsRxA9HaRLYUsfb6sWIPO4	";
		// ss += ",	oEH_KwHmyIG3xHKs7M5xD2uNUJPQ	";
		// ss += ",	oEH_KwNjQMX97hHLq90F47MXmQ7U	";
		// ss += ",	oEH_KwMMCrUYamfhy5jeeffG4fcA	";
		// ss += ",	oEH_KwNQWSA6jXku9siP4O9EWlyY	";
		// ss += ",	oEH_KwHr7PQ130ue_yb_yAuIg32s	";
		// ss += ",	oEH_KwOcEX7WUgppeLMzjC-N9uIk	";
		// ss += ",	oEH_KwAEPl9WfY8wUzobnSa8Otyk	";
		// ss += ",	oEH_KwJe9Jg09C3bTlsOqR-bg11s	";
		// ss += ",	oEH_KwJXmuR_4RlxvA207FY_1DVk	";
		// ss += ",	oEH_KwKF5LDDFt7ofLMSEsCVtpAU	";
		// ss += ",	oEH_KwIZo7kmU_h4Eb5Ee1lN18eE	";
		// ss += ",	oEH_KwIpjAGlQ_iAVpXBbhnYc0Wk	";
		// ss += ",	oEH_KwALXAOrX7Zj13A3pfOuS_ck	";
		// ss += ",	oEH_KwAXSjXEMxY_tnlUAMFtTPPo	";
		// ss += ",	oEH_KwIzObubnBbWTLoEf6OiygI0	";
		// ss += ",	oEH_KwF-iwxxFWvDIzKlHoz0DFX4	";
		// ss += ",	oEH_KwGEbOqyUTA4pXAfmCMAqi-g	";
		// ss += ",	oEH_KwBF-gUnvGamaGqSAv2giyL4	";
		// ss += ",	oEH_KwAphiEOHN5C2oUwk3F3WZyM	";
		// ss += ",	oEH_KwBfsqae5wd3f3taH-bVarmk	";
		// ss += ",	oEH_KwDT_1rc5fCwZjw_aSUVu0cg	";
		// ss += ",	oEH_KwFdr0tUYfHuvNFuL1Wz3RCQ	";
		// ss += ",	oEH_KwAm3Jjqhwi9RbS69lfdU0V8	";
		// ss += ",	oEH_KwNqqqx5XuwhcXFiRLm90hCI	";
		// ss += ",	oEH_KwIbAVYtNVH7_U6442vw12wk	";
		// ss += ",	oEH_KwKFZOupyqW4JMlaVyUpxEUY	";
		// ss += ",	oEH_KwJ13fFnmIK3lgUJM3ixsykY	";
		// ss += ",	oEH_KwASmcAb2ebWrNStT5zOdWh4	";
		// ss += ",	oFk3osnLm7bGphToPQ7ilJvKUONs	";
		// ss += ",	oEH_KwI40EwRR71akZdF6VsE3S_0	";
		// ss += ",	oEH_KwAoSW_NVxAj5yBr5o0vOAMA	";
		// ss += ",	oEH_KwJg9tfHXiqoYrez7k-e2z-g	";
		// ss += ",	oEH_KwCpBvCBFo-lfdDLbTaP8N3o	";
		// ss += ",	oEH_KwBXG7I_xraGvlOtUas_v8P0	";
		// ss += ",	oEH_KwJ_bdkeI8SOBq431e4L91rs	";
		// ss += ",	oFk3osjDZiBxgxb3obK-YJ6UBvx4	";
		// ss += ",	oEH_KwBK0_HErHwjcxniG-BA3h7E	";
		// ss += ",	oEH_KwB6M7JBI_DQzHo9rHRlGikM	";
		// ss += ",	oEH_KwCMUww8k_QtqXDBDZAJyFuI	";
		// ss += ",	oEH_KwAOiYAxZDYhg861mE8vcF1w	";
		// ss += ",	oEH_KwAcIXq75qwIbx0vC06MhVPU	";
		// ss += ",	oEH_KwO30KW67fVjMgnKRx4mz6FQ	";
		// ss += ",	oFk3oslFK94BzZFAA3axHBIjqX2Y	";
		// ss += ",	oFk3ossDOHoThNOwQJ3hK6jCgIKo	";
		// ss += ",	oEH_KwAAN_E1kQdsBRUC-Fl1u9bk	";
		// ss += ",	oFk3osqjBnPjpUHjQbS7bLMSNG40	";
		// ss += ",	oEH_KwG22UxTpwFMbRqBx0tLR5aU	";
		// ss += ",	oEH_KwDLXewEnzwYtUjse5RVKeyQ	";
		// ss += ",	oEH_KwI_YTXx1Q3MPOuvPckfr1kA	";
		// ss += ",	oEH_KwM39e8goitmOUyL8MXHHuOI	";
		// ss += ",	oEH_KwGv_7k8jJWDSsfXPgWKtsaQ	";
		// ss += ",	oEH_KwBQy6cJMh887m0Ufrdv3wVo	";
		// ss += ",	oEH_KwLyS_mE23z3WPtJvzdqc_MQ	";
		// ss += ",	oEH_KwP90CxFn2tIqcoH1UDmUUck	";
		// ss += ",	oFk3osth_XgSNODh4-aujWRt_uB8	";
		// ss += ",	oFk3osongM88eN6wTm-v044ctNAk	";
		// ss += ",	oFk3osgL6Lvn-UbyBQu13kJ1pToo	";
		// ss += ",	oEH_KwHQrS_mYnpvpqhGLvpo4S7o	";
		// ss += ",	oEH_KwOR6LoA7IIKMGAOsn8WEo4g	";
		// ss += ",	oFk3oskIqZpvkkkR7e21XYY6ydok	";
		// ss += ",	oEH_KwEdoLj-5Y8dOrfjUr9VqUd8	";
		// ss += ",	oEH_KwMXAzU3k7ROid93Yi5d7vDc	";
		// ss += ",	oFk3osput2gzR6UjiCA_B6c1mnVo	";
		// ss += ",	oEH_KwDrDX0BzaYzmaoqT3tdK_dw	";
		// ss += ",	oEH_KwMLCpGORn6jndkzEqvlI0oc	";
		// ss += ",	oEH_KwGp_59G0a3JnX2esmRVlSgE	";
		// ss += ",	oEH_KwAN7PbRbRCrCY39THrWMvyI	";
		// ss += ",	oEH_KwFe-zlDsUer-jBRo5aNlHew	";
		// ss += ",	oEH_KwHdYBm1vUWbsSPjsMi6XeEk	";
		// ss += ",	oFk3osqTeqTz4dVWH2QLHGlgy0E4	";
		// ss += ",	oEH_KwCvWQaAjqCl3Z_l2MzQ5CaU	";
		// ss += ",	oFk3oskgMnN0eB3qXZGtKIYgFZSk	";
		// ss += ",	oEH_KwLsIHCq0rcIpSs3YChw5rYA	";
		// ss += ",	oEH_KwKqlMa-SpYtVrzODSDfT804	";
		// ss += ",	oEH_KwEx9LSjxny9GXKvjSd2OLKA	";
		// ss += ",	oEH_KwDGx6BlYs1HPUGxovPzfHFM	";
		// ss += ",	oEH_KwGu93wz7y4EC55r9GQ3bebA	";
		// ss += ",	oEH_KwN2y8x551uJDFoSEm9Ya6GI	";
		// ss += ",	oEH_KwNTl1SQX4s-cZcbfNzxk_G0	";
		// ss += ",	oFk3oskuDP8OwYFxWgvhTyH_UMtU	";
		// ss += ",	oEH_KwPsTpDx16ceH3nFg5dh8aNU	";
		// ss += ",	oEH_KwGLFn1TB-7fB-GcOCyrUxSo	";
		// ss += ",	oEH_KwCO35n90wODSAvCDZyC25fI	";
		// ss += ",	oFk3osgR2C7avJcRFmJr-m1fIy8U	";
		// ss += ",	oFk3oshvhVwBBnhCEctjFGLO-QeQ	";
		// ss += ",	oFk3ostU6LKQfkWd7q085bgmOXcA	";
		// ss += ",	oEH_KwHMOK56B2kSBUlGvDa-Tiw4	";
		// ss += ",	oFk3osgNDMFsl8jQNpLcHvlDbvh4	";
		// ss += ",	oFk3osmTVJd9NdVZgKo4UWmD40Dk	";
		// ss += ",	oEH_KwOZGrsWHSEJmEImuGpRA0UQ	";
		// ss += ",	oEH_KwEopjUuKnl0SSGsJSM-yO2U	";
		// ss += ",	oEH_KwHyUK9ycC0wZ0L-UtA1HG38	";
		// ss += ",	oEH_KwHiv9J_Iutx2d2febp8pRBs	";
		// ss += ",	oEH_KwB8Xeu-Om1w_r0CULIHdO0Q	";
		// ss += ",	oEH_KwE_tSOC9RMT4FqB1Nm5p2VI	";
		// ss += ",	oEH_KwJoNIVFIDdTXqEMrGwkM3VI	";
		// ss += ",	oEH_KwPHodsT-Gff10ai3945vz4Q	";
		// ss += ",	oEH_KwBE-MeGbdb-GjckCz5x020Y	";
		// ss += ",	oEH_KwNkO50gXyxGPAlSnbLK_aO8	";
		// ss += ",	oEH_KwBpkFZnmo5nn-N3zaj3LU9E	";
		//
		// String ass = ss.replace("	", "");
		// System.out.println(ass);
		//
		// for (String s : ass.split(",")) {
		// System.out.println(s);
		// try {
		// wxSvc.getWxMpService(mSysId).userUpdateGroup(s, 104);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }
		// }

		// wxSvc.getWxMpService(mSysId).userUpdateGroup(openid, 104);
	}

	/**
	 * 获取订阅回复信息
	 * 
	 * @param mSysId
	 * @param timestamp
	 * @param signature
	 * @return
	 */
	@RequestMapping(value = "/findSubscribeReply.do")
	public @ResponseBody WxReplyMsg findSubscribeReply(String mSysId, String timestamp, String signature) {
		try {
			WxReplyMsg wxReplyMsg = wxReplyMsgSvc.findUniqueReply(mSysId, WxConsts.EVT_SUBSCRIBE,
					WxReplyMsgService.DEFAULT_KEY_WORD);
			return wxReplyMsg;
		} catch (Exception e) {
			logger.error("findSubscribeReply error...", e);
			return null;
		}
	}

	/**
	 * 获取微信用户信息
	 * 
	 * @param mSysId
	 * @param timestamp
	 * @param signature
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/userInfo.do")
	public @ResponseBody WxMpUser userInfo(String mSysId, String timestamp, String signature, String openId) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			String lang = "zh_CN"; // 语言
			WxMpUser user = wxSvc.getWxMpService(mSysId).userInfo(openId, lang);
			return user;
		} catch (Exception e) {
			logger.error("userInfo error", e);
			return null;
		}
	}

	/**
	 * 移动用户的分组
	 * 
	 * @param mSysId
	 * @param timestamp
	 * @param signature
	 * @param openId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/userUpdateGroup.do")
	public @ResponseBody Result userUpdateGroup(String mSysId, String timestamp, String signature, String openId,
			long groupId) {
		try {
			logger.info("user update group , openId : " + openId + ",groupId : " + groupId);
			mSvc.checkSignature(mSysId, timestamp, signature);
			wxSvc.getWxMpService(mSysId).userUpdateGroup(openId, groupId);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("userUpdateGroup error", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/createMenu.do")
	public @ResponseBody Result createMenu(String mSysId, Long menuRuleId, String timestamp, String signature) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			wxSvc.getWxMpService(mSysId).menuCreate(wxMenuSvc.getWxMenu(mSysId, menuRuleId));
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("createMenu error", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	// /**
	// * 创建带场景值的二维码
	// *
	// * @param scenicId
	// */
	// @RequestMapping(value = "/createQR.do")
	// public @ResponseBody void createQR(int scenicId) {
	// try {
	// WxMpQrCodeTicket ticket = wxSvc.getWxMpService()
	// .qrCodeCreateLastTicket(scenicId);
	// File file = wxSvc.getWxMpService().qrCodePicture(ticket);
	// logger.info(file.getAbsolutePath());
	// } catch (Exception e) {
	// logger.error("createQr", e);
	// }
	// }

	// @RequestMapping(value = "/materialFileBatchGet.do")
	// public @ResponseBody void materialFileBatchGet() {
	// try {
	// WxMpMaterialFileBatchGetResult result = wxSvc.getWxMpService()
	// .materialFileBatchGet(WxConsts.MATERIAL_IMAGE, 0, 100);
	// for (WxMaterialFileBatchGetNewsItem item : result.getItems()) {
	// logger.info(JSON.toJSONString(item));
	// }
	// } catch (Exception e) {
	// logger.error("materialFileBatchGet error...", e);
	// }
	// }

	/**
	 * 发送文本消息接口
	 */
	@WebMethod
	@RequestMapping(value = "/sendText.do", method = RequestMethod.POST)
	public @ResponseBody Result sendText(String mSysId, String timestamp, String signature, @RequestBody WxTextDto tDto) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			WxMpXmlMessage msg = new WxMpXmlMessage();
			msg.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
			msg.setContent(tDto.getContent());
			msg.setToUserName(tDto.getOpenId());
			Map<String, Object> extra = Maps.newHashMap();
			extra.put("mobile", tDto.getMobile());

			mqSvc.enqueue(new MqDto<WxMpXmlMessage>(mSysId, MqMsgType.WX, msg, extra));
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendText error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	/**
	 * 发送图文消息接口
	 */
	@WebMethod
	@RequestMapping(value = "/sendNews.do", method = RequestMethod.POST)
	public @ResponseBody Result sendNews(String mSysId, String timestamp, String signature, @RequestBody WxNewsDto nDto) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			WxMpMessageRouter router = wxSvc.getSendMsgRoute(mSysId);
			WxMpXmlMessage msg = new WxMpXmlMessage();
			msg.setMsgType(WxConsts.CUSTOM_MSG_NEWS);
			msg.setUrl(nDto.getUrl());
			msg.setPicUrl(nDto.getPicUrl());
			msg.setTitle(nDto.getTitle());
			msg.setDescription(nDto.getDesc());
			msg.setToUserName(nDto.getOpenId());
			router.route(msg, null);

			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendNews error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	/**
	 * 群送图文消息接口
	 */
	@RequestMapping(value = "/sendMassNews.do")
	public @ResponseBody void sendMassNews(String mSysId, String timestamp, String signature) {
		try {
			File file = new File("/Users/luoyb/Downloads/drink.png");
			if (!file.exists()) {
				throw new Exception("要上传的文件不存在");
			}
			InputStream is = new FileInputStream(file);

			// 先上传图文消息里需要的图片
			WxMediaUploadResult uploadMediaRes = wxSvc.getWxMpService(mSysId).mediaUpload(WxConsts.MEDIA_IMAGE,
					WxConsts.FILE_JPG, is);

			logger.info(JSON.toJSONString(uploadMediaRes));

			WxMpMassNews news = new WxMpMassNews();
			WxMpMassNews.WxMpMassNewsArticle article1 = new WxMpMassNews.WxMpMassNewsArticle();
			article1.setTitle("标题1");
			article1.setContent("内容1");
			article1.setThumbMediaId(uploadMediaRes.getMediaId());
			news.addArticle(article1);

			WxMpMassNews.WxMpMassNewsArticle article2 = new WxMpMassNews.WxMpMassNewsArticle();
			article2.setTitle("标题2");
			article2.setContent("内容2");
			article2.setThumbMediaId(uploadMediaRes.getMediaId());
			article2.setShowCoverPic(true);
			article2.setAuthor("作者2");
			article2.setContentSourceUrl("www.baidu.com");
			article2.setDigest("摘要2");
			news.addArticle(article2);

			WxMpMassUploadResult massUploadResult = wxSvc.getWxMpService(mSysId).massNewsUpload(news);

			String mediaId = massUploadResult.getMediaId();
			logger.info(mediaId);

			// String mediaId =
			// "T3IqLt5WE65wu09DFFZdnxzVUBOqBGaqV6pMCOpxTX6Q5AioPX0N2onZe6La7VdO";
			WxMpMessageRouter router = wxSvc.getSendMsgRoute(mSysId);
			WxMpXmlMessage msg = new WxMpXmlMessage();
			msg.setMsgType(WxConsts.MASS_MSG_NEWS);
			msg.setMediaId(mediaId);
			router.route(msg, null);
		} catch (Exception e) {
			logger.error("sendMassNews error...", e);
		}
	}
}

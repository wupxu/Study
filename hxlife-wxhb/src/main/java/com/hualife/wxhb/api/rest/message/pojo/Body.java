package com.hualife.wxhb.api.rest.message.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
	/**
	 * 体检函ID
	 */
	@XmlElement(name = "physical_note_id")
	private String physical_note_id = "";
	/**
	 * TIF图片
	 */
	@XmlElement(name = "physical_tif")
	private PhysicalTif physical_tif = new PhysicalTif();
	/**
	 * PDF图片
	 */
	@XmlElement(name = "physical_pdf")
	private PhysicalPdf physical_pdf = new PhysicalPdf();

	public String getPhysical_note_id() {
		return physical_note_id;
	}

	public void setPhysical_note_id(String physical_note_id) {
		this.physical_note_id = physical_note_id;
	}

	public PhysicalTif getPhysical_tif() {
		return physical_tif;
	}

	public void setPhysical_tif(PhysicalTif physical_tif) {
		this.physical_tif = physical_tif;
	}

	public PhysicalPdf getPhysical_pdf() {
		return physical_pdf;
	}

	public void setPhysical_pdf(PhysicalPdf physical_pdf) {
		this.physical_pdf = physical_pdf;
	}

}

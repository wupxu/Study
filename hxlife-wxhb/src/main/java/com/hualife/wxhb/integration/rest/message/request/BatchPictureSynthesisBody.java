package com.hualife.wxhb.integration.rest.message.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.hualife.wxhb.integration.rest.message.pojo.Client;
import com.hualife.wxhb.integration.rest.message.pojo.PhysicalBypitcure;

/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchPictureSynthesisBody {
	/**
	 * 体检函ID
	 */
	private String physical_note_id = "";
	/**
	 * 自拍照
	 */
	@XmlElement(name = "Client")
	private Client Client = new Client();
	/**
	 * 体检函
	 */
	@XmlElement(name = "Physical")
	private PhysicalBypitcure Physical = new PhysicalBypitcure();

	public String getPhysical_note_id() {
		return physical_note_id;
	}

	public void setPhysical_note_id(String physical_note_id) {
		this.physical_note_id = physical_note_id;
	}

	public Client getClient() {
		return Client;
	}

	public void setClient(Client client) {
		Client = client;
	}

	public PhysicalBypitcure getPhysical() {
		return Physical;
	}

	public void setPhysical(PhysicalBypitcure physical) {
		Physical = physical;
	}

}

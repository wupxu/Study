package com.hualife.wxhb.integration.rest.message.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlRootElement(name = "TranData")
public class BatchPictureSynthesisRequestMessage {
	
		/**
		 * head
		 */
		@XmlElement(name = "Head")
		private BatchPictureSynthesisHead head = new BatchPictureSynthesisHead();
		/**
		 * body
		 */
		@XmlElement(name = "Body")
		private BatchPictureSynthesisBody body = new BatchPictureSynthesisBody();

		public BatchPictureSynthesisHead getHead() {
			return head;
		}

		public void setHead(BatchPictureSynthesisHead head) {
			this.head = head;
		}

		public BatchPictureSynthesisBody getBody() {
			return body;
		}

		public void setBody(BatchPictureSynthesisBody body) {
			this.body = body;
		}

	}

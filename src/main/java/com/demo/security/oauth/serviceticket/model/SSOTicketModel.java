package com.demo.security.oauth.serviceticket.model;

public class SSOTicketModel {

	private String serviceTicketId;
	private String customerId;
	private String customerGUID;

	public String getServiceTicketId() {
		return serviceTicketId;
	}

	public void setServiceTicketId(String serviceTicketId) {
		this.serviceTicketId = serviceTicketId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerGUID() {
		return customerGUID;
	}

	public void setCustomerGUID(String customerGUID) {
		this.customerGUID = customerGUID;
	}

}

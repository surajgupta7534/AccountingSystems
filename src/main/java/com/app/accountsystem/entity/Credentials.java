package com.app.accountsystem.entity;

public class Credentials {
	private final String partnerUserID;

	private final String partnerUserSecret;
	
	public Credentials(final String partnerUserID, final  String partnerUserSecret) {
		this.partnerUserID = partnerUserID;
		this.partnerUserSecret = partnerUserSecret;
	}

	public String getPartnerUserID() {
		return partnerUserID;
	}

	public String getPartnerUserSecret() {
		return partnerUserSecret;
	}
	
}

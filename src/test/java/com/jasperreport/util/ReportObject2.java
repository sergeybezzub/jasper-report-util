package com.jasperreport.util;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class ReportObject2 
{

	private long id;
	private String status;
	private String applicationName;
	private String sun;
	private String paymentType;
	private LocalDate paymentDate;
	private long debitCount;
	private long auddisCount;
	private BigDecimal creditTotal;
	private BigDecimal debitTotal;
	private String enteredBy;
	private DateTime enteredDate;
	
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
	}
	public String getStatus() 
	{
		return status;
	}
	public void setStatus(String status) 
	{
			this.status = status;
	}
	public String getApplicationName() 
	{
		return applicationName;
	}
	public void setApplicationName(String applicationName) 
	{
		this.applicationName = applicationName;
	}
	public String getSun() 
	{
		return sun;
	}
	public void setSun(String sun) 
	{
		this.sun = sun;
	}
	public String getPaymentType() 
	{
		return paymentType;
	}
	public void setPaymentType(String paymentType) 
	{
		this.paymentType = paymentType;
	}
	public LocalDate getPaymentDate() 
	{
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) 
	{
		this.paymentDate = paymentDate;
	}
	public long getDebitCount() 
	{
		return debitCount;
	}
	public void setDebitCount(long debitCount) 
	{
		this.debitCount = debitCount;
	}
	public long getAuddisCount() 
	{
		return auddisCount;
	}
	public void setAuddisCount(long auddisCount) 
	{
		this.auddisCount = auddisCount;
	}
	public BigDecimal getCreditTotal() 
	{
		return creditTotal;
	}
	public void setCreditTotal(BigDecimal creditTotal) 
	{
		this.creditTotal = creditTotal;
	}
	public BigDecimal getDebitTotal() 
	{
		return debitTotal;
	}
	public void setDebitTotal(BigDecimal debitTotal) 
	{
		this.debitTotal = debitTotal;
	}
	public String getEnteredBy() 
	{
		return enteredBy;
	}
	public void setEnteredBy(String enteredBy) 
	{
		this.enteredBy = enteredBy;
	}
	public DateTime getEnteredDate() 
	{
		return enteredDate;
	}
	public void setEnteredDate(DateTime enteredDate) 
	{
		this.enteredDate = enteredDate;
	}
	
}

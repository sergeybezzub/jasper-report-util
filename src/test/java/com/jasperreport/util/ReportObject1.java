package com.jasperreport.util;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class ReportObject1 
{

	private long id;
	private String reference;
	private LocalDate paymentDate;
	private String sortCode;
	private String accountNumber;
	private BigDecimal amount;
	private String text;
	private String name;
	private long index;

	
	
	public long getId() 
	{
		return id;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getReference() 
	{
		return reference;
	}
	
	public void setReference(String reference) 
	{
		this.reference = reference;
	}
	
	public LocalDate getPaymentDate() 
	{
		return paymentDate;
	}
	
	public void setPaymentDate(LocalDate paymentDate) 
	{
		this.paymentDate = paymentDate;
	}
	
	public String getSortCode() 
	{
		return sortCode;
	}
	
	public void setSortCode(String sortCode) 
	{
		this.sortCode = sortCode;
	}
	
	public String getAccountNumber() 
	{
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) 
	{
		this.accountNumber = accountNumber;
	}
	
	public BigDecimal getAmount() 
	{
		return amount;
	}
	
	public void setAmount(BigDecimal amount) 
	{
		this.amount = amount;
	}
	
	public String getText() 
	{
		return text;
	}
	
	public void setText(String text) 
	{
		this.text = text;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public long getIndex() 
	{
		return index;
	}

	public void setIndex(long index) 
	{
		this.index = index;
	}
	
}

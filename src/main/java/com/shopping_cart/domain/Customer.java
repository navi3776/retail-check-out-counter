package com.shopping_cart.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "customer")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerId;

	private String firstName;
	private String lastName;

	/*
	 * Keeping it as string for now, later on if required, we can maintain a
	 * different table for state/country/city
	 */
	private String state;
	private long pinCode;
	private String address;
	private CustomerType customerType;

	public Customer() {
	}

	public Customer(CustomerBuilder customerBuilder) {
		this.firstName = customerBuilder.firstName;
		this.lastName = customerBuilder.lastName;
		this.state = customerBuilder.state;
		this.pinCode = customerBuilder.pinCode;
		this.address = customerBuilder.address;
		this.customerType = customerBuilder.customerType;
	}

	public long getPinCode() {
		return pinCode;
	}

	public void setPinCode(long pinCode) {
		this.pinCode = pinCode;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getPincode() {
		return pinCode;
	}

	public void setPincode(long pincode) {
		this.pinCode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerId != other.customerId)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", state=" + state
				+ ", pincode=" + pinCode + ", address=" + address + "]";
	}

	public static class CustomerBuilder {

		private String state;
		private long pinCode;
		private String address;
		private String firstName;
		private String lastName;
		private CustomerType customerType;

		public CustomerBuilder withState(String state) {
			this.state = state;
			return this;
		}

		public CustomerBuilder withPincode(long pinCode) {
			this.pinCode = pinCode;
			return this;
		}

		public CustomerBuilder withAddress(String address) {
			this.address = address;
			return this;
		}

		public CustomerBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public CustomerBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public CustomerBuilder withCustomerType(CustomerType customerType) {
			this.customerType = customerType;
			return this;
		}

		public Customer build() {
			return new Customer(this);
		}

	}

}

package com.springbatch.dbtocsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRecord {
   private String country;
  private String itemType;
  private String orderId;
  private String unitsSold;
  private String unitPrice;
  private String unitCost;

  @Override
  public String toString() {
    return "{" +
      " country='" + getCountry() + "'" +
      ", itemType='" + getItemType() + "'" +
      ", orderId='" + getOrderId() + "'" +
      "}";
  }

    
}

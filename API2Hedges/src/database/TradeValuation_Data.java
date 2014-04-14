package database;

import java.util.Vector;


public class TradeValuation_Data {

	public String excelTab;
	public String sqlStmt;
	
	public String tradePurpose;
	public String execution;
	public String positionType;
	public String description;
	public String createdOn;
	public String trade;
	public String price;
	public String priceIndex;
	public String acctgMethod;
	public String begDate;
	public String quantStatus;
	public String timePeriod;
	public String begTime;
	public String endTime;
	public String counterparty;
	public String product;
	public String tradebook;
	public String quantity;
	public String marketPrice;
	public String value;
	public String marketValue;
	public String location;
	public String priceStatus;
	public String valuation;
	public String company;
	public String priceQuantity;
	public String moPriceChange = "0";
	public String moPriceChangeDesAra = "0";
	public String diff = "0";
	public String SYSDATE;
	public String DIFFxQUANT = "0";
	public String tradeDate;
	
	public String delim = "|";
	
	public String toString() {
		String out = tradePurpose + delim + execution + delim;
		out += positionType + delim + description + delim;
		out += createdOn + delim + trade + delim;
		out += price + delim + priceIndex + delim + acctgMethod + delim;
		out += begDate + delim + quantStatus + delim + timePeriod + delim;
		out += begTime + delim + endTime + delim + counterparty + delim;
		out += product + delim + tradebook + quantity + marketPrice + delim;
		out += value + delim + marketValue + delim + location + delim;
		out += priceStatus + delim + valuation + delim;
		out += company + delim + priceQuantity + delim + moPriceChange + delim;
		out += moPriceChangeDesAra + delim + diff + delim + SYSDATE + delim;
		out += DIFFxQUANT + delim + tradeDate;

		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.tradePurpose");
		columnHeader.add("B.execution");
		columnHeader.add("C.positionType");
		columnHeader.add("D.description");
		columnHeader.add("E.createdOn");
		columnHeader.add("F.trade");
		columnHeader.add("G.price");
		columnHeader.add("H.priceIndex");
		columnHeader.add("I.acctgMethod");
		columnHeader.add("J.begDate");
		columnHeader.add("K.quantStatus");
		columnHeader.add("L.timePeriod");
		columnHeader.add("M.begTime");
		columnHeader.add("N.endTime");
		columnHeader.add("O.counterparty");
		columnHeader.add("P.product");
		columnHeader.add("Q.tradebook");
		columnHeader.add("R.marketPrice");
		columnHeader.add("S.value");
		columnHeader.add("T.marketValue");
		columnHeader.add("U.location");
		columnHeader.add("V.priceStatus");
		columnHeader.add("W.valuation");
		columnHeader.add("X.company");
		columnHeader.add("Y.priceQuantity");
		columnHeader.add("Z.moPriceChange");
		columnHeader.add("AA.moPriceChangeDesAra");
		columnHeader.add("AB.diff");
		columnHeader.add("AC.SYSDATE");
		columnHeader.add("AD.DIFFxQUANT");
		columnHeader.add("AE.TradeDate");
				
		return columnHeader;
	}
}

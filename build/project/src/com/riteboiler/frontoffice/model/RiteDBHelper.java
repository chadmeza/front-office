package com.riteboiler.frontoffice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * This class is a wrapper for the database
 * and the database class. It is used to 
 * connect to and use the database.
 * @author Chad Meza
 */
public class RiteDBHelper {
	private RiteDB riteDB;
	private Connection connection;
	
	private List<Acknowledgment> acknowledgmentList;
	private List<BillOfLading> billOfLadingList;
	private List<Product> productList;
	private List<Boiler> boilerList;
	private List<Pallet> palletList;
	private List<Address> addressList;
	
	private String acknowledgmentsTable = "acknowledgments";
	private String acknowledgmentsColumns[] = {"id", "date", 
			"soldTo", "shipTo", "notify", "tag", "dateOrderReceived", 
			"customerOrderNumber", "approxShipDate", "shippingCost", "fob", "shipVia", 
			"documents", "price", "username", "aNumber"};
	private String productsTable = "products";
	private String productsColumns[] = {"id", "acknowledgmentId", 
			"quantity", "description"};
	private String addressesTable = "addresses";
	private String addressesColumns[] = {"id", "name", 
			"street1", "city", "state", "zip",
			"country"};
	private String billsOfLadingTable = "bills_of_lading";
	private String billsOfLadingColumns[] = {"id", "acknowledgmentId", 
			"date", "shipTo", "contact", "carrier", "agentNumber",
			"isPrepaid", "isBoilerNoWheels", "isBoilerPart", "isAirRideVerified",
			"isTarped", "notes", "username", "otherPayment", "isAtmospheric"};
	private String boilersTable = "boilers";
	private String boilersColumns[] = {"id", "billOfLadingId", 
			"quantity", "model", "serial", "weight", 
			"dimensions"};
	private String palletsTable = "pallets";
	private String palletsColumns[] = {"id", "billOfLadingId", 
			"quantity", "description", "weight", 
			"dimensions"};
	private String errorTable = "error_log";
	private String errorColumns[] = {"id", "description", "username", "timestamp"};
	
	/**
	 * This constructor establishes the connection
	 * to the database, gets the database table name,
	 * and instantiates the lists.
	 */
	public RiteDBHelper() {
		riteDB = new RiteDB();
		
		connection = riteDB.getConnection();
				
		acknowledgmentList = new ArrayList<Acknowledgment>();
		billOfLadingList = new ArrayList<BillOfLading>();
		productList = new ArrayList<Product>();
		boilerList = new ArrayList<Boiler>();
		palletList = new ArrayList<Pallet>();
		addressList = new ArrayList<Address>();
	}
	
	/**
	 * Queries database for all acknowledgments.
	 * @return List of acknowledgments
	 */
	public List<Acknowledgment> retrieveAcknowledgmentList() {
		acknowledgmentList.clear();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + acknowledgmentsTable + 
	        " ORDER BY aNumber";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	String date = rs.getString(2);
	            int soldTo = rs.getInt(3);
				int shipTo = rs.getInt(4);
				String notify = rs.getString(5);
				String tag = rs.getString(6);
				String dateOrderReceived = rs.getString(7);
				String customerOrderNumber = rs.getString(8);
				String approxShipDate = rs.getString(9);
				String shippingCost = rs.getString(10);
				String fob = rs.getString(11);
				String shipVia = rs.getString(12);
				String documents = rs.getString(13);
				String price = rs.getString(14);
				String username = rs.getString(15);
				String aNumber = rs.getString(16);
				
				String soldToName = retrieveAddressName(soldTo);
				String shipToName = retrieveAddressName(shipTo);
				
				Address soldToAddress = retrieveAddress(soldTo);
				Address shipToAddress = retrieveAddress(shipTo);
					            
	            Acknowledgment acknowledgment = new Acknowledgment(id, aNumber, date, soldTo, shipTo, notify, tag,
	        			dateOrderReceived, customerOrderNumber, approxShipDate, shippingCost, fob, shipVia,
	        			documents, price, username, soldToName, shipToName);
	            
	            acknowledgment.setSoldToAddress(soldToAddress);
	            acknowledgment.setShipToAddress(shipToAddress);
	            acknowledgment.getProductData().addAll(retrieveProductList(id));
	            
	            acknowledgmentList.add(acknowledgment);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return acknowledgmentList;
	}
	
	/**
	 * Queries database for acknowledgment that matches the given ID.
	 * @return Acknowledgment that matches the ID
	 */
	public Acknowledgment loadAcknowledgment(String ackNumber) {
		Statement stmt = null;
		Acknowledgment acknowledgment = new Acknowledgment();
	    
		String query =
	        "SELECT * " +
	        "FROM " + acknowledgmentsTable + 
	        " WHERE aNumber = '" + ackNumber + "'";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	String date = rs.getString(2);
	            int soldTo = rs.getInt(3);
				int shipTo = rs.getInt(4);
				String notify = rs.getString(5);
				String tag = rs.getString(6);
				String dateOrderReceived = rs.getString(7);
				String customerOrderNumber = rs.getString(8);
				String approxShipDate = rs.getString(9);
				String shippingCost = rs.getString(10);
				String fob = rs.getString(11);
				String shipVia = rs.getString(12);
				String documents = rs.getString(13);
				String price = rs.getString(14);
				String username = rs.getString(15);
				String aNumber = rs.getString(16);
				
				String soldToName = retrieveAddressName(soldTo);
				String shipToName = retrieveAddressName(shipTo);
				
				Address soldToAddress = retrieveAddress(soldTo);
				Address shipToAddress = retrieveAddress(shipTo);
					            
	            acknowledgment = new Acknowledgment(id, aNumber, date, soldTo, shipTo, notify, tag,
	        			dateOrderReceived, customerOrderNumber, approxShipDate, shippingCost, fob, shipVia,
	        			documents, price, username, soldToName, shipToName);
	            
	            acknowledgment.setSoldToAddress(soldToAddress);
	            acknowledgment.setShipToAddress(shipToAddress);
	            acknowledgment.getProductData().addAll(retrieveProductList(id));
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return acknowledgment;
	}
	
	/**
	 * Returns the last inserted acknowledgment.
	 * @return Acknowledgment to be edited
	 */
	public Acknowledgment getLastAcknowledgment() {
		Statement stmt = null;
		Acknowledgment acknowledgment = null;
	    
		String query =
	        "SELECT id " +
	        "FROM " + acknowledgmentsTable +
	        " ORDER BY " + acknowledgmentsColumns[0] + " DESC";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        rs.next();
	        int id = rs.getInt(1);
            
	        acknowledgment = new Acknowledgment();
	        acknowledgment.setId(id);
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return acknowledgment;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the properties from the passed in
	 * acknowledgment.
	 * @param acknowledgment Acknowledgment to add to the database
	 */
	public void insertAcknowledgment(Acknowledgment acknowledgment) {
		String query = "INSERT INTO " + acknowledgmentsTable + " (" + acknowledgmentsColumns[1] + ", " + acknowledgmentsColumns[2] + ", " +
				acknowledgmentsColumns[3] + ", " + acknowledgmentsColumns[4] + ", " + acknowledgmentsColumns[5] + ", " + acknowledgmentsColumns[6] + ", " + 
				acknowledgmentsColumns[7] + ", " + acknowledgmentsColumns[8] + ", " + acknowledgmentsColumns[9] + ", " + acknowledgmentsColumns[10] + ", " + 
				acknowledgmentsColumns[11] + ", " + acknowledgmentsColumns[12] + ", " + acknowledgmentsColumns[13] + ", " + acknowledgmentsColumns[14] + ", " +
				acknowledgmentsColumns[15] 
						+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, acknowledgment.getDate());
			stmt.setInt(2, acknowledgment.getSoldTo());
			stmt.setInt(3, acknowledgment.getShipTo());
			stmt.setString(4, acknowledgment.getNotify());
			stmt.setString(5, acknowledgment.getTag());
			stmt.setString(6, acknowledgment.getDateOrderReceived());
			stmt.setString(7, acknowledgment.getCustomerOrderNumber());
			stmt.setString(8, acknowledgment.getApproxShipDate());
			stmt.setString(9, acknowledgment.getShippingCost());
			stmt.setString(10, acknowledgment.getFob());
			stmt.setString(11, acknowledgment.getShipVia());
			stmt.setString(12, acknowledgment.getDocuments());
			stmt.setString(13, acknowledgment.getPrice());
			stmt.setString(14, acknowledgment.getUsername());
			stmt.setString(15, acknowledgment.getANumber());
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Updates the database record with the
	 * properties of the passed in acknowledgment.
	 * @param acknowledgment Acknowledgment to update in the database
	 */
	public void updateAcknowledgment(Acknowledgment acknowledgment) {
		String query = "UPDATE " + acknowledgmentsTable + " SET " + acknowledgmentsColumns[1] + "=?, " + acknowledgmentsColumns[2] + "=?, " +
				acknowledgmentsColumns[3] + "=?, " + acknowledgmentsColumns[4] + "=?, " + acknowledgmentsColumns[5] + "=?, " +
				acknowledgmentsColumns[6] + "=?, " + acknowledgmentsColumns[7] + "=?, " + acknowledgmentsColumns[8] + "=?, " +
				acknowledgmentsColumns[9] + "=?, " + acknowledgmentsColumns[10] + "=?, " + acknowledgmentsColumns[11] + "=?, " +
				acknowledgmentsColumns[12] + "=?, " + acknowledgmentsColumns[13] + "=?, " + acknowledgmentsColumns[14] + "=?, " +
				acknowledgmentsColumns[15] + "=? " +
				"WHERE " + acknowledgmentsColumns[0] + "=?";

		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);

			stmt.setString(1, acknowledgment.getDate());
			stmt.setInt(2, acknowledgment.getSoldTo());
			stmt.setInt(3, acknowledgment.getShipTo());
			stmt.setString(4, acknowledgment.getNotify());
			stmt.setString(5, acknowledgment.getTag());
			stmt.setString(6, acknowledgment.getDateOrderReceived());
			stmt.setString(7, acknowledgment.getCustomerOrderNumber());
			stmt.setString(8, acknowledgment.getApproxShipDate());
			stmt.setString(9, acknowledgment.getShippingCost());
			stmt.setString(10, acknowledgment.getFob());
			stmt.setString(11, acknowledgment.getShipVia());
			stmt.setString(12, acknowledgment.getDocuments());
			stmt.setString(13, acknowledgment.getPrice());
			stmt.setString(14, acknowledgment.getUsername());
			stmt.setString(15, acknowledgment.getANumber());
			stmt.setInt(16, acknowledgment.getId());
			
			int rowsUpdated = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Deletes the passed in acknowledgment from the database.
	 * It also deletes products attached to this acknowledgment.
	 * @param acknowledgment Acknowledgment to delete from the database
	 */
	public void deleteAcknowledgment(Acknowledgment acknowledgment) {
		String query = "DELETE FROM " + acknowledgmentsTable + " WHERE " + acknowledgmentsColumns[0] + "=?";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, acknowledgment.getId());
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
		
		query = "DELETE FROM " + productsTable + " WHERE " + productsColumns[1] + "=?";
		 
		stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, acknowledgment.getId());
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Queries database for all bills of lading.
	 * @return List of bills of lading
	 */
	public List<BillOfLading> retrieveBillOfLadingList() {
		billOfLadingList.clear();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + billsOfLadingTable + 
	        " ORDER BY acknowledgmentId";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	String acknowledgmentId = rs.getString(2);
				String date = rs.getString(3);
				int shipTo = rs.getInt(4);
				String contact = rs.getString(5);
				String carrier = rs.getString(6);
				String agentNumber = rs.getString(7);
				int isPrepaid = rs.getInt(8);
				int isBoilerNoWheels = rs.getInt(9);
				int isBoilerPart = rs.getInt(10);
				int isAirRideVerified = rs.getInt(11);
				int isTarped = rs.getInt(12);
				String notes = rs.getString(13);
				String username = rs.getString(14);
				String otherPayment = rs.getString(15);
				boolean isAtmospheric = rs.getBoolean(16);
				
				String shipToName = retrieveAddressName(shipTo);
				Address shipToAddress = retrieveAddress(shipTo);
	            
	            BillOfLading billOfLading = new BillOfLading(id, acknowledgmentId, date, shipTo, contact, carrier,
	        			agentNumber, isPrepaid, isBoilerNoWheels, isBoilerPart, isAirRideVerified, isTarped,
	        			notes, username, shipToName, otherPayment, isAtmospheric);
	            
	            billOfLading.setShipToAddress(shipToAddress);
	            billOfLading.getBoilerData().addAll(retrieveBoilerList(id));
	            billOfLading.getPalletData().addAll(retrievePalletList(id));
	            
	            billOfLadingList.add(billOfLading);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return billOfLadingList;
	}
	
	/**
	 * Returns the last inserted bill of lading.
	 * @return BillOfLading to be edited
	 */
	public BillOfLading getLastBillOfLading() {
		Statement stmt = null;
		BillOfLading billOfLading = null;
	    
		String query =
	        "SELECT id " +
	        "FROM " + billsOfLadingTable +
	        " ORDER BY " + billsOfLadingColumns[0] + " DESC";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        rs.next();
	        int id = rs.getInt(1);
				
			billOfLading = new BillOfLading();
			billOfLading.setId(id);
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return billOfLading;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the properties from the passed in
	 * bill of lading.
	 * @param billOfLading Bill Of Lading to add to the database
	 */
	public void insertBillOfLading(BillOfLading billOfLading) {
		String query = "INSERT INTO " + billsOfLadingTable + " (" + billsOfLadingColumns[1] + ", " + billsOfLadingColumns[2] + ", " +
				billsOfLadingColumns[3] + ", " + billsOfLadingColumns[4] + ", " + billsOfLadingColumns[5] + ", " + billsOfLadingColumns[6] + ", " + 
				billsOfLadingColumns[7] + ", " + billsOfLadingColumns[8] + ", " + billsOfLadingColumns[9] + ", " + billsOfLadingColumns[10] + ", " + 
				billsOfLadingColumns[11] + ", " + billsOfLadingColumns[12] + ", " + billsOfLadingColumns[13] + ", " + billsOfLadingColumns[14] + ", " +
				billsOfLadingColumns[15]
						+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, billOfLading.getAcknowledgmentId());
			stmt.setString(2, billOfLading.getDate());
			stmt.setInt(3, billOfLading.getShipTo());
			stmt.setString(4, billOfLading.getContact());
			stmt.setString(5, billOfLading.getCarrier());
			stmt.setString(6, billOfLading.getAgentNumber());
			stmt.setInt(7, billOfLading.getIsPrepaid() == true ? 1 : 0);
			stmt.setInt(8, billOfLading.getIsBoilerNoWheels() == true ? 1 : 0);
			stmt.setInt(9, billOfLading.getIsBoilerPart() == true ? 1 : 0);
			stmt.setInt(10, billOfLading.getIsAirRideVerified() == true ? 1 : 0);
			stmt.setInt(11, billOfLading.getIsTarped() == true ? 1 : 0);
			stmt.setString(12, billOfLading.getNotes());
			stmt.setString(13, billOfLading.getUsername());
			stmt.setString(14,  billOfLading.getOtherPayment());
			stmt.setBoolean(15, billOfLading.getIsAtmospheric());
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Updates the database record with the
	 * properties of the passed in bill of lading.
	 * @param billOfLading Bill Of Lading to update in the database
	 */
	public void updateBillOfLading(BillOfLading billOfLading) {
		String query = "UPDATE " + billsOfLadingTable + " SET " + billsOfLadingColumns[1] + "=?, " + billsOfLadingColumns[2] + "=?, " +
				billsOfLadingColumns[3] + "=?, " + billsOfLadingColumns[4] + "=?, " + billsOfLadingColumns[5] + "=?, " +
				billsOfLadingColumns[6] + "=?, " + billsOfLadingColumns[7] + "=?, " + billsOfLadingColumns[8] + "=?, " +
				billsOfLadingColumns[9] + "=?, " + billsOfLadingColumns[10] + "=?, " + billsOfLadingColumns[11] + "=?, " +
				billsOfLadingColumns[12] + "=?, " + billsOfLadingColumns[13] + "=?, " + billsOfLadingColumns[14] + "=?, " +
				billsOfLadingColumns[15] + "=? " +
				"WHERE " + billsOfLadingColumns[0] + "=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);

			stmt.setString(1, billOfLading.getAcknowledgmentId());
			stmt.setString(2, billOfLading.getDate());
			stmt.setInt(3, billOfLading.getShipTo());
			stmt.setString(4, billOfLading.getContact());
			stmt.setString(5, billOfLading.getCarrier());
			stmt.setString(6, billOfLading.getAgentNumber());
			stmt.setInt(7, billOfLading.getIsPrepaid() == true ? 1 : 0);
			stmt.setInt(8, billOfLading.getIsBoilerNoWheels() == true ? 1 : 0);
			stmt.setInt(9, billOfLading.getIsBoilerPart() == true ? 1 : 0);
			stmt.setInt(10, billOfLading.getIsAirRideVerified() == true ? 1 : 0);
			stmt.setInt(11, billOfLading.getIsTarped() == true ? 1 : 0);
			stmt.setString(12, billOfLading.getNotes());
			stmt.setString(13, billOfLading.getUsername());
			stmt.setString(14,  billOfLading.getOtherPayment());
			stmt.setBoolean(15, billOfLading.getIsAtmospheric());
			stmt.setInt(16, billOfLading.getId());
			
			int rowsUpdated = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Deletes the passed in bill of lading from the database.
	 * It also deletes boilers and pallets attached to the bill of lading.
	 * @param billOfLading Bill Of Lading to delete from the database
	 */
	public void deleteBillOfLading(BillOfLading billOfLading) {
		String query = "DELETE FROM " + billsOfLadingTable + " WHERE " + billsOfLadingColumns[0] + "=?";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, billOfLading.getId());
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
		
		query = "DELETE FROM " + boilersTable + " WHERE " + boilersColumns[1] + "=?";
		 
		stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, billOfLading.getId());
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
		
		query = "DELETE FROM " + palletsTable + " WHERE " + palletsColumns[1] + "=?";
		 
		stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, billOfLading.getId());
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Queries database for all products related
	 * to a particular acknowledgment.
	 * @param ackId Acknowledgment ID.
	 * @return List of products
	 */
	public List<Product> retrieveProductList(int ackId) {
		productList.clear();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + productsTable +
	        " WHERE acknowledgmentId = " + ackId;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	int acknowledgmentId = rs.getInt(2);
				int quantity = rs.getInt(3);
				String description = rs.getString(4);
	            
	            Product product = new Product(id, acknowledgmentId, quantity, description);
	            
	            productList.add(product);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return productList;
	}
	
	/**
	 * Queries database for all product descriptions.
	 * @return TreeSet of product descriptions
	 */
	public TreeSet<String> retrieveProductDescriptionList() {
		TreeSet<String> productDescriptions = new TreeSet<>();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT description " +
	        "FROM " + productsTable;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	String description = rs.getString(1);
	            
	            productDescriptions.add(description);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return productDescriptions;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the properties from the passed in
	 * product.
	 * @param product Product to add to the database
	 */
	public void insertProduct(Product product) {
		String query = "INSERT INTO " + productsTable + " (" + productsColumns[1] + ", " + productsColumns[2] + ", " +
				productsColumns[3]
						+ ") VALUES (?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setInt(1, product.getAcknowledgmentId());
			stmt.setInt(2, product.getQuantity());
			stmt.setString(3, product.getDescription());
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Deletes products from the database
	 * that are related to a particular
	 * acknowledgment.
	 * @param ackId Acknowledgment ID
	 */
	public void removeProducts(int ackId) {
		String query = "DELETE FROM " + productsTable + " WHERE " + productsColumns[1] + "=?";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, ackId);
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Queries database for all boilers
	 * related to a particular bill of lading.
	 * @param billId BillOfLading ID.
	 * @return List of boilers
	 */
	public List<Boiler> retrieveBoilerList(int billId) {
		boilerList.clear();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + boilersTable +
	        " WHERE billOfLadingId = " + billId;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	int billOfLadingId = rs.getInt(2);
				int quantity = rs.getInt(3);
				String model = rs.getString(4);
				String serial = rs.getString(5);
				String weight = rs.getString(6);
				String dimensions = rs.getString(7);
	            
	            Boiler boiler = new Boiler(id, billOfLadingId, quantity,
	            		model, serial, weight, dimensions);
	            
	            boilerList.add(boiler);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return boilerList;
	}
	
	/**
	 * Queries database for all boiler models.
	 * @return TreeSet of boiler models
	 */
	public TreeSet<String> retrieveBoilerModelList() {
		TreeSet<String> boilerModels = new TreeSet<>();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT model " +
	        "FROM " + boilersTable;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	String model = rs.getString(1);
	            
	        	boilerModels.add(model);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return boilerModels;
	}
	
	/**
	 * Queries database for "1" boiler
	 * related to a particular model.
	 * @param model String boiler model.
	 * @return First boiler
	 */
	public Boiler retrieveBoilerFromModel(String modelString) {
		Boiler boiler = new Boiler();
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + boilersTable +
	        " WHERE model = '" + modelString + "'";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	int billOfLadingId = rs.getInt(2);
				int quantity = rs.getInt(3);
				String model = rs.getString(4);
				String serial = rs.getString(5);
				String weight = rs.getString(6);
				String dimensions = rs.getString(7);
	            
	            boiler = new Boiler(id, billOfLadingId, quantity,
	            		model, serial, weight, dimensions);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return boiler;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the properties from the passed in
	 * boiler.
	 * @param boiler Boiler to add to the database
	 */
	public void insertBoiler(Boiler boiler) {
		String query = "INSERT INTO " + boilersTable + " (" + boilersColumns[1] + ", " + boilersColumns[2] + ", " +
				boilersColumns[3] + ", " + boilersColumns[4] + ", " + boilersColumns[5] + ", " + 
				boilersColumns[6]
						+ ") VALUES (?, ?, ?, ?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setInt(1, boiler.getBillOfLadingId());
			stmt.setInt(2, boiler.getQuantity());
			stmt.setString(3, boiler.getModel());
			stmt.setString(4, boiler.getSerial());
			stmt.setString(5, boiler.getWeight());
			stmt.setString(6, boiler.getDimensions());
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Deletes boilers from the database
	 * that are related to a particular
	 * bill of lading.
	 * @param billId Bill Of Lading ID
	 */
	public void removeBoilers(int billId) {
		String query = "DELETE FROM " + boilersTable + " WHERE " + boilersColumns[1] + "=?";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, billId);
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Queries database for all pallets
	 * related to a particular bill of lading.
	 * @param billId BillOfLading ID.
	 * @return List of pallets
	 */
	public List<Pallet> retrievePalletList(int billId) {
		palletList.clear();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + palletsTable +
	        " WHERE billOfLadingId = " + billId;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	int billOfLadingId = rs.getInt(2);
				int quantity = rs.getInt(3);
				String description = rs.getString(4);
				String weight = rs.getString(5);
				String dimensions = rs.getString(6);
	            
	            Pallet pallet = new Pallet(id, billOfLadingId, quantity,
	            		description, weight, dimensions);
	            
	            palletList.add(pallet);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return palletList;
	}
	
	/**
	 * Queries database for all pallet descriptions.
	 * @return TreeSet of pallet descriptions
	 */
	public TreeSet<String> retrievePalletDescriptionList() {
		TreeSet<String> palletDescriptions = new TreeSet<>();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT description " +
	        "FROM " + palletsTable;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	String description = rs.getString(1);
	            
	        	palletDescriptions.add(description);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return palletDescriptions;
	}
	
	/**
	 * Queries database for "1" pallet
	 * related to a particular description.
	 * @param model String pallet description.
	 * @return First pallet
	 */
	public Pallet retrievePalletFromDescription(String descriptionString) {
		Pallet pallet = new Pallet();
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + palletsTable +
	        " WHERE description = '" + descriptionString + "'";

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	int billOfLadingId = rs.getInt(2);
				int quantity = rs.getInt(3);
				String description = rs.getString(4);
				String weight = rs.getString(5);
				String dimensions = rs.getString(6);
	            
	            pallet = new Pallet(id, billOfLadingId, quantity,
	            		description, weight, dimensions);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return pallet;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the properties from the passed in
	 * pallet.
	 * @param pallet Pallet to add to the database
	 */
	public void insertPallet(Pallet pallet) {
		String query = "INSERT INTO " + palletsTable + " (" + palletsColumns[1] + ", " + palletsColumns[2] + ", " +
				palletsColumns[3] + ", " + palletsColumns[4] + ", " + palletsColumns[5]
						+ ") VALUES (?, ?, ?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setInt(1, pallet.getBillOfLadingId());
			stmt.setInt(2, pallet.getQuantity());
			stmt.setString(3, pallet.getDescription());
			stmt.setString(4, pallet.getWeight());
			stmt.setString(5, pallet.getDimensions());
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Deletes pallets from the database
	 * that are related to a particular
	 * bill of lading.
	 * @param billId Bill Of Lading ID
	 */
	public void removePallets(int billId) {
		String query = "DELETE FROM " + palletsTable + " WHERE " + palletsColumns[1] + "=?";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, billId);
			
			int rowsDeleted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Queries database for all addresses.
	 * @return List of addresses
	 */
	public List<Address> retrieveAddressList() {
		addressList.clear();
		
		Statement stmt = null;
	    
		String query =
	        "SELECT * " +
	        "FROM " + addressesTable + " ORDER BY " + addressesColumns[1];

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
	        	String name = rs.getString(2);
				String street1 = rs.getString(3);
				String city = rs.getString(4);
				String state = rs.getString(5);
				String zip = rs.getString(6);
				String country = rs.getString(7);
	            
	            Address address = new Address(id, name, street1, city, state, 
	            		zip, country);
	            
	            addressList.add(address);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return addressList;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the properties from the passed in
	 * address.
	 * @param address Address to add to the database
	 */
	public void insertAddress(Address address) {
		String query = "INSERT INTO " + addressesTable + " (" + addressesColumns[1] + ", " + addressesColumns[2] + ", " +
				addressesColumns[3] + ", " + addressesColumns[4] + ", " + addressesColumns[5] + ", " + addressesColumns[6]
						+ ") VALUES (?, ?, ?, ?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, address.getName());
			stmt.setString(2, address.getStreet1());
			stmt.setString(3, address.getCity());
			stmt.setString(4, address.getState());
			stmt.setString(5, address.getZip());
			stmt.setString(6, address.getCountry());
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Updates the database record with the
	 * properties of the passed in address.
	 * @param address Address to update in the database
	 */
	public void updateAddress(Address address) {
		String query = "UPDATE " + addressesTable + " SET " + addressesColumns[1] + "=?, " + addressesColumns[2] + "=?, " +
				addressesColumns[3] + "=?, " + addressesColumns[4] + "=?, " + addressesColumns[5] + "=?, " +
				addressesColumns[6] + "=? " + 
				" WHERE " + addressesColumns[0] + "=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);

			stmt.setString(1, address.getName());
			stmt.setString(2, address.getStreet1());
			stmt.setString(3, address.getCity());
			stmt.setString(4, address.getState());
			stmt.setString(5, address.getZip());
			stmt.setString(6, address.getCountry());
			stmt.setInt(7,  address.getId());
			
			int rowsUpdated = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	/**
	 * Queries database for an address'
	 * name property.
	 * @param addressId Address ID.
	 * @return Name property for the address
	 */
	public String retrieveAddressName(int addressId) {
		Statement stmt = null;
		String name = null;
	    
		String query =
	        "SELECT name " +
	        "FROM " + addressesTable +
	        " WHERE id = " + addressId;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	name = rs.getString(1);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return name;
	}
	
	/**
	 * Queries database for an address.
	 * @param addressId Address ID.
	 * @return Address object
	 */
	public Address retrieveAddress(int addressId) {
		Statement stmt = null;
		Address address = null;
		
		String query =
	        "SELECT * " +
	        "FROM " + addressesTable +
	        " WHERE id = " + addressId;

	    try {
	    	stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	int id = rs.getInt(1);
				String name = rs.getString(2);
				String street1 = rs.getString(3);
				String city = rs.getString(4);
				String state = rs.getString(5);
				String zip = rs.getString(6);
				String country = rs.getString(7);
	        	
	        	address = new Address(id, name, street1, 
	        			city, state, zip, country);
	        }
	    } catch (SQLException e ) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { 
	        	try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	    }
	    
	    return address;
	}
	
	/**
	 * Inserts a new record into the database
	 * using the details from the passed in
	 * exception.
	 * @param e Exception to add to the error log
	 */
	public void insertError(Exception e, String username) {
		String query = "INSERT INTO " + errorTable + " (" + errorColumns[1] + ", " + errorColumns[2]
				+ ", " + errorColumns[3]
						+ ") VALUES (?, ?, ?)";
		 
		PreparedStatement stmt = null;
		
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy:HH-mm-ss");
		String myDateString = sdf.format(myDate);
		
		try {
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, "" + e.getMessage());
			stmt.setString(2, username);
			stmt.setString(3, myDateString);
			
			int rowsInserted = stmt.executeUpdate();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
	        if (stmt != null) { 
	        	try {
	        		stmt.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
	        }
	    }
	}
}

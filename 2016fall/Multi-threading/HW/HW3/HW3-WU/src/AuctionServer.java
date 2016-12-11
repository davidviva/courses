
/**
 *  @author Yan WU
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuctionServer {
	/**
	 * Singleton: the following code makes the server a Singleton. You should
	 * not edit the code in the following noted section.
	 * 
	 * For test purposes, we made the constructor protected.
	 */

	/**
	 * @Invariant: each bidder offers number of bids for each item < maxBidCount,
	 *             the number of items that each seller offers < maxSellerItems,
	 *             the number of items that all sellers offer < serverCapacity, 
	 *             the number of items that are currently open for bidding < serverCapacity, 
	 *             the current highest bidding price = max(biddingAmount) 
	 *             the bidding price > the item's lowestBiddingPrice
	 */
	
	/* Singleton: Begin code that you SHOULD NOT CHANGE! */
	protected AuctionServer() {
	}

	private static AuctionServer instance = new AuctionServer();

	public static AuctionServer getInstance() {
		return instance;
	}

	/* Singleton: End code that you SHOULD NOT CHANGE! */

	/*
	 * Statistic variables and server constants: Begin code you should likely
	 * leave alone.
	 */

	/**
	 * Server statistic variables and access methods:
	 */
	private int soldItemsCount = 0;
	private int revenue = 0;

	public int soldItemsCount() {
		return this.soldItemsCount;
	}

	public int revenue() {
		return this.revenue;
	}

	/**
	 * Server restriction constants:
	 */
	public static final int maxBidCount = 10; // The maximum number of bids at
												// any given time for a buyer.
	public static final int maxSellerItems = 20; // The maximum number of items
													// that a seller can submit
													// at any given time.
	public static final int serverCapacity = 80; // The maximum number of active
													// items at a given time.

	/*
	 * Statistic variables and server constants: End code you should likely
	 * leave alone.
	 */

	/**
	 * Some variables we think will be of potential use as you implement the
	 * server...
	 */

	// List of items currently up for bidding (will eventually remove things
	// that have expired).
	private List<Item> itemsUpForBidding = new ArrayList<Item>();

	// The last value used as a listing ID. We'll assume the first thing added
	// gets a listing ID of 0.
	private int lastListingID = -1;

	// List of item IDs and actual items. This is a running list with everything
	// ever added to the auction.
	private HashMap<Integer, Item> itemsAndIDs = new HashMap<Integer, Item>();

	// List of itemIDs and the highest bid for each item. This is a running list
	// with everything ever added to the auction.
	private HashMap<Integer, Integer> highestBids = new HashMap<Integer, Integer>();

	// List of itemIDs and the person who made the highest bid for each item.
	// This is a running list with everything ever bid upon.
	private HashMap<Integer, String> highestBidders = new HashMap<Integer, String>();

	// List of sellers and how many items they have currently up for bidding.
	private HashMap<String, Integer> itemsPerSeller = new HashMap<String, Integer>();

	// List of buyers and how many items on which they are currently bidding.
	private HashMap<String, Integer> itemsPerBuyer = new HashMap<String, Integer>();

	// Object used for instance synchronization if you need to do it at some
	// point
	// since as a good practice we don't use synchronized (this) if we are doing
	// internal
	// synchronization.
	//
//	private Object instanceLock = new Object();

	/*
	 * The code from this point forward can and should be changed to correctly
	 * and safely implement the methods as needed to create a working
	 * multi-threaded server for the system. If you need to add Object instances
	 * here to use for locking, place a comment with them saying what they
	 * represent. Note that if they just represent one structure then you should
	 * probably be using that structure's intrinsic lock.
	 */

	/**
	 * Attempt to submit an <code>Item</code> to the auction
	 * 
	 * @param sellerName
	 *            Name of the <code>Seller</code>
	 * @param itemName
	 *            Name of the <code>Item</code>
	 * @param lowestBiddingPrice
	 *            Opening price
	 * @param biddingDurationMs
	 *            Bidding duration in milliseconds
	 * @return A positive, unique listing ID if the <code>Item</code> listed
	 *         successfully, otherwise -1
	 */
	
	/**
	 * Precondition: lowestBidderPrice > 0 and biddingDurationMs > 0, itemsAndIDs, itemsPerSeller, itemsUpForBidding are not null
	 * Postcondition: return lastlistingID when success, or return -1
	 * Exception: Nothing
	 */
	public synchronized int submitItem(String sellerName, String itemName, int lowestBiddingPrice, int biddingDurationMs) {
		
		// Make sure there's room in the auction site.
		if (itemsUpForBidding.size() >= serverCapacity) {
			return -1;
		} 
		// If the seller is a new one, add them to the list of sellers.
		// If the seller has too many items up for bidding, don't let them add
		// this one.
		if (!itemsPerSeller.containsKey(sellerName)) {
			itemsPerSeller.put(sellerName, 0);
		} else if (itemsPerSeller.get(sellerName) >= maxSellerItems) {
			return -1;
		}
		lastListingID += 1;
		Item item = new Item(sellerName, itemName, lastListingID, 
				lowestBiddingPrice, biddingDurationMs);
		// Don't forget to increment the number of things the seller has
		// currently listed.
		int curNum = itemsPerSeller.get(sellerName) + 1;
		itemsPerSeller.put(sellerName, curNum);
		itemsUpForBidding.add(item);
		itemsAndIDs.put(lastListingID, item);
		
		return lastListingID;
	}

	/**
	 * Get all <code>Items</code> active in the auction
	 * 
	 * @return A copy of the <code>List</code> of <code>Items</code>
	 */
	/**
	 * Precondition: Nothing
	 * PostCondidtion: returns items that are currently bidding
	 * Exception:  Nothing
	 */
	public List<Item> getItems() {
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		// Don't forget that whatever you return is now outside of your control.
		synchronized(itemsUpForBidding) {
			ArrayList<Item> currentItems = new ArrayList<Item>(itemsUpForBidding);
			return currentItems;
		}
	}

	/**
	 * Attempt to submit a bid for an <code>Item</code>
	 * 
	 * @param bidderName
	 *            Name of the <code>Bidder</code>
	 * @param listingID
	 *            Unique ID of the <code>Item</code>
	 * @param biddingAmount
	 *            Total amount to bid
	 * @return True if successfully bid, false otherwise
	 */
	
	/**
	 * Precondition: listingID >= 0 and cash > biddingAmount >= 0, itemsPerBuyer, itemsUpForBidding, highestBidder, highestBids are not null
	 * Postcondition: return true when successfully bid, or return false
	 * Exception: Nothing
	 */
	public synchronized boolean submitBid(String bidderName, int listingID, int biddingAmount) {
		// See if the item exists.
		// See if it can be bid upon.
		// See if this bidder has too many items in their bidding list.
		// Get current bidding info.
		// See if they already hold the highest bid.
		// See if the new bid isn't better than the existing/opening bid floor.
		// Decrement the former winning bidder's count
		// Put your bid in place
		
		if (!itemsAndIDs.containsKey(listingID)) {
			return false;
		}
		if (!itemsUpForBidding.contains(itemsAndIDs.get(listingID))) {
			return false;
		}
		// check if the bidder qualified
		int bidCount = 0;
		if (itemsPerBuyer.isEmpty() || itemsPerBuyer.get(bidderName) == null) {
			itemsPerBuyer.put(bidderName, bidCount);
		} else {
			bidCount = itemsPerBuyer.get(bidderName);
			if (bidCount >= maxBidCount) {
				return false;
			}
		}
		// check if the bid amount qualified
		if (biddingAmount < itemsAndIDs.get(listingID).lowestBiddingPrice()) {
			return false;
		}
		
		if (highestBidders.containsKey(listingID)){
			String curName = highestBidders.get(listingID);
			if (curName == bidderName) {
				return false;
			}
			if (biddingAmount <= highestBids.get(listingID)) {
					return false;
			} else {
				itemsPerBuyer.put(curName, itemsPerBuyer.get(curName) - 1);
				highestBids.put(listingID, biddingAmount);
				highestBidders.put(listingID, bidderName);
				itemsPerBuyer.put(bidderName, ++bidCount);
				return true;
			}
		} else {
			highestBids.put(listingID, biddingAmount);
			highestBidders.put(listingID, bidderName);
			itemsPerBuyer.put(bidderName, ++bidCount);
			return true;
		}
	}

	/**
	 * Check the status of a <code>Bidder</code>'s bid on an <code>Item</code>
	 * 
	 * @param bidderName
	 *            Name of <code>Bidder</code>
	 * @param listingID
	 *            Unique ID of the <code>Item</code>
	 * @return 1 (success) if bid is over and this <code>Bidder</code> has won
	 *         <br>
	 *         2 (open) if this <code>Item</code> is still up for auction<br>
	 *         3 (failed) If this <code>Bidder</code> did not win or the
	 *         <code>Item</code> does not exist
	 */
	
	/**
	 * Precondition: itemsAndIDs, itemsPerSeller, higestBids, itemsUpForBidding, itemsPerBuyer are not null, 
	 * 			highest bid price > lowestBiddingPrice, listingID >= 0
	 * Postcondition: return 1 or 2 or 3 to represent different bid status
	 * Exception: Nothing
	 */
	public int checkBidStatus(String bidderName, int listingID) {
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		// If the bidding is closed, clean up for that item.
		// Remove item from the list of things up for bidding.
		// Decrease the count of items being bid on by the winning bidder if
		// there was any...
		// Update the number of open bids for this seller
		
		// check if the item exists
		if (!itemsAndIDs.containsKey(listingID)) {
			return 3;
		}
		Item item = itemsAndIDs.get(listingID);
		String seller = item.seller();
		
		if (item.biddingOpen()){
			return 2;
		}  else {
			synchronized (itemsUpForBidding) {
				if (highestBids.containsKey(listingID)){
					revenue += highestBids.get(listingID);
					soldItemsCount++;
				}
				itemsUpForBidding.remove(item);
				itemsPerSeller.put(seller, itemsPerSeller.get(seller) - 1);
			}
			// check the winner
			String winner = highestBidders.get(listingID);
			itemsPerBuyer.put(bidderName, itemsPerBuyer.get(winner) - 1);
			if(winner == bidderName){
				return 1;
			}else{
				return 3;
			}
		}
	}

	/**
	 * Check the current bid for an <code>Item</code>
	 * 
	 * @param listingID
	 *            Unique ID of the <code>Item</code>
	 * @return The highest bid so far or the opening price if no bid has been
	 *         made, -1 if no <code>Item</code> exists
	 */
	
	/**
	 * Precondition: itemsUpForBidding, highestBids are not null, listingID >= 0
	 * Postcondition: itemPrice >= items.lowestBiddingPrice
	 * Exception: Nothing
	 */
	public int itemPrice(int listingID) {
		// get the highest price so far
		if (highestBids.containsKey(listingID)){
			return highestBids.get(listingID);
		} 
		// no one bid, get the floor price
		else {
			synchronized(itemsAndIDs){
				if (!itemsAndIDs.containsKey(listingID)) {
					return -1;
				}
				return itemsAndIDs.get(listingID).lowestBiddingPrice();
			}
		}
	}

	/**
	 * Check whether an <code>Item</code> has been bid upon yet
	 * 
	 * @param listingID
	 *            Unique ID of the <code>Item</code>
	 * @return True if there is no bid or the <code>Item</code> does not exist,
	 *         false otherwise
	 */
	
	/**
	 * Precondition: listingID >= 0, highestBids, itemsAndIDs, itemsUpForBidding are not null
	 * Postcondition: return true or false
	 * Exception: Nothing
	 */
	public Boolean itemUnbid(int listingID) {
		// check if the item exists
		if (!itemsAndIDs.containsKey(listingID)) {
			return true;
		}
		Item item = itemsAndIDs.get(listingID);
		synchronized(itemsUpForBidding) {
		// currently open for bidding but no one gives valid bid
			if (itemsUpForBidding.contains(item) && !highestBids.containsKey(listingID)){
					return true;
			}
		}
		return false;
	}
}

package com.example.zensah;


public class Entity {
    private String id; 
    private String Store;
    private String ImageUrl;
    private String Price;
    private String PurchaseUrl;
    //private String Date;
    private String Title;
    private String Type;
    public boolean loaded = false;
    public Entity(String imageUrl, String type, String price) {
    	this.ImageUrl = imageUrl;
    	this.Price = price;
    	this.Type = type;
    }
    public Entity() {
    	System.out.println("Nada in entity");
    }
	public Entity(String id, String store, String imageUrl,String price,
			String purchaseUrl, String type, String Title) {
		super();
		this.id = id;
		this.Store = store;
		this.ImageUrl = imageUrl;
		this.PurchaseUrl = purchaseUrl;
		this.Price = price;
		this.Type = type;
		this.Title = Title;
	}
	
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStore() {
		return Store;
	}
	public void setStore(String store) {
		Store = store;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public String getPurchaseUrl() {
		return PurchaseUrl;
	}
	public void setPurchaseUrl(String purchaseUrl) {
		PurchaseUrl = purchaseUrl;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
}

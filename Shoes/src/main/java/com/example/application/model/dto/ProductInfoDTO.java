package com.example.application.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO {
    private String id;
    private String name;
    private String slug;
    private Long price;
    private int views;
    private String images;
    private int totalSold;
    private Long promotionPrice;

    public ProductInfoDTO(String id, String name, String slug, long price, int views, String images, int totalSold) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.views = views;
        this.images = images;
        this.totalSold = totalSold;
    }
    
    public Long getPrice() {
        return price;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(int totalSold) {
		this.totalSold = totalSold;
	}

	public Long getPromotionPrice() {
		return promotionPrice;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
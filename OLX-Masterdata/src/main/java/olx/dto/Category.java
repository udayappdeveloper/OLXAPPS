package olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Category model")

public class Category {
	@ApiModelProperty("Uniques identifier of the Category")
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ApiModelProperty("Category Name")
	private String category;
	public Category() {
		
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", catgory=" + category + "]";
	}
	public Category(int id, String catgory) {
		super();
		this.id = id;
		this.category = catgory;
	}
	public String getCatgory() {
		return category;
	}
	public void setCatgory(String catgory) {
		this.category = catgory;
	}

}

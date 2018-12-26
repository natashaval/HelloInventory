package hello.inven.helloinven.dto;

public class ItemSerialCount {
    private Long itemId;
    private String itemName;
    private String categoryName;
    private Long empId;
    private Long countSerial;

    public ItemSerialCount(Long itemId, String itemName, String categoryName, Long empId, Long countSerial) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.empId = empId;
        this.countSerial = countSerial;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCountSerial() {
        return countSerial;
    }

    public void setCountSerial(Long countSerial) {
        this.countSerial = countSerial;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

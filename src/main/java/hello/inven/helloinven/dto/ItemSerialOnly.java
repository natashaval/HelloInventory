package hello.inven.helloinven.dto;

public class ItemSerialOnly {
    private Long serialId;
    private Long clerkId;
    private Long itemId;
    private String itemName;
    private Long myUserId;
    private String myUserName;

    public ItemSerialOnly(Long serialId, Long clerkId, Long itemId, String itemName, Long myUserId, String myUserName) {
        this.serialId = serialId;
        this.clerkId = clerkId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.myUserId = myUserId;
        this.myUserName = myUserName;
    }

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Long getClerkId() {
        return clerkId;
    }

    public void setClerkId(Long clerkId) {
        this.clerkId = clerkId;
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

    public Long getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(Long myUserId) {
        this.myUserId = myUserId;
    }

    public String getMyUserName() {
        return myUserName;
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }
}

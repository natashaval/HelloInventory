package hello.inven.helloinven.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "asset") // user many-to-many item
// mapping the join table using composite-ID class (AssetId)
@AssociationOverrides({ // override the mapping for MyUser and Item for composite key
        @AssociationOverride(name = "primaryKey.myUser", joinColumns = @JoinColumn(name = "emp_id")),
        @AssociationOverride(name = "primaryKey.item", joinColumns = @JoinColumn(name = "item_id"))
        // AssociationOverride namanya harus sama dengan yang private AssetId primaryKey
})
public class Asset implements Serializable {

    @EmbeddedId // embedding composite-id as primary key
    private AssetId primaryKey = new AssetId();

    // additional field - menyimpan serial number dari barang yang digunakan oleh User
    @Column(name = "serial_number", nullable = false, length = 20)
    private String serialNumber;

//    @EmbeddedId // embedding composite-id as primary key
    public AssetId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(AssetId primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Transient
    public MyUser getMyUser(){
        return getPrimaryKey().getMyUser();
    }

    public void setMyUser(MyUser myUser){
        getPrimaryKey().setMyUser(myUser);
    }

    @Transient
    public Item getItem(){
        return getPrimaryKey().getItem();
    }

    public void setItem(Item item){
        getPrimaryKey().setItem(item);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}

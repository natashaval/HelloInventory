package hello.inven.helloinven.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

//https://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/
//        https://huongdanjava.com/many-many-relationship-extra-columns-jpa.html
//        https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
//https://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example

@Embeddable // so this class can be embedded in other entities, to hold composite entity identifier
public class AssetId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    private MyUser myUser;

//    @ManyToOne(cascade = CascadeType.ALL)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetId assetId = (AssetId) o;
        return Objects.equals(item, assetId.item) &&
                Objects.equals(myUser, assetId.myUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, myUser);
    }
}

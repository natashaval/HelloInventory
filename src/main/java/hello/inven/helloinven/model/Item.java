package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item extends AuditModel {

//    https://www.codejava.net/frameworks/hibernate/hibernate-enum-type-mapping-example
    public enum ItemType {
        ITEM, ASSET
    }

    @Id
    @Column(name = "item_id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Transient
//    https://www.quora.com/What-is-Transient-in-Hibernate-What-is-use-of-this
    private MultipartFile image;

    @Column
    private String imagePath;

    @Enumerated(EnumType.ORDINAL)
    private ItemType itemType;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    @Column
    private Double weight;
    @Column
    private Double height;
    @Column
    private Double width;
    @Column
    private Double depth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    // nullable = true, karena jika category nya dihapus item masih ada

//    https://grokonez.com/json/resolve-json-infinite-recursion-problems-working-jackson
//    @JsonIgnore
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryKey.item", cascade = CascadeType.ALL)
    private Set<Asset> assets = new HashSet<Asset>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    @OneToMany(mappedBy = "primaryKey.item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
}

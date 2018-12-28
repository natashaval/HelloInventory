package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

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

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryKey.item", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<Asset> assets = new HashSet<Asset>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnoreProperties(value = {"item", "myUser"})
    @JsonIgnore
    private List<ItemSerial> itemSerials;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actionItemId.item", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JsonIgnoreProperties(value = {"item", "actionTransaction"})
//    @JsonIgnoreProperties(value = "actionItemId")
    @JsonIgnore
    private List<ActionItem> actionItemList = new ArrayList<>();
}

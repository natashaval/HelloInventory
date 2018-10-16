package hello.inven.helloinven.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends AuditModel {

    @Id
    @GeneratedValue
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "category_description", columnDefinition = "text")
    private String description;

//    @OneToMany(mappedBy = "item")
//    private List<Item> items = new ArrayList<Item>();

    public Integer getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

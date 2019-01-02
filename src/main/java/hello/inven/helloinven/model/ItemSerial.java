package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "item_serial")
public class ItemSerial {
    @Id
    @Column(name = "serial_id", unique = true)
    private Long serialId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties(value = "itemSerials", allowSetters = true)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_id")
    @JsonIgnoreProperties(value = {"userItemSerials", "actionTransactions"})
    private MyUser myUser;

    @Column(name = "clerk_id")
    private Long clerkId; // clerk yang bertugas menghandle barang tersebut
}

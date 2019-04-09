package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class AuditModel implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
//    @Temporal is a JPA annotation which can be used to store only TIME (java.sql.Time), DATE(java.sql.Date) or TIMESTAMP (java.sql.Timestamp) in the database table. Generally when we declare a Date field in the class and try to store it. It will store the timestamp in the database.
//    @Temporal is a JPA annotation that converts back and forth between timestamp and java.util.Date. It also converts time-stamp into time
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;
}

package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.kitchen.booting.domain.id.IngredientId;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="report")
@Table(name="tbl_report")
public class Report{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "reporter_user_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reportee_user_id")
    private User reportee;

    @CreationTimestamp
    private Date reportDate;

    public User getReporter(){
        return reporter;
    }
    public void setReporter(User reporter)
    {
        this.reporter = reporter;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public User getReportee() {
        return reportee;
    }

    public void setReportee(User reportee) {
        this.reportee = reportee;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public String toString() {
        return "Report{" +
                "seq=" + seq +
                ", reporter=" + reporter +
                ", reportee=" + reportee +
                '}';
    }
}

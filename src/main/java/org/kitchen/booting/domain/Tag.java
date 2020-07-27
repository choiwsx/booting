package org.kitchen.booting.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "tag")
@Table(name = "tbl_tag")
public class Tag {
    @Id
    @Column(name = "tag_no", updatable = false, nullable = false)
    private Long tagNo;
    private String content;

}
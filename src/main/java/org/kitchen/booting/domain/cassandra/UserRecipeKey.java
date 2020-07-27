//package org.kitchen.booting.domain;
//
//import org.springframework.data.cassandra.core.cql.Ordering;
//import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
//import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
//import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.UUID;
//
//@PrimaryKeyClass
//public class UserRecipeKey implements Serializable {
//    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
//    private UUID userUuid;
//    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
//    private Date regDate;
//    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
//    private String title;
//    @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.CLUSTERED)
//    private String thumbnail;
//    @PrimaryKeyColumn(ordinal = 4, type = PrimaryKeyType.CLUSTERED)
//    private Integer like;
//    @PrimaryKeyColumn(ordinal = 5, type = PrimaryKeyType.CLUSTERED)
//    private Long recipeNo;
//}

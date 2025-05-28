package com.rrecom.ecomsoft.io;



import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
@Data
@Builder
public class CategoryResponse {
    private  String categoryId;
    private String name;
    private String description;
    private String bgColor;
    private Timestamp createdAt;
    private Timestamp updateAt;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imgUrl;
    private Integer items;
}

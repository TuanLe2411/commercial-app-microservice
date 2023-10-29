package com.commercial.app.shop_service.models;

import com.commercial.app.shop_service.dtos.UpdateShopInfoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "shops")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Shop {
    @Id
    private String id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(unique = true)
    private String ownerId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createAt;

    public boolean isOwner(String userId) {
        return this.ownerId.equals(userId);
    }

    public void updateFromDto(UpdateShopInfoDto updateShopInfoDto) {
        if(updateShopInfoDto.getOwnerId() != null) {
            this.setOwnerId(updateShopInfoDto.getOwnerId());
        }
        if(updateShopInfoDto.getName() != null) {
            this.setName(updateShopInfoDto.getName());
        }
    }
}

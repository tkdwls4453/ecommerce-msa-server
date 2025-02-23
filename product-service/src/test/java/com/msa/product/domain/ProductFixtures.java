package com.msa.product.domain;

import com.msa.common.vo.Money;
import com.msa.product.adapter.in.web.dto.CreateShoesRequest;
import com.msa.product.adapter.in.web.dto.ShoesDto;
import com.msa.product.adapter.out.persistence.ShoesEntity;
import com.msa.product.adapter.out.persistence.ShoesModelEntity;
import com.msa.product.domain.vo.Color;
import com.msa.product.domain.vo.Quantity;
import com.msa.product.domain.vo.ShoesName;
import com.msa.product.domain.vo.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductFixtures {

    public static CreateShoesRequest createShoesRequest(String shoeName, Integer price) {

        return CreateShoesRequest.builder()
            .shoesName(shoeName)
            .price(price)
            .shoesList(shoesDtoList())
            .build();
    }

    public static ShoesModel shoesModel(Long modelId, String shoesName, int price) {

        return ShoesModel.builder()
            .modelId(modelId)
            .shoesName(new ShoesName(shoesName))
            .price(new Money(price))
            .shoesList(shoesDtoList().stream()
                .map(ShoesDto::toDomain)
                .toList()
            )
            .build();
    }
    public static List<ShoesDto> shoesDtoList(){
        List<ShoesDto> shoesDtoList = new ArrayList<>();
        shoesDtoList.add(new ShoesDto(1L, 260, "BLACK", 10));
        shoesDtoList.add(new ShoesDto(2L, 270, "WHITE", 5));
        return shoesDtoList;
    }

    public static List<ShoesEntity> shoesEntityList(){
        List<ShoesEntity> shoesEntityList = new ArrayList<>();
        shoesEntityList.add(
            ShoesEntity.builder()
                .shoesId(1L)
                .size(Size.fromInt(260))
                .color(Color.BLACK)
                .quantity(10)
                .build()
        );
        shoesEntityList.add(
            ShoesEntity.builder()
                .shoesId(2L)
                .size(Size.fromInt(270))
                .color(Color.WHITE)
                .quantity(5)
                .build()
        );
        return shoesEntityList;
    }

    public static List<ShoesEntity> nonSavedShoesEntityList(){
        List<ShoesEntity> shoesEntityList = new ArrayList<>();
        shoesEntityList.add(
            ShoesEntity.builder()
                .size(Size.fromInt(260))
                .color(Color.BLACK)
                .quantity(10)
                .build()
        );
        shoesEntityList.add(
            ShoesEntity.builder()
                .size(Size.fromInt(270))
                .color(Color.WHITE)
                .quantity(5)
                .build()
        );
        return shoesEntityList;
    }

    public static List<Shoes> shoesList(){
        List<Shoes> shoesList = new ArrayList<>();
        shoesList.add(
            Shoes.builder()
                .shoesId(1L)
                .size(Size.fromInt(260))
                .color(Color.BLACK)
                .quantity(new Quantity(10))
                .build()
        );
        shoesList.add(
            Shoes.builder()
                .shoesId(2L)
                .size(Size.fromInt(270))
                .color(Color.WHITE)
                .quantity(new Quantity(5))
                .build()
        );
        return shoesList;
    }

    public static ShoesModelEntity shoesModelEntity(long modelId, String shoesName, int price) {

        ShoesModelEntity shoesModelEntity = ShoesModelEntity.builder()
            .modelId(modelId)
            .shoesName(shoesName)
            .price(new BigDecimal(price))
            .build();

        for(ShoesDto shoesDto : shoesDtoList()){
            ShoesEntity shoesEntity = ShoesEntity.builder()
                .shoesId(shoesDto.shoesId())
                .size(Size.fromInt(shoesDto.size()))
                .color(Color.valueOf(shoesDto.color()))
                .quantity(shoesDto.quantity())
                .build();

            shoesModelEntity.addShoe(shoesEntity);
        }

        return shoesModelEntity;
    }

}

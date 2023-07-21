package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ReviewImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg, ReviewImgDto.class);
    }
}

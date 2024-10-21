package com.example.productdisplayapp.mapper

import com.example.domain.model.BannerEntity
import com.example.domain.model.ComponentEntity
import com.example.domain.model.ContentEntity
import com.example.domain.model.FooterEntity
import com.example.domain.model.GoodsEntity
import com.example.domain.model.HeaderEntity
import com.example.domain.model.StyleEntity
import com.example.productdisplayapp.uimodel.BannerUiModel
import com.example.productdisplayapp.uimodel.ComponentUiModel
import com.example.productdisplayapp.uimodel.ContentUiModel
import com.example.productdisplayapp.uimodel.FooterUiModel
import com.example.productdisplayapp.uimodel.GoodsUiModel
import com.example.productdisplayapp.uimodel.HeaderUiModel
import com.example.productdisplayapp.uimodel.StyleUiModel
import javax.inject.Inject

class ComponentUiMapper @Inject constructor() {
    
    fun mapToComponentUiModel(componentList: List<ComponentEntity>): List<ComponentUiModel> =
        componentList.map { component ->
            ComponentUiModel(
                contentType = component.contentType,
                contentList = mapToContentUiModelList(component.contentList),
                headerUiModel = mapToHeaderUiModel(component.headerEntity),
                footerUiModel = mapToFooterUiModel(component.footerEntity)
            )
        }

    private fun mapToContentUiModelList(contentList: List<ContentEntity>): List<ContentUiModel> =
        contentList.map { content ->
            when(content) {
                is BannerEntity -> BannerUiModel(
                    linkURL = content.linkURL,
                    thumbnailURL = content.thumbnailURL,
                    title = content.title,
                    description = content.description,
                    keyword = content.keyword
                )
                is GoodsEntity -> GoodsUiModel(
                    linkURL = content.linkURL,
                    thumbnailURL = content.thumbnailURL,
                    brandName = content.brandName,
                    price = content.price,
                    saleRate = content.saleRate,
                    hasCoupon = content.hasCoupon,
                )
                is StyleEntity -> StyleUiModel(
                    linkURL = content.linkURL,
                    thumbnailURL = content.thumbnailURL
                )
            }
        }

    private fun mapToHeaderUiModel(headerEntity: HeaderEntity): HeaderUiModel =
        HeaderUiModel(
            title = headerEntity.title,
            iconURL = headerEntity.iconURL,
            linkURL = headerEntity.linkURL
        )

    private fun mapToFooterUiModel(footerEntity: FooterEntity): FooterUiModel =
        FooterUiModel(
            title = footerEntity.title,
            iconURL = footerEntity.iconURL,
            footerType = footerEntity.footerType // TODO : 타입
        )
}
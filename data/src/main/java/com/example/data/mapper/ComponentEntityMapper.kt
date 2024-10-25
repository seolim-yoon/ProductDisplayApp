package com.example.data.mapper

import com.example.data.dto.ComponentDTO
import com.example.data.dto.Contents
import com.example.data.dto.Footer
import com.example.data.dto.Header
import com.example.domain.model.BannerEntity
import com.example.domain.model.ComponentEntity
import com.example.domain.model.ContentEntity
import com.example.domain.model.FooterEntity
import com.example.domain.model.GoodsEntity
import com.example.domain.model.HeaderEntity
import com.example.domain.model.StyleEntity
import com.example.domain.util.ContentType
import com.example.domain.util.FooterType
import javax.inject.Inject

class ComponentEntityMapper @Inject constructor() {

    fun mapToComponentEntity(componentList: List<ComponentDTO.Component?>?): List<ComponentEntity> =
        componentList?.mapNotNull { component ->
            ComponentEntity(
                contentType = ContentType.fromValue(component?.contents?.type.orEmpty()),
                contentList = mapToContentEntityList(component?.contents),
                headerEntity = mapToHeaderEntity(component?.header),
                footerEntity = mapToFooterEntity(component?.footer)
            )
        } ?: listOf()

    private fun mapToContentEntityList(contents: Contents?): List<ContentEntity> {
        return when(ContentType.fromValue(contents?.type.orEmpty())) {
            ContentType.BANNER -> mapToBannerEntityList(contents?.banners)
            ContentType.GRID, ContentType.SCROLL -> mapToGoodsEntityList(contents?.goods)
            ContentType.STYLE -> mapToStyleEntityList(contents?.styles)
            else -> listOf()
        }
    }

    private fun mapToHeaderEntity(header: Header?): HeaderEntity =
        HeaderEntity(
            title = header?.title.orEmpty(),
            iconURL = header?.iconURL.orEmpty(),
            linkURL = header?.linkURL.orEmpty()
        )

    private fun mapToFooterEntity(footer: Footer?): FooterEntity =
        FooterEntity(
            title = footer?.title.orEmpty(),
            iconURL = footer?.iconURL.orEmpty(),
            footerType = FooterType.fromValue(footer?.type.orEmpty())
        )

    private fun mapToBannerEntityList(bannerList: List<Contents.Banner?>?): List<BannerEntity> =
        bannerList?.mapNotNull { banner ->
            BannerEntity(
                linkURL = banner?.linkURL.orEmpty(),
                thumbnailURL = banner?.thumbnailURL.orEmpty(),
                title = banner?.title.orEmpty(),
                description = banner?.description.orEmpty(),
                keyword = banner?.keyword.orEmpty()
            )
        } ?: listOf()

    private fun mapToGoodsEntityList(goodsList: List<Contents.Goods?>?): List<GoodsEntity> =
        goodsList?.mapNotNull { goods ->
            GoodsEntity(
                linkURL = goods?.linkURL.orEmpty(),
                thumbnailURL = goods?.thumbnailURL.orEmpty(),
                brandName = goods?.brandName.orEmpty(),
                price = goods?.price ?: 0,
                saleRate = goods?.saleRate ?: 0,
                hasCoupon = goods?.hasCoupon ?: false
            )
        } ?: listOf()

    private fun mapToStyleEntityList(styleList: List<Contents.Style?>?): List<StyleEntity> =
        styleList?.mapNotNull { style ->
            StyleEntity(
                linkURL = style?.linkURL.orEmpty(),
                thumbnailURL = style?.thumbnailURL.orEmpty()
            )
        } ?: listOf()
}
package com.example.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shortlink.project.common.convention.exception.ServiceException;
import com.example.shortlink.project.dao.entity.ShortLinkDO;
import com.example.shortlink.project.dao.mapper.ShortLinkMapper;
import com.example.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.example.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.example.shortlink.project.service.ShortLinkService;
import com.example.shortlink.project.toolkit.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * 短链接接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    private final RBloomFilter<String> shortUriCachePenetrationBloomFilter;


    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam) {

        String shortLinkSuffix = generateSuffix(requestParam);
        ShortLinkDO shortLinkDO = BeanUtil.toBean(requestParam, ShortLinkDO.class);
        shortLinkDO.setShortUri(shortLinkSuffix);
        String fullShortUrl = requestParam.getDomain() + "/" + shortLinkSuffix;
        shortLinkDO.setFullShortUrl(fullShortUrl);
        try{
            baseMapper.insert(shortLinkDO);
        }catch(DuplicateKeyException ex) {
            //TODO 已经误判的短链接如何处理
            //短链接真实存在
            //短链接不一定存在
            log.warn("短链接{}:重复入库", fullShortUrl);
            throw new ServiceException("短链接生成重复");
        }

        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getShortUri())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .build();
    }


    public String generateSuffix(ShortLinkCreateReqDTO requestParam) {
        int customGenerate = 0;
        String shortUri ;
        while(true) {
            if(customGenerate > 10) {
                throw new RuntimeException("短链接频繁失效，请重试");
            }
            String originUrl = requestParam.getOriginUrl();
            shortUri = HashUtil.hashToBase62(originUrl);
            if(!shortUriCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shortUri)){
                break;
            }
            customGenerate ++;
        }
        return shortUri;
    }
}

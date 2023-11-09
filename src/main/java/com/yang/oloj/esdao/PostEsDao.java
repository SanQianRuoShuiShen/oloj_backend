package com.yang.oloj.esdao;

import com.yang.oloj.model.dto.post.PostEsDTO;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/SanQianRuoShuiShen">三千弱水</a>
 * @from  
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);
}
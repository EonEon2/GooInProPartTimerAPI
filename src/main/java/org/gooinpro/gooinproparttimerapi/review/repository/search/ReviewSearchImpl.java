package org.gooinpro.gooinproparttimerapi.review.repository.search;

import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.review.domain.QReviewEntity;
import org.gooinpro.gooinproparttimerapi.review.domain.ReviewEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(ReviewEntity.class);
    }

    @Override
    public List<ReviewEntity> search(String keyword) {
        QReviewEntity review = QReviewEntity.reviewEntity;

        JPQLQuery<ReviewEntity> query = from(review);

        query.where(review.rdelete.eq(false));

        if(StringUtils.hasText(keyword)) {
            query.where(review.rcontent.contains(keyword));
        }

        query.orderBy(review.rregdate.desc());

        return query.fetch();
    }
}
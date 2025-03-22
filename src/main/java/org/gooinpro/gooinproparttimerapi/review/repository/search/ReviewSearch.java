package org.gooinpro.gooinproparttimerapi.review.repository.search;

import org.gooinpro.gooinproparttimerapi.review.domain.ReviewEntity;
import java.util.List;

public interface ReviewSearch {
    List<ReviewEntity> search(String keyword, Long eno, Long pno);
}
package org.gooinpro.gooinproparttimerapi.parttimerimage.repository.search;

import org.gooinpro.gooinproparttimerapi.parttimerimage.domain.PartTimerImageEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.domain.QPartTimerImageEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PartTimerImageSearchImpl extends QuerydslRepositorySupport implements PartTimerImageSearch {

    public PartTimerImageSearchImpl() {
        super(PartTimerImageEntity.class); // 엔티티 클래스 지정
    }

    @Override
    public List<String> getPartTimerImages(Long pno) {
        QPartTimerImageEntity image = QPartTimerImageEntity.partTimerImageEntity;
        return from(image)
                .where(image.partTimer.pno.eq(pno))
                .select(image.pifilename)
                .fetch();
    }
}
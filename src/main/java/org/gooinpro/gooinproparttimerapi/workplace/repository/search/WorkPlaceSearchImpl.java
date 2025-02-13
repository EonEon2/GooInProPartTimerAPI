package org.gooinpro.gooinproparttimerapi.workplace.repository.search;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.employer.domain.QEmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.workplace.domain.QWorkPlaceEntity;
import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;
import org.gooinpro.gooinproparttimerapi.workplace.dto.WorkPlaceListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class WorkPlaceSearchImpl extends QuerydslRepositorySupport implements WorkPlaceSearch {

    public WorkPlaceSearchImpl() {
        super(WorkPlaceEntity.class);
    }

    @Override
    public PageResponseDTO<WorkPlaceListDTO> WorkPlaceList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize());

        QWorkPlaceEntity work = QWorkPlaceEntity.workPlaceEntity;

        JPQLQuery<WorkPlaceEntity> query = from(work);

        query.where(work.wdelete.isFalse());


        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<WorkPlaceListDTO> tupleQuery = query.select(
                Projections.bean(WorkPlaceListDTO.class,
                        work.wpno,
                        work.wroadAddress,
                        work.wdetailAddress,
                        work.wlati,
                        work.wlong,
                        work.wdelete
                )
        );

        List<WorkPlaceListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<WorkPlaceListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}

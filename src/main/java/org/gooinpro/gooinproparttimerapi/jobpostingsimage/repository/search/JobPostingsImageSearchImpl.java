package org.gooinpro.gooinproparttimerapi.jobpostingsimage.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.domain.JobPostingsImageEntity;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.domain.QJobPostingsImageEntity;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.dto.JobPostingsImageDetailDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsInDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class JobPostingsImageSearchImpl extends QuerydslRepositorySupport implements JobPostingsImageSearch {

    public JobPostingsImageSearchImpl() {
        super(JobPostingsImageEntity.class);
    }


    @Override
    public JobPostingsImageDetailDTO getJobPostingsImage(Long jpno) {

        QJobPostingsImageEntity jimage = QJobPostingsImageEntity.jobPostingsImageEntity;

        JPQLQuery<JobPostingsImageEntity> query = from(jimage);

        query.where(jimage.jpno.jpno.eq(jpno));

        List<String> filenames = query.select(jimage.jpifilename).fetch();  // List<String> 형태로 반환

        JobPostingsImageDetailDTO result = new JobPostingsImageDetailDTO();
        result.setJpifilename(filenames);

        return result;
    }
}

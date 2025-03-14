package org.gooinpro.gooinproparttimerapi.jobpostingsimage.repository.search;

import org.gooinpro.gooinproparttimerapi.jobpostingsimage.dto.JobPostingsImageDetailDTO;

import java.util.List;

public interface JobPostingsImageSearch {

    JobPostingsImageDetailDTO getJobPostingsImage(Long jpno);
}

package org.gooinpro.gooinproparttimerapi.jobmatchings;

import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobmatchings.repository.JobMatchingsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // db로 테스트?
public class JobMatchingsTests {

    @Autowired
    private JobMatchingsRepository jobMatchingsRepository;


    @Test
    @Transactional
    @Commit
    public void testWorkmatching() {
        Long pno = 17L;
        Long jpno = 11L;
        Timestamp result = jobMatchingsRepository.findWorkStartTime(pno,jpno);
        log.info(result);
    }

}

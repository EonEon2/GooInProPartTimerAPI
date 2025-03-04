package org.gooinpro.gooinproparttimerapi.worklogs;

import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsTimeDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.WorkLogsRepository;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.search.WorkLogsSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // db로 테스트?
public class WorkLogsTest {

    @Autowired
    private WorkLogsRepository workLogsRepository;


    @Test
    @Transactional
    @Commit
    public void testWorkLogs() {
        Long pno = 17L;
        Long jmno = 1L;

        Optional<Timestamp> result = workLogsRepository.findTodayStartByPnoAndJmno(pno, jmno);

        log.info("----------");
        log.info(result);


    }
}

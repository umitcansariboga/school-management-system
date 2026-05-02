package com.ucs.repository.business;

import com.ucs.entity.concretes.business.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {

    List<Meet> findByAdvisoryTeacher_Id(Long advisoryTeacherId);

    List<Meet> findByStudentList_Id(Long studentId);

    Page<Meet> findByAdvisoryTeacher_Id(Long advisoryTeacherId, Pageable pageable);

    Page<Meet> findByAdvisoryTeacher_IdGreaterThan(Long advisoryTeacherId, Pageable pageable);

    Page<Meet> findAllMeetByAdvisorTeacherId(Long advisorTeacherId,Pageable pageable);
}

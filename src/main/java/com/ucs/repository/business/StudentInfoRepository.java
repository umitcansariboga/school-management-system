package com.ucs.repository.business;

import com.ucs.entity.concretes.business.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {

    List<StudentInfo> findByStudent_Id(Long studentId);

    Page<StudentInfo> findByTeacher_Username(String username, Pageable pageable);

    Page<StudentInfo> findByStudent_Username(String username, Pageable pageable);
}

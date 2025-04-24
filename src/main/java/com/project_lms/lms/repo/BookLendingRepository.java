package com.project_lms.lms.repo;

import com.project_lms.lms.entity.BookLending;
import com.project_lms.lms.entity.User;
import com.project_lms.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookLendingRepository extends JpaRepository<BookLending, Long> {
    List<BookLending> findByUser(User user);
    List<BookLending> findByBook(Book book);
    List<BookLending> findByStatus(String status);
}
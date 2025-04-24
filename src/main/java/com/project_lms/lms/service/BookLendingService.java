package com.project_lms.lms.service;

import com.project_lms.lms.dto.BookLendingDTO;
import java.util.List;

public interface BookLendingService {
    BookLendingDTO issueBook(BookLendingDTO bookLendingDTO);
    BookLendingDTO returnBook(Long lendingId);
    List<BookLendingDTO> getLendingsByUser(Long userId);
    List<BookLendingDTO> getLendingsByBook(Long bookId);
    List<BookLendingDTO> getAllLendings();
    BookLendingDTO getLendingById(Long id);
}
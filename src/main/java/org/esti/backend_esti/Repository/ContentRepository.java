package org.esti.backend_esti.Repository;

import org.esti.backend_esti.Entity.Content;
//import com.oztotipac.org.Entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    public List<Content> findByState(boolean state);
    List<Content> findByCategory(String category);
    List<Content> findByCategoryAndStateTrue(String category);
    List<Content> findByCategoryAndState(String category, boolean state);
    List<Content> findByDeletedAtIsNull();
    List<Content> findByCategoryIgnoreCase(String category);
    int countByCategoryIgnoreCaseAndDeletedAtIsNull(String category);
}
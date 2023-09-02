package org.berka.Repository;

import org.berka.Repository.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkRepository extends JpaRepository<Work,Long> {

    boolean existsByTitle(String title);
}

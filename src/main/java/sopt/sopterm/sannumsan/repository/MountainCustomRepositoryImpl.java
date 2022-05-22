package sopt.sopterm.sannumsan.repository;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class MountainCustomRepositoryImpl implements MountainCustomRepository {

    @Override
    public List<Long> getIdList(Long count) {
        List<Long> idList = new ArrayList<Long>();
        while (idList.size() < 5) {
            Long newId = (long) (Math.random() * count) + 1;
            if (idList.indexOf(newId) == -1) {
                idList.add(newId);
            }
        }
        return idList;
    }
}
